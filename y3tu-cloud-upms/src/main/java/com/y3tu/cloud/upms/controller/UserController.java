package com.y3tu.cloud.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.y3tu.cloud.common.constants.ServerNameConstants;
import com.y3tu.cloud.common.enums.UserStatusEnum;
import com.y3tu.cloud.common.util.UserUtil;
import com.y3tu.cloud.common.vo.ResourceVO;
import com.y3tu.cloud.common.vo.RoleVO;
import com.y3tu.cloud.common.vo.UserVO;
import com.y3tu.cloud.log.starter.annotation.Log;
import com.y3tu.cloud.log.starter.constant.SaveModeEnum;
import com.y3tu.cloud.upms.model.dto.UserDTO;
import com.y3tu.cloud.upms.model.entity.*;
import com.y3tu.cloud.upms.service.*;
import com.y3tu.tool.core.bean.BeanUtil;
import com.y3tu.tool.core.bean.copier.CopyOptions;
import com.y3tu.tool.core.collection.CollectionUtil;
import com.y3tu.tool.core.date.DateUtil;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.core.util.ObjectUtil;
import com.y3tu.tool.core.util.StrUtil;
import com.y3tu.tool.web.annotation.MethodMapping;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.tool.web.base.pojo.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/user")
@Slf4j
@Api(description = "用户接口")
public class UserController extends BaseController<UserService, User> {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private HttpServletRequest request;

    /**
     * 根据传入的token解析获取用户
     *
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录用户接口")
    public R getUserInfo() {
        String userId = UserUtil.getUserId(request);
        return R.success(userService.findUserById(userId));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(value = "注册用户")
    public R register(@ModelAttribute User u,
                      @RequestParam String verify,
                      @RequestParam String captchaId) {

        if (StrUtil.isBlank(verify) || StrUtil.isBlank(u.getUsername())
                || StrUtil.isBlank(u.getPassword())) {
            return R.warn("缺少必需表单字段");
        }
        //从redis中获取验证码
        String code = redisTemplate.opsForValue().get(captchaId).toString();
        if (StrUtil.isBlank(code)) {
            return R.warn("验证码已过期，请重新获取");
        }
        if (!verify.toLowerCase().equals(code.toLowerCase())) {
            log.error("注册失败，验证码错误：code:" + verify + ",redisCode:" + code.toLowerCase());
            return R.warn("验证码输入错误");
        }
        User user = userService.getOne(new QueryWrapper<User>().eq("username", u.getUsername()));
        if (ObjectUtil.isNotNull(user)) {
            return R.warn("该用户名已被注册");
        }

        //密码加密
        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        //普通用户
        //u.setType(CommonConstants.USER_TYPE_NORMAL);
        userService.save(u);

        // 默认角色
        List<Role> roleList = roleService.list(new QueryWrapper<Role>().eq("default_role", true));
        if (roleList != null && roleList.size() > 0) {
            for (Role role : roleList) {
                UserRole ur = new UserRole();
                ur.setUserId(user.getId());
                ur.setRoleId(role.getId());
                userRoleService.save(ur);
            }
        }
        return R.success();
    }


    @PostMapping(value = "/unlock")
    @ApiOperation(value = "解锁验证密码")
    public R unLock(@RequestBody UserDTO userDTO) {

        User user = userService.getById(userDTO.getId());
        if (!new BCryptPasswordEncoder().matches(userDTO.getPassword(), user.getPassword())) {
            return R.warn("密码不正确");
        }
        return R.success();
    }

    /**
     * 获取用户分页数据
     *
     * @param pageInfo
     * @return
     */
    @Override
    @ApiOperation(value = "多条件分页获取用户列表")
    @MethodMapping(method = RequestMethod.POST)
    @Log(serviceId = ServerNameConstants.UPMS_SERVER, moduleName = "User", actionName = "多条件分页获取用户列表", saveMode = SaveModeEnum.ES)
    public R page(@RequestBody PageInfo pageInfo) {

        PageInfo<User> page = userService.page(pageInfo);
        List<UserVO> userVOS = new ArrayList<>();
        for (User user : page.getRecords()) {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            // 关联角色
            List<Role> roles = userRoleService.findByUserId(user.getId());
            List<RoleVO> roleVOS = roles.stream().map(role -> {
                RoleVO roleVO = new RoleVO();
                BeanUtils.copyProperties(role, roleVO);
                return roleVO;
            }).collect(Collectors.toList());

            userVO.setRoles(roleVOS);
            //关联资源权限
            if (roles.size() > 0) {
                Set<Resource> resources = resourceService.getResourceRoleCodes(roles.stream().map(role -> role.getRoleCode()).collect(Collectors.toList()));
                Set<ResourceVO> resourceVOS = resources.stream().map(resource -> {
                            ResourceVO resourceVO = new ResourceVO();
                            BeanUtils.copyProperties(resource, resourceVO);
                            return resourceVO;
                        }
                ).collect(Collectors.toSet());
                userVO.setResources(resourceVOS);
            }
            userVOS.add(userVO);
        }
        PageInfo pageInfoCopy = new PageInfo();
        BeanUtils.copyProperties(pageInfo, pageInfoCopy);
        pageInfoCopy.setRecords(userVOS);
        return R.success(pageInfoCopy);
    }

    @RequestMapping(value = "/disable/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "后台禁用用户")
    public R disable(@ApiParam("用户唯一id标识") @PathVariable String userId) {

        User user = userService.getById(userId);
        if (user == null) {
            return R.warn("通过userId获取用户失败");
        }
        user.setStatus(1);
        userService.updateById(user);
        return R.success();
    }

    @RequestMapping(value = "/enable/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "后台启用用户")
    public R enable(@ApiParam("用户唯一id标识") @PathVariable String userId) {

        User user = userService.getById(userId);
        if (user == null) {
            return R.warn("通过userId获取用户失败");
        }
        user.setStatus(UserStatusEnum.NORMAL.getCode());
        userService.updateById(user);
        return R.success();
    }

    @ApiOperation(value = "批量通过ids删除")
    @DeleteMapping(value = "/delByIds/{ids}")
    @Override
    public R delByIds(@PathVariable String[] ids) {
        for (String id : ids) {
            userService.removeById(id);
            //删除关联角色
            userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", id));
        }
        return R.success();
    }

    @PostMapping(value = "/save")
    @ApiOperation(value = "添加用户")
    public R save(@RequestBody UserDTO userDTO) {
        if (StrUtil.isBlank(userDTO.getUsername()) || StrUtil.isBlank(userDTO.getPassword())) {
            return R.error("缺少必需表单字段");
        }

        if (CollectionUtil.isNotEmpty(userService.list(new QueryWrapper<User>().eq("username", userDTO.getUsername())))) {
            return R.error("该用户名已被注册");
        }

        String encryptPass = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        userDTO.setPassword(encryptPass);
        userDTO.setCreateTime(DateUtil.date());
        userDTO.setDelFlag(0);
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        userService.save(user);
        addRole(userDTO.getRoles(), user.getId());
        return R.success(user);
    }

    @PutMapping(value = "/edit")
    public R edit(@RequestBody UserDTO user) {

        User old = userService.getById(user.getId());
        //若修改了用户名
        if (!old.getUsername().equals(user.getUsername())) {
            //判断新用户名是否存在
            if (userService.findByUsernameAndStatus(user.getUsername()) != null) {
                return R.error("该用户名已被存在");
            }
        }
        // 若修改了手机和邮箱判断是否唯一
        if (!old.getMobile().equals(user.getMobile()) && userService.findByMobile(user.getMobile()) != null) {
            return R.error("该手机号已绑定其他账户");
        }
        if (StrUtil.isNotEmpty(old.getEmail()) && StrUtil.isNotEmpty(user.getEmail())) {
            if (!old.getEmail().equals(user.getEmail()) && userService.findByMobile(user.getEmail()) != null) {
                return R.error("该邮箱已绑定其他账户");
            }
        }

        user.setPassword(old.getPassword());
        BeanUtil.copyProperties(user, old, new CopyOptions().setIgnoreNullValue(true));
        if (!userService.updateById(old)) {
            return R.error("修改失败");
        }

        //删除该用户角色
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", user.getId()));
        addRole(user.getRoles(), user.getId());
        return R.success();
    }


    /**
     * 根据用户id和角色id添加用户角色关联信息
     *
     * @param roleList
     * @param userId
     */
    private void addRole(List<Role> roleList, String userId) {
        if (CollectionUtil.isNotEmpty(roleList)) {
            //添加角色
            for (Role role : roleList) {
                UserRole ur = new UserRole();
                ur.setUserId(userId);
                ur.setRoleId(role.getId());
                userRoleService.save(ur);
            }
        }
    }


    /**
     * 通过用户名查询用户及其角色信息和权限
     *
     * @param username 用户名
     * @return UseVo 对象
     */
    @GetMapping("/findUserByUsername/{username}")
    public UserVO findUserByUsername(@PathVariable String username) {

        UserVO userVO = userService.findUserByUsername(username);
        if (userVO != null && userVO.getRoles().size() > 0) {
            List<String> roleCodes = userVO.getRoles().stream().map(role -> role.getRoleCode()).collect(Collectors.toList());
            //获取资源权限
            Set<Resource> resources = resourceService.getResourceRoleCodes(roleCodes);
            Set<ResourceVO> resourceVOS = resources.stream().map(resource -> {
                        ResourceVO resourceVO = new ResourceVO();
                        BeanUtils.copyProperties(resource, resourceVO);
                        return resourceVO;
                    }
            ).collect(Collectors.toSet());
            userVO.setResources(resourceVOS);
        }
        return userVO;
    }

    /**
     * 通过手机号查询用户及其角色信息
     *
     * @param mobile 手机号
     * @return UseVo 对象
     */
    @GetMapping("/findUserByMobile/{mobile}")
    public UserVO findUserByMobile(@PathVariable String mobile) {
        return userService.findUserByMobile(mobile);
    }

    /**
     * 通过OpenId查询
     *
     * @param openId openid
     * @return 对象
     */
    @GetMapping("/findUserByOpenId/{openId}")
    public UserVO findUserByOpenId(@PathVariable String openId) {
        return userService.findUserByOpenId(openId);
    }
}

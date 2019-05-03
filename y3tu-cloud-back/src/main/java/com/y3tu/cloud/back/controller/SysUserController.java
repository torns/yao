package com.y3tu.cloud.back.controller;

import com.y3tu.cloud.back.model.dto.SysUserInfoDTO;
import com.y3tu.cloud.back.model.entity.SysUser;
import com.y3tu.cloud.back.model.query.SysUserVoQuery;
import com.y3tu.cloud.back.service.SysUserService;
import com.y3tu.cloud.common.annotation.SysLog;
import com.y3tu.cloud.common.constants.SecurityConstants;
import com.y3tu.cloud.common.constants.ServiceNameConstants;
import com.y3tu.cloud.common.enums.ResponseCodeEnum;
import com.y3tu.cloud.common.enums.SmsMessageChannnelEnum;
import com.y3tu.cloud.common.enums.SmsTemplateEnum;
import com.y3tu.cloud.common.template.sms.SmsMessageTemplate;
import com.y3tu.cloud.common.util.UserUtil;
import com.y3tu.cloud.common.vo.SysUserVo;
import com.y3tu.tool.core.pojo.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@RestController
@RequestMapping("/user")
@Api(value = "用户controller", tags = {"用户操作接口"})
public class SysUserController {

    private static final String MODULE_NAME = "系统用户模块";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @SysLog(serviceId = ServiceNameConstants.USER_SERVICE, moduleName = MODULE_NAME, actionName = "根据token获取用户信息")
    @ApiOperation(value = "获取用户信息", notes = "用户详细信息，附带角色信息，权限信息", httpMethod = "GET")
    @GetMapping("/info")
    public R<SysUserInfoDTO> getInfo() {
        Integer userId = UserUtil.getUserId(request);
        List<String> roles = UserUtil.getRoles(request);
        return new R<>(sysUserService.getUserInfo(userId, roles));
    }

    @SysLog(serviceId = ServiceNameConstants.USER_SERVICE, moduleName = MODULE_NAME, actionName = "根据用户名获取用户信息")
    @ApiOperation(value = "根据用户名获取用户信息", notes = "用户详细信息，附带角色信息，权限信息", httpMethod = "GET")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "string")
    @GetMapping("/loadUserByUsername/{username}")
    public SysUserVo loadUserByUsername(@PathVariable(value = "username") String username) {
        return sysUserService.loadUserByUsername(username);
    }

    @ApiOperation(value = "根据mobile获取用户信息", notes = "用户详细信息，附带角色信息，权限信息", httpMethod = "GET")
    @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "string")
    @GetMapping("/loadUserByMobile/{mobile}")
    public SysUserVo loadUserByMobile(@PathVariable(value = "mobile") String mobile) {
        return sysUserService.loadUserByMobile(mobile);
    }

    @ApiOperation(value = "获取用户角色信息", notes = "根据token获取用户角色信息", httpMethod = "GET")
    @GetMapping("/roles")
    public R<List<String>> getRoles() {
        return new R<>(UserUtil.getRoles(request));
    }

    @SysLog(serviceId = ServiceNameConstants.USER_SERVICE, moduleName = MODULE_NAME, actionName = "用户信息分页查询")
    @ApiOperation(value = "获取用户信息 分页查询", notes = "用户信息分页查询", httpMethod = "GET")
    @ApiImplicitParam(name = "query", value = "用户信息查询条件", required = false, dataType = "SysUserVoQuery")
    @GetMapping("/page")
    public R<SysUserVoQuery> pageByQuery(SysUserVoQuery query) {
        return new R<>(sysUserService.pageUserVoByQuery(query));
    }

    @SysLog(serviceId = ServiceNameConstants.USER_SERVICE, moduleName = MODULE_NAME, actionName = "用户信息分页查询")
    @ApiOperation(value = "添加用户", notes = "添加用户信息  带角色信息", httpMethod = "POST")
    @ApiImplicitParam(name = "sysUserVo", value = "用户信息", required = true, dataType = "SysUserVo")
    @PostMapping
    public R<Boolean> save(@RequestBody SysUserVo sysUserVo) {
        return new R<>(sysUserService.save(sysUserVo));
    }

    @SysLog(serviceId = ServiceNameConstants.USER_SERVICE, moduleName = MODULE_NAME, actionName = "修改用户信息")
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息 带角色信息", httpMethod = "PUT")
    @ApiImplicitParam(name = "sysUserVo", value = "用户信息", required = true, dataType = "SysUserVo")
    @PutMapping
    public R<Boolean> update(@RequestBody SysUserVo sysUserVo) {
        return new R<>(sysUserService.update(sysUserVo));
    }

    @SysLog(serviceId = ServiceNameConstants.USER_SERVICE, moduleName = MODULE_NAME, actionName = "删除用户信息")
    @ApiOperation(value = "删除用户信息", notes = "删除用户信息", httpMethod = "DELETE")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "integer")
    @DeleteMapping("/id/{id}")
    public R<Boolean> delete(@PathVariable("id") Integer id) {
        return new R<>(sysUserService.delete(id));
    }

    @SysLog(serviceId = ServiceNameConstants.USER_SERVICE, moduleName = MODULE_NAME, actionName = "主键查询用户信息")
    @ApiOperation(value = "主键查询用户信息", notes = "查询用户信息", httpMethod = "GET")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "integer")
    @GetMapping("/id/{id}")
    public R<SysUser> get(@PathVariable("id") Integer id) {
        return new R<>(sysUserService.getById(id));
    }

    @ApiOperation(value = "发送登录验证码", notes = "发送登录验证码", httpMethod = "GET")
    @ApiImplicitParam(name = "mobile", value = "电话号码", required = true, dataType = "string")
    @GetMapping("/mobile/{mobile}")
    public R<String> sendMobileCode(@PathVariable("mobile") String mobile) {
        Object originCode = redisTemplate.opsForValue().get(SecurityConstants.REDIS_CODE_PREFIX + mobile);
        if (originCode != null) {
            log.info("手机号{}验证码{}尚未失效，请失效后再申请。", mobile, originCode);
            return R.error("验证码尚未失效");
        }
        SysUserVo sysUserVo = sysUserService.loadUserByMobile(mobile);
        if (sysUserVo == null) {
            log.error("手机号为{} 用户不存在", mobile);
            return R.error("手机号不存在");
        }
        String code = RandomStringUtils.random(4, false, true);
        String[] params = {code};
        SmsMessageTemplate smsMessageTemplate = new SmsMessageTemplate();
        smsMessageTemplate.setParams(params);
        smsMessageTemplate.setMobile(mobile);
        smsMessageTemplate.setSignName(SmsTemplateEnum.LOGIN_CODE.getSignName());
        smsMessageTemplate.setTemplate(SmsTemplateEnum.LOGIN_CODE.getTempalte());
        smsMessageTemplate.setChannel(SmsMessageChannnelEnum.TENCENT_CLOUD.getCode());

        // 发送消息处理中心
//        rabbitTemplate.convertAndSend(MqQueueNameConstant.MOBILE_CODE_QUEUE,smsMessageTemplate);
        // 存redis
        redisTemplate.opsForValue().set(SecurityConstants.REDIS_CODE_PREFIX + mobile, Integer.valueOf(code), SecurityConstants.REDIS_CODE_EXPIRE, TimeUnit.SECONDS);
        return new R<>(code);
    }


}

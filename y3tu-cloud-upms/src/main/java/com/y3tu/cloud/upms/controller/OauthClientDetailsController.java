package com.y3tu.cloud.upms.controller;

import com.y3tu.cloud.upms.model.entity.SysOauthClientDetails;
import com.y3tu.cloud.upms.service.SysOauthClientDetailsService;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.tool.web.base.pojo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/client")
public class OauthClientDetailsController extends BaseController {

    @Autowired
    private SysOauthClientDetailsService sysOauthClientDetailsService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return SysOauthClientDetails
     */
    @GetMapping("/{id}")
    public R get(@PathVariable Integer id) {
        return R.success(sysOauthClientDetailsService.getById(id));
    }


    /**
     * 分页查询信息
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    public R page(@RequestParam Map<String, Object> params) {

        PageInfo<SysOauthClientDetails> pageInfo = sysOauthClientDetailsService.queryPage(PageInfo.mapToPageInfo(params), params);
        return R.success(pageInfo);
    }

    /**
     * 添加
     *
     * @param client 实体
     * @return success/false
     */
    @PostMapping
    public R add(@RequestBody SysOauthClientDetails client) {
        if (StringUtils.isEmpty(client.getAdditionalInformation())) {
            client.setAdditionalInformation(null);
        }
        final String secret = encoder.encode(client.getClientId() + ":" + client.getClientSecret());
        client.setClientSecret(secret);
        return R.success(sysOauthClientDetailsService.save(client));
    }

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return R.success(sysOauthClientDetailsService.removeById(id));
    }

    /**
     * 编辑
     *
     * @param client 实体
     * @return success/false
     */
    @PutMapping
    public R edit(@RequestBody SysOauthClientDetails client) {
        final String pass = client.getClientId() + ":" + client.getClientSecret();
        final SysOauthClientDetails details = sysOauthClientDetailsService.getById(client.getClientId());
        if (encoder.matches(pass, details.getClientSecret())) {
            client.setClientSecret(encoder.encode(pass));
        }
        return R.success(sysOauthClientDetailsService.updateById(client));
    }
}

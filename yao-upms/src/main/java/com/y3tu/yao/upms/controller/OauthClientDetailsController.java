package com.y3tu.yao.upms.controller;

import com.y3tu.yao.upms.model.entity.OauthClientDetails;
import com.y3tu.yao.upms.service.OauthClientDetailsService;
import com.y3tu.tool.core.pojo.R;
import com.y3tu.tool.web.base.controller.BaseController;
import com.y3tu.tool.web.base.pojo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/client")
public class OauthClientDetailsController extends BaseController<OauthClientDetailsService, OauthClientDetails> {

    @Autowired
    private OauthClientDetailsService oauthClientDetailsService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 通过ID查询
     *
     * @param id ID
     * @return OauthClientDetails
     */
    @GetMapping("/{id}")
    public R get(@PathVariable Integer id) {
        return R.success(oauthClientDetailsService.getById(id));
    }


    /**
     * 分页查询信息
     *
     * @param pageInfo 分页对象
     * @return 分页对象
     */
    @GetMapping("/page")
    @Override
    public R page(@RequestBody PageInfo pageInfo) {
        return R.success(oauthClientDetailsService.page(pageInfo));
    }

    /**
     * 添加
     *
     * @param client 实体
     * @return success/false
     */
    @PostMapping
    public R add(@RequestBody OauthClientDetails client) {
        if (StringUtils.isEmpty(client.getAdditionalInformation())) {
            client.setAdditionalInformation(null);
        }
        final String secret = encoder.encode(client.getClientId() + ":" + client.getClientSecret());
        client.setClientSecret(secret);
        return R.success(oauthClientDetailsService.save(client));
    }

    /**
     * 删除
     *
     * @param id ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable String id) {
        return R.success(oauthClientDetailsService.removeById(id));
    }

    /**
     * 编辑
     *
     * @param client 实体
     * @return success/false
     */
    @PutMapping
    public R edit(@RequestBody OauthClientDetails client) {
        final String pass = client.getClientId() + ":" + client.getClientSecret();
        final OauthClientDetails details = oauthClientDetailsService.getById(client.getClientId());
        if (encoder.matches(pass, details.getClientSecret())) {
            client.setClientSecret(encoder.encode(pass));
        }
        return R.success(oauthClientDetailsService.updateById(client));
    }
}

package com.y3tu.yao.authentication.feign;

import com.y3tu.yao.common.constants.ServerNameConstants;
import com.y3tu.yao.common.vo.ResourceVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

/**
 * @author y3tu
 */
@FeignClient(name = ServerNameConstants.BACK_SERVER)
public interface ResourceService {
    /**
     * 通过角色名查询资源信息
     *
     * @param roleCode 角色编码
     * @return 菜单列表
     */
    @GetMapping("/resource/role/{roleCode}")
    Set<ResourceVO> listResourceByRole(@PathVariable("roleCode") String roleCode);

    /**
     * 获取全部资源信息
     *
     * @return
     */
    @GetMapping("/resource/listAllResource")
    Set<ResourceVO> listAllResource();
}

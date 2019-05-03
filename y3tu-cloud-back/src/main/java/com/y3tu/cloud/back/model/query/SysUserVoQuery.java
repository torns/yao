package com.y3tu.cloud.back.model.query;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.y3tu.cloud.common.vo.SysUserVo;
import lombok.Data;

@Data
public class SysUserVoQuery extends Page<SysUserVo> {

    /**
     * 用户名
     */
    private String username;

}

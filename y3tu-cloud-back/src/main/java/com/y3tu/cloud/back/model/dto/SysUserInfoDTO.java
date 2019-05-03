package com.y3tu.cloud.back.model.dto;

import com.y3tu.cloud.back.model.entity.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class SysUserInfoDTO {

    private SysUser sysUser;

    private List<String> roles;

    private List<String> permissions;
}

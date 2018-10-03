package com.y3tu.cloud.auth.util;


import com.y3tu.cloud.common.constant.CommonConstant;
import com.y3tu.cloud.common.constant.SecurityConstants;
import com.y3tu.cloud.common.vo.RolesVO;
import com.y3tu.cloud.common.vo.UsersVO;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 扩展UserDetails信息 满足业务需求
 *
 * @author y3tu
 */
@Data
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String username;
    private String password;
    private String status;
    private List<RolesVO> roleList;

    public UserDetailsImpl(UsersVO userVo) {
        this.userId = userVo.getId() + "";
        this.username = userVo.getUsername();
        this.password = userVo.getPassword();
        this.status = userVo.getEnabled() + "";
        roleList = userVo.getRolesList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (RolesVO role : roleList) {
            authorityList.add(new SimpleGrantedAuthority(role.getCode()));
        }
        // 为每一个用户添加一个基本角色
        authorityList.add(new SimpleGrantedAuthority(SecurityConstants.BASE_ROLE));
        return authorityList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return StringUtils.equals(CommonConstant.STATUS_LOCK, status) ? false : true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return StringUtils.equals(CommonConstant.STATUS_NORMAL, status) ? true : false;
    }

}

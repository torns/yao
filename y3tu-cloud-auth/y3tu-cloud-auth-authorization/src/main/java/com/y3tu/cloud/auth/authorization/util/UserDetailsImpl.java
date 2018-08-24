package com.y3tu.cloud.auth.authorization.util;


import com.y3tu.cloud.common.constant.CommonConstant;
import com.y3tu.cloud.common.constant.SecurityConstants;
import com.y3tu.cloud.common.vo.RoleVO;
import com.y3tu.cloud.common.vo.UserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author lengleng
 * @date 2017/10/29
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String username;
    private String password;
    private String status;
    private List<RoleVO> roleList;

    public UserDetailsImpl(UserVO userVo) {
        this.userId = userVo.getId();
        this.username = userVo.getUsername();
        this.password = userVo.getPassword();
        this.status = userVo.getDelFlag()+"";
        roleList = userVo.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (RoleVO role : roleList) {
            authorityList.add(new SimpleGrantedAuthority(role.getName()));
        }
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RoleVO> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<RoleVO> roleList) {
        this.roleList = roleList;
    }

    public String getStatus() {
        return status;
    }

    public String getUserId() {
        return userId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

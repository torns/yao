package com.y3tu.yao.authorization.security;

import com.y3tu.yao.common.enums.UserStatusEnum;
import com.y3tu.yao.common.vo.RoleVO;
import com.y3tu.yao.common.vo.UserVO;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * security 用户对象
 */
@Data
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = -2636609458742965698L;

    private String userId;
    private String username;
    private String password;
    private String status;
    private List<RoleVO> roleVos;


    public UserDetailsImpl(UserVO userVo) {
        this.userId = userVo.getId();
        this.username = userVo.getUsername();
        this.password = userVo.getPassword();
        this.status = userVo.getDelFlag() + "";
        this.roleVos = userVo.getRoles();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        roleVos.forEach(role -> {
            authorityList.add(new SimpleGrantedAuthority(role.getRoleCode()));
        });
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
        return !StringUtils.equals(UserStatusEnum.LOCK.getCode() + "", status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return StringUtils.equals(UserStatusEnum.NORMAL.getCode() + "", status);
    }
}

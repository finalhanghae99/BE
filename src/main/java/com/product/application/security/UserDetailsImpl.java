package com.product.application.security;


import com.product.application.security.jwt.UserRoleEnum;
import com.product.application.user.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final Users users;
    private final String useremail;

    public UserDetailsImpl(Users users, String useremail) {
        this.users = users;
        this.useremail = useremail;
    }

    public Long getUserId() {
        return users.getId();
    }

    public Users getUser() {
        return users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        // *주의*
        // getUseremail로 써야하는데, getUsername이 인터페이스에 선언되어 있어서 이렇게 선언하고 userid를 반환합니다.
        return this.useremail;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

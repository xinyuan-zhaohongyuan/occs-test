package com.knowology.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * <p>Implement UserDetails</p>
 * @see UserDetails
 * @author : Conan
 * @date : 2019-07-17 15:14
 **/

public class OccsUserDetails implements UserDetails {
    private static final long serialVersionUID = -1459096178917300353L;

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private String fullName;
    /**
     * 用户id，可用于client标识
     */
    private Integer userId;

    public OccsUserDetails(String username,String password,String fullName,Integer userId) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.userId = userId;
    }

    public OccsUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,String fullName,Integer userId) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.fullName = fullName;
        this.userId = userId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

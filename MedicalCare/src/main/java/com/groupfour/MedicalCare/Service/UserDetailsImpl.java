package com.groupfour.MedicalCare.Service;

import com.groupfour.MedicalCare.Model.Pacijent.Pacijent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private Pacijent pacijent;

    public UserDetailsImpl(Pacijent pacijent) {
        this.pacijent = pacijent;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("PACIJENT"));
    }

    @Override
    public String getPassword() {
        return pacijent.getLozinka();
    }

    @Override
    public String getUsername() {
        return pacijent.getEmail();
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
}

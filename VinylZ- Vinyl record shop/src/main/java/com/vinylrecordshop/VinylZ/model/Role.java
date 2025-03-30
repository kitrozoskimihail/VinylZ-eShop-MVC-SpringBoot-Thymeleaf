package com.vinylrecordshop.VinylZ.model;
import org.springframework.security.core.GrantedAuthority;



import lombok.Getter;

@Getter
public enum Role implements GrantedAuthority {
    USER, RADMIN, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

package com.georgen.hawthornerest.model.users;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {
    GUEST(
            Sets.newHashSet(
                    Permission.DOCUMENT_READ,
                    Permission.FILE_READ),
            new SimpleGrantedAuthority("ROLE_GUEST")
    ),
    USER(
            Sets.newHashSet(
                    Permission.DOCUMENT_READ,
                    Permission.DOCUMENT_WRITE,
                    Permission.FILE_READ,
                    Permission.FILE_WRITE,
                    Permission.USER_READ),
            new SimpleGrantedAuthority("ROLE_USER")
    ),
    ADMIN(
            Sets.newHashSet(Permission.values()),
            new SimpleGrantedAuthority("ROLE_ADMIN")
    );
    private Set<Permission> permissions;
    private GrantedAuthority roleAuthority;

    Role(Set<Permission> permissions, GrantedAuthority roleAuthority) {
        this.permissions = permissions;
        this.roleAuthority = roleAuthority;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public GrantedAuthority getRoleAuthority() {
        return roleAuthority;
    }

    public Set<GrantedAuthority> toGrantedAuthorities(){
        Set<GrantedAuthority> authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getSubject()))
                .collect(Collectors.toSet());
        authorities.add(getRoleAuthority());
        return authorities;
    }
}

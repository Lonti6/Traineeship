package ru.work.trainsheep.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }

    public static Role of(String str) throws NotRoleException {
        if(Objects.equals(str, "USER"))
            return USER;
        if(Objects.equals(str, "ADMIN"))
            return ADMIN;
        throw new NotRoleException(str);
    }
    static class NotRoleException extends Exception {
        public NotRoleException(String role) {
            super("Not role " + role);
        }
    }
}

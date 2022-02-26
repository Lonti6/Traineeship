package ru.work.trainsheep.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class UserPasswords implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String roles;

    private boolean enabled = true;

    @Override
    public Collection<Role> getAuthorities() {
        val strs = roles.split(" ");
        val result = new ArrayList<Role>(strs.length);
        for(val str : strs){
            try {
                result.add(Role.of(str));
            } catch (Role.NotRoleException exception){
                exception.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }



}

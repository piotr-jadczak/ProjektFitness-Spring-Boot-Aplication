package app.fitness.FitnessApp.login;

import app.fitness.FitnessApp.domain.Coach;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CoachUserDetails implements UserDetails {

    private final Coach coach;

    public CoachUserDetails(Coach coach) {
        this.coach = coach;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return coach.getPassword();
    }

    @Override
    public String getUsername() {
        return coach.getLogin();
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

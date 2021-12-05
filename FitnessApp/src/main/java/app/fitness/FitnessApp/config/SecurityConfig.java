package app.fitness.FitnessApp.config;

import app.fitness.FitnessApp.login.UserForm;
import app.fitness.FitnessApp.login.CustomerUserDetailsService;
import app.fitness.FitnessApp.login.UserType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public CustomerUserDetailsService customerDetailsService() {
        return new CustomerUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customerDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .authorizeRequests()
                    .antMatchers("/resources/**")
                    .permitAll()
                    .and()
                .authorizeRequests()
                    .antMatchers("/customer-panel")
                    .hasAuthority("ROLE_CUSTOMER")
                .and()
                    .authorizeRequests()
                    .antMatchers("/owner-panel")
                    .hasAuthority("ROLE_OWNER")
                .and()
                .authorizeRequests()
                    .antMatchers("/coach-panel")
                    .hasAuthority("ROLE_COACH")
                .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/success_login_redirect")
                    .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll()
                    .and()
                .httpBasic();


    }


}

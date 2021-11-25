package app.fitness.FitnessApp.config;

import app.fitness.FitnessApp.login.BaseUserLogin;
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
    @Qualifier("customer-prototype")
    public BaseUserLogin addCustomerPrototype() {
        BaseUserLogin customer = new BaseUserLogin("customer", "password", "jan@gmail.com", "Jan", "Kowalski", null , "123456789", UserType.CUSTOMER);
        return customer;
    }

    @Bean
    @Qualifier("coach-prototype")
    public BaseUserLogin addCoachPrototype() {
        BaseUserLogin coach = new BaseUserLogin("coach", "password", "jan@gmail.com", "Marek", "Dąbrowski", null , "123456789", UserType.COACH);
        return coach;
    }

    @Bean
    @Qualifier("owner-prototype")
    public BaseUserLogin addOwnerPrototype() {
        BaseUserLogin owner = new BaseUserLogin("owner", "password", "jan@gmail.com", "Kamil", "Nowak", null , "123456789", UserType.OWNER);
        return owner;
    }

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
                    .antMatchers("/customer_panel")
                    .authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/customer_panel")
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/")
                    .permitAll()
                    .and()
                .httpBasic();


    }


}

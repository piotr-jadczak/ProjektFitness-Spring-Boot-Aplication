package app.fitness.FitnessApp.config;

import app.fitness.FitnessApp.domain.login.BaseUserLogin;
import app.fitness.FitnessApp.domain.login.CustomerUserDetailsService;
import app.fitness.FitnessApp.domain.login.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Qualifier("customer-prototype")
    public BaseUserLogin addCustomerPrototype() {
        BaseUserLogin customer = new BaseUserLogin("customer", "password", "jan@gmail.com", "Jan", "Kowalski", null , "123456789", UserType.CUSTOMER);
        return customer;
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

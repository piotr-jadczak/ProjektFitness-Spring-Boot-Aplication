package app.fitness.FitnessApp.domain.login;

import app.fitness.FitnessApp.domain.Customer;
import app.fitness.FitnessApp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public CustomerUserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByLogin(login);
        if (customer == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomerUserDetails(customer);
    }
}

package app.fitness.FitnessApp.login;

import app.fitness.FitnessApp.domain.Coach;
import app.fitness.FitnessApp.domain.Customer;
import app.fitness.FitnessApp.domain.Owner;
import app.fitness.FitnessApp.repository.CoachRepository;
import app.fitness.FitnessApp.repository.CustomerRepository;
import app.fitness.FitnessApp.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Customer foundCustomer = null;
        Owner foundOwner = null;
        Coach foundCoach = null;

        foundCustomer = customerRepository.findByLogin(login);
        if(foundCustomer != null) {
            return new CustomerUserDetails(foundCustomer);
        }
        foundCoach = coachRepository.findByLogin(login);
        if(foundCoach != null) {
            return new CoachUserDetails(foundCoach);
        }
        foundOwner = ownerRepository.findByLogin(login);
        if(foundOwner != null) {
            return  new OwnerUserDetails(foundOwner);
        }

        throw new UsernameNotFoundException("User not found");
    }
}

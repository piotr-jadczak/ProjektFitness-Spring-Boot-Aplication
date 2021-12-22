package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.*;
import app.fitness.FitnessApp.login.UserForm;
import app.fitness.FitnessApp.login.UserRole;
import app.fitness.FitnessApp.repository.CoachRepository;
import app.fitness.FitnessApp.repository.CustomerRepository;
import app.fitness.FitnessApp.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
public class UserManagerImp implements UserManager {

    private CustomerRepository customerRepository;
    private OwnerRepository ownerRepository;
    private CoachRepository coachRepository;
    private EntityManager entityManager;
    private PasswordEncoder passwordEncoder;

    UserManagerImp(@Autowired CustomerRepository customerRepository,
                   @Autowired OwnerRepository ownerRepository,
                   @Autowired CoachRepository coachRepository,
                   @Autowired EntityManager entityManager,
                   @Autowired PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.ownerRepository = ownerRepository;
        this.coachRepository = coachRepository;
        this.entityManager = entityManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public BaseUser addUser(UserForm user) {
        BaseUser newBaseUser = new BaseUser();
        newBaseUser.setLogin(user.getLogin());
        newBaseUser.setEmail(user.getEmail());
        newBaseUser.setFirstName(user.getFirstName());
        newBaseUser.setLastName(user.getLastName());
        System.out.println(user.getDob());
        newBaseUser.setDob(null);
        newBaseUser.setPhoneNumber(user.getPhoneNumber());

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        newBaseUser.setPassword(encodedPassword);

        switch (user.getUserType()) {
            case CUSTOMER:
                Customer newCustomer = new Customer(newBaseUser);
                customerRepository.save(newCustomer);
                break;
            case OWNER:
                Owner newOwner = new Owner(newBaseUser);
                ownerRepository.save(newOwner);
                break;
            case COACH:
                Coach newCoach = new Coach(newBaseUser);
                coachRepository.save(newCoach);
                break;
            default:
                throw new IllegalArgumentException("Cannot add not exisitng user type");
        }

        return newBaseUser;
    }

    @Override
    public String getAuthorityName(Authentication authentication) {
        GrantedAuthority ownerAuthority = new SimpleGrantedAuthority(UserRole.OWNER.getValue());
        GrantedAuthority customerAuthority = new SimpleGrantedAuthority(UserRole.CUSTOMER.getValue());
        GrantedAuthority coachAuthority = new SimpleGrantedAuthority(UserRole.COACH.getValue());

        if(authentication.getAuthorities().contains(ownerAuthority))
            return "ROLE_OWNER";
        if(authentication.getAuthorities().contains(coachAuthority))
            return "ROLE_COACH";
        if(authentication.getAuthorities().contains(customerAuthority))
            return "ROLE_CUSTOMER";

        return null;
    }

    @Override
    public Owner findOwnerByLogin(String login) {
        if(ownerRepository.findByLogin(login) != null)
            return ownerRepository.findByLogin(login);
        throw new RuntimeException("No owner with login " + login + " exist");
    }

    @Override
    public Coach findCoachById(int id) {
        return coachRepository.findById(id);
    }

    @Override
    public Coach findCoachByLogin(String login) {
        if(coachRepository.findByLogin(login) != null)
            return coachRepository.findByLogin(login);
        throw new RuntimeException("No coach with login " + login + " exist");
    }


}

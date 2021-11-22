package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.*;
import app.fitness.FitnessApp.domain.login.BaseUserLogin;
import app.fitness.FitnessApp.repository.CoachRepository;
import app.fitness.FitnessApp.repository.CustomerRepository;
import app.fitness.FitnessApp.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public BaseUser addUser(BaseUserLogin user) {
        BaseUser newBaseUser = new BaseUser();
        newBaseUser.setLogin(user.getLogin());
        newBaseUser.setEmail(user.getEmail());
        newBaseUser.setFirstName(user.getFirstName());
        newBaseUser.setLastName(user.getLastName());
        newBaseUser.setDob(user.getDob());
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
}

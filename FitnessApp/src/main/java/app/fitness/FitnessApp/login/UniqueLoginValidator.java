package app.fitness.FitnessApp.login;

import app.fitness.FitnessApp.repository.CoachRepository;
import app.fitness.FitnessApp.repository.CustomerRepository;
import app.fitness.FitnessApp.repository.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueLoginValidator implements ConstraintValidator<UniqueLogin, String> {
    private static Logger logger = LoggerFactory.getLogger(UniqueLoginValidator.class);

    private CustomerRepository customerRepository;
    private OwnerRepository ownerRepository;
    private CoachRepository coachRepository;

    UniqueLoginValidator(@Autowired CustomerRepository customerRepository,
                   @Autowired OwnerRepository ownerRepository,
                   @Autowired CoachRepository coachRepository) {
        this.customerRepository = customerRepository;
        this.ownerRepository = ownerRepository;
        this.coachRepository = coachRepository;
    }

    @Override
    public void initialize(UniqueLogin login) {

    }

    @Override
    public boolean isValid(String login, ConstraintValidatorContext context) {
        logger.info("Sprawdzam login");
        return login != null && customerRepository.findByLogin(login) == null
                && ownerRepository.findByLogin(login) == null
                && coachRepository.findByLogin(login) == null;
    }
}

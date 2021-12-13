package app.fitness.FitnessApp.validators;

import app.fitness.FitnessApp.repository.CoachRepository;
import app.fitness.FitnessApp.repository.CustomerRepository;
import app.fitness.FitnessApp.repository.OwnerRepository;
import app.fitness.FitnessApp.validators.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private CustomerRepository customerRepository;
    private OwnerRepository ownerRepository;
    private CoachRepository coachRepository;

    UniqueEmailValidator(@Autowired CustomerRepository customerRepository,
                         @Autowired OwnerRepository ownerRepository,
                         @Autowired CoachRepository coachRepository) {
        this.customerRepository = customerRepository;
        this.ownerRepository = ownerRepository;
        this.coachRepository = coachRepository;
    }

    @Override
    public void initialize(UniqueEmail email) {

    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return email != null && customerRepository.findByEmail(email) == null
                && ownerRepository.findByEmail(email) == null
                && coachRepository.findByEmail(email) == null;
    }
}

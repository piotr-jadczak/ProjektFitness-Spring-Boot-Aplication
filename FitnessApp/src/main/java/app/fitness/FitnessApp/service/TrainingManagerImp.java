package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.*;
import app.fitness.FitnessApp.domain.extra.TrainingForm;
import app.fitness.FitnessApp.domain.extra.TrainingType;
import app.fitness.FitnessApp.repository.CoachRepository;
import app.fitness.FitnessApp.repository.TrainingCategoryRepository;
import app.fitness.FitnessApp.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class TrainingManagerImp implements TrainingManager {

    private final CoachRepository coachRepository;
    private final TrainingCategoryRepository trainingCategoryRepository;
    private final TrainingRepository trainingRepository;

    public TrainingManagerImp(@Autowired CoachRepository coachRepository,
                              @Autowired TrainingCategoryRepository trainingCategoryRepository,
                              @Autowired TrainingRepository trainingRepository) {
        this.coachRepository = coachRepository;
        this.trainingCategoryRepository = trainingCategoryRepository;
        this.trainingRepository = trainingRepository;
    }

    @Override
    public void addTrainingCategory(TrainingCategory trainingCategory) {
        trainingCategoryRepository.save(trainingCategory);
    }

    @Override
    public Stream<TrainingCategory> getAllTrainingCategories() {
        return StreamSupport.stream(trainingCategoryRepository.findAll().spliterator(), false);
    }

    @Override
    public void addTraining(Training training) {
        trainingRepository.save(training);
    }

    @Override
    public List<Training> getAllTrainings(Coach coach) {
        return trainingRepository.findAllByCoach(coach);
    }

    @Override
    public Stream<Training> getAllTrainings() {
        return StreamSupport.stream(trainingRepository.findAll().spliterator(), false);
    }

    @Override
    public boolean isTrainingInDB(int id) {
        return trainingRepository.findById(id).isPresent();
    }

    @Override
    public void updateTraining(TrainingForm trainingForm) {
        Training savedTraining = getTraining(trainingForm.getId());
        savedTraining.update(trainingForm);
        if(savedTraining.getTrainingType() == TrainingType.ONETIME) {
            trainingForm.filterValidOneTimeDates();
            Set<OneTimeDate> dateSet = new HashSet<>(trainingForm.getOneTimeDates());
            savedTraining.setOneTimeDates(dateSet);
        }
        if(savedTraining.getTrainingType() == TrainingType.REGULAR) {
            trainingForm.filterValidRegularDates();
            Set<RegularDate> dateSet = new HashSet<>(trainingForm.getRegularDates());
            savedTraining.setRegularDates(dateSet);
        }

        trainingRepository.save(savedTraining);
    }

    @Override
    public Training getTraining(int id) {
        return trainingRepository.findById(id).get();
    }

    @Override
    public void deleteTraining(int id) {
        trainingRepository.deleteTrainingById(id);
    }

    @Override
    public void enrollCustomer(Customer customer, int trainingId) {
        Training training = trainingRepository.findById(trainingId).get();
        if(!training.isTrainingFull() && !training.getCustomers().contains(customer)) {
            training.setCurrentParticipants(training.getCurrentParticipants()+1);
            training.addCustomer(customer);
            trainingRepository.save(training);
        }
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void resignCustomer(Customer customer, int trainingId) {
        Training training = trainingRepository.findById(trainingId).get();
        if(training.getCustomers().contains(customer)) {
            training.removeCustomer(customer);
            training.setCurrentParticipants(training.getCurrentParticipants()-1);
            trainingRepository.save(training);
        }
    }

    @Override
    public void addTraining(TrainingForm trainingForm) {
        Training training = new Training(trainingForm.getTrainingType(), trainingForm.getName(), trainingForm.getDescription(), trainingForm.getMaxParticipants(),
                0, trainingForm.getPrice(), trainingForm.getClub(), trainingForm.getTrainingCategory(), trainingForm.getCoach());

        if(training.getTrainingType() == TrainingType.ONETIME) {
            trainingForm.filterValidOneTimeDates();
            Set<OneTimeDate> dateSet = new HashSet<>(trainingForm.getOneTimeDates());
            training.setOneTimeDates(dateSet);
        }
        if(training.getTrainingType() == TrainingType.REGULAR) {
            trainingForm.filterValidRegularDates();
            Set<RegularDate> dateSet = new HashSet<>(trainingForm.getRegularDates());
            training.setRegularDates(dateSet);
        }

        trainingRepository.save(training);
    }


}

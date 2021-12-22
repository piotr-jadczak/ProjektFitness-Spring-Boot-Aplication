package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.Coach;
import app.fitness.FitnessApp.domain.Training;
import app.fitness.FitnessApp.domain.TrainingCategory;
import app.fitness.FitnessApp.repository.CoachRepository;
import app.fitness.FitnessApp.repository.TrainingCategoryRepository;
import app.fitness.FitnessApp.repository.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}

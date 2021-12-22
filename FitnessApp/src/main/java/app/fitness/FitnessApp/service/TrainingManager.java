package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.Coach;
import app.fitness.FitnessApp.domain.Training;
import app.fitness.FitnessApp.domain.TrainingCategory;

import java.util.List;
import java.util.stream.Stream;

public interface TrainingManager {

    void addTrainingCategory(TrainingCategory trainingCategory);
    Stream<TrainingCategory> getAllTrainingCategories();
    void addTraining(Training training);
    List<Training> getAllTrainings(Coach coach);
    Stream<Training> getAllTrainings();
}

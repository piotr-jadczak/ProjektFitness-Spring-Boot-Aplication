package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.Coach;
import app.fitness.FitnessApp.domain.Customer;
import app.fitness.FitnessApp.domain.Training;
import app.fitness.FitnessApp.domain.TrainingCategory;
import app.fitness.FitnessApp.domain.extra.TrainingForm;

import java.util.List;
import java.util.stream.Stream;

public interface TrainingManager {

    void addTrainingCategory(TrainingCategory trainingCategory);
    Stream<TrainingCategory> getAllTrainingCategories();
    TrainingCategory getTrainingCategory(int id);
    void addTraining(Training training);
    List<Training> getAllTrainings(Coach coach);
    Stream<Training> getAllTrainings();
    boolean isTrainingInDB(int id);
    void updateTraining(TrainingForm training);
    Training getTraining(int id);
    void deleteTraining(int id);
    void enrollCustomer(Customer customer, int trainingId);
    void resignCustomer(Customer customer, int trainingId);
    void addTraining(TrainingForm trainingForm);
    Stream<List<Training>> groupTrainingsByClub(List<Training> trainings);
}

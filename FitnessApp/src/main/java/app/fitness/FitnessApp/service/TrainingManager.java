package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.TrainingCategory;

import java.util.stream.Stream;

public interface TrainingManager {

    void addTrainingCategory(TrainingCategory trainingCategory);

    Stream<TrainingCategory> getAllTrainingCategories();
}

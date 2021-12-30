package app.fitness.FitnessApp.domain.extra;

import app.fitness.FitnessApp.domain.Club;
import app.fitness.FitnessApp.domain.Training;

import java.util.ArrayList;
import java.util.List;

public class ClubTrainingsWrapper {

    private Club club;
    private List<Training> trainings;

    public ClubTrainingsWrapper() {
        trainings = new ArrayList<>();
    }

    public ClubTrainingsWrapper(Club club, List<Training> trainings) {
        this.club = club;
        this.trainings = trainings;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }
}

package app.fitness.FitnessApp.domain;

public class TrainingCategory {

    private int TrainingCategoryId;
    private String name;

    public TrainingCategory() {
    }

    public TrainingCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrainingCategoryId() {
        return TrainingCategoryId;
    }

    public void setTrainingCategoryId(int trainingCategoryId) {
        TrainingCategoryId = trainingCategoryId;
    }
}

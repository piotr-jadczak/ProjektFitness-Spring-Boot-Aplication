package app.fitness.FitnessApp.domain;

public class ClubCategory {

    private int ClubCategoryId;
    private String name;

    public ClubCategory() {
    }

    public ClubCategory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClubCategoryId() {
        return ClubCategoryId;
    }

    public void setClubCategoryId(int clubCategoryId) {
        ClubCategoryId = clubCategoryId;
    }
}

package app.fitness.FitnessApp.domain;


public class Club {

    private int ClubId;
    private String name;
    private String description;
    private String adress;

    public Club() {
    }

    public Club(int clubId, String name, String description, String adress) {
        ClubId = clubId;
        this.name = name;
        this.description = description;
        this.adress = adress;
    }

    public int getClubId() {
        return ClubId;
    }

    public void setClubId(int clubId) {
        ClubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}

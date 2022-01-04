package app.fitness.FitnessApp.domain.extra;

import app.fitness.FitnessApp.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainingForm {

    private int id;
    private TrainingType trainingType;
    private String name;
    private String description;
    private int maxParticipants;
    private TrainingCategory trainingCategory;
    private Coach coach;
    private Club club;
    private double price;

    private List<RegularDate> regularDates;
    private List<OneTimeDate> oneTimeDates;

    public TrainingForm(TrainingType trainingType, String name, String description, int maxParticipants, double price, List<RegularDate> regularDates, List<OneTimeDate> oneTimeDates) {
        this.trainingType = trainingType;
        this.name = name;
        this.description = description;
        this.maxParticipants = maxParticipants;
        this.price = price;
        this.regularDates = regularDates;
        this.oneTimeDates = oneTimeDates;
    }

    public TrainingForm() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
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

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public TrainingCategory getTrainingCategory() {
        return trainingCategory;
    }

    public void setTrainingCategory(TrainingCategory trainingCategory) {
        this.trainingCategory = trainingCategory;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public List<RegularDate> getRegularDates() {
        return regularDates;
    }

    public void setRegularDates(List<RegularDate> regularDates) {
        this.regularDates = regularDates;
    }

    public List<OneTimeDate> getOneTimeDates() {
        return oneTimeDates;
    }

    public void setOneTimeDates(List<OneTimeDate> oneTimeDates) {
        this.oneTimeDates = oneTimeDates;
    }

    public void addOneTimeDate(OneTimeDate oneTimeDate) {
        oneTimeDates.add(oneTimeDate);
    }

    public void removeOneTimeDate(OneTimeDate oneTimeDate) {
        oneTimeDates.remove(oneTimeDate);
    }

    public void addRegularDate(RegularDate regularDate) {
        regularDates.add(regularDate);
    }

    public void removeRegularDate(RegularDate regularDate) {
        regularDates.remove(regularDate);
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void filterValidOneTimeDates() {
        List<OneTimeDate> validDates = oneTimeDates.stream().filter(d -> (d.getDate() != null && d.getStartTime() != null && d.getEndTime() != null)).collect(Collectors.toList());
        setOneTimeDates(validDates);
    }

    public void filterValidRegularDates() {
        List<RegularDate> validDates = regularDates.stream().filter(d -> (d.getDayOfWeek() != null && d.getStartTime() != null && d.getEndTime() != null)).collect(Collectors.toList());
        setRegularDates(validDates);
    }

    public TrainingForm(Training training) {
        this.trainingType = training.getTrainingType();
        this.name = training.getName();
        this.description = training.getDescription();
        this.maxParticipants = training.getMaxParticipants();
        this.trainingCategory = training.getTrainingCategory();
        this.coach = training.getCoach();
        this.club = training.getClub();
        this.price = training.getPrice();
        this.id = training.getId();
        if (training.getRegularDates() != null && training.getRegularDates().size() > 0) {
            this.regularDates = new ArrayList<>(training.getRegularDates());
            this.oneTimeDates = new ArrayList<>();
        }
        if (training.getOneTimeDates() != null && training.getOneTimeDates().size() > 0) {
            this.oneTimeDates = new ArrayList<>(training.getOneTimeDates());
            this.regularDates = new ArrayList<>();
        }
        while(this.regularDates.size() < 5)
            this.regularDates.add(new RegularDate());
        while(this.oneTimeDates.size() < 5)
            this.oneTimeDates.add(new OneTimeDate());
    }

}

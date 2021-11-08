package app.fitness.FitnessApp.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Training {

    private int TrainingId;
    private String name;
    private String description;
    private int maxCapacity;
    private int dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private int pointReward;
    private double price;

    public Training() {
    }

    public Training(String name, String description, int maxCapacity, int dayOfWeek, LocalTime startTime, LocalTime endTime, int pointReward, double price) {
        this.name = name;
        this.description = description;
        this.maxCapacity = maxCapacity;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.pointReward = pointReward;
        this.price = price;
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

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getPointReward() {
        return pointReward;
    }

    public void setPointReward(int pointReward) {
        this.pointReward = pointReward;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTrainingId() {
        return TrainingId;
    }

    public void setTrainingId(int trainingId) {
        TrainingId = trainingId;
    }
}

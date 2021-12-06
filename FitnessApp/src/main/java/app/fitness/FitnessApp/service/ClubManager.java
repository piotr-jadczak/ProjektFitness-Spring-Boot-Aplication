package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.Club;
import app.fitness.FitnessApp.domain.ClubCategory;
import app.fitness.FitnessApp.domain.Owner;

import java.util.List;

public interface ClubManager {

    ClubCategory addClubCategory(ClubCategory categoryToAdd);
    List<ClubCategory> getAllCategories();
    List<Club> getAllClubs();
    Club addClub(Club clubToAdd);
    List<Club> getAllClubs(Owner owner);

}

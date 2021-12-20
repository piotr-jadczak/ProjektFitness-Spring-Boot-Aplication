package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.Club;
import app.fitness.FitnessApp.domain.ClubCategory;
import app.fitness.FitnessApp.domain.Coach;
import app.fitness.FitnessApp.domain.Owner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ClubManager {

    ClubCategory addClubCategory(ClubCategory categoryToAdd);
    List<ClubCategory> getAllCategories();
    List<Club> getAllClubs();
    Club addClub(Club clubToAdd);
    List<Club> getAllClubs(Owner owner);
    boolean isClubInDB(int id);
    Club getClub(int id);
    void updateClub(Club club);
    void deleteClub(int id);
    Stream<Coach> getAllCoaches();
    Stream<Coach> getAllCoachesInClub(Club club);
    void addCoachToClub(Coach coach, Club club);
    void removeCoachFromClub(Coach coach, Club club);

}

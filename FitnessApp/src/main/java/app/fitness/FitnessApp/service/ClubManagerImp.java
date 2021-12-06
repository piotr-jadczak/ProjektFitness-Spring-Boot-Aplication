package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.Club;
import app.fitness.FitnessApp.domain.ClubCategory;
import app.fitness.FitnessApp.domain.Owner;
import app.fitness.FitnessApp.repository.ClubCategoryRepository;
import app.fitness.FitnessApp.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ClubManagerImp implements ClubManager {

    private ClubCategoryRepository clubCategoryRepository;
    private ClubRepository clubRepository;

    ClubManagerImp(@Autowired ClubCategoryRepository clubCategoryRepository,
                   @Autowired ClubRepository clubRepository) {
        this.clubCategoryRepository = clubCategoryRepository;
        this.clubRepository =  clubRepository;
    }

    @Override
    public ClubCategory addClubCategory(ClubCategory categoryToAdd) {
        clubCategoryRepository.save(categoryToAdd);
        return categoryToAdd;
    }

    @Override
    public List<ClubCategory> getAllCategories() {
        return (List<ClubCategory>) clubCategoryRepository.findAll();
    }

    @Override
    public List<Club> getAllClubs() {
        return (List<Club>) clubRepository.findAll();
    }

    @Override
    public Club addClub(Club clubToAdd) {
        clubRepository.save(clubToAdd);
        return clubToAdd;
    }

    @Override
    public List<Club> getAllClubs(Owner owner) {
        return (List<Club>) clubRepository.findAllByOwner(owner);
    }
}

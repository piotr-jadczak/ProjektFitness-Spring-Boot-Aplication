package app.fitness.FitnessApp.service;

import app.fitness.FitnessApp.domain.Club;
import app.fitness.FitnessApp.domain.ClubCategory;
import app.fitness.FitnessApp.domain.Coach;
import app.fitness.FitnessApp.domain.Owner;
import app.fitness.FitnessApp.repository.ClubCategoryRepository;
import app.fitness.FitnessApp.repository.ClubRepository;
import app.fitness.FitnessApp.repository.CoachRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class ClubManagerImp implements ClubManager {
    private static Logger logger = LoggerFactory.getLogger(ClubManagerImp.class);

    private ClubCategoryRepository clubCategoryRepository;
    private ClubRepository clubRepository;
    private CoachRepository coachRepository;

    ClubManagerImp(@Autowired ClubCategoryRepository clubCategoryRepository,
                   @Autowired ClubRepository clubRepository,
                   @Autowired CoachRepository coachRepository) {
        this.clubCategoryRepository = clubCategoryRepository;
        this.clubRepository =  clubRepository;
        this.coachRepository = coachRepository;
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

    @Override
    public boolean isClubInDB(int id) {
        return clubRepository.findById(id).isPresent();
    }

    @Override
    public Club getClub(int id) {
        return clubRepository.findClubById(id);
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void updateClub(Club club) {
        logger.info("Uruchamiam update");
        logger.info(club.getId() + " " + club.getName() + " " + club.getDescription());
        clubRepository.updateClubInfo(club.getId(), club.getName(), club.getDescription(), club.getAddressStreet(), club.getAddressNumber(), club.getAddressCity(), club.getClubCategory());
    }

    @Override
    public void deleteClub(int id) {
        clubRepository.deleteClubById(id);
    }

    @Override
    public Stream<Coach> getAllCoaches() {
        return StreamSupport.stream(coachRepository.findAll().spliterator(), false);
    }

    @Override
    public Stream<Coach> getAllCoachesInClub(Club club) {
        return clubRepository.findClubById(club.getId()).getCoaches().stream();
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void addCoachToClub(Coach coach, Club club) {
        club.addCoach(coach);
        clubRepository.save(club);
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void removeCoachFromClub(Coach coach, Club club) {
        club.removeCoach(coach);
        clubRepository.save(club);
    }

    @Override
    public void injectAddCoachToClub(Coach coach, Club club) {
        club.addCoach(coach);
        clubRepository.save(club);
    }
}

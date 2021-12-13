package app.fitness.FitnessApp.repository;

import app.fitness.FitnessApp.domain.ClubCategory;
import app.fitness.FitnessApp.domain.Owner;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.fitness.FitnessApp.domain.Club;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ClubRepository extends CrudRepository<Club, Integer>{
    public List<Club> findAllByOwner(Owner owner);

    public Club findClubById(int id);

    @Transactional
    public void deleteClubById(int id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Club c SET c.name = ?2, c.description = ?3, c.addressStreet = ?4, c.addressNumber = ?5, c.addressCity = ?6, c.clubCategory = ?7 WHERE c.id = ?1")
    public void updateClubInfo(int id, String name, String description, String addressStreet, String addressNumber, String addressCity, ClubCategory clubCategory);

}

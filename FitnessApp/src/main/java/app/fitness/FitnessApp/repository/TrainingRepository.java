package app.fitness.FitnessApp.repository;

import app.fitness.FitnessApp.domain.*;
import app.fitness.FitnessApp.domain.extra.DayOfWeek;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TrainingRepository extends CrudRepository<Training, Integer>{

    List<Training> findAllByCoach(Coach coach);

    @Transactional
    public void deleteTrainingById(int id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Training t SET t.name =?2, t.description = ?3, t.maxParticipants = ?4, t.price = ?5, t.club = ?6, t.trainingCategory = ?7 WHERE t.id = ?1")
    public void updateClubInfo(int id, String name, String description, int maxParticipants, double price, Club club, TrainingCategory trainingCategory);

}

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
    @Query("UPDATE Training t SET t.currentParticipants =?2 WHERE t.id = ?1")
    public void updateTrainingCurrentParticipants(int id, int currentParticipants);

}

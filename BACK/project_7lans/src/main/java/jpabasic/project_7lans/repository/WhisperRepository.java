package jpabasic.project_7lans.repository;

import jpabasic.project_7lans.entity.ChildVolunteerRelation;
import jpabasic.project_7lans.entity.Whisper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WhisperRepository extends JpaRepository<Whisper, Long> {
    List<Whisper> findByChildVolunteerRelation(ChildVolunteerRelation relation);
}
package jpabasic.project_7lans.repository;

import jpabasic.project_7lans.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    public List<Volunteer> findByNameLike(String volunteerName);
}

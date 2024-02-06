package jpabasic.project_7lans.repository;

import jpabasic.project_7lans.entity.Child;
import jpabasic.project_7lans.entity.ChildCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long>{
    Optional<Child> findByEmail(String email);
    Optional<Child> findByName(String name);
    List<Child> findByChildCenter(ChildCenter childCenter);
}
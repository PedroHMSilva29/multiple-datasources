package youtube.java.puzzle.college.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import youtube.java.puzzle.college.entity.CollegeEntity;

public interface CollegeRepository extends JpaRepository<CollegeEntity, Integer> {
}

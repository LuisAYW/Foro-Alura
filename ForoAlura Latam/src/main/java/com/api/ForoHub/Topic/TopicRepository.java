package com.api.ForoHub.Topic;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByTitleAndMessage(String title, String message);

    List<Topic> findTop10ByOrderByCreationDateAsc();

    List<Topic> findByCourseIdAndCreationDateBetween(Integer courseId, LocalDateTime start, LocalDateTime end);
}

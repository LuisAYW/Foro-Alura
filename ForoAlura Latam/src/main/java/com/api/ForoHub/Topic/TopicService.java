package com.api.ForoHub.Topic;


import com.api.ForoHub.infra.exception.DuplicateTopicException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public Topic createTopic(TopicDTO topicDto) {
        if (topicRepository.existsByTitleAndMessage(topicDto.title(), topicDto.message())) {
            throw new DuplicateTopicException("Un tópico con el mismo título y mensaje ya existe.");
        }

        Topic topic = new Topic();
        topic.setTitle(topicDto.title());
        topic.setMessage(topicDto.message());
        topic.setCreationDate(LocalDateTime.now());
        topic.setStatus(TopicStatus.OPEN);
        topic.setAuthorId(topicDto.authorId());
        topic.setCourseId(topicDto.courseId());
        return topicRepository.save(topic);
    }

    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    public List<Topic> getTop10Topics() {
        return topicRepository.findTop10ByOrderByCreationDateAsc();
    }

    public List<Topic> getTopicsByCourseAndYear(Integer courseId, int year) {
        LocalDateTime start = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(year, 12, 31, 23, 59);
        return topicRepository.findByCourseIdAndCreationDateBetween(courseId, start, end);
    }

    public Page<Topic> getTopicsWithPagination(Pageable pageable) {
        return topicRepository.findAll(pageable);
    }

    public Optional<Topic> getTopicById(Long id) {
        return topicRepository.findById(id);
    }

    public Topic updateTopic(Long id, TopicDTO topicDto) {
        Topic existingTopic = getTopicById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el tópico con el ID proporcionado."));
        
        existingTopic.setTitle(topicDto.title());
        existingTopic.setMessage(topicDto.message());
        existingTopic.setAuthorId(topicDto.authorId());
        existingTopic.setCourseId(topicDto.courseId());

        return topicRepository.save(existingTopic);
    }

    public List<Topic> getTopicsByCourseIdAndDateRange(Integer courseId, LocalDateTime start, LocalDateTime end) {
        return topicRepository.findByCourseIdAndCreationDateBetween(courseId, start, end);
    }
    
    public boolean deleteTopic(Long id) {
        Optional<Topic> existingTopic = getTopicById(id);
        if (existingTopic.isPresent()) {
            topicRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

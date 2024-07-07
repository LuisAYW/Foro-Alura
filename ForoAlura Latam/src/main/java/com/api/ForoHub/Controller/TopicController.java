package com.api.ForoHub.Controller;


import com.api.ForoHub.Topic.Topic;
import com.api.ForoHub.Topic.TopicDTO;
import com.api.ForoHub.Topic.TopicService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody @Valid TopicDTO topicDTO) {
        Topic createdTopic = topicService.createTopic(topicDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTopic);
    }

    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/top10")
    public ResponseEntity<List<Topic>> getTop10Topics() {
        List<Topic> topics = topicService.getTop10Topics();
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Topic>> getTopicsByCourseAndYear(@RequestParam Integer courseId, @RequestParam int year) {
        List<Topic> topics = topicService.getTopicsByCourseAndYear(courseId, year);
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Topic>> getTopicsWithPagination(@PageableDefault(size = 10) Pageable pageable) {
        Page<Topic> topics = topicService.getTopicsWithPagination(pageable);
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        Optional<Topic> topic = topicService.getTopicById(id);
        if (topic.isPresent()) {
            return ResponseEntity.ok(topic.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Topic> updateTopic(@PathVariable Long id, @RequestBody @Valid TopicDTO topicDTO) {
        Optional<Topic> existingTopic = topicService.getTopicById(id);
        if (existingTopic.isPresent()) {
            Topic updatedTopic = topicService.updateTopic(id, topicDTO);
            return ResponseEntity.ok(updatedTopic);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        if (topicService.deleteTopic(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

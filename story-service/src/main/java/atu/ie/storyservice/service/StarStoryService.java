package atu.ie.storyservice.service;

import atu.ie.storyservice.client.UserClient;
import atu.ie.storyservice.model.StarStory;
import atu.ie.storyservice.model.UserDTO;
import atu.ie.storyservice.repository.StarStoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StarStoryService {

    private final StarStoryRepository starStoryRepository;

    private final UserClient userClient;

    public StarStory createStory(StarStory storyDetails) {
        UserDTO user = userClient.getUserById(storyDetails.getUserId());

        System.out.println("Success! Creating story for verified user: " + user.getName());

        return starStoryRepository.save(storyDetails);
    }

    public List<StarStory> getAllStories() {
        return starStoryRepository.findAll();
    }

    public List<StarStory> getUserStories(Long userId) {
        return starStoryRepository.findByUserId(userId);
    }

    public StarStory updateStory(Long storyId, StarStory updatedStory) {
        StarStory existingStory = starStoryRepository.findById(storyId)
                .orElseThrow(() -> new RuntimeException("Story not found"));

        existingStory.setTitle(updatedStory.getTitle());
        existingStory.setContent(updatedStory.getContent());
        return starStoryRepository.save(existingStory);
    }

    public void deleteStory(Long storyId) {
        starStoryRepository.deleteById(storyId);
    }
}
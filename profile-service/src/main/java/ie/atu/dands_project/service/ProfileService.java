package ie.atu.dands_project.service;

import ie.atu.dands_project.model.Profile;
import ie.atu.dands_project.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public Profile createProfile(Long userId, String username) {
        String avatarUrl = "https://i.pravatar.cc/150?u=" + userId;

        Profile profile = new Profile();
        profile.setUserId(userId);
        profile.setName(username);
        profile.setBio("");
        profile.setLocation("");
        profile.setProfilePictureUrl(avatarUrl);

        return profileRepository.save(profile);
    }

    public Profile getProfile(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));
    }

    public Profile updateProfile(Long userId, Profile profileUpdates) {
        Profile existingProfile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        existingProfile.setName(profileUpdates.getName());
        existingProfile.setBio(profileUpdates.getBio());
        existingProfile.setLocation(profileUpdates.getLocation());
        existingProfile.setProfilePictureUrl(profileUpdates.getProfilePictureUrl());

        return profileRepository.save(existingProfile);
    }
}
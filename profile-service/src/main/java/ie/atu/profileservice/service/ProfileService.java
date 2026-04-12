package ie.atu.profileservice.service;

import ie.atu.profileservice.model.Profile;
import ie.atu.profileservice.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public Profile createProfile(Long userId, String username) {

        if (profileRepository.findByUserId(userId).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Profile already exists for this user.");
        }

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

        if (profileUpdates.getName() != null) {
            existingProfile.setName(profileUpdates.getName());
        }
        if (profileUpdates.getBio() != null) {
            existingProfile.setBio(profileUpdates.getBio());
        }
        if (profileUpdates.getLocation() != null) {
            existingProfile.setLocation(profileUpdates.getLocation());
        }
        if (profileUpdates.getProfilePictureUrl() != null) {
            existingProfile.setProfilePictureUrl(profileUpdates.getProfilePictureUrl());
        }

        return profileRepository.save(existingProfile);
    }
}
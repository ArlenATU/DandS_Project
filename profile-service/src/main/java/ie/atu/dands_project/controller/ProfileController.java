package ie.atu.dands_project.controller;

import ie.atu.dands_project.model.Profile;
import ie.atu.dands_project.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/{userId}")
    public ResponseEntity<Profile> createProfile(@PathVariable Long userId,
                                                 @RequestParam String username) {
        return ResponseEntity.ok(profileService.createProfile(userId, username));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Profile> getProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getProfile(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Profile> updateProfile(@PathVariable Long userId,
                                                 @RequestBody Profile profile) {
        return ResponseEntity.ok(profileService.updateProfile(userId, profile));
    }
}
package ie.atu.dands_project.controller;

import ie.atu.dands_project.model.Profile;
import ie.atu.dands_project.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/{userId}")
    public Profile createProfile(@PathVariable Long userId, @RequestParam String username) {
        return profileService.createProfile(userId, username);
    }

    @GetMapping("/{userId}")
    public Profile getProfile(@PathVariable Long userId){
        return profileService.getProfile(userId);
    }

    @PutMapping("/{userId}")
    public Profile updateProfile(@PathVariable Long userId, @RequestBody Profile profile){
        return profileService.updateProfile(userId, profile);
    }
}
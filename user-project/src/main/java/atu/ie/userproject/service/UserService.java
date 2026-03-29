package atu.ie.userproject.service;

import atu.ie.userproject.model.User;
import atu.ie.userproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(User user) {

        // Check if email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Set creation time
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public Map<String, Object> login(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return Map.of(
                "message", "Login successful",
                "userId", user.getId()
        );
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

package atu.ie.userproject.service;

import atu.ie.userproject.model.User;
import atu.ie.userproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User register(User user) {
        // Return 409 Conflict if email is taken
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public Map<String, Object> login(String email, String password) {
        // Return 404 Not Found if email doesn't exist
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Return 401 Unauthorized for bad passwords
        if (!user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        return Map.of(
                "message", "Login successful",
                "userId", user.getId()
        );
    }

    public User getUserById(Long id) {
        // Return 404 Not Found instead of 500 Server Error
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
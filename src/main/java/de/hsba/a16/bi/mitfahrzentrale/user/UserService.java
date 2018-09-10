package de.hsba.a16.bi.mitfahrzentrale.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@Component
public class UserService {

	@Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
	// This user were initiated to be able to login to the app with empty schemas
            createUserByEntiy(new User("admin", "admin"));
        }
    }

    // get user by username
	public User getUserByName(String name) {
		return userRepository.findByName(name);
	}

	// Einen neuen Nutzer erstellen
    public void createUserByEntiy (User user){
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	userRepository.save(user);
    }
    public User findUserById (Long id) {
        return userRepository.findById(id).orElse(null);
    }
}

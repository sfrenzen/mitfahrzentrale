package de.hsba.a16.bi.mitfahrzentrale.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

	// Diese Nutzer wurde schon definiert und damit ist die Einlogen schon m�glich
    @PostConstruct
    public void init() {
        if (userRepository.count() == 0) {
//            createUserByEntiy(new User("admin", "admin", "ADMIN", "Admin", "Last Name Admin", "mo@mail.com"));
            createUserByEntiy(new User("admin", "admin"));
        }
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

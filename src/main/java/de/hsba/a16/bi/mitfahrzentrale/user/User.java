package de.hsba.a16.bi.mitfahrzentrale.user;

import de.hsba.a16.bi.mitfahrzentrale.trip.Rating;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
/**
 *todo: Owner verbinden mit dem Trip
*/
@Entity
public class User implements Comparable<User> {

    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserAdapter) {
            return ((UserAdapter) principal).getUser();
        }

        return null;
    }


    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    //Durchschnittsbwertung des Anbieters
    //Fuer Benutzer, die keine Anbieter sind, ist der Wert 0
    private float averageRating = 0;

    //    private String role;
//
//    @Column(nullable = false)
//    private String firstName;
//	@Column(nullable = false)
//	private String lastName;
//
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	@Column(nullable = false)
//	private String email;
//
//	public String getFirstName() {
//		return firstName;
//	}
//
//	public void setFirstName(String fristName) {
//		this.firstName = fristName;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}

	public User() {
    }

    public User(String name, String password
//                String role, String firstName, String lastName, String email
    ) {
        this.name= name;
        this.password = password;
//        this.role = role;
//        this.firstName = firstName;
//        this.lastName= lastName;
//        this.email = email;
    }


    //
    // Getter und Setter
    //

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }

    @Override
    public int compareTo(User other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}

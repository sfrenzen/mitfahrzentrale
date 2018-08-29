package de.hsba.a16.bi.mitfahrzentrale.trip;

import de.hsba.a16.bi.mitfahrzentrale.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TripRatingRepository extends JpaRepository<Rating, Long> {
    @Query("select distinct r from Rating r where r.trip.owner = :user")
    List<Rating> findRatingsForTripOwner(@Param("user") User user);

}

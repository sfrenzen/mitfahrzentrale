package de.hsba.a16.bi.mitfahrzentrale.trip;

import de.hsba.a16.bi.mitfahrzentrale.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
@Transactional
public interface TripRepository extends JpaRepository<Trip, Long> {
    // Filter-Methode (um nach Abfahrts- und/oder Ankunftsort zu suchen
    @Query("select distinct t from Trip t where t.start = :start and t.end = :end")
    public Collection<Trip> searchTrips(@Param("start") String start, @Param("end") String end);

	@Query("select distinct j from Trip j where j.owner= :user")
	List<Trip> findAllByOwnedByCurrentUser(@Param("user") User user);
}

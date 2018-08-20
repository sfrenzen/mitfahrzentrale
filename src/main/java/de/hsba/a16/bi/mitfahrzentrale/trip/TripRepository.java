package de.hsba.a16.bi.mitfahrzentrale.trip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TripRepository extends JpaRepository<Trip, Long> {
}

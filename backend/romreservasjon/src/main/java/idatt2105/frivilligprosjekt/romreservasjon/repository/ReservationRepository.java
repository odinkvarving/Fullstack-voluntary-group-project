package idatt2105.frivilligprosjekt.romreservasjon.repository;

import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Integer> {

    /**
     * Custom method for finding all Reservations in a specific Section by id
     *
     * @param sectionId the if of the Section
     * @return a List of Reservations that was found in the Section
     */
    List<Reservation> findReservationsBySectionId(int sectionId);
}

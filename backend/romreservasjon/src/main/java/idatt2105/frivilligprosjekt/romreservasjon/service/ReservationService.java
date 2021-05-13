package idatt2105.frivilligprosjekt.romreservasjon.service;

import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;
import idatt2105.frivilligprosjekt.romreservasjon.repository.AccountRepository;
import idatt2105.frivilligprosjekt.romreservasjon.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * Method for finding all Accounts registered in the database
     *
     * @return a collection of all Accounts registered in the database
     */
    public List<Reservation> findAll() {
        Iterable<Reservation> itAccounts = reservationRepository.findAll();
        List<Reservation> reservations = new ArrayList<>();

        itAccounts.forEach(reservations::add);

        return reservations;
    }

    /**
     *Method for registering a new Reservation to the database
     *
     * @param reservation the new Reservation to be registered
     * @return true or false
     */
    public boolean saveReservation(Reservation reservation) {
       try {
            reservation.setSection(reservation.getSection());
            reservation.setAccount(reservation.getAccount());
            reservationRepository.save(reservation);
            return true;
       }catch (DataAccessException e) {
           e.printStackTrace();
           logger.info("Could not fetch either the Account or Section for this reservation.");
           return false;
       }
    }

    /**
     * Method for updating a specific Account
     *
     * @param id the id of the account (not used)
     * @param reservation the updated version of the Reservation with the specified ID
     * @return the Reservation that was updated
     */
    public Reservation updateReservation(int id, Reservation reservation) {
        try {
            logger.info("Reservation updated");
            reservation.setId(id);
            return reservationRepository.save(reservation);
        } catch (DataAccessException e) {
            logger.info("Could not update reservation");
        }
        return null;
    }

    public Reservation findReservationById(int id) {
        try {
            return reservationRepository.findById(id).orElseThrow(NoSuchElementException::new);
        }catch (DataAccessException e) {
            e.printStackTrace();
            logger.info("Could not find a reservation with this ID");
        }
        return null;
    }
}

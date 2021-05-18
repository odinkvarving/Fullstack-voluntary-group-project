package idatt2105.frivilligprosjekt.romreservasjon.service;

import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;
import idatt2105.frivilligprosjekt.romreservasjon.model.Section;
import idatt2105.frivilligprosjekt.romreservasjon.repository.ReservationRepository;
import idatt2105.frivilligprosjekt.romreservasjon.repository.SectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SectionRepository sectionRepository;

    /**
     * Method for finding all Reservations registered in the database
     *
     * @return a collection of all Reservations registered in the database
     */
    public List<Reservation> findAll() {
        Iterable<Reservation> itReservations = reservationRepository.findAll();
        List<Reservation> reservations = new ArrayList<>();

        itReservations.forEach(reservations::add);

        return reservations;
    }

    /**
     *Method for registering a new Reservation to the database
     *
     * @param reservation the new Reservation to be registered
     * @return true or false
     */
    public boolean saveReservation(Reservation reservation) {

        Section section = sectionRepository.findById(reservation.getSection().getId()).orElse(null);
        List<Reservation> relevantDateReservations = section.getInReservations().stream().filter(res -> {
           return res.getFrom_date().getYear() == reservation.getFrom_date().getYear()
                   && res.getFrom_date().getMonth() == reservation.getFrom_date().getMonth()
                   && res.getFrom_date().getDayOfMonth() == reservation.getFrom_date().getDayOfMonth();
        }).collect(Collectors.toList());
        for(Reservation res : relevantDateReservations){
            if((res.getFrom_date().isBefore(reservation.getTo_date()) && reservation.getFrom_date().isBefore(res.getFrom_date())) || (res.getTo_date().isAfter(reservation.getFrom_date())
            && res.getFrom_date().isBefore(reservation.getFrom_date())) || res.getTo_date().isEqual(reservation.getTo_date()) || res.getFrom_date().isEqual(reservation.getFrom_date())){
                logger.info("Reservation is overlapping with another reservation...");
                return false;
            }
        }

       try {
            reservation.setSection(reservation.getSection());
            reservation.setAccount(reservation.getAccount());
            reservationRepository.save(reservation);
            return true;
       }catch (DataAccessException e) {
           e.printStackTrace();
           logger.info("Could not fetch either the Account or Section for this reservation.");
       }
       return false;
    }

    /**
     * Method for updating a specific Reservation
     *
     * @param id the id of the Reservation
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

    /**
     * Method for finding a Reservation with a specific ID
     *
     * @param id the id of the Reservation
     * @return the Reservation that was found
     */
    public Reservation findReservationById(int id) {
        try {
            return reservationRepository.findById(id).orElseThrow(NoSuchElementException::new);
        }catch (DataAccessException e) {
            e.printStackTrace();
            logger.info("Could not find a reservation with this ID");
        }
        return null;
    }

    /**
     * Method for deleting a specific Reservation
     *
     * @param id the ID of the Reservation to be deleted
     */
    public boolean deleteReservation(int id) {
        try {
            reservationRepository.deleteById(id);
            return true;
        }catch (DataAccessException e) {
            e.printStackTrace();
            logger.info("Could not delete this reservation. ID does not exist");
        }

        return false;
    }
}

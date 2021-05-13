package idatt2105.frivilligprosjekt.romreservasjon.controller;

import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;
import idatt2105.frivilligprosjekt.romreservasjon.service.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ReservationController {

    Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private ReservationService reservationService;

    /**
     * GetMapping for getting all registered Reservations
     *
     * @return a list of all registered Reservations
     */
    @GetMapping("reservations")
    public List<Reservation> getAllReservations() {
        return reservationService.findAll();
    }

    /**
     * PostMapping for saving a new Account to the database
     *
     * @param reservation requesting the body of the Reservation to save
     * @return true or false whether the Reservation was created successfully or not
     */
    @PostMapping("reservations")
    public boolean saveReservation(@RequestBody Reservation reservation) {
        logger.info("Trying to save reservation...");
        boolean success = reservationService.saveReservation(reservation);
        if (success) {
            logger.info("Reservation has been registered.");
        }
        return success;
    }

    /**
     * PutMapping for updating a specific Account
     *
     * @param newReservation requesting the body of a new Reservation (updated version)
     * @param reservation_id the PathVariable of the id for the Reservation
     * @return the Account that was updated
     */
    @PutMapping("reservations/{reservation_id}")
    public Reservation updateReservation(@RequestBody Reservation newReservation, @PathVariable("reservation_id") int reservation_id) {
        logger.info("Trying to update reservation to: \n" + newReservation.toString());
        return reservationService.updateReservation(reservation_id, newReservation);
    }

    /**
     * GetMapping for finding a specific Reservation
     *
     * @param reservation_id the PathVariable of the ID for the Reservation
     * @return the Reservation that was found
     */
    @GetMapping("reservations/{reservation_id}")
    public Reservation findReservationById(@PathVariable int reservation_id) {
        return reservationService.findReservationById(reservation_id);
    }

    /**
     * DeleteMapping for deleting a specific Reservation
     *
     * @param reservation_id the PathVariable of the ID for the Reservation
     */
    @DeleteMapping("reservations/{reservation_id}")
    public void deleteReservation(@PathVariable int reservation_id) {
        reservationService.deleteReservation(reservation_id);
    }

}

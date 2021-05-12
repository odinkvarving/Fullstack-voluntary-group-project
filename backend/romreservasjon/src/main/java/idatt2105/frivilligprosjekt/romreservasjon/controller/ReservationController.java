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
    @GetMapping("reservations/")
    public List<Reservation> getAllAccounts() {
        return reservationService.findAll();
    }

    /**
     * PostMapping for saving a new Account to the database
     *
     * @param reservation requesting the body of the Reservation to save
     * @return true or false whether the Reservation was created successfully or not
     */
    @PostMapping("reservations/register")
    public boolean saveAccount(@RequestBody Reservation reservation) {
        logger.info("Trying to save reservation:\n" + reservation.toString());
        boolean success = reservationService.saveReservation(reservation);
        if (success) {
            logger.info("Success!");
        }
        return success;
    }
}

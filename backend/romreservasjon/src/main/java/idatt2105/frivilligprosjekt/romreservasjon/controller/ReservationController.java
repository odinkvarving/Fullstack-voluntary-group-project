package idatt2105.frivilligprosjekt.romreservasjon.controller;

import idatt2105.frivilligprosjekt.romreservasjon.model.DTO.ReservationDTO;
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
    @GetMapping("/reservations")
    public List<ReservationDTO> getAllReservations() {
        return reservationService.findAll();
    }

    /**
     * PostMapping for saving a new Account to the database
     *
     * @param reservation requesting the body of the Reservation to save
     * @return true or false whether the Reservation was created successfully or not
     */
    @PostMapping("/reservations")
    public boolean saveReservation(@RequestBody Reservation reservation, @RequestHeader("authorization") String jwt) {
        logger.info("Trying to save reservation...");
        boolean success = reservationService.saveReservation(reservation, jwt);
        if (success) {
            logger.info("Reservation has been registered!");
        }
        return success;
    }

    @PostMapping("/reservations/room")
    public boolean saveRoomReservation(@RequestBody List<Reservation> reservations, @RequestHeader("authorization") String jwt){
        logger.info("Trying to save room reservation...");
        boolean success = reservationService.saveRoomReservation(reservations, jwt);
        if(success){
            logger.info("Room reservation was successfully registered!");
        }
        return success;
    }

    /**
     * PutMapping for updating a specific Reservation
     *
     * @param newReservation requesting the body of a new Reservation (updated version)
     * @param reservation_id the PathVariable of the id for the Reservation
     * @return the Reservation that was updated
     */
    @PutMapping("/reservations/{reservation_id}")
    public Reservation updateReservation(@PathVariable("reservation_id") int reservation_id, @RequestBody Reservation newReservation, @RequestHeader("authorization") String jwt) {
        logger.info("Trying to update reservation to: \n" + newReservation.toString());
        return reservationService.updateReservation(reservation_id, newReservation, jwt);
    }

    /**
     * GetMapping for finding a specific Reservation
     *
     * @param reservation_id the PathVariable of the ID for the Reservation
     * @return the Reservation that was found
     */
    @GetMapping("/reservations/{reservation_id}")
    public ReservationDTO findReservationById(@PathVariable int reservation_id) {
        return reservationService.findReservationById(reservation_id);
    }

    /**
     * DeleteMapping for deleting a specific Reservation
     *
     * @param reservation_id the PathVariable of the ID for the Reservation
     */
    @DeleteMapping("/reservations/{reservation_id}")
    public boolean deleteReservation(@PathVariable int reservation_id, @RequestHeader("authorization") String jwt) {
        return reservationService.deleteReservation(reservation_id, jwt);
    }

    @GetMapping("/reservations/sections/{section_id}")
    public List<ReservationDTO> findReservationsBySectionId(@PathVariable int section_id) {
        return reservationService.findReservationsBySectionId(section_id);
    }

}

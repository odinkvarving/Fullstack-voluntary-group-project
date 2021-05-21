package idatt2105.frivilligprosjekt.romreservasjon.controller;

import idatt2105.frivilligprosjekt.romreservasjon.model.Equipment;
import idatt2105.frivilligprosjekt.romreservasjon.model.EquipmentReservation;
import idatt2105.frivilligprosjekt.romreservasjon.service.EquipmentReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class EquipmentReservationController {

    Logger logger = LoggerFactory.getLogger(EquipmentReservationController.class);

    @Autowired
    private EquipmentReservationService equipmentReservationService;

    /**
     * GetMapping for getting all registered EquipmentReservations
     *
     * @return a list of all registered EquipmentReservations
     */
    @GetMapping("/equipment-reservations")
    public List<EquipmentReservation> getAllEquipmentsReservations() {
        return equipmentReservationService.findAll();
    }

    /**
     * PostMapping for saving a new EquipmentReservation to the database
     *
     * @param reservation requesting the body of the EquipmentReservation to save
     * @return true or false whether the EquipmentReservation was created successfully or not
     */
    @PostMapping("/equipment-reservations")
    public boolean saveEquipmentReservation(@RequestBody EquipmentReservation reservation, @RequestHeader("authorization") String jwt) {
        logger.info("Trying to save reservation...");
        boolean success = equipmentReservationService.saveEquipmentReservation(reservation, jwt);
        if (success) {
            logger.info("Reservation has been registered.");
        }
        return success;
    }

    /**
     * PutMapping for updating a specific EquipmentReservation
     *
     * @param newReservation requesting the body of a new EquipmentReservation (updated version)
     * @param reservation_id the PathVariable of the id for the EquipmentReservation
     * @return the EquipmentReservation that was updated
     */
    @PutMapping("/equipment-reservations/{equipment_reservation_id}")
    public EquipmentReservation updateEquipmentReservation(@PathVariable("equipment_reservation_id") int reservation_id, @RequestBody EquipmentReservation newReservation, @RequestHeader("authorization") String jwt) {
        logger.info("Trying to update reservation to: \n" + newReservation.toString());
        return equipmentReservationService.updateEquipmentReservation(reservation_id, newReservation, jwt);
    }

    /**
     * GetMapping for finding a specific EquipmentReservation
     *
     * @param equipment_reservation_id the PathVariable for the id of the EquipmentReservation
     * @return the EquipmentReservation that was found
     */
    @GetMapping("/equipment-reservations/{equipment_reservation_id}")
    public EquipmentReservation findEquipmentReservationById(@PathVariable int equipment_reservation_id){
        return equipmentReservationService.findEquipmentReservationById(equipment_reservation_id);
    }

    /**
     * DeleteMapping for deleting a specific EquipmentReservation
     *
     * @param equipment_reservation_id the PathVariable of the ID for the EquipmentReservation
     */
    @DeleteMapping("/equipment-reservations/{equipment_reservation_id}")
    public boolean deleteEquipmentReservation(@PathVariable int equipment_reservation_id, @RequestHeader("authorization") String jwt) {
        return equipmentReservationService.deleteEquipmentReservation(equipment_reservation_id, jwt);
    }

    /**
     * GetMapping for finding Equipment in a specific EquipmentReservation
     * @param equipment_reservation_id the id of the EquipmentReservation
     * @return the Equipment that was found
     */
    @GetMapping("equipment-reservations/{equipment_reservation_id}/equipments")
    public Equipment findReservationEquipment(@PathVariable int equipment_reservation_id) {
        return equipmentReservationService.findReservationEquipment(equipment_reservation_id);
    }
}

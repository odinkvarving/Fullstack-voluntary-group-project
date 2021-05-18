package idatt2105.frivilligprosjekt.romreservasjon.controller;

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
    @GetMapping("/equipments")
    public List<EquipmentReservation> getAllEquipmentsReservations() {
        return equipmentReservationService.findAll();
    }

    /**
     * PostMapping for saving a new EquipmentReservation to the database
     *
     * @param reservation requesting the body of the EquipmentReservation to save
     * @return true or false whether the EquipmentReservation was created successfully or not
     */
    @PostMapping("/equipments")
    public boolean saveEquipmentReservation(@RequestBody EquipmentReservation reservation) {
        logger.info("Trying to save reservation...");
        boolean success = equipmentReservationService.saveEquipmentReservation(reservation);
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
    @PutMapping("/equipments/{equipment_reservation_id}")
    public EquipmentReservation updateEquipmentReservation(@PathVariable("equipment_reservation_id") int reservation_id, @RequestBody EquipmentReservation newReservation) {
        logger.info("Trying to update reservation to: \n" + newReservation.toString());
        return equipmentReservationService.updateEquipmentReservation(reservation_id, newReservation);
    }

    /**
     * GetMapping for finding a specific EquipmentReservation
     *
     * @param equipment_reservation_id the PathVariable for the id of the EquipmentReservation
     * @return the EquipmentReservation that was found
     */
    @GetMapping("equipments/{equipment_reservation_id}")
    public EquipmentReservation findEquipmentReservationById(@PathVariable int equipment_reservation_id){
        return equipmentReservationService.findEquipmentReservationById(equipment_reservation_id);
    }

    /**
     * DeleteMapping for deleting a specific EquipmentReservation
     *
     * @param equipment_reservation_id the PathVariable of the ID for the EquipmentReservation
     */
    @DeleteMapping("/equipments/{equipment_reservation_id}")
    public boolean deleteEquipmentReservation(@PathVariable int equipment_reservation_id) {
        return equipmentReservationService.deleteEquipmentReservation(equipment_reservation_id);
    }
}

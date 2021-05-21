package idatt2105.frivilligprosjekt.romreservasjon.controller;

import idatt2105.frivilligprosjekt.romreservasjon.model.Equipment;
import idatt2105.frivilligprosjekt.romreservasjon.service.EquipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class EquipmentController {

    Logger logger = LoggerFactory.getLogger(EquipmentController.class);

    @Autowired
    private EquipmentService equipmentService;

    /**
     * GetMapping for getting all registered Equipment
     *
     * @return a list of all registered Equipment
     */
    @GetMapping("/equipment")
    public List<Equipment> getAllEquipment() {
        return equipmentService.findAllEquipment();
    }

    /**
     * GetMapping for finding Equipment by ID
     *
     * @param id the id of the Equipment to be found
     * @return the Equipment that was found
     */
    @GetMapping("/equipment/{id}")
    public Equipment findEquipmentById(@PathVariable int id){
        return equipmentService.findEquipmentById(id);
    }

    /**
     * PostMapping for saving a new Equipment to the database
     *
     * @param equipment requesting the body of the Equipment to save
     * @return true or false whether the Equipment was created successfully or not
     */
    @PostMapping("/equipment")
    public boolean saveEquipment(@RequestBody Equipment equipment) {
        logger.info("Trying to save equipment:\n" + equipment.toString());
        boolean success = equipmentService.saveEquipment(equipment);
        if (success) {
            logger.info("The equipment was successfully created");
        }
        return success;
    }

    /**
     * PutMapping for updating a specific Equipment
     *
     * @param newEquipment requesting the body of a new Equipment (updated version)
     * @param equipment_id the PathVariable of the id for the Equipment
     * @return the Equipment that was updated
     */
    @PutMapping("/equipment/{equipment_id}")
    public Equipment updateEquipment(@PathVariable("equipment_id") int equipment_id, @RequestBody Equipment newEquipment) {
        return equipmentService.updateEquipment(equipment_id, newEquipment);
    }

    /**
     * DeleteMapping for deleting an Equipment
     *
     * @param equipment_id the PathVariable for the id of the Equipment
     */
    @DeleteMapping("/equipment/{equipment_id}")
    public void deleteEquipment(@PathVariable("equipment_id") int equipment_id) {
        equipmentService.deleteEquipment(equipment_id);
    }
}

package idatt2105.frivilligprosjekt.romreservasjon.service;

import idatt2105.frivilligprosjekt.romreservasjon.model.Equipment;
import idatt2105.frivilligprosjekt.romreservasjon.repository.EquipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService {

    private static final Logger logger = LoggerFactory.getLogger(EquipmentService.class);

    @Autowired
    private EquipmentRepository equipmentRepository;

    /**
     * Method for finding all Equipment registered in the database
     *
     * @return a collection of all Equipment registered in the database
     */
    public List<Equipment> findAllEquipment() {
        Iterable<Equipment> itEquipment = equipmentRepository.findAll();
        List<Equipment> equipment = new ArrayList<>();

        itEquipment.forEach(equipment::add);

        return equipment;
    }

    /**
     * Method for finding a specific Equipment
     *
     * @param id the id of the Equipment to be found
     * @return the Equipment that was found
     */
    public Equipment findEquipmentById(int id){
        Optional<Equipment> equipment = equipmentRepository.findById(id);
        return equipment.orElse(null);
    }

    /**
     * Method for registering a new Equipment
     *
     * @param equipment the new Equipment to be registered
     * @return true if the Equipment was registered, false if an Equipment with the given id already exists
     */
    public boolean saveEquipment(Equipment equipment) {
        Optional<Equipment> eq = equipmentRepository.findById(equipment.getId());
        if (eq.isPresent()) {
            logger.info("Error! Could not create equipment, ID already exists");
            return false;
        } else {
            equipmentRepository.save(equipment);
            return true;
        }
    }

    /**
     * Method for updating a specific Equipment
     *
     * @param id the id of the Equipment
     * @param equipment the updated version of the Equipment with the specified ID
     * @return the Equipment that was updated
     */
    public Equipment updateEquipment(int id, Equipment equipment) {
        try {
            logger.info("Equipment updated!");
            equipment.setId(id);
            return equipmentRepository.save(equipment);
        } catch (DataAccessException e) {
            logger.info("Could not update equipment");
        }
        return null;
    }

    /**
     * Method for deleting an Equipment with the specific ID from the database
     * If no Equipment with the given ID exists, nothing happens
     *
     * @param id the id of the Equipment to be deleted
     */
    public void deleteEquipment(int id) {
        try {
            this.equipmentRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            logger.error("null was passed as an argument while trying to delete equipment");
        }
    }
}

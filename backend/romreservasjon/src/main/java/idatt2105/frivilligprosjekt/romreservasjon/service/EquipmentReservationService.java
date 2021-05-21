package idatt2105.frivilligprosjekt.romreservasjon.service;

import idatt2105.frivilligprosjekt.romreservasjon.model.Equipment;
import idatt2105.frivilligprosjekt.romreservasjon.model.EquipmentReservation;
import idatt2105.frivilligprosjekt.romreservasjon.repository.EquipmentReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EquipmentReservationService {

    private static final Logger logger = LoggerFactory.getLogger(EquipmentReservationService.class);

    @Autowired
    private EquipmentReservationRepository equipmentReservationRepository;

    @Autowired
    private AccountService accountService;

    /**
     * Method for finding all EquipmentReservations registered in the database
     *
     * @return a collection of all EquipmentReservations registered in the database
     */
    public List<EquipmentReservation> findAll() {
        Iterable<EquipmentReservation> itReservations = equipmentReservationRepository.findAll();
        List<EquipmentReservation> reservations = new ArrayList<>();

        itReservations.forEach(reservations::add);

        return reservations;
    }

    /**
     *Method for registering a new EquipmentReservation to the database
     *
     * @param reservation the new EquipmentReservation to be registered
     * @return true or false
     */
    public boolean saveEquipmentReservation(EquipmentReservation reservation, String jwt) {

        if(accountService.findById(reservation.getAccount().getId(), jwt) == null){
            logger.info("Can't create equipmentreservation for another user unless you are admin...");
            return false;
        }else {
            try {
                reservation.setEquipment(reservation.getEquipment());
                reservation.setAccount(reservation.getAccount());
                equipmentReservationRepository.save(reservation);
                return true;
            } catch (DataAccessException e) {
                e.printStackTrace();
                logger.info("Could not fetch either the Account or Equipment for this reservation.");
                return false;
            }
        }
    }

    /**
     * Method for updating a specific EquipmentReservation
     *
     * @param id the id of the EquipmentReservation
     * @param reservation the updated version of the EquipmentReservation with the specified ID
     * @return the EquipmentReservation that was updated
     */
    public EquipmentReservation updateEquipmentReservation(int id, EquipmentReservation reservation, String jwt) {
        if(!accountService.isAdmin(jwt)){
            logger.info("Only admin can update equipmentreservations...");
            return null;
        }else {
            try {
                logger.info("Reservation updated");
                reservation.setId(id);
                return equipmentReservationRepository.save(reservation);
            } catch (DataAccessException e) {
                logger.info("Could not update reservation");
            }
            return null;
        }
    }

    /**
     * Method for finding an EquipmentReservation with a specific ID
     *
     * @param id the id of the EquipmentReservation
     * @return the EquipmentReservation that was found
     */
    public EquipmentReservation findEquipmentReservationById(int id) {
        try {
            return equipmentReservationRepository.findById(id).orElseThrow(NoSuchElementException::new);
        }catch (DataAccessException e) {
            e.printStackTrace();
            logger.info("Could not find a reservation with this ID");
        }
        return null;
    }

    /**
     * Method for deleting a specific EquipmentReservation
     *
     * @param id the ID of the EquipmentReservation to be deleted
     */
    public boolean deleteEquipmentReservation(int id, String jwt) {
        if(accountService.isAdmin(jwt)){
            logger.info("Only admin can delete reservations...");
            return false;
        }else{
            try {
                equipmentReservationRepository.deleteById(id);
                return true;
            }catch (DataAccessException e) {
                e.printStackTrace();
                logger.info("Could not delete this reservation. ID does not exist");
            }

            return false;
        }
    }

    /**
     * Method for finding Equipment in a specific EquipmentReservation
     *
     * @param id the id of the EquipmentReservation
     * @return the Equipment that was found
     */
    public Equipment findReservationEquipment(int id) {
        try {
            logger.info("Successfully found all equipment");
            return equipmentReservationRepository.findById(id).orElseThrow(NoSuchElementException::new).getEquipment();
        }catch (DataAccessException e) {
            e.printStackTrace();
            logger.info("Could not find any equipment for this reservation");
        }
        return null;
    }
}

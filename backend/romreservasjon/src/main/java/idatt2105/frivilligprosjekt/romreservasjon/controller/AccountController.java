package idatt2105.frivilligprosjekt.romreservasjon.controller;

import idatt2105.frivilligprosjekt.romreservasjon.model.Account;
import idatt2105.frivilligprosjekt.romreservasjon.model.EquipmentReservation;
import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;
import idatt2105.frivilligprosjekt.romreservasjon.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    /**
     * GetMapping for getting all registered Accounts
     *
     * @return a list of all registered Accounts
     */
    @GetMapping("accounts")
    public List<Account> getAllAccounts(@RequestHeader("authorization") String jwt) {
        return accountService.findAll(jwt);
    }

    /**
     * Find account by email
     *
     * @param email
     * @return
     */
    @GetMapping("accounts/email={email}")
    public Account findAccountByEmail(@PathVariable String email, @RequestHeader("authorization") String jwt){
        return accountService.findByEmail(email, jwt);
    }


    /**
     * Find account by ID
     *
     * @param id
     * @return
     */
    @GetMapping("/accounts/{id}")
    public Account findById(@PathVariable int id, @RequestHeader("authorization") String jwt){
        return accountService.findById(id, jwt);
    }

    /**
     * PostMapping for saving a new Account to the database
     *
     * @param account requesting the body of the Account to save
     * @return true or false whether the Account was created successfully or not
     */
    @PostMapping("/accounts")
    public boolean saveAccount(@RequestBody Account account, @RequestHeader("authorization") String jwt) {
        logger.info("Trying to save account:\n" + account.toString());
        boolean success = accountService.saveAccount(account, jwt);
        if (success) {
            logger.info("The account was successfully created");
        }
        return success;
    }

    /**
     * PutMapping for updating a specific Account
     *
     * @param newAccount requesting the body of a new Account (updated version)
     * @param account_id the PathVariable of the id for the Account
     * @return the Account that was updated
     */
    @PutMapping("/accounts/{account_id}")
    public Account updateAccount(@PathVariable("account_id") int account_id, @RequestBody Account newAccount, @RequestHeader("authorization") String jwt) {
        return accountService.updateAccount(account_id, newAccount, jwt);
    }

    /**
     * DeleteMapping for deleting an Account
     *
     * @param account_id the PathVariable for the id of the Account
     */
    @DeleteMapping("/accounts/{account_id}")
    public void deleteAccount(@PathVariable("account_id") int account_id, @RequestHeader("authorization") String jwt) {
        accountService.deleteAccount(account_id, jwt);
    }

    /**
     * GetMapping for finding all Reservations for an Account
     *
     * @param account_id the ID of the Account
     * @return a Set containing the Reservations for the Account
     */
    @GetMapping("/accounts/{account_id}/reservations")
    public Set<Reservation> findAccountReservations(@PathVariable int account_id) {
       logger.info("Finding reservations for Account with ID: " + account_id);
        return accountService.findAccountReservations(account_id);
    }

    /**
     * GetMapping for finding all EquipmentReservations for an Account
     *
     * @param account_id the ID of the Account
     * @return a Set containing the EquipmentReservations for the Account
     */
    @GetMapping("/accounts/{account_id}/equipment-reservations")
    public Set<EquipmentReservation> findAccountEquipmentReservations(@PathVariable int account_id) {
        logger.info("Finding equipment-reservations for Account with ID: " + account_id);
        return accountService.findAccountEquipmentReservations(account_id);
    }

    @GetMapping("/reset/{suffix}")
    public Account getAccountByResetSuffix(@PathVariable("suffix") String suffix) {
        return this.accountService.findAccountByResetSuffix(suffix);
    }

    @PostMapping("/reset/{mail}")
    public void requestPasswordReset(@PathVariable("mail") String mail) {
        this.accountService.generatePasswordReset(mail);
    }
}

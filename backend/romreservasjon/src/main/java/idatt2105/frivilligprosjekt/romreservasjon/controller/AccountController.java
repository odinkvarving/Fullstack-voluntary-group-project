package idatt2105.frivilligprosjekt.romreservasjon.controller;

import idatt2105.frivilligprosjekt.romreservasjon.model.Account;
import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;
import idatt2105.frivilligprosjekt.romreservasjon.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Account> getAllAccounts() {
        return accountService.findAll();
    }

    @GetMapping("accounts/{email}")
    public Account findAccountByEmail(@PathVariable String email){
        return accountService.findByEmail(email);
    }


    /**
     * PostMapping for saving a new Account to the database
     *
     * @param account requesting the body of the Account to save
     * @return true or false whether the Account was created successfully or not
     */
    @PostMapping("accounts")
    public boolean saveAccount(@RequestBody Account account) {
        logger.info("Trying to save account:\n" + account.toString());
        boolean success = accountService.saveAccount(account);
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
    @PutMapping("accounts/{account_id}")
    public Account updateAccount(@RequestBody Account newAccount, @PathVariable("account_id") int account_id) {
        return accountService.updateAccount(account_id, newAccount);
    }

    /**
     * DeleteMapping for deleting an Account
     *
     * @param account_id the PathVariable for the id of the Account
     */
    @DeleteMapping("accounts/{account_id}")
    public void deleteAccount(@PathVariable("account_id") int account_id) {
        accountService.deleteAccount(account_id);
    }

    /**
     * GetMapping for finding all Reservations for an Account
     *
     * @param account_id the ID of the Account
     * @return a Set containing the Reservations for the Account
     */
    @GetMapping("accounts/{account_id}/reservations")
    public Set<Reservation> findAccountReservations(@PathVariable int account_id) {
       logger.info("Finding reservations for Account with ID: " + account_id);
        return accountService.findAccountReservations(account_id);
    }
}

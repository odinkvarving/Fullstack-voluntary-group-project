package idatt2105.frivilligprosjekt.romreservasjon.controller;

import idatt2105.frivilligprosjekt.romreservasjon.model.Account;
import idatt2105.frivilligprosjekt.romreservasjon.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("accounts/")
    public List<Account> getAllAccounts() {
        return accountService.findAll();
    }

    /**
     * PostMapping for saving a new Account to the database
     *
     * @param account requesting the body of the Account to save
     * @return true or false whether the user was created successfully or not
     */
    @PostMapping("accounts/register")
    public boolean saveAccount(@RequestBody Account account) {
        logger.info("Trying to save user:\n" + account.toString());
        boolean success = accountService.saveAccount(account);
        if (success) {
            logger.info("Success!");
        }
        return success;
    }

    /**
     * PutMapping for updating a specific Account
     *
     * @param newAccount requesting the body of a new Account (updated version)
     * @param id the PathVariable of the id for the Account
     * @return the Account that was updated
     */
    @PutMapping("accounts/{id}")
    public Account updateAccount(@RequestBody Account newAccount, @PathVariable("id") int id) {
        return accountService.updateAccount(id, newAccount);
    }

    /**
     * DeleteMapping for deleting an Account
     *
     * @param id the PathVariable for the id of the Account
     */
    @DeleteMapping("accounts/{id}")
    public void deleteAccount(@PathVariable("id") int id) {
        accountService.deleteAccount(id);
    }
}

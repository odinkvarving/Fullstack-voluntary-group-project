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

    @GetMapping("accounts/")
    public List<Account> getAllAccounts() {
        return accountService.findAll();
    }

    @PostMapping("accounts/register")
    public boolean saveAccount(@RequestBody Account account) {
        logger.info("Trying to save user:\n" + account.toString());
        boolean success = accountService.saveAccount(account);
        if (success) {
            logger.info("Success!");
        }
        return success;
    }


}

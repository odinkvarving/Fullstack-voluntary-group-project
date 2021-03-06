package idatt2105.frivilligprosjekt.romreservasjon.service;

import idatt2105.frivilligprosjekt.romreservasjon.model.Account;
import idatt2105.frivilligprosjekt.romreservasjon.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Method for loading a User by userName
     *
     * @param userName the userName to find a User by
     * @return the UserDetails that was created
     * @throws UsernameNotFoundException if a User is not found with this userName
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(userName).orElse(null);
        if(account == null){
            return null;
        }
        return new User(account.getEmail(), account.getPassword(), new ArrayList<>());
    }
}

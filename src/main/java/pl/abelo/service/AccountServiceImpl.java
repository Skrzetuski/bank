package pl.abelo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.abelo.model.Account;
import pl.abelo.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account createAccount(String name, String email, String password) {
        final Account newAccount = new Account();
        newAccount.setName(name);
        newAccount.setEmail(email);
        newAccount.setPassword(password);
        newAccount.setRole("ROLE_USER");

        var tmpBalance = ThreadLocalRandom.current().nextInt(10_000) + 1000;
        newAccount.setBalance(BigDecimal.valueOf(tmpBalance));

        return accountRepository.save(newAccount);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(account.getEmail(), account.getPassword(), Collections.emptyList());
    }
}

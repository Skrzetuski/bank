package pl.abelo.service;

import org.springframework.stereotype.Service;
import pl.abelo.model.Account;

@Service
public interface AccountService {

    Account createAccount(String name, String email, String password);
}

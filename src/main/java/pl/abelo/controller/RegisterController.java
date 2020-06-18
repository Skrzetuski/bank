package pl.abelo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.abelo.model.Account;
import pl.abelo.repository.AccountRepository;
import pl.abelo.service.AccountService;

@RequestMapping("/register")
@RestController
public class RegisterController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> registerAccount(@RequestBody Account account) {

        //TODO: Add validation data

        if (isNotExist(account.getEmail())) {
            accountService.createAccount(account.getName(), account.getEmail(),
                    bCryptPasswordEncoder.encode(account.getPassword()));

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    private boolean isNotExist(String email) {
        return (accountRepository.findByEmail(email) == null);
    }
}

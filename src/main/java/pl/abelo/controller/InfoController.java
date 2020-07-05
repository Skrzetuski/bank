package pl.abelo.controller;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.abelo.model.Account;
import pl.abelo.model.InfoAccount;
import pl.abelo.repository.AccountRepository;
import pl.abelo.security.JWTConstants;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/info")
public class InfoController {

    @Autowired
    AccountRepository accountRepository;

    @GetMapping
    public ResponseEntity<String> getAccountDetails(@RequestHeader("Authorization") String token) throws JsonProcessingException {
        Long id = JWT.decode(token.replace(JWTConstants.TOKEN_PREFIX.get(), "")).getClaim("id").asLong();

        Optional<Account> account = accountRepository.findById(id);

        if (account.isPresent()){
            InfoAccount infoAccount = new InfoAccount();

            infoAccount.setName(account.get().getName());
            infoAccount.setEmail(account.get().getEmail());
            infoAccount.setBalance(account.get().getBalance());

            ObjectMapper objectMapper = new ObjectMapper();
            return new ResponseEntity<>(objectMapper.writeValueAsString(infoAccount),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

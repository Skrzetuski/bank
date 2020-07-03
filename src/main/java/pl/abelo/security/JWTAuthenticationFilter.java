package pl.abelo.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.abelo.model.Account;
import pl.abelo.repository.AccountRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Log4j2
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final AccountRepository accountRepository;

    private final String SECRET;

    private final String ISSUER;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,
                                   AccountRepository accountRepository,
                                   String SECRET, String ISSUER) {
        this.authenticationManager = authenticationManager;
        this.accountRepository = accountRepository;
        this.SECRET = SECRET;
        this.ISSUER = ISSUER;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {

        try {
            Account account = new ObjectMapper()
                    .readValue(req.getInputStream(), Account.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            account.getEmail(),
                            account.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {
        String email = ((User) auth.getPrincipal()).getUsername();
        Account account = accountRepository.findByEmail(email);

        String token = JWT.create()
                .withIssuer(ISSUER)
                .withIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(2))))
                .withClaim("id", account.getId())
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plusDays(JWTConstants.EXPIRATION_TIME.getExpirationTime()).toInstant(ZoneOffset.ofHours(2))))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader("Access-Control-Expose-Headers", "Authorization");
        res.addHeader(JWTConstants.HEADER_STRING.get(), JWTConstants.TOKEN_PREFIX.get() + token);
    }
}

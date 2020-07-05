package pl.abelo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final String SECRET;

    public JWTAuthorizationFilter(String SECRET, AuthenticationManager authManager) {
        super(authManager);
        this.SECRET = SECRET;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(JWTConstants.HEADER_STRING.get());

        if (header == null || !header.startsWith(JWTConstants.TOKEN_PREFIX.get())) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JWTConstants.HEADER_STRING.get());
        if (token != null) {
            Integer userID = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(JWTConstants.TOKEN_PREFIX.get(), ""))
                    .getClaim("id").asInt();
            if (userID != null) {
                return new UsernamePasswordAuthenticationToken(userID, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
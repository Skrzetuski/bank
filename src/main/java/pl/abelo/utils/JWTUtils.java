package pl.abelo.utils;

import com.auth0.jwt.JWT;
import pl.abelo.security.JWTConstants;

public class JWTUtils {
    public static Long getID(String token){
        return JWT.decode(token.replace(JWTConstants.TOKEN_PREFIX.get(), "")).getClaim("id").asLong();
    }
}
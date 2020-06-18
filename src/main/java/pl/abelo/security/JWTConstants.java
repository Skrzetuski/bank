package pl.abelo.security;

public enum JWTConstants {
    TOKEN_PREFIX("Bearer "),
    HEADER_STRING("Authorization"),
    EXPIRATION_TIME("1");

    JWTConstants(final String tokenConst){ this.tokenConst = tokenConst;}

    final String tokenConst;

    public Integer getExpirationTime(){
        return EXPIRATION_TIME.ordinal();
    }

    public String get(){
        return tokenConst;
    }
}

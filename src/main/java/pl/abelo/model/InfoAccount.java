package pl.abelo.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InfoAccount {

    private String name;

    private String email;

    private BigDecimal balance;
}

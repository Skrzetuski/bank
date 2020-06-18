package pl.abelo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Account extends BaseEntity{

    private String name;

    private String email;

    private String password;

    private String role;

    private BigDecimal balance;
}
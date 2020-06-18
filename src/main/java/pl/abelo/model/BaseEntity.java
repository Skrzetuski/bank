package pl.abelo.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid = UUID.randomUUID().toString();

    public int hashCode(){
        return Objects.hash(uuid);
    }

    public boolean equals(Object that){
        return this == that || that instanceof BaseEntity &&
                Objects.equals(uuid, ((BaseEntity) that).uuid);
    }
}


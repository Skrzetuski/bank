package pl.abelo.mq.model;

import lombok.Data;
import java.util.UUID;

@Data
public class MsgRequestModel {

    private String orderID = UUID.randomUUID().toString();

    private String amountMoney;

    private String sender;

    private String receiver;

}

package pl.abelo.mq.model;

import lombok.Data;

@Data
public class MsgResponseModel {

    private String orderID;

    private Boolean status;
}

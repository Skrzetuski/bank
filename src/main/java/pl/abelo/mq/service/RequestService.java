package pl.abelo.mq.service;

import org.springframework.stereotype.Service;
import pl.abelo.mq.model.MsgRequestModel;

import java.util.UUID;

@Service
public class RequestService {

    public MsgRequestModel prepareMsg(MsgRequestModel msg){
        msg.setOrderID(UUID.randomUUID().toString());
        return msg;
    }
}

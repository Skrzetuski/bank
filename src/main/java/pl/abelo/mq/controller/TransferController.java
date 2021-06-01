package pl.abelo.mq.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.abelo.model.Account;
import pl.abelo.mq.model.MsgRequestModel;
import pl.abelo.mq.model.TransferStatusModel;
import pl.abelo.mq.service.QueueTransferRequestService;
import pl.abelo.mq.service.RequestService;
import pl.abelo.repository.AccountRepository;
import pl.abelo.utils.JWTUtils;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/transfer")
class TransferController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RequestService requestService;

    @Autowired
    private QueueTransferRequestService queueTransferRequestService;


    @CrossOrigin(origins = "*", methods = RequestMethod.POST)
    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> getAccountDetails(@RequestHeader("Authorization") String token,
                                                    @RequestBody MsgRequestModel msgRequest) throws Exception {
        Long authID = JWTUtils.getID(token);
        Long senderID = Long.valueOf(msgRequest.getSender());
        Long receiverID = Long.valueOf(msgRequest.getReceiver());

        Optional<Account> sender = accountRepository.findById(senderID);
        Optional<Account> receiver = accountRepository.findById(receiverID);

        if (sender.isPresent() && receiver.isPresent() && (authID.equals(senderID))) {
            MsgRequestModel finalRequestMsg = requestService.prepareMsg(msgRequest);
            ObjectMapper jsonMsgMapper = new ObjectMapper();
            String jsonMsg = jsonMsgMapper.writeValueAsString(finalRequestMsg);
            queueTransferRequestService.publishMsg(jsonMsg);

            TransferStatusModel transferStatus = new TransferStatusModel();
            transferStatus.setStatus("Sent!");
            ObjectMapper objectMapper = new ObjectMapper();

            return new ResponseEntity<>(objectMapper.writeValueAsString(transferStatus), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}

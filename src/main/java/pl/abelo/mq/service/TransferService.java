package pl.abelo.mq.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.abelo.model.Account;
import pl.abelo.mq.model.MsgRequestModel;
import pl.abelo.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class TransferService {

    @Autowired
    private AccountRepository accountRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void transferMoney(MsgRequestModel msg) {

        Long senderID = Long.parseLong(msg.getSender());
        Long receiverID = Long.parseLong(msg.getReceiver());
        BigDecimal money = BigDecimal.valueOf(Math.abs(Long.parseLong(msg.getAmountMoney())));

        Optional<Account> sender = accountRepository.findById(senderID);
        Optional<Account> receiver = accountRepository.findById(receiverID);
        if (sender.isPresent() && receiver.isPresent()) {
            Account tmpSender = sender.get();
            Account tmpReceiver = receiver.get();

            tmpSender.setBalance(tmpSender.getBalance().subtract(money));
            tmpReceiver.setBalance(tmpReceiver.getBalance().add(money));

        }


    }

}

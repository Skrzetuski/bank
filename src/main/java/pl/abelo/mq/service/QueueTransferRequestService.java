package pl.abelo.mq.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import pl.abelo.mq.model.MsgRequestModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class QueueTransferRequestService implements CommandLineRunner {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private TransferService transferService;

    @Value("${mq.queue.request.transfer.name}")
    private String queueName;

    private Channel channel;

    @Override
    public void run(String... args) throws Exception {
        channel = connectionFactory.newConnection().createChannel();
        channel.queueDeclare(queueName, true, false, false, null);
        Thread.sleep(5000);

        channel.basicQos(1);
        channel.basicConsume(queueName, false, deliverCallback, consumerTag -> {
        });

        while (channel.isOpen()) {
            Thread.sleep(1_000);
        }
    }

    public void publishMsg(String msg) throws IOException {
        channel.basicPublish("", queueName, MessageProperties.MINIMAL_PERSISTENT_BASIC,
                msg.getBytes(StandardCharsets.UTF_8));
    }

    private final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        String msg = new String(delivery.getBody(), StandardCharsets.UTF_8);
        ObjectMapper objectMapper = new ObjectMapper();
        MsgRequestModel msgRequest = objectMapper.readValue(msg, MsgRequestModel.class);
        transferService.transferMoney(msgRequest);
        channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    };

}

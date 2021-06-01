package pl.abelo.mq.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ConfigurationMQ {

    @Value("${spring.rabbitmq.password}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;


    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Bean("ConnectionFactory")
    public ConnectionFactory getConnection(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername(user);
        connectionFactory.setPassword(password);
        connectionFactory.setPort(port);
        connectionFactory.setHost(host);

        return connectionFactory;
    }


}

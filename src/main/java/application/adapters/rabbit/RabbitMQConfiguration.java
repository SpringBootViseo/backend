package application.adapters.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration {

    private final static String EXCHANGE_NAME = "logs";

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setEncoding("UTF-8");
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }


    @Bean
    public Queue queueLogs() {
        RabbitAdmin rabbitAdmin = rabbitAdmin();
        Queue queue = new Queue("queue-logs", true);
        rabbitAdmin.declareQueue(queue); // Explicitly declare the queue
        rabbitAdmin.purgeQueue(queue.getName(), false); // Purge the queue
        return queue;
    }



    @Bean
    public Binding bindingLogs() {
        return BindingBuilder
                .bind(queueLogs())
                .to(exchange())
                .with("#");
    }


    @Bean
    public RabbitAdmin rabbitAdmin() {
        return new RabbitAdmin(connectionFactory());
    }
}

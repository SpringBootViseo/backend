package application.adapters.log4j2;


import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.Serializable;

@Plugin(name = "RabbitMQAppender",category = "Core",elementType ="appender", printObject = true )
public class RabbitMQAppender extends AbstractAppender {
    private final CachingConnectionFactory connectionFactory ;


    private final RabbitTemplate rabbitTemplate;
    private final AmqpAdmin amqpAdmin;
    private final String exchangeName;
    private final String routingKey;
    private final String queueName;


    protected RabbitMQAppender(String name, Filter filter, Layout<? extends Serializable> layout,String queueName, String exchangeName, String routingKey) {
        super(name, filter, layout,true);
        this.exchangeName = exchangeName;
        this.routingKey = routingKey;
        this.connectionFactory = new CachingConnectionFactory("localhost");
        this.queueName=queueName;
        this.connectionFactory.setUsername("guest");
        this.connectionFactory.setPassword("guest");
        this.rabbitTemplate=new RabbitTemplate(connectionFactory);
        this.amqpAdmin=new RabbitAdmin(connectionFactory);
        amqpAdmin.declareExchange(new DirectExchange(exchangeName, true, false));

        // Declare the queue (if it doesn't exist)
        amqpAdmin.declareQueue(new Queue(queueName, true));

        // Bind the queue to the exchange with the routing key
        amqpAdmin.declareBinding(BindingBuilder.bind(new Queue(queueName))
                .to(new DirectExchange(exchangeName))
                .with(routingKey));

    }

    @Override
    public void append(LogEvent event) {
        final byte[] messageBody = getLayout().toByteArray(event);
        Message message = new Message(messageBody);
        this.rabbitTemplate.convertAndSend(this.exchangeName,this.routingKey,message);
        // Ensure that the exchange and queue exist

        // Send the message to RabbitMQ
    }
    @PluginFactory
    public static RabbitMQAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filter") Filter filter,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
            @PluginAttribute("exchangeName") String exchangeName,
            @PluginAttribute("routingKey") String routingKey,
            @PluginAttribute("queueName") String queueName,
            @PluginElement("Properties") Property[] properties0
    ) {
        if (name == null) {
            LOGGER.error("No name provided for RabbitMQAppender");
            return null;
        }

        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }


        return new RabbitMQAppender(name, filter, layout, queueName,exchangeName, routingKey);
    }
}


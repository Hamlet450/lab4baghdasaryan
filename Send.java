import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Send {
    private final static String QUEUE_NAME = "pizzaQueue";

    public static void main(String[] argv) throws Exception {
        // Define a Pizza object
        Pizza pizza = new Pizza("Pepperoni", "Large", 12.99);

        // Serialize the Pizza object to a comma-delimited string
        String message = pizza.serialize();

        // Set up RabbitMQ connection and channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Send the serialized pizza as a message
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent: '" + message + "'");
        }
    }
}

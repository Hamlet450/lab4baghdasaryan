import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Recv {

    private final static String QUEUE_NAME = "PizzaQueue"; // Match the queue name used by the sender

    public static void main(String[] argv) throws Exception {
        // Create a connection factory to connect to RabbitMQ server
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Declare the same queue used by the sender
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            // Define the callback to handle received messages
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                // Get the message (Pizza object as a serialized string)
                String message = new String(delivery.getBody(), "UTF-8");

                // Deserialize the message back into a Pizza object
                Pizza pizza = Pizza.deserialize(message);

                // Print the Pizza object details
                System.out.println(" [x] Received Pizza: ");
                System.out.println("Name: " + pizza.getName());
                System.out.println("Size: " + pizza.getSize());
                System.out.println("Price: " + pizza.getPrice());
            };

            // Start consuming messages from the queue
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        }
    }
}

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class SimpleHttpServer {
    public static void main(String[] args) throws Exception {
        // Create an HTTP server on port 8000
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/pizza", new PizzaHandler()); // Define the /pizza context
        server.setExecutor(null); // Creates a default executor
        server.start();
        System.out.println("Server started on port 8000...");
    }

    static class PizzaHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Handle POST requests (expecting serialized Pizza data)

                // Read request body (serialized Pizza object)
                InputStream inputStream = exchange.getRequestBody();
                String requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                inputStream.close();

                // Deserialize the Pizza object from the request body
                Pizza pizza = Pizza.deserialize(requestBody);

                // Prepare the response with the Pizza details
                String response = "Received Pizza:\n" +
                        "Name: " + pizza.getName() + "\n" +
                        "Size: " + pizza.getSize() + "\n" +
                        "Price: " + pizza.getPrice();

                // Send HTTP response
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                // Handle unsupported request methods
                String response = "Only POST requests are supported!";
                exchange.sendResponseHeaders(405, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
    }
}

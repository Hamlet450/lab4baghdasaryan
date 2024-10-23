import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebServiceCaller {

    public static void main(String[] args) {
        try {
            // Specify the URL of the web service (the /pizza endpoint)
            String url = "http://localhost:8000/pizza";

            // Create a URL object
            URL obj = new URL(url);

            // Open a connection to the URL
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Set the request method to POST
            con.setRequestMethod("POST");

            // Set the request headers
            con.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");

            // Prepare the Pizza object to send (as a serialized string)
            Pizza pizza = new Pizza("Margherita", "Medium", 9.99);
            String serializedPizza = pizza.serialize();

            // Enable sending data in the request body
            con.setDoOutput(true);

            // Write the serialized Pizza object to the request body
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(serializedPizza);
                wr.flush();
            }

            // Get the response code
            int responseCode = con.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response from the web service
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Print the response
            System.out.println("Response: " + response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

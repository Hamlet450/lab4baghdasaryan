/** Project: Systems Integration
 * Purpose Details: Define a Pizza object and provide necessary methods for serialization and deserialization
 * Course:IST 242
 * Author:Hamlet Baghdasaryan
 * Date Developed:10/19/24
 * Last Date Changed:10/22/24
 * Rev:1.0

 */

public class Pizza {
    private String name;
    private String size;
    private double price;

    public Pizza(String name, String size, double price) {
        this.name = name;
        this.size = size;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Pizza [name=" + name + ", size=" + size + ", price=" + price + "]";
    }

    // Manually serialize the Pizza object to a comma-delimited string
    public String serialize() {
        return name + "," + size + "," + price;
    }

    // Manually deserialize the comma-delimited string to a Pizza object
    public static Pizza deserialize(String data) {
        String[] fields = data.split(",");
        String name = fields[0];
        String size = fields[1];
        double price = Double.parseDouble(fields[2]);
        return new Pizza(name, size, price);
    }
}

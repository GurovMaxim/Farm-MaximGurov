import java.util.Scanner;
import java.util.StringTokenizer;

class Animal {
    // Attributes
    private int quantity;
    private String type;

    private int id;
    public int feedQuantity;

    public int getId() {
        return id;
    }

    // Constructor
    public Animal(int id, String type, int quantity) {
        this.id = id;
        this.quantity = quantity;
        this.type = type;
    }

    // Getter methods
    public int getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String get_csv() {
        return id + "," + type + "," + quantity;
    }

    public static Animal parseCSV(String csv) {
        StringTokenizer tokenizer = new StringTokenizer(csv, ",");
        int id = Integer.parseInt(tokenizer.nextToken());
        String type = tokenizer.nextToken();
        int quantity = Integer.parseInt(tokenizer.nextToken());
        return new Animal(id, type, quantity);
    }
    public int feed(Crop crop) {
        int cropQuantity = crop.getQuantity();

        if (cropQuantity >= feedQuantity) {
            crop.setQuantity(cropQuantity - feedQuantity);
            System.out.println("Animal fed successfully.");
            return feedQuantity;
        } else {
            System.out.println("Not enough crop to feed the animal.");
            return 0;
        }
    }
}

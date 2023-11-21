import java.util.StringTokenizer;

class Crop {

    private int quantity;
    private String type;
    private int id;


    public Crop(int id, String type, int quantity) {
        this.id = id;
        this.quantity = quantity;
        this.type = type;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

    // Setter methods
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String get_csv() {
        return id + "," + type + "," + quantity;
    }

    public static Crop parseCSV(String csv) {
        StringTokenizer tokenizer = new StringTokenizer(csv, ",");
        int id = Integer.parseInt(tokenizer.nextToken());
        String type = tokenizer.nextToken();
        int quantity = Integer.parseInt(tokenizer.nextToken());
        return new Crop(id,type,quantity);
    }

}

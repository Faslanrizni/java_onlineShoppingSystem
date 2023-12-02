public class Clothing extends Product {
    private int size;
    private String color;
    public Clothing() {
    }

    public Clothing(int size, String color) {
        this.setSize(size);
        this.setColor(color);
    }

    public Clothing(String productId, String productName, double price, int quantity, int size, String color) {
        super(productId, productName, price, quantity);
        this.size = size;
        this.color = color;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", size=" + size +
                ", color='" + color + '\'';
    }
}

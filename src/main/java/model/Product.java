package model;

public class Product {
    private int id;
    private String productName;
    private double price;
    private int quantityInStock;

    public Product(int id, String productName, double price, int quantityInStock) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public Product(String productName, double price, int quantityInStock) {
        this.productName = productName;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", quantityInStock=" + quantityInStock +
                '}';
    }
}

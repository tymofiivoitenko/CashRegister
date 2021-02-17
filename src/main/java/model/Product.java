package model;

import java.util.Arrays;

public class Product {
    private int id;
    private String name;
    private double price;
    private double quantity;
    private String unit;
    private byte[] imageData;
    private String imageFileName;

    public Product() {
    }

    public Product(String name, double price, String unit) {
        this.name = name;
        this.price = price;
        this.unit = unit;
    }

    public Product(int id, String name, double price, double quantity, String unit) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Product(String name, double price, double quantity, String unit) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Product(String name, double price, double quantity, String unit, String imageFileName, byte[] imageData) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
        this.imageData = imageData;
        this.imageFileName = imageFileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", imageData=" + Arrays.toString(imageData) +
                ", imageFileName='" + imageFileName + '\'' +
                '}';
    }
}

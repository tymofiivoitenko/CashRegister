package model;

public class Item {

    private Product product;
    private int quantityInCheck;

    public Item() {
    }

    public Item(Product product, int quantityInCheck) {
        this.product = product;
        this.quantityInCheck = quantityInCheck;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantityInCheck() {
        return quantityInCheck;
    }

    public void setQuantityInCheck(int quantityInCheck) {
        this.quantityInCheck = quantityInCheck;
    }
}

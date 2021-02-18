package model;

import java.time.LocalDateTime;
import java.util.List;

public class Receipt {
    private int id;
    private LocalDateTime createdDate;
    private LocalDateTime closedDate;
    private CashBox cashBox;
    private String status;
    private double totalPrice;
    private List<ReceiptItem> receiptItems;

    public Receipt(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public Receipt(int id, LocalDateTime createdDate, LocalDateTime closedDate, CashBox cashBox, double totalPrice, String status) {
        this.id = id;
        this.createdDate = createdDate;
        this.closedDate = closedDate;
        this.cashBox = cashBox;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Receipt(int id, LocalDateTime createdDate, String status, CashBox cashBox, List<ReceiptItem> receiptItems) {
        this.id = id;
        this.createdDate = createdDate;
        this.status = status;
        this.cashBox = cashBox;
        this.receiptItems = receiptItems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ReceiptItem> getItems() {
        return receiptItems;
    }

    public void setItems(List<ReceiptItem> receiptItems) {
        this.receiptItems = receiptItems;
    }

    public CashBox getCashBox() {
        return cashBox;
    }

    public void setCashBox(CashBox cashBox) {
        this.cashBox = cashBox;
    }

    public List<ReceiptItem> getReceiptItems() {
        return receiptItems;
    }

    public void setReceiptItems(List<ReceiptItem> receiptItems) {
        this.receiptItems = receiptItems;
    }

    public LocalDateTime getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(LocalDateTime closedDate) {
        this.closedDate = closedDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", closedDate=" + closedDate +
                ", cashBox=" + cashBox +
                ", status='" + status + '\'' +
                ", totalPrice=" + totalPrice +
                ", receiptItems=" + receiptItems +
                '}';
    }
}

package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CashBox implements Serializable {

    private int id;
    private User user;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;

    public CashBox(int id, User user) {
        this.id = id;
        this.user = user;
    }

    public CashBox(int id, User user, LocalDateTime startDate) {
        this.id = id;
        this.user = user;
        this.startDate = startDate;
        this.status = status;
    }

    public CashBox(int id, User user, LocalDateTime startDate, LocalDateTime endDate, String status) {
        this.id = id;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CashBox{" +
                "id=" + id +
                ", user=" + user +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status='" + status + '\'' +
                '}';
    }
}

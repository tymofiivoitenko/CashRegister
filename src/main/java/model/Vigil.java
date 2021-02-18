package model;

import java.util.Date;

public class Vigil {

    private int id;
    private User user;
    private Date startDate;
    private Date endDate;
    private String status;

    public Vigil(int id, User user) {
        this.id = id;
        this.user = user;
    }

    public Vigil(int id, Date startDate, String status) {
        this.id = id;
        this.startDate = startDate;
        this.status = status;
    }

    public Vigil(int id, User user, Date startDate, Date endDate, String status) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Vigil{" +
                "id=" + id +
                ", cashier=" + user +
                ", startDate=" + startDate +
                ", status='" + status + '\'' +
                '}';
    }
}

package model;

public class CashBox {

    private int id;
    private Vigil vigil;
    private String machineName;
    private String status;

    public CashBox(Vigil vigil, String machineName, String status) {
        this.vigil = vigil;
        this.machineName = machineName;
        this.status = status;
    }

    public CashBox(int id, Vigil vigil, String machineName, String status) {
        this.id = id;
        this.vigil = vigil;
        this.machineName = machineName;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vigil getVigil() {
        return vigil;
    }

    public void setVigil(Vigil vigil) {
        this.vigil = vigil;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
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
                ", vigil=" + vigil +
                ", machineName='" + machineName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

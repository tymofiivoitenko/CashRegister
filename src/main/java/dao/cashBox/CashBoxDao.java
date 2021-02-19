package dao.cashBox;

import bean.CashBox;

public interface CashBoxDao {

    // Start new Vigil
    void startCashBox(int userId);

    // Check if given user has active vigil
    void finishCashBox(int userId);

    // Get vigil with status = "started", which is assigned to user with given id
    CashBox getActiveCashBox(int userId);


    // Check if given user has active vigil
    void getCashBoxes(int userId);

}

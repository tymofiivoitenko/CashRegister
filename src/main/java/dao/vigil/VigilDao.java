package dao.vigil;

import model.Vigil;

public interface VigilDao {

    // Start new Vigil
    void startVigil(int userId);

    // Check if given user has active vigil
    void finishVigil(int userId);

    // Get vigil with status = "started", which is assigned to user with given id
    Vigil getActiveVigil(int userId);

}

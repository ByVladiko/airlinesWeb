import dao.api.DAO;
import model.Airship;
import repository.DatabaseConnectivityProvider;
import dao.impl.AirshipDAO;

import java.util.UUID;

public class MainApp {
    public static void main(String[] args) {
        DatabaseConnectivityProvider.registryDriver();

        Airship airship = new Airship();
        airship.setId(UUID.fromString("e823c8e6-737b-11ea-bc55-0242ac130003")); // Boeing

        DAO<Airship> airshipDAO = new AirshipDAO();
        airshipDAO.delete(airship);
    }
}

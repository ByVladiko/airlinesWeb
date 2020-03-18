import model.Airship;
import sql.daoImpl.FactoryDAO;

public class MainApp {
    public static void main(String[] args) {
        System.out.println("hello");
        FactoryDAO.getAirshipDAO().create(new Airship("TU-154", 100));
    }
}

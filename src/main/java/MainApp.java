import org.sqlite.JDBC;
import sql.daoImpl.FactoryDAOImpl;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        System.out.println("hello");
        DriverManager.registerDriver(new JDBC());
        System.out.println(FactoryDAOImpl.getAirshipDAO().getAll());
        FactoryDAOImpl.getAirshipDAO().delete(FactoryDAOImpl.getAirshipDAO().getAll().get(0));
        System.out.println(FactoryDAOImpl.getAirshipDAO().getAll());
    }
}

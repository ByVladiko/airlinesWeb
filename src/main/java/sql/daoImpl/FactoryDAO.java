package sql.daoImpl;

import dao.*;
import model.*;

public class FactoryDAO{

    private FactoryDAO() {
    }

    public static DAO<Airship> getAirshipDAO() {
        return AirshipDAOImpl.getInstance();
    }

    public static DAO<Client> getClientDAO() {
        return null;
    }

    public static  DAO<Route> getRouteDAO() {
        return null;
    }

    public static  DAO<Ticket> getTicketDAO() {
        return null;
    }

    public static  DAO<Flight> getFlightDAO() {
        return null;
    }

}

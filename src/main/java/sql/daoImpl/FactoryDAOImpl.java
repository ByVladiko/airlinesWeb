package sql.daoImpl;

import dao.*;
import model.*;

public class FactoryDAOImpl {

    private FactoryDAOImpl() {
    }

    public static DAO<Airship> getAirshipDAO() { return new AirshipDAOImpl(); }

    public static DAO<Client> getClientDAO() {
        return null;
    }

    public static  DAO<Route> getRouteDAO() {
        return new RouteDAOImpl();
    }

    public static  DAO<Ticket> getTicketDAO() {
        return null;
    }

    public static  DAO<Flight> getFlightDAO() {
        return new FlightDAOImpl();
    }

}

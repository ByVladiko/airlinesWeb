package dao.impl;

import dao.api.DAO;
import model.*;

public class FactoryDAO {

    private FactoryDAO() {
    }

    public static DAO<Airship> getAirshipDAO() { return new AirshipDAO(); }

    public static DAO<Client> getClientDAO() {
        return new ClientDAO();
    }

    public static  DAO<Route> getRouteDAO() {
        return new RouteDAO();
    }

    public static  DAO<Ticket> getTicketDAO() {
        return new TicketDAO();
    }

    public static  DAO<Flight> getFlightDAO() {
        return new FlightDAO();
    }

}

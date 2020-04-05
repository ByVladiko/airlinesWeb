package dao.api;

import model.*;

public interface FactoryDAO {

    DAO<Airship> getAirshipDAO();

    DAO<Client> getClientDAO();

    DAO<Route> getRouteDAO();

    DAO<Ticket> getTicketDAO();

    DAO<Flight> getFlightDAO();

}

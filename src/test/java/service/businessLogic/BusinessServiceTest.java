package service.businessLogic;

import model.*;
import org.junit.Assert;
import org.junit.Test;
import service.BusinessService;
import service.BusinessServiceException;
import service.SQLTestOperations;
import util.GeneratorSQL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusinessServiceTest extends SQLTestOperations {

    private final BusinessService businessService = new BusinessService();

    @Test
    public void buyTicketToClient() throws SQLException {
        connection.setAutoCommit(false);

        Client client = createClient();

        Ticket ticket = createTicket();
        ticket.setStatus(Status.FREE);
        ticketDAO.update(connection, ticket);

        client.setBill(ticket.getCost());
        clientDAO.update(connection, client);
        businessService.buyTicketToClient(connection, client, ticket);
        client.setBill(client.getBill() - ticket.getCost());

        List<Ticket> ticketList = new ArrayList<>(1);
        ticketList.add(ticket);
        client.setTickets(ticketList);

        Client actual = clientDAO.getById(connection, client.getId().toString());
        Assert.assertEquals(client, actual);

        connection.rollback();
    }

    @Test
    public void buyTicketToClientNotEnoughMoneyException() throws SQLException {
        connection.setAutoCommit(false);

        Client client = createClient();

        Ticket ticket = createTicket();
        ticket.setStatus(Status.FREE);
        ticket.setCost(1000);
        ticketDAO.update(connection, ticket);

        client.setBill(0);
        clientDAO.update(connection, client);

        Assert.assertThrows(BusinessServiceException.class, () -> businessService.buyTicketToClient(connection, client, ticket));
    }

    @Test
    public void addTicketToDB() throws SQLException, BusinessServiceException {
        connection.setAutoCommit(false);

        Ticket ticket = GeneratorSQL.getRandomTicket();
        airshipDAO.create(connection, ticket.getFlight().getAirship());
        routeDAO.create(connection, ticket.getFlight().getRoute());
        flightDAO.create(connection, ticket.getFlight());
        businessService.addTicketToDB(connection, ticket);

        Ticket actual = ticketDAO.getById(connection, ticket.getId().toString());
        Assert.assertEquals(ticket, actual);

        connection.rollback();
    }

    @Test
    public void addTicketToDBOutOfSeats() throws SQLException, BusinessServiceException {
        connection.setAutoCommit(false);

        Ticket ticket = GeneratorSQL.getRandomTicket();

        Airship airship = GeneratorSQL.getRandomAirship();
        airship.setPremiumCategory(0);
        airship.setEconomyCategory(0);
        airship.setBusinessCategory(0);

        Flight flight = ticket.getFlight();
        flight.setAirship(airship);

        airshipDAO.create(connection, airship);
        routeDAO.create(connection, flight.getRoute());
        flightDAO.create(connection, flight);
        businessService.addTicketToDB(connection, ticket);

        Assert.assertThrows(BusinessServiceException.class, () -> businessService.addTicketToDB(connection, ticket));
    }
}

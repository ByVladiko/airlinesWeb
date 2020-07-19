package service.businessLogic;

import model.Client;
import model.Status;
import model.Ticket;
import org.junit.Assert;
import org.junit.Test;
import service.BusinessService;
import service.MainTestOperations;
import util.GeneratorSQL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BusinessServiceTest extends MainTestOperations {

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
    public void addTicketToDB() throws SQLException {
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
}

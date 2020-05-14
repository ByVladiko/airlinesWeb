package service;

import dao.impl.ClientDAO;
import dao.impl.TicketDAO;
import model.Category;
import model.Client;
import model.Status;
import model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusinessLogic {

    public void buyTicketToClient(final Connection connection, Client client, Ticket ticket) throws SQLException {
        try {
            connection.setAutoCommit(false);
            checkTicketForBuying(connection, ticket);
            reserveTicket(connection, ticket);
            connection.setSavepoint("RESERVED");
            addTicketToClient(connection, client, ticket);
            connection.setSavepoint("ADDED");
            buyTicket(connection, client, ticket);
            connection.setSavepoint("SOLD");
            connection.commit();
        } catch (SQLException | BusinessLogicException e) {
            e.printStackTrace();
            connection.rollback();
            connection.close();
        }
    }

    private void reserveTicket(final Connection connection, Ticket ticket) throws BusinessLogicException {
        try {
            TicketDAO ticketDAO = new TicketDAO();
            if (ticket.getStatus().equals(Status.FREE)) {
                ticket.setStatus(Status.RESERVED);
                ticketDAO.update(connection, ticket);
            } else {
                throw new BusinessLogicException("Ticket unavailable");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addTicketToClient(final Connection connection, Client client, Ticket ticket) {
        String queryCountTicketOfCategory = "UPDATE ticket SET client = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(queryCountTicketOfCategory)) {
            statement.setString(1, client.getId().toString());
            statement.setString(2, ticket.getId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void buyTicket(final Connection connection, Client client, Ticket ticket) throws BusinessLogicException, SQLException {
        ClientDAO clientDAO = new ClientDAO();
        TicketDAO ticketDAO = new TicketDAO();
        float billOfClient = client.getBill();
        if (billOfClient < ticket.getCost()) {
            throw new BusinessLogicException("Not enough funds on the account");
        }
        checkTicketForBuying(connection, ticket);
        client.setBill(billOfClient - ticket.getCost());
        clientDAO.update(connection, client);
        ticket.setStatus(Status.SOLD);
        ticketDAO.update(connection, ticket);
    }

    private void checkTicketForBuying(final Connection connection, Ticket ticket) throws BusinessLogicException {
        String queryCountTicketOfCategory = "SELECT COUNT(id) as count FROM ticket WHERE category = ?";
        try (PreparedStatement statement = connection.prepareStatement(queryCountTicketOfCategory)) {
            statement.setInt(1, ticket.getCategory().getIndex());
            ResultSet resultSet = statement.executeQuery();
            if (getCountOfSeat(ticket) == resultSet.getInt("count")) {
                throw new BusinessLogicException("Out of seats");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getCountOfSeat(Ticket ticket) {
        Category categoryOfTicket = ticket.getCategory();
        if (categoryOfTicket.equals(Category.BUSINESS)) {
            return ticket.getFlight().getAirship().getBusinessCategory();
        } else if (categoryOfTicket.equals(Category.ECONOMY)) {
            return ticket.getFlight().getAirship().getEconomyCategory();
        } else if (categoryOfTicket.equals(Category.PREMIUM)) {
            return ticket.getFlight().getAirship().getPremiumCategory();
        }
        return 0;
    }

}

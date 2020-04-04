package sql.daoImpl;

import dao.DAO;
import model.*;
import sql.ConnectToDB;
import sql.ConvertDate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TicketDAOImpl implements DAO<Ticket> {

    public TicketDAOImpl() {
    }

    private static String createTicket = "INSERT INTO ticket VALUES(?, ?, ?, ?, ?)";
    private static String getByIdTicket = "SELECT \n" +
            "        a.id,\n" +
            "        a.category,\n" +
            "        a.cost,\n" +
            "        b.id as \"flight_id\",\n" +
            "        b.date_of_departure as \"flight_date_of_departure\",\n" +
            "        b.date_of_arrival as \"flight_date_of_arrival\",\n" +
            "        c.id as \"airship_id\",\n" +
            "        c.model as \"airship_model\",\n" +
            "        c.number_of_seat as \"airship_number_of_seat\",\n" +
            "        d.id as \"route_id\",\n" +
            "        d.start_point as \"route_start_point\",\n" +
            "        d.end_point as \"route_end_point\"\n" +
            "FROM\n" +
            "        ticket a\n" +
            "INNER JOIN\n" +
            "        flight b\n" +
            "ON\n" +
            "        a.flight = b.id\n" +
            "INNER JOIN \n" +
            "        airship c\n" +
            "ON\n" +
            "        b.airship = c.id\n" +
            "INNER JOIN\n" +
            "        route d\n" +
            "ON\n" +
            "        b.route = d.id\n" +
            "WHERE\n" +
            "        a.id = ?";
    private static String updateTicket = "UPDATE ticket SET " +
            "flight = ?, " +
            "category = ?, " +
            "cost = ?, " +
            "WHERE id = ?";
    private static String deleteTicket = "DELETE FROM ticket WHERE id = ?";
    private static String getAllTicket = "SELECT \n" +
            "        a.id,\n" +
            "        a.category,\n" +
            "        a.cost,\n" +
            "        b.id as \"flight_id\",\n" +
            "        b.date_of_departure as \"flight_date_of_departure\",\n" +
            "        b.date_of_arrival as \"flight_date_of_arrival\",\n" +
            "        c.id as \"airship_id\",\n" +
            "        c.model as \"airship_model\",\n" +
            "        c.number_of_seat as \"airship_number_of_seat\",\n" +
            "        d.id as \"route_id\",\n" +
            "        d.start_point as \"route_start_point\",\n" +
            "        d.end_point as \"route_end_point\"\n" +
            "FROM\n" +
            "        ticket a\n" +
            "INNER JOIN\n" +
            "        flight b\n" +
            "ON\n" +
            "        a.flight = b.id\n" +
            "INNER JOIN \n" +
            "        airship c\n" +
            "ON\n" +
            "        b.airship = c.id\n" +
            "INNER JOIN\n" +
            "        route d\n" +
            "ON\n" +
            "        b.route = d.id";

    @Override
    public void create(Ticket ticket) {
        try (Connection connection = ConnectToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(createTicket)) {
            statement.setString(1, ticket.getId().toString());
            statement.setString(2, ticket.getFlight().getId().toString());
            statement.setInt(3, ticket.getCategory().getIndex());
            statement.setFloat(4, ticket.getCost());
            statement.setString(5, null);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Ticket getById(String id) {
        try (Connection connection = ConnectToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(getByIdTicket)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Ticket(UUID.fromString(resultSet.getString("id")),
                                new Flight(UUID.fromString(resultSet.getString("flight_id")),
                        ConvertDate.convert(resultSet.getString("flight_date_of_departure")),
                        ConvertDate.convert(resultSet.getString("flight_date_of_arrival")),
                        new Airship(UUID.fromString(resultSet.getString("airship_id")),
                                resultSet.getString("airship_model"),
                                resultSet.getInt("airship_number_of_seat")),
                        new Route(UUID.fromString(resultSet.getString("route_id")),
                                resultSet.getString("route_start_point"),
                                resultSet.getString("route_end_point"))),
                        Category.byOrdinal(resultSet.getInt("category")),
                        resultSet.getFloat("cost"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Ticket ticket) {
        try (Connection connection = ConnectToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateTicket)) {
            statement.setString(1, ticket.getFlight().getId().toString());
            statement.setInt(2, ticket.getCategory().getIndex());
            statement.setFloat(3, ticket.getCost());
            statement.setString(4, ticket.getId().toString());
            if (statement.executeUpdate() == 0) {
                create(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Ticket ticket) {
        try (Connection connection = ConnectToDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteTicket)) {
            statement.setString(1, ticket.getId().toString());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Ticket> getAll() {
        try(Connection connection = ConnectToDB.getConnection();
            Statement statement = connection.createStatement())
        {
            List<Ticket> ticketList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(getAllTicket);
            while (resultSet.next()) {
                ticketList.add(
                        new Ticket(UUID.fromString(resultSet.getString("id")),
                                new Flight(UUID.fromString(resultSet.getString("flight_id")),
                                        ConvertDate.convert(resultSet.getString("flight_date_of_departure")),
                                        ConvertDate.convert(resultSet.getString("flight_date_of_arrival")),
                                        new Airship(UUID.fromString(resultSet.getString("airship_id")),
                                                resultSet.getString("airship_model"),
                                                resultSet.getInt("airship_number_of_seat")),
                                        new Route(UUID.fromString(resultSet.getString("route_id")),
                                                resultSet.getString("route_start_point"),
                                                resultSet.getString("route_end_point"))),
                                Category.byOrdinal(resultSet.getInt("category")),
                                resultSet.getFloat("cost")));
            }
            return ticketList;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

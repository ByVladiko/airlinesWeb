package util;

import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EntityFactoryDAO {

    public static Airship createAirship(ResultSet resultSet) throws SQLException {
        return new Airship(UUID.fromString(resultSet.getString("airship_id")),
                resultSet.getString("model"),
                resultSet.getInt("economy_category"),
                resultSet.getInt("business_category"),
                resultSet.getInt("premium_category"));
    }

    public static Client createClient(ResultSet resultSet) throws SQLException {
        Client client = new Client(UUID.fromString(resultSet.getString("client_id")),
                resultSet.getString("first_name"),
                resultSet.getString("middle_name"),
                resultSet.getString("last_name"),
                resultSet.getFloat("bill"));
        if (resultSet.getString("ticket_id") != null) {
            List<Ticket> listTickets = new ArrayList<>();
            listTickets.add(createTicket(resultSet));
            while (resultSet.next()) {
                listTickets.add(createTicket(resultSet));
            }
            client.setTickets(listTickets);
        }
        return client;
    }

    public static Flight createFlight(ResultSet resultSet) throws SQLException {
        return new Flight(UUID.fromString(resultSet.getString("flight_id")),
                DateConverter.convert(resultSet.getString("date_of_departure")),
                DateConverter.convert(resultSet.getString("date_of_arrival")),
                createAirship(resultSet),
                createRoute(resultSet));
    }

    public static Route createRoute(ResultSet resultSet) throws SQLException {
        return new Route(UUID.fromString(resultSet.getString("route_id")),
                resultSet.getString("start_point"),
                resultSet.getString("end_point"));
    }

    public static Ticket createTicket(ResultSet resultSet) throws SQLException {
        return new Ticket(UUID.fromString(resultSet.getString("ticket_id")),
                createFlight(resultSet),
                Category.byOrdinal(resultSet.getInt("category")),
                resultSet.getFloat("cost"),
                resultSet.getFloat("baggage"),
                Status.byOrdinal(resultSet.getInt("status")));
    }
}

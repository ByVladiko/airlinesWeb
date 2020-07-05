import dao.impl.TicketDAO;
import repository.DatabaseConnectivityProvider;

import java.sql.Connection;
import java.sql.SQLException;

public class MainApp {
    public static void main(String[] args) {
        DatabaseConnectivityProvider.registryDriver();
        try (Connection connection = DatabaseConnectivityProvider.getConnection()) {
            TicketDAO ticketDAO = new TicketDAO();
            System.out.println(ticketDAO.getAll(connection).get(0).getFlight().getDateOfDeparture());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

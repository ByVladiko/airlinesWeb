import dao.impl.ClientDAO;
import dao.impl.TicketDAO;
import model.Client;
import model.Ticket;
import repository.DatabaseConnectivityProvider;
import service.BusinessLogic;

import java.sql.Connection;
import java.sql.SQLException;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        DatabaseConnectivityProvider.registryDriver();
        try (Connection connection = DatabaseConnectivityProvider.getConnection()) {
            BusinessLogic businessLogic = new BusinessLogic();
            ClientDAO clientDAO = new ClientDAO();
            TicketDAO ticketDAO = new TicketDAO();
            Client client = clientDAO.getById("9ffc9e34-0604-4503-bb29-4a923c030ecf");
            Ticket ticket = ticketDAO.getById("06c96a92-01f6-45b8-a4cc-7d663de30d76");
            businessLogic.buyTicketToClient(connection, client, ticket);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

import dao.impl.TicketDAO;
import repository.DatabaseConnectivityProvider;

import java.sql.Connection;
import java.sql.SQLException;

public class MainApp {
    public static void main(String[] args) {
        DatabaseConnectivityProvider.registryDriver();
//        try (Connection connection = DatabaseConnectivityProvider.getConnection()) {
//            BusinessLogic businessLogic = new BusinessLogic();
//            ClientDAO clientDAO = new ClientDAO();
//            TicketDAO ticketDAO = new TicketDAO();
//            Client client = clientDAO.getById(connection, "9ffc9e34-0604-4503-bb29-4a923c030ecf");
//            Ticket ticket = ticketDAO.getById(connection, "06c96a92-01f6-45b8-a4cc-7d663de30d76");
//            businessLogic.buyTicketToClient(connection, client, ticket);
//            System.out.println(clientDAO.getAll(connection).get(0));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try (Connection connection = DatabaseConnectivityProvider.getConnection()) {
            TicketDAO ticketDAO = new TicketDAO();
            System.out.println(ticketDAO.getAll(connection).get(0).getFlight().getDateOfDeparture().getTime());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

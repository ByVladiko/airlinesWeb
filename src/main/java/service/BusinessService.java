package service;

public class BusinessService {

//    private static final Logger logger = LoggerFactory.getLogger(BusinessService.class);
//
//    public void buyTicketToClient(final Connection connection, Client client, Ticket ticket) throws SQLException {
//        try {
//            connection.setAutoCommit(false);
//            reserveTicket(connection, ticket);
//            connection.setSavepoint("RESERVED");
//            addTicketToClient(connection, client, ticket);
//            connection.setSavepoint("ADDED");
//            buyTicket(connection, client, ticket);
//            connection.setSavepoint("SOLD");
//            connection.commit();
//        } catch (SQLException | BusinessServiceException e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//            connection.rollback();
//        }
//    }
//
//    public void addTicketToDB(final Connection connection, Ticket ticket) throws SQLException, BusinessServiceException {
//        try {
//            connection.setAutoCommit(false);
//            checkTicketForAdding(connection, ticket);
//            TicketDAO ticketDAO = new TicketDAO();
//            ticketDAO.create(connection, ticket);
//            connection.commit();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            connection.rollback();
//        }
//    }
//
//    private void reserveTicket(final Connection connection, Ticket ticket) throws BusinessServiceException, SQLException {
//        try {
//            TicketDAO ticketDAO = new TicketDAO();
//            if (ticket.getStatus().equals(Status.FREE)) {
//                ticket.setStatus(Status.RESERVED);
//                ticketDAO.update(connection, ticket);
//            } else {
//                throw new BusinessServiceException("Ticket unavailable");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            logger.error(e.getMessage(), e);
//            connection.rollback();
//        }
//    }
//
//    private void addTicketToClient(final Connection connection, Client client, Ticket ticket) throws SQLException {
//        String queryCountTicketOfCategory = "UPDATE ticket SET client = ? WHERE id = ?";
//        try (PreparedStatement statement = connection.prepareStatement(queryCountTicketOfCategory)) {
//            statement.setString(1, client.getId().toString());
//            statement.setString(2, ticket.getId().toString());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            logger.error(e.getMessage(), e);
//            connection.rollback();
//        }
//    }
//
//    private void buyTicket(final Connection connection, Client client, Ticket ticket) throws BusinessServiceException, SQLException {
//        try {
//            ClientDAO clientDAO = new ClientDAO();
//            TicketDAO ticketDAO = new TicketDAO();
//            float billOfClient = client.getBill();
//            if (billOfClient < ticket.getCost()) {
//                throw new BusinessServiceException("Not enough money on the account");
//            }
//            client.setBill(billOfClient - ticket.getCost());
//            clientDAO.update(connection, client);
//            ticket.setStatus(Status.SOLD);
//            ticketDAO.update(connection, ticket);
//        } catch (SQLException e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//            connection.rollback();
//        }
//    }
//
//    private void checkTicketForAdding(final Connection connection, Ticket ticket) throws BusinessServiceException, SQLException {
//        String queryCountTicketOfCategory = "SELECT COUNT(id) as count FROM ticket WHERE category = ? and flight = ?";
//        try (PreparedStatement statement = connection.prepareStatement(queryCountTicketOfCategory)) {
//            statement.setInt(1, ticket.getCategory().getIndex());
//            statement.setString(2, ticket.getFlight().getId().toString());
//            ResultSet resultSet = statement.executeQuery();
//            if (getCountOfSeat(ticket) == resultSet.getInt("count")) {
//                throw new BusinessServiceException("Out of seats");
//            }
//        } catch (SQLException e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//            connection.rollback();
//        }
//    }
//
//    private int getCountOfSeat(Ticket ticket) {
//        Category categoryOfTicket = ticket.getCategory();
//        if (categoryOfTicket.equals(Category.BUSINESS)) {
//            return ticket.getFlight().getAirship().getBusinessCategory();
//        } else if (categoryOfTicket.equals(Category.ECONOMY)) {
//            return ticket.getFlight().getAirship().getEconomyCategory();
//        } else if (categoryOfTicket.equals(Category.PREMIUM)) {
//            return ticket.getFlight().getAirship().getPremiumCategory();
//        }
//        return 0;
//    }

}

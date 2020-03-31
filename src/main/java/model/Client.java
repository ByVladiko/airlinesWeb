package model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private List<Ticket> tickets;

    public Client(UUID id, String firstName, String middleName, String lastName, List<Ticket> tickets) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.tickets = tickets;
    }

    public Client(String firstName, String middleName, String lastName, List<Ticket> tickets) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.tickets = tickets;
    }

    public Client() {
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj.getClass() == this.getClass()) {
            Client client = (Client) obj;
            if (client.tickets.size() != this.tickets.size()) {
                return false;
            }

            for (int i = 0; i < this.tickets.size(); ++i) {
                if (!client.tickets.contains(this.tickets.get(i))) {
                    return false;
                }
            }

            return this.id.equals(client.id)
                    && this.firstName.equals(client.firstName)
                    && this.middleName.equals(client.middleName)
                    && this.lastName.equals(client.lastName);
        }

        return false;
    }


    public int hashCode() {
        int result = this.id.hashCode();
        result = 31 * result + (this.firstName == null ? 0 : this.firstName.hashCode());
        result = 31 * result + (this.middleName == null ? 0 : this.middleName.hashCode());
        result = 31 * result + (this.lastName == null ? 0 : this.lastName.hashCode());

        for (Ticket ticket : tickets) {
            result = 31 * result + ticket.hashCode();
        }

        return result;
    }
}

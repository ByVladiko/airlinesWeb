package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private float bill;
    private List<Ticket> tickets;

    public Client(UUID id, String firstName, String middleName, String lastName, float bill, List<Ticket> tickets) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.bill = bill;
        this.tickets = tickets;
    }

    public Client(String firstName, String middleName, String lastName, float bill, List<Ticket> tickets) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.bill = bill;
        this.tickets = tickets;
    }

    public Client(UUID id, String firstName, String middleName, String lastName, float bill) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.bill = bill;
        this.tickets = new ArrayList<>();
    }

    public Client(String firstName, String middleName, String lastName, float bill) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.bill = bill;
        this.tickets = new ArrayList<>();
    }

    public Client(String firstName, String middleName, String lastName) {
        this.id = UUID.randomUUID();
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.bill = 0;
        this.tickets = new ArrayList<>();
    }

    public Client() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public float getBill() {
        return bill;
    }

    public void setBill(float bill) {
        this.bill = bill;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bill=" + bill +
                ", tickets=" + tickets +
                '}';
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
                    && this.lastName.equals(client.lastName)
                    && this.bill == bill;
        }

        return false;
    }


    public int hashCode() {
        int result = this.id.hashCode();
        result = 31 * result + (this.firstName == null ? 0 : this.firstName.hashCode());
        result = 31 * result + (this.middleName == null ? 0 : this.middleName.hashCode());
        result = 31 * result + (this.lastName == null ? 0 : this.lastName.hashCode());
        result = 31 * result + Float.hashCode(this.bill);

        for (Ticket ticket : this.tickets) {
            result = 31 * result + ticket.hashCode();
        }

        return result;
    }
}

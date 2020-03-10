package model;

import enums.Category;

import java.io.Serializable;
import java.util.UUID;

public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private Flight flight;
    private Category category;
    private float cost;

    public Ticket(UUID id, Flight flight, Category category, float cost) {
        this.id = id;
        this.flight = flight;
        this.category = category;
        this.cost = cost;
    }

    public Ticket(Flight flight, Category category, float cost) {
        this.id = UUID.randomUUID();
        this.flight = flight;
        this.category = category;
        this.cost = cost;
    }

    public Ticket() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", flight=" + flight +
                ", category=" + category +
                ", cost=" + cost +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (obj.getClass() == this.getClass()) {
            Ticket ticket = (Ticket) obj;
            return id.equals(ticket.id)
                    && flight.equals(ticket.flight)
                    && category.equals(ticket.category)
                    && cost == ticket.cost;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (flight == null ? 0 : flight.hashCode());
        result = 31 * result + (category == null ? 0 : category.hashCode());
        result = 31 * result + Float.hashCode(cost);
        return result;
    }
}

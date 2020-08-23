package com.airlines.model.airship;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Ticket {

    @Id
    private UUID id;
    @ManyToOne(fetch = FetchType.EAGER)
    private Flight flight;
    @Enumerated(EnumType.ORDINAL)
    private Category category;
    private float cost;
    private float baggage;
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public Ticket(UUID id, Flight flight, Category category, float cost, float baggage, Status status) {
        this.id = id;
        this.flight = flight;
        this.category = category;
        this.cost = cost;
        this.baggage = baggage;
        this.status = status;
    }

    public Ticket(Flight flight, Category category, float cost, float baggage, Status status) {
        this.id = UUID.randomUUID();
        this.flight = flight;
        this.category = category;
        this.cost = cost;
        this.baggage = baggage;
        this.status = status;
    }

    public Ticket() {
    }

    public UUID getId() {
        return id;
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

    public float getBaggage() {
        return baggage;
    }

    public void setBaggage(float baggage) {
        this.baggage = baggage;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", flight=" + flight +
                ", category=" + category +
                ", cost=" + cost +
                ", baggage=" + baggage +
                ", status=" + status +
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
                    && cost == ticket.cost
                    && status.equals(ticket.status);
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
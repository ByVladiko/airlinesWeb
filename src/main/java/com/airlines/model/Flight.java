package com.airlines.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;

@Entity
public class Flight {

    @Id
    private UUID id;
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Date dateOfDeparture;
    private Date dateOfArrival;
    @ManyToOne(fetch = FetchType.EAGER)
    private Airship airship;
    @ManyToOne(fetch = FetchType.EAGER)
    private Route route;

    public Flight(UUID id, Date dateOfDeparture, Date dateOfArrival, Airship airship, Route route) {
        this.id = id;
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.airship = airship;
        this.route = route;
    }

    public Flight(Date dateOfDeparture, Date dateOfArrival, Airship airship, Route route) {
        this.id = UUID.randomUUID();
        this.dateOfDeparture = dateOfDeparture;
        this.dateOfArrival = dateOfArrival;
        this.airship = airship;
        this.route = route;
    }

    public Flight() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(Date dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public Date getDateOfArrival() {
        return dateOfArrival;
    }

    public void setDateOfArrival(Date dateOfArrival) {
        this.dateOfArrival = dateOfArrival;
    }

    public Airship getAirship() {
        return airship;
    }

    public void setAirship(Airship airship) {
        this.airship = airship;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return String.format("%s %s - %s", route.toString(), dateOfDeparture, dateOfArrival);
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
            Flight flight = (Flight) obj;
            return id.equals(flight.id)
                    && dateOfDeparture.equals(flight.dateOfDeparture)
                    && dateOfArrival.equals(flight.dateOfArrival)
                    && airship.equals(flight.airship)
                    && route.equals(flight.route);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (dateOfDeparture == null ? 0 : dateOfDeparture.hashCode());
        result = 31 * result + (dateOfArrival == null ? 0 : dateOfArrival.hashCode());
        result = 31 * result + (airship == null ? 0 : airship.hashCode());
        result = 31 * result + (route == null ? 0 : route.hashCode());
        return result;
    }

}
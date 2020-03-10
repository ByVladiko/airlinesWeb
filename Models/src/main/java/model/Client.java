package model;

import java.io.Serializable;
import java.util.UUID;

public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private Airship airship;
    private Route route;

    public Client(UUID id, Airship airship, Route route) {
        this.id = id;
        this.airship = airship;
        this.route = route;
    }

    public Client(Airship airship, Route route) {
        this.id = UUID.randomUUID();
        this.airship = airship;
        this.route = route;
    }

    public Client() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
        return "Client{" +
                "id=" + id +
                ", airship=" + airship +
                ", route=" + route +
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
            Client client = (Client) obj;
            return id.equals(client.id)
                    && airship.equals(client.airship)
                    && route.equals(client.route);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (airship == null ? 0 : airship.hashCode());
        result = 31 * result + (route == null ? 0 : route.hashCode());
        return result;
    }
}

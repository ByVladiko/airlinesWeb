package com.airlines.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Route {

    @Id
    private UUID id;
    private String startPoint;
    private String endPoint;

    public Route(String startPoint, String endPoint) {
        this.id = UUID.randomUUID();
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Route(UUID id, String startPoint, String endPoint) {
        this.id = id;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Route() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public String toString() {
        return startPoint + " --> " + endPoint;
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
            Route route = (Route) obj;
            return id.equals(route.id) || startPoint.equals(route.getStartPoint()) || endPoint.equals(route.getEndPoint());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (startPoint == null ? 0 : startPoint.hashCode());
        result = 31 * result + (endPoint == null ? 0 : endPoint.hashCode());
        return result;
    }
}
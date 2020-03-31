package model;

import java.io.Serializable;
import java.util.UUID;

public class Route implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String startPoint;
    private String endPoint;


    public Route(String startPoint, String endPoint) {
        this.id = UUID.randomUUID();
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Route() {
    }

    public Route(UUID id, String startPoint, String endPoint) {
        this.id = id;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
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
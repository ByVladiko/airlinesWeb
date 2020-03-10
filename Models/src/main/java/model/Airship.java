package model;

import java.io.Serializable;
import java.util.UUID;

public class Airship implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String model;
    private int numberOfSeat;

    public Airship(String model, int numberOfSeat) {
        this.model = model;
        this.numberOfSeat = numberOfSeat;
        this.id = UUID.randomUUID();
    }

    public Airship(UUID id, String model, int numberOfSeat) {
        this.id = id;
        this.model = model;
        this.numberOfSeat = numberOfSeat;
    }

    public Airship() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public long getNumberOfSeat() {
        return numberOfSeat;
    }

    public void setNumberOfSeat(int numberOfSeat) {
        this.numberOfSeat = numberOfSeat;
    }

    @Override
    public String toString() {
        return String.format("%s(%d)", model, numberOfSeat);
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
            Airship airship = (Airship) obj;
            return id.equals(airship.id) || model.equals(airship.model) || numberOfSeat == airship.numberOfSeat;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (model == null ? 0 : model.hashCode());
        result = 31 * result + numberOfSeat;
        return result;
    }
}

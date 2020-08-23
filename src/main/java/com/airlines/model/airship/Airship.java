package com.airlines.model.airship;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Airship {

    @Id
    private UUID id;
    private String model;
    private int economyCategory;
    private int businessCategory;
    private int premiumCategory;

    public Airship(String model, int economyCategory, int businessCategory, int premiumCategory) {
        this.id = UUID.randomUUID();
        this.model = model;
        this.economyCategory = economyCategory;
        this.businessCategory = businessCategory;
        this.premiumCategory = premiumCategory;
    }

    public Airship(UUID id, String model, int economyCategory, int businessCategory, int premiumCategory) {
        this.id = id;
        this.model = model;
        this.economyCategory = economyCategory;
        this.businessCategory = businessCategory;
        this.premiumCategory = premiumCategory;
    }

    public Airship() {
    }

    public UUID getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getEconomyCategory() {
        return economyCategory;
    }

    public void setEconomyCategory(int economyCategory) {
        this.economyCategory = economyCategory;
    }

    public int getBusinessCategory() {
        return businessCategory;
    }

    public void setBusinessCategory(int businessCategory) {
        this.businessCategory = businessCategory;
    }

    public int getPremiumCategory() {
        return premiumCategory;
    }

    public void setPremiumCategory(int premiumCategory) {
        this.premiumCategory = premiumCategory;
    }

    @Override
    public String toString() {
        return "Airship{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", economyCategory=" + economyCategory +
                ", businessCategory=" + businessCategory +
                ", premiumCategory=" + premiumCategory +
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
            Airship airship = (Airship) obj;
            return id.equals(airship.id)
                    && model.equals(airship.model)
                    && economyCategory == airship.economyCategory
                    && businessCategory == airship.businessCategory
                    && premiumCategory == airship.premiumCategory;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (model == null ? 0 : model.hashCode());
        result = 31 * result + economyCategory;
        result = 31 * result + businessCategory;
        result = 31 * result + premiumCategory;
        return result;
    }
}


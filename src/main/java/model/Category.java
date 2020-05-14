package model;

public enum Category {

    ECONOMY, PREMIUM, FIRST, BUSINESS;

    public static Category byOrdinal(int ord) {
        for (Category category : Category.values()) {
            if (category.ordinal() + 1 == ord) {
                return category;
            }
        }
        return null;
    }

    public int getIndex() {
        return ordinal() + 1;
    }

}
package client;

public enum MealType {
    BREAKFAST("Breakfast"), 
    LUNCH("Lunch"), 
    DINNER("Dinner");

    private final String mealType;

    MealType(String mealType) {
        this.mealType = mealType;
    }
}
package miniproject;

public enum MealType {
    BREAKFAST("Breakfast"), 
    LUNCH("Lunch"), 
    DINNER("Dinner");

    private final String mealType;

    MealType(String mealType) {
        this.mealType = mealType;
    }

    @Override
    public String toString() {
        return (String) this.mealType;
    }
}
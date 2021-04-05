package io.joaoseidel.knapsack.genetic;

public enum Items {
    SLEEPING_BAG(15, 15),
    ROPE(3, 7),
    KNIFE(2, 10),
    FLASHLIGHT(5, 5),
    WATER_BOTTLE(9, 8),
    FOOD(20, 17);

    private final int size;
    private final int value;

    Items(int size, int value) {
        this.size = size;
        this.value = value;
    }

    public int getSize() {
        return size;
    }

    public int getValue() {
        return value;
    }
}

package io.joaoseidel.knapsack.genetic.models;

public class Gene {
    private String name;
    private int size;
    private int value;
    private boolean active;

    public Gene(String name, int size, int value) {
        this.name = name;
        this.size = size;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Gene setName(String name) {
        this.name = name;
        return this;
    }

    public int getSize() {
        return size;
    }

    public Gene setSize(int size) {
        this.size = size;
        return this;
    }

    public int getValue() {
        return value;
    }

    public Gene setValue(int value) {
        this.value = value;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Gene setActive(boolean active) {
        this.active = active;
        return this;
    }
}

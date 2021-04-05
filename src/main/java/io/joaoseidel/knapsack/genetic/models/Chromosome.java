package io.joaoseidel.knapsack.genetic.models;

import java.util.UUID;

public class Chromosome {
    private final UUID id;
    private int length;
    private Gene[] genes;

    public Chromosome(int length) {
        this.id = UUID.randomUUID();
        this.length = length;
    }

    public UUID getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public Chromosome setLength(int length) {
        this.length = length;
        return this;
    }

    public Gene[] getGenes() {
        return genes;
    }

    public Chromosome setGenes(Gene[] genes) {
        this.genes = genes;
        return this;
    }
}

package io.joaoseidel.knapsack.genetic.models;

public class Population {
    private final Phenotype[] phenotypes;
    private final int maxGeneration;
    private final int criteria;
    private int size;
    private int generation;

    public Population(Builder builder) {
        generation = builder.generation;
        size = builder.size;
        phenotypes = builder.phenotypes;
        criteria = builder.criteria;
        maxGeneration = builder.maxGeneration;
    }

    public int getCriteria() {
        return criteria;
    }

    public int getMaxGeneration() {
        return maxGeneration;
    }

    public int getGeneration() {
        return generation;
    }

    public void increaseGeneration() {
        this.generation++;
    }

    public int getSize() {
        return size;
    }

    public Population setSize(int size) {
        this.size = size;
        return this;
    }

    public Phenotype[] getPhenotypes() {
        return phenotypes;
    }

    public static class Builder {
        private final int size;
        private final int generation = 0;
        private final Phenotype[] phenotypes;
        private int criteria = 30;
        private int maxGeneration = 1000;

        public Builder(int size) {
            this.size = size;
            this.phenotypes = new Phenotype[size];
        }

        public Builder withRandomIndividuals() {
            for (int individual = 0; individual < size; individual++) {
                this.phenotypes[individual] = new Phenotype.Builder().withRandomChromosome().build();
            }
            return this;
        }

        public Builder withMaxGeneration(int maxGeneration) {
            this.maxGeneration = maxGeneration;
            return this;
        }

        public Builder withCriteria(int criteria) {
            this.criteria = criteria;
            return this;
        }

        public Population build() {
            return new Population(this);
        }
    }
}

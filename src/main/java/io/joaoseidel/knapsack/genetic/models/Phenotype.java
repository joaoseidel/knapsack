package io.joaoseidel.knapsack.genetic.models;

import io.joaoseidel.knapsack.genetic.ChromosomeDecoder;
import io.joaoseidel.knapsack.genetic.Items;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class Phenotype implements Cloneable {
    private final Chromosome chromosome;
    private double probability;

    private Phenotype(Builder builder) {
        chromosome = builder.chromosome;
    }

    public int getFitness() {
        int fitness = 0;
        for (int i = 0; i < chromosome.getGenes().length; i++) {
            final Gene gene = chromosome.getGenes()[i];
            if (gene.isActive()) {
                fitness += gene.getValue();
            }
        }
        return fitness;
    }

    public Chromosome getChromosome() {
        return chromosome;
    }

    public double getProbability() {
        return probability;
    }

    public Phenotype setProbability(double probability) {
        this.probability = probability;
        return this;
    }

    public static class Builder {
        private Chromosome chromosome;

        public Builder() {
        }

        public Builder withRandomChromosome() {
            Random random = new Random();
            Chromosome chromosome = new Chromosome(ChromosomeDecoder.CHROMOSOME_SIZE);
            Gene[] genes = new Gene[chromosome.getLength()];
            PriorityQueue<Items> itemsPriorityQueue = new PriorityQueue<>(Arrays.asList(Items.values()));

            while (!itemsPriorityQueue.isEmpty()) {
                Items item = itemsPriorityQueue.poll();
                Gene generatedGene = new Gene(item.name(), item.getSize(), item.getValue());
                generatedGene.setActive(Math.abs(random.nextInt() % 2) == 1);
                genes[item.ordinal()] = generatedGene;
            }
            chromosome.setGenes(genes);

            this.chromosome = chromosome;
            return this;
        }

        public Phenotype build() {
            return new Phenotype(this);
        }
    }
}


package io.joaoseidel.knapsack;

import io.joaoseidel.knapsack.genetic.ChromosomeDecoder;
import io.joaoseidel.knapsack.genetic.models.Phenotype;
import io.joaoseidel.knapsack.genetic.models.Population;

public class Main {
    private static final int POPULATION_SIZE = 10;
    private static final int BAG_SIZE = 30;
    private static final int MAX_GENERATION = 100;

    public static void main(String[] args) {
        Population population = new Population.Builder(POPULATION_SIZE)
                .withRandomIndividuals()
                .withCriteria(BAG_SIZE)
                .withMaxGeneration(MAX_GENERATION)
                .build();

        Phenotype fittest = new KnapsackFF().apply(population);
        System.out.println("=============");
        System.out.println("Population of " + population.getSize() + " individuals.");
        System.out.println("Generations: " + population.getGeneration());
        System.out.println("The individual has a fitness of: " + fittest.getFitness());
        System.out.println("=============");
        System.out.println("Best selection (binary): " + ChromosomeDecoder.encode(fittest.getChromosome()));
        System.out.println("Best selection: " + ChromosomeDecoder.decode(fittest.getChromosome()));
    }
}

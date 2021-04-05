package io.joaoseidel.knapsack;

import io.joaoseidel.knapsack.genetic.ChromosomeDecoder;
import io.joaoseidel.knapsack.genetic.models.Chromosome;
import io.joaoseidel.knapsack.genetic.models.Gene;
import io.joaoseidel.knapsack.genetic.models.Phenotype;
import io.joaoseidel.knapsack.genetic.models.Population;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class KnapsackFF implements Function<Population, Phenotype> {
    private static int score;
    private List<Phenotype> roulette;

    private Phenotype rouletteSelection() {
        Phenotype selected = null;

        double totalSum = 0.0;
        for (Phenotype phenotype : roulette) {
            totalSum += phenotype.getFitness();
        }

        for (Phenotype phenotype : roulette) {
            phenotype.setProbability(phenotype.getFitness() / totalSum);
        }

        double partialSum = 0;
        double rouletteNumber = Math.random();

        for (Phenotype phenotype : this.roulette) {
            partialSum += phenotype.getProbability();
            if (partialSum >= rouletteNumber) {
                selected = phenotype;
                break;
            }
        }

        return selected;
    }

    private void crossover(Phenotype phenotypeA, Phenotype phenotypeB) {
        Random random = new Random();

        //Select a random crossover point
        int crossOverPoint = random.nextInt(ChromosomeDecoder.CHROMOSOME_SIZE);

        //Swap values among parents
        for (int i = 0; i < crossOverPoint; i++) {
            Gene temp = phenotypeA.getChromosome().getGenes()[i];
            phenotypeA.getChromosome().getGenes()[i] = phenotypeB.getChromosome().getGenes()[i];
            phenotypeB.getChromosome().getGenes()[i] = temp;
        }
    }

    private void mutate(Phenotype phenotypeA, Phenotype phenotypeB) {
        Random rn = new Random();

        Chromosome fittest = phenotypeA.getChromosome();
        Chromosome secondFittest = phenotypeB.getChromosome();

        // mutate the fittest phenotype
        int mutationPoint = rn.nextInt(ChromosomeDecoder.CHROMOSOME_SIZE);
        boolean active = fittest.getGenes()[mutationPoint].isActive();
        final Gene[] aGenes = fittest.getGenes();
        phenotypeA.getChromosome().getGenes()[mutationPoint] = aGenes[mutationPoint].setActive(!active);

        // mutate the second fittest phenotype
        mutationPoint = rn.nextInt(ChromosomeDecoder.CHROMOSOME_SIZE);
        active = secondFittest.getGenes()[mutationPoint].isActive();
        final Gene[] bGenes = secondFittest.getGenes();
        phenotypeA.getChromosome().getGenes()[mutationPoint] = bGenes[mutationPoint].setActive(!active);
    }

    private boolean isValid(Phenotype phenotype, int criteria) {
        int count = 0;
        for (Gene gene : phenotype.getChromosome().getGenes()) {
            if (gene.isActive()) {
                count += gene.getSize();
            }
        }

        int fitness = phenotype.getFitness();
        return count <= criteria && fitness > score;
    }

    @Override
    public Phenotype apply(Population population) {
        this.roulette = new ArrayList<>(Arrays.asList(population.getPhenotypes()));

        Phenotype fittestPhenotype = null;
        while (population.getGeneration() < population.getMaxGeneration()) {
            population.increaseGeneration();

            // selection
            Phenotype phenotypeA = rouletteSelection();
            Phenotype phenotypeB = rouletteSelection();

            crossover(phenotypeA, phenotypeB);
            mutate(phenotypeA, phenotypeB);

            // which one between phenA and phenB is better
            Phenotype fittest = phenotypeA;
            if (phenotypeB.getFitness() > phenotypeA.getFitness()) {
                fittest = phenotypeB;
            }

            // replace the fittest phenotype
            if (isValid(fittest, population.getCriteria())) {
                fittestPhenotype = fittest;
                score = fittestPhenotype.getFitness();
            }
        }

        return fittestPhenotype;
    }
}

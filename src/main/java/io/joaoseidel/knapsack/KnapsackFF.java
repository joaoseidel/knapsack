package io.joaoseidel.knapsack;

import io.joaoseidel.knapsack.genetic.ChromosomeDecoder;
import io.joaoseidel.knapsack.genetic.models.Chromosome;
import io.joaoseidel.knapsack.genetic.models.Gene;
import io.joaoseidel.knapsack.genetic.models.Phenotype;
import io.joaoseidel.knapsack.genetic.models.Population;

import java.util.Random;
import java.util.function.Function;

public class KnapsackFF implements Function<Population, Phenotype> {
    private static int score;

    private Phenotype selection(Phenotype[] phenotypes) {
        int maxFit = Integer.MIN_VALUE;

        Phenotype selected = null;
        for (Phenotype phenotype : phenotypes) {
            if (maxFit <= phenotype.getFitness()) {
                maxFit = phenotype.getFitness();
                selected = phenotype;
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

    private Phenotype reproduce(Phenotype phenotypeA, Phenotype phenotypeB, Population population) {
        Phenotype fittest = phenotypeA;
        if (phenotypeB.getFitness() > phenotypeA.getFitness()) {
            fittest = phenotypeB;
        }

        final Phenotype[] phenotypes = population.getPhenotypes();
        int minFitVal = Integer.MAX_VALUE;
        int minFitIndex = 0;
        for (int i = 0; i < phenotypes.length; i++) {
            if (minFitVal >= phenotypes[i].getFitness()) {
                minFitVal = phenotypes[i].getFitness();
                minFitIndex = i;
            }
        }

        phenotypes[minFitIndex] = fittest;
        population.setPhenotypes(phenotypes);
        return fittest;
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

        Phenotype fittestPhenotype = null;
        while (population.getGeneration() < population.getMaxGeneration()) {
            population.increaseGeneration();

            // selection
            Phenotype phenotypeA = selection(population.getPhenotypes());
            Phenotype phenotypeB = selection(population.getPhenotypes());

            crossover(phenotypeA, phenotypeB);
            mutate(phenotypeA, phenotypeB);
            Phenotype fittest = reproduce(phenotypeA, phenotypeB, population);

            // update score and select the fittest phenotype
            if (isValid(fittest, population.getCriteria())) {
                fittestPhenotype = fittest;
                score = fittestPhenotype.getFitness();
            }
        }

        return fittestPhenotype;
    }
}

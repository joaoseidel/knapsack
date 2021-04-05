package io.joaoseidel.knapsack.genetic;

import io.joaoseidel.knapsack.genetic.models.Chromosome;
import io.joaoseidel.knapsack.genetic.models.Gene;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ChromosomeDecoder {
    public static final int CHROMOSOME_SIZE = Items.values().length;

    public static String decode(Chromosome chromosome) {
        return Arrays.stream(chromosome.getGenes())
                .filter(Gene::isActive)
                .map(Gene::getName)
                .collect(Collectors.joining(", "));
    }

    public static String encode(Chromosome chromosome) {
        return Arrays.stream(chromosome.getGenes())
                .map(gene -> {
                    if (gene.isActive()) {
                        return "1";
                    }
                    return "0";
                })
                .collect(Collectors.joining());
    }
}

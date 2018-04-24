/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

import Engine.GeneticEngine;
import Engine.Paramters;

/**
 *
 * @author Thales
 */
public class GeneticAlgorithm {

    static String target = "In computer science and operations research, a genetic algorithm (GA) is a metaheuristic inspired by the process of natural selection that belongs to the larger class of evolutionary algorithms (EA). Genetic algorithms are commonly used to generate high-quality solutions to optimization and search problems by relying on bio-inspired operators such as mutation, crossover and selection.";
    
    public static void main(String[] args) {
                
        EvolvableString es = new EvolvableString(target.length());
        
        Paramters p = new Paramters();
        p.populationSize = 150;
        p.randomMax = 128;
        p.randomMin = 31;
        
        GeneticEngine<EvolvableString> ge = new GeneticEngine<EvolvableString>(p,es) {
            
            @Override
            public double evaluateFitness(EvolvableString a) {
                double fitness = 0;
                for (int i = 0; i < a.string.length; i++){
                    if (a.string[i] == target.charAt(i)){
                        fitness++;
                    }
                }
                return fitness/a.string.length;
            }
        
        };
        
        for (int i = 0; i < 5000; i++){
            ge.evolve();
            System.out.println("\n"+ge);
            if (ge.best.fitness >= 0.99999999) break;
        }
        
    }
}

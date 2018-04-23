/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

import genetic_code.GeneticEngine;

/**
 *
 * @author Thales
 */
public class GeneticAlgorithm {

    static String target = "In computer science and operations research, a genetic algorithm (GA) is a metaheuristic inspired by the process of natural selection that belongs to the larger class of evolutionary algorithms (EA). Genetic algorithms are commonly used to generate high-quality solutions to optimization and search problems by relying on bio-inspired operators such as mutation, crossover and selection.";
    //static String target = "ABCDEFGHIJKLMNOP";
    public static void main(String[] args) {

        //create the engine        
        GeneticEngine ge = new GeneticEngine(target.length(),100);
        ge.randomize(032,200);
        //System.out.println("randomized | "+population[0]);
        while(ge.best.fitness < 0.99999){
            EvolvableString s = new EvolvableString(target.length());
            for (int i = 0; i < ge.pop.length; i++){
                s.fromDNA(ge.pop[i]);
                s.evaluate(target);
                ge.pop[i] = s.getDNA();
            } 
            
            //selecte only the best one
            //p.elitism(1);
            ge.selection(1);
            //p.addRandons(10, 032, 200);
            //p.crossOver();
            ge.mutate(0.001,032,200);
            ge.updatePop();
            System.out.println(ge);
            System.out.println(s.fromDNA(ge.best).string);
        }
        //remove unwanted add to generation after the best was found
        
    }
    
}

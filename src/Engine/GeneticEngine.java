/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author TERMINAL
 * @param <T>
 */
public abstract class GeneticEngine<T extends Evolvable>{
    
    private Random random;
    public Paramters p;
    
    public T sample;
    public int generation;
    public ArrayList<Organism> pop;
    public ArrayList<Organism> popNextGeneration;
    public double averageFitness;
    public Organism generationBest;
    public Organism best;
    
    public GeneticEngine(Paramters parameters, T sample){
        this.sample = sample;
        this.p = parameters;
        random = new Random();
        pop = new ArrayList<>(p.populationSize);
        popNextGeneration = new ArrayList<>(p.populationSize);
        for (int i = 0; i < p.populationSize; i++){
            pop.add(new Organism(sample, i).ramdomize(p.randomMin, p.randomMax, random));
        }
        best = pop.get(0);
        generationBest = best;
    }
    
    public abstract double evaluateFitness(T a);
    
    public void evolve(){
        //evaluate population and calc everage fitness
        averageFitness = 0;
        pop.forEach((o) -> {
            o.fitness = evaluateFitness(o.getFenotype());
            averageFitness += o.fitness;
            if (o.fitness > generationBest.fitness){
                generationBest = o;
            }
        });
        averageFitness = averageFitness/pop.size();
        generationBest.generationBest = true;
        
        //if is the best ever
        if (best.fitness < generationBest.fitness){
            best = generationBest;
        }
        
        //sort population
        Collections.sort(pop);
        //matte
            //generate probabilitys
        double scoreSum = 0;
        double[] score = new double[p.mattingPool];
        for (int i = 0; i < p.mattingPool; i++){
            score[i] = pop.get(i).fitness;
            scoreSum += score[i];
        }
        
        //crossover
        popNextGeneration.clear();
        while(popNextGeneration.size() < p.populationSize){
            //offspring
            Organism child = pop.get(selector(score,scoreSum)).crossover(
                    pop.get(selector(score,scoreSum)),
                    popNextGeneration.size(), random
            );
            child.mutate(p.mutationRate, p.randomMutation, p.randomMin, p.randomMax, p.mutationPower, random);
            popNextGeneration.add(child);
        }
        
        //add ellite
        for (int i = 0; i < p.ellitism; i++){
            popNextGeneration.add(pop.get(i));
        }
        
        //add random organisms
        for (int i = 0; i < p.addRandomOrganism; i++){
            popNextGeneration.add(
                new Organism(
                        sample,
                        popNextGeneration.size()
                ).ramdomize(
                    p.randomMin,
                    p.randomMax,
                    random
                )
            );
        }
        pop = (ArrayList<Organism>) popNextGeneration.clone();
        generation++;
    }
    
    private static int selector(double[] score, double scoreSum){
        int select = 0;
        double selector = Math.random()*scoreSum;
        while(selector > 0){
            selector -= score[select];
            select++;
        }
        select--;
        return select;
    }

    @Override
    public String toString(){
        return "Generation "+generation+":"+
                "\n Population size: "+pop.size()+
                "\n Average fitness: "+averageFitness+
                "\n Best of generation: "+generationBest.fitness+
                "\n Best ever: "+best.fitness;
    }

    public class Organism implements Comparable<Organism>{
        
        public final int id;
        public double[] genome;
        public double fitness;

        public boolean generationBest;

        public Organism(T sample, int id){
            this.id = id;
            genome = sample.getGenome();
            generationBest = false;
        }

        protected Organism(int genomeSize, int id){
            this.id = id;
            this.genome = new double[genomeSize];
        }

        public T getFenotype(){
           return (T) sample.copy().fromGenome(genome);
        }

        public Organism ramdomize(double min, double max, Random r){
            for (int i = 0; i < genome.length; i++){
                genome[i] = r.nextDouble()*(max-min)+min;
            }
            return this;
        }

        public void mutate(double mutationRate,
                           double randomMutationRate,
                           double min, double max,
                           double power, Random r){
            for (int i = 0; i < genome.length; i++){
                if (r.nextDouble() <= mutationRate){
                    if (r.nextDouble() <= randomMutationRate){
                        genome[i] = r.nextDouble()*(max-min)+min;
                    }else{
                        genome[i] += r.nextGaussian()*power;
                    }
                }
            }
        }

        public Organism crossover(Organism other, int id, Random r){
            Organism child = new Organism(genome.length, id);
            for (int i = 0; i < child.genome.length; i++){
                if (r.nextBoolean()){
                    child.genome[i] = this.genome[i];
                }else{
                    child.genome[i] = other.genome[i];
                }
            }
            return child;
        }

        public Organism copy(){
            Organism organismCopy = new Organism(genome.length, id);
            organismCopy.fitness = fitness;
            organismCopy.genome = new double[genome.length];
            organismCopy.generationBest = generationBest;
            System.arraycopy(genome, 0, organismCopy.genome, 0, genome.length);
            return organismCopy;
        }

        @Override
        public int compareTo(Organism other) {
            if (fitness > other.fitness){
                return -1;
            }else if(fitness < other.fitness){
                return 1;
            }else{
                return 0;
            }
        }

        @Override
        public String toString(){
            return "Organism id: "+id+" fitness: "+fitness;
        }

    }
}

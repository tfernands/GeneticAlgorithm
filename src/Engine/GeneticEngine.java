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
    public int generations;
    public ArrayList<Organism> pop;
    public ArrayList<Organism> lastPop;
    public double averageFitness;
    public Organism generationBest;
    public Organism best;
    
    public GeneticEngine(Paramters parameters, T sample){
        this.sample = sample;
        this.p = parameters;
        random = new Random();
        pop = new ArrayList<>();
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
        for (Organism o: pop){
            o.fitness = evaluateFitness(o.getFenotype());
            averageFitness += o.fitness;
            if (o.fitness > generationBest.fitness){
                generationBest = o;
            }
        }
        averageFitness /= pop.size();
        generationBest.generationBest = true;
        
        //if is the best ever
        if (best.fitness < generationBest.fitness){
            best = generationBest;
        }
        
        //sort population
        Collections.sort(pop);
        
        //remove bad organisms
        for (int i = 0; i < p.exclusion; i++){
            pop.remove(pop.size()-1);
        }
        
    }
    
    @Override
    public String toString(){
        return "Generation "+generations+": individuals: "+pop.size()+" average fitness: "+averageFitness+" best of generation: "+generationBest.fitness+" best ever: "+best.fitness;
    }
    
    public class Organism implements Comparable<Organism>{
    
        public final int id;
        public double[] genome;
        public double fitness;

        public boolean generationBest;

        public Organism(T fenotype, int id){
            this.id = id;
            genome = fenotype.getGenome();
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

        public void mutate(double mutationRate, double randomMutationRate, double min, double max, double power, Random r){
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
                    child.genome[i] = genome[i];
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

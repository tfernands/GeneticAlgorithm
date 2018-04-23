package genetic_code;

import java.util.Arrays;

public class GeneticEngine{

    public DNA[] pop;
    public DNA[] theBests;
    public DNA[] elite;
    public DNA best;
    public DNA generationBest;
    public double averageFitness;
    public int generation;
    public int generationOfLastBest;
    
    private boolean popSorted = false;
    
    public GeneticEngine(int dnaSize, int popSize){
        pop = new DNA[popSize];
        for (int i = 0; i < pop.length; i++){
            pop[i] = new DNA(dnaSize);
        }
        best = new DNA(dnaSize);
        elite = new DNA[0];
    }

    public static void randomize(DNA[] p, double min, double max){
        for (DNA p1 : p) {
            p1.randomize(min, max);
        }
    }
    public void randomize(double min, double max){
        for (DNA pop1 : pop) {
            pop1.randomize(min, max);
        }
    }
    
    public void setPopulation(DNA[] pop){
        this.pop = pop;
    }
    
    public void elitism(int n){
        sort();
        elite = new DNA[n];
        System.arraycopy(pop, 0, elite, 0, n);
    }
    
    public void addRandons(int n, double min, double max){
        sort();
        for (int i = pop.length-n; i < pop.length; i++){
            pop[i].randomize(min, max);
        }
    }
    
    public void selection(int threshold){
        sort();
        theBests = new DNA[threshold];
        System.arraycopy(pop, 0, theBests, 0, threshold);
        double[] scores = new double[threshold];
        for (int i = 0; i < threshold; i++){
            scores[i] = theBests[i].fitness;
        }
        normalizeArray(scores);
        averageFitness = 0;
        generationBest = pop[0];
        for (int i = 0; i < pop.length; i++){
            averageFitness += pop[i].fitness;
            if (pop[i].fitness > best.fitness){
                best = pop[i];
            }
            if (pop[i].fitness > generationBest.fitness){
                generationBest = pop[i];
            }
            pop[i] = theBests[selector(scores)].copy();
        }
        popSorted = false;
        averageFitness /= pop.length;
    }
    
    public void crossOver(){
        DNA[] temp = new DNA[pop.length];
        System.arraycopy(pop, 0, temp, 0, pop.length);
        for (int i = 0; i < pop.length; i++){
            int parent1 = (int) (Math.random() * temp.length);
            int parent2 = (int) (Math.random() * temp.length);
            DNA child = DNA.crossOver(temp[parent1], temp[parent2]);
            pop[i] = child;
        }
    }
    
    public void mutate(double mutationRate, double min, double max){      
        for (DNA p : pop) {
            p.mutate(mutationRate, min, max);
        }
    }
    public void evolveValue(double mutationRate, double v){      
        for (DNA p : pop) {
            p.evolveValue(mutationRate, v);
        }
    }
    
    public void updatePop(){
        sort();
        DNA[] nextGen = new DNA[pop.length];
        System.arraycopy(elite, 0, nextGen, 0, elite.length);
        for (int i = elite.length; i < nextGen.length; i++){
            nextGen[i] = pop[i-elite.length];
        }
        pop = nextGen;
        generation++;
        popSorted = false;
    }

    @Override
    public String toString(){
        return "Generation: "+generation+" | Generation Best fitness: "+generationBest.fitness+" | Average Fitness: "+averageFitness + " | Best of all: "+ best.fitness;
    }
    private static int selector(double[] scores){
        int select = 0;
        double selector = Math.random();
        while(selector > 0){
            selector-=scores[select];
            select++;
        }
        select--;
        return select;
    }
    
    public static void normalizeArray(double[] scores){
        double sum = 0;
        for (int i = 0; i < scores.length; i++){
            sum += scores[i];
        }
        if (sum != 0){
            for (int i = 0; i < scores.length; i++){
                scores[i] = scores[i]/sum;
            }
        }else{
            for (int i = 0; i < scores.length; i++){
                scores[i] = 1./scores.length;
            }
        }
    }
    
    private void sort(){
        if (!popSorted){
            Arrays.sort(pop);
            popSorted = true;
        }
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import java.util.Random;

public class Organism implements Evolvable, Comparable<Organism>{
    Evolvable fenotype;
    public double[] genome;
    double fitness;
    
    public Organism(Evolvable fenotype){
        this.fenotype = fenotype;
        genome = fenotype.getDNA();
    }
    public Organism(double[] genome){
        this.genome = genome;
        fenotype = (Evolvable) Evolvable.fromGenome(genome);
    }
    private Organism(){}
    
    public void mutate(double mutationRate,
                       double randomMutationRate,
                       double min,
                       double max,
                       double power,
                       Random r){
        for (int i = 0; i < genome.length; i++){
            if (r.nextDouble() <= mutationRate){
                if (r.nextDouble() <= randomMutationRate){
                    genome[i] = r.nextDouble()*(max-min)+min;
                }else{
                    genome[i] += (r.nextDouble()-0.5)*power;
                }
            }
        }
    }
    
    public static Organism crossover(Organism a, Organism b, Random r){
        Organism bestParent;
        Organism otherParent;
        if (a.fitness > b.fitness){
            bestParent = a;
            otherParent = b;
        }else{
            bestParent = b;
            otherParent = a;
        }
        
        Organism child = new Organism();
        child.genome = new double[bestParent.genome.length];
        
        for (int i = 0; i < child.genome.length; i++){
            if (i < otherParent.genome.length){
                if (r.nextBoolean()){
                    child.genome[i] = bestParent.genome[i];
                }else{
                    child.genome[i] = otherParent.genome[i];
                }
            }else{
                child.genome[i] = bestParent.genome[i];
            }
        }
        return child;
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
    
    public Organism copy(){
        Organism organismCopy = new Organism();
        organismCopy.fenotype = fenotype;
        organismCopy.fitness = fitness;
        organismCopy.genome = new double[genome.length];
        System.arraycopy(genome, 0, organismCopy.genome, 0, genome.length);
        return organismCopy;
    }

}

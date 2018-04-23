/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic_code;

/**
 *
 * @author Thales
 */
public class DNA implements Comparable<DNA>{
    
    public double[] dna;
    public double fitness;

    public DNA(int dnaLength){
        dna = new double[dnaLength];
        fitness = 0;
    }

    public DNA(double[] dna, double fitness){
        this.dna = dna;
        this.fitness = fitness;
    }

    @Override
    public int compareTo(DNA other){
        // compareTo should return < 0 if this is supposed to be
        // less than other, > 0 if this is supposed to be greater than 
        // other and 0 if they are supposed to be equal
        if (fitness > other.fitness){
            return -1;
        }else if(fitness < other.fitness){
            return 1;
        }else{
            return 0;
        }
    }

    public void randomize(double min, double max){
        for (int i = 0; i < dna.length; i++){
            dna[i] = Math.random()*(max-min)+min;
        }
    }

    public void mutate(double chance, double min, double max){
        for (int i = 0; i < dna.length; i++){
            double c = Math.random();
            if (c <= chance){
                dna[i] = Math.random()*(max-min)+min;
            }
        }
    }

    public void evolveValue(double chance, double v){
        for (int i = 0; i < dna.length; i++){
            double c = Math.random();
            if (c <= chance){
                dna[i] += Math.random()*v*2-v;
            }
        }
    }

    public static DNA crossOver(DNA a, DNA b){
        DNA cross = a.copy();
        for (int i = 0; i < cross.dna.length; i++){
            double c = Math.random();
            if (c < 0.5){
                cross.dna[i] = a.dna[i];
            }else{
                cross.dna[i] = b.dna[i];
            }
        }
        return cross;
    }

    public DNA copy(){
        DNA c = new DNA(dna.length);
        System.arraycopy(dna, 0, c.dna, 0, dna.length);
        c.fitness = fitness;
        return c;
    }
    
    public String toString(){
        String r = "";
        for (int i = 0; i < dna.length; i++){
            r += String.format("%5.1f",dna[i])+" ";
        }
        return "DNA:"+r+"  Fitness: "+fitness;
    }

}

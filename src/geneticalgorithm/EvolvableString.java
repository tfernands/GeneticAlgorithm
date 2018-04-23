/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

import genetic_code.DNA;
import genetic_code.Evolvable;

public class EvolvableString implements Evolvable<EvolvableString>{
    
    char[] string;
    double fitness;
    
    EvolvableString(int length){
        string = new char[length];
    }
    
    public double evaluate(String target) {
        fitness = 0;
        for (int i = 0; i < string.length; i++){
            if (target.charAt(i) == string[i]){
                fitness+= 1./string.length;
            }
        }
        return fitness;
    }

    @Override
    public DNA getDNA() {
        double[] dna = new double[string.length];
        for (int i = 0; i < dna.length; i++){
            dna[i] = string[i];
        }
        return new DNA(dna,fitness);
    }

    @Override
    public EvolvableString fromDNA(DNA dna) {
        char[] tempString = new char[dna.dna.length];
        for (int i = 0; i < dna.dna.length; i++){
            tempString[i] = (char) dna.dna[i];
        }
        string = tempString;
        return this;
    }
    
    public EvolvableString copy(){
        EvolvableString c = new EvolvableString(string.length);
        c.string = string.clone();
        return c;
    }
    
}
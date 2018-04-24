/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticalgorithm;

import Engine.Evolvable;

public class EvolvableString implements Evolvable<EvolvableString>{
    
    char[] string;
    
    EvolvableString(int length){
        string = new char[length];
    }

    @Override
    public double[] getGenome() {
        double[] genome = new double[string.length];
        for (int i = 0; i < genome.length; i++){
            genome[i] = string[i];
        }
        return genome;
    }

    @Override
    public EvolvableString fromGenome(double[] genome) {
        char[] tempString = new char[genome.length];
        for (int i = 0; i < genome.length; i++){
            tempString[i] = (char) genome[i];
        }
        string = tempString;
        return this;
    }
    
    @Override
    public EvolvableString copy(){
        EvolvableString c = new EvolvableString(string.length);
        c.string = string.clone();
        return c;
    }
    
    @Override
    public String toString(){
        return new String(string);
    }

}
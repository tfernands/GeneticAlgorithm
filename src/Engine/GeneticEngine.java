/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine;

import java.util.ArrayList;

/**
 *
 * @author TERMINAL
 */
public abstract class GeneticEngine{
    
    public Paramters p;
    
    public final int popSize;
    public int generations;
    public ArrayList<Organism> pop;
    public double averageFitness;
    public ArrayList<Organism> lastPop;
    public Organism generationBest;
    public Organism best;
    
    public GeneticEngine(int popSize){
        this.popSize = popSize;
    }
    
    public abstract double evaluateFitness(Organism a);
    
    public void evolve(){
        
    }
    
}

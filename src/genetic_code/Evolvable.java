/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic_code;
/**
 *
 * @author usuario2
 * @param <C> class that implements Evolvable
 */
public interface Evolvable<C>{
    public DNA getDNA();
    public C fromDNA(DNA dna);
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import esinf_parte2.*;
import graphbase.*;
import graph.*;
import java.io.*;
/**
 *
 * @author diogobarbosa
 */
public class TestEx1 {
    
    Graph<pais, String> grafotest = new Graph<>(false);
    AdjacencyMatrixGraph<pais, Double> matrizteste= new AdjacencyMatrixGraph<>();

    public TestEx1() {
    }
    
    public void setup() throws IOException{
        File pfl= new File("paises.txt");
        File ffl= new File("fronteiras.txt");
        
        mundo mundoteste = new mundo(grafotest, matrizteste);
        
        mundoteste.lerFronteira(pfl, grafotest, matrizteste);
        mundoteste.lerPais(ffl, grafotest, matrizteste);
    }
    
    
    
}

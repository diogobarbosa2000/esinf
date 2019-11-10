/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esinf_parte2;

import java.io.*;
import java.util.*;
import graphbase.*;
import graph.*;

/**
 *
 * @author diogobarbosa
 */
public class Esinf_parte2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        File fFronteira = new File("fronteiras.txt");
        File fpais = new File("paises.txt");
        
        double dist;

        Graph<pais, String> testmundo = new Graph(false);
                
        AdjacencyMatrixGraph<pais, Double> testmatriz= new AdjacencyMatrixGraph<>();
        
        mundo grafomundo =new mundo(testmundo, testmatriz);
        
        grafomundo.lerPais(fpais, testmundo, testmatriz);
        grafomundo.lerFronteira(fFronteira, testmundo, testmatriz);
        
        for (int i=0; i<testmundo.numVertices(); i++){
            pais[] array = testmundo.allkeyVerts();
            System.out.println(array[i].name);
        }
        
        System.out.println(grafomundo.calDistance("colombia", "equador", testmundo, testmundo.allkeyVerts()));
 
        
    }
    
}

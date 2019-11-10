/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esinf_parte2;

import graphbase.*;
import java.io.*;
import java.util.*;
import graph.*;


/**
 *
 * @author diogobarbosa
 */
public class mundo {

    private Graph<pais, String> mundo;
    private AdjacencyMatrixGraph<pais, Double> matrizMundo;

    public mundo(Graph<pais, String> mundo, AdjacencyMatrixGraph<pais, Double> matrizMundo) {
        this.mundo = mundo;
        this.matrizMundo = matrizMundo;
    }
    
    
    //criar um pais segundo o documento disponibilizado
    public void lerPais(File file, Graph<pais, String> grafo, AdjacencyMatrixGraph<pais, Double> matriz) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Scanner sPais = new Scanner(file);
        String str;
        
        while((str=reader.readLine())!=null){
            if (!str.equals("")){
                String[] temp = str.split(",");
                pais p = new pais(temp[0], temp[1], Double.parseDouble(temp[2]), temp[3], Double.parseDouble(temp[4]), Double.parseDouble(temp[5]));
                grafo.insertVertex(p);
                matriz.insertVertex(p);
            }
        }
    }
    
    //calcular distancia entre dois paises
    public double calDistance (String s1, String s2, Graph<pais, String> grafo, pais[] temp){
        String ss = s2;
        pais p1=nomePais(s1, grafo, temp);
        pais p2=nomePais(ss, grafo, temp);
        double dist;
        double a;
        double c;
        double R = 6371*1000;
        double deltalat = (p2.lat-p1.lat);
        double deltalog = (p2.log-p1.log);
        
        a=((Math.sin(Math.toRadians(deltalat)/2))*(Math.sin(Math.toRadians(deltalat)/2)))+
                (Math.cos(Math.toRadians(p1.lat)))*(Math.cos(Math.toRadians(p2.lat)))*((Math.sin(Math.toRadians(deltalog)/2))*
                (Math.sin(Math.toRadians(deltalog)/2)));
        
        c=2*(Math.atan2((Math.sqrt(a)), Math.sqrt(1-a)));
        
        dist=R*c;
        dist=dist/1000;
        
        System.out.println("a distancia entre "+ p1.name + " e "+ p2.name+ " é: " + dist);
        return dist;
    }
    
    //converter o nome de um pais para a sua chave
    private pais nomePais(String name, Graph<pais, String> grafo, pais[] temp){

        int x=0;
        for (int i=0; i<temp.length; i++){
            
            if (temp[i].name.equals(name)){
                System.out.println(temp[i].name);
                x=i;
            }
            
        }
        return temp[x];
    }
    
    
    //criar fronteiras entre os paises
    public void lerFronteira(File file, Graph<pais, String> grafo, AdjacencyMatrixGraph<pais, Double> matriz) throws FileNotFoundException, IOException{  
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Scanner sPais = new Scanner(file);
        String str;
        
        pais[] t_pais = grafo.allkeyVerts();
        
        while((str=reader.readLine())!=null){
                String[] temp = str.split(",");
                pais p1= nomePais(temp[0], grafo, t_pais);
                pais p2= nomePais(temp[1], grafo, t_pais);
                

                double dist = calDistance(temp[0], temp[1], grafo, t_pais);
                grafo.insertEdge(p1, p2, ("caminho entre " + temp[0] + " e " + temp[1]), dist);
                matriz.insertEdge(p1, p2, dist);
            
        }
    }
    
    
    public LinkedList<pais> shortpath(String p1, String p2, Graph<pais, String> grafo){
        LinkedList<pais> str = new LinkedList();
        pais[] t_pais = grafo.allkeyVerts();
        pais ps1 = nomePais(p1, grafo, t_pais);
        pais ps2 = nomePais(p2, grafo, t_pais);
        
        graphbase.GraphAlgorithms.shortestPath(mundo, ps1, ps2, str);
        return str;
    }
    
    public LinkedList<pais> intshortpath(pais orig, pais dest, pais[] intermedios){
        

        
        
        
        return null;
    }


    
    
    
    
    
    
    
    
    
        public LinkedList<pais> shortestCircuit(pais orig, Graph<pais, String> grafo) {
        LinkedList<pais> circuit = new LinkedList<>();
        LinkedList<pais> shortest = new LinkedList<>();
        
        pais[] listaadj= new pais[grafo.numVertices()];
        
        int counter1=0;
        int counter2=0;

        circuit.add(orig);
        pais temp = orig;
        pais stemp = orig;
        int save_orig;
        int iterable = 0;
        boolean[] visited = new boolean[grafo.numVertices()];
        save_orig = grafo.getKey(orig);
        double dist1 = 0;
        double dist2;

        if (grafo.outDegree(orig) <= 1) {
            return null;
        }

        while (visited[save_orig] == false) {
            for (pais p : grafo.adjVertices(temp)) {

                if (iterable <= 1 && p != orig) {

                    if (dist1 == 0) {

                        if (grafo.outDegree(p) > 1 || visited[grafo.getKey(p)] == false) {
                            dist1 = graphbase.GraphAlgorithms.shortestPath(grafo, temp, p, shortest);
                            stemp=p;
                        }

                    } else {
                        if (grafo.outDegree(p) > 1 || visited[grafo.getKey(p)] == false) {
                            dist2 = graphbase.GraphAlgorithms.shortestPath(grafo, temp, p, shortest);

                            if (dist2 < dist1) {
                                dist1 = dist2;
                                stemp = p;
                            }
                        }
                    }
                } 
                
                else {

                    if (grafo.outDegree(p) > 1 || visited[grafo.getKey(p)] == false) {
                        dist2 = graphbase.GraphAlgorithms.shortestPath(grafo, temp, p, shortest);

                        if (dist2 < dist1) {
                            dist1 = dist2;
                            stemp = p;
                        }

                    }

                }
            }
            visited[grafo.getKey(stemp)]=true;
            circuit.add(stemp);
            
            if (stemp==orig){
                break;
            }
            
            int i=0;
            
            for (pais x : grafo.adjVertices(stemp)){
                counter1++;
                if (visited[grafo.getKey(x)]==true){
                counter2++;    
                }
            }
            
            if (counter1==counter2){
                circuit.removeLast();
                temp=circuit.getLast();
                iterable++;
            }
                
            else {
            temp = circuit.getLast();
            iterable++;
            }
            
            counter1=0;
            counter2=0;
        }
        return circuit;
    }
        
}

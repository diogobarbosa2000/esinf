/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esinf_parte2;
import graphbase.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author diogobarbosa
 *//**
 *
 * @author diogobarbosa
 */
public class pais {
    String name;
    String continente;
    String capital;
    double popul;
    double lat;
    double log;
    int id;

    public pais(String name, String continente, double popul, String capital, double lat, double log) {     //Construtor da classe pais
        this.name = name;
        this.continente = continente;
        this.capital = capital;
        this.popul = popul;
        this.lat = lat;
        this.log = log;
    }
    
    
    //definicao do nome do pais
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    //definicao do continente em que se encontra o pais
    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }


    //definicao da capital
    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    
    
    //definicao da populacao
    public double getPopul() {
        return popul;
    }

    public void setPopul(int popul) {
        this.popul = popul;
    }

    
    
    //definicao de latitude
    public double getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    
    // definicao da longitude do pais
    public double getLog() {
        return log;
    }

    public void setLog(int log) {
        this.log = log;
    }
    
    
    
    
}

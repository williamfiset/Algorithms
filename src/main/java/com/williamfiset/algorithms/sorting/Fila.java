package com.williamfiset.algorithms.sorting;

public class Fila {
    public int[] novaFila(int[] lista){
        if (lista.length == 0) {
            return lista;
        }
        
        int item0 = lista[0];
        int[] nova = new int[lista.length];
        
        for (int i = 1; i < lista.length; i++) {
            nova[i - 1] = lista[i];
        }
        
        nova[nova.length - 1] = item0;
        
        return nova;
    }
}

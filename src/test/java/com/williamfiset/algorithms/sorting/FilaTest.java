package com.williamfiset.algorithms.sorting;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FilaTest {

    @Test
    //Deveria mover o primeiro item para o final
    public void MoverPrimeiro(){
        var lista = new int[]{ 10, 15, 20, 30};
        var gira = new Fila();

        var nova = gira.novaFila(lista);

        Assertions.assertEquals(15, nova[0]);

    }

    @Test
    //Deveria funcionar em uma lista com diferentes quantidade de itens
    public void Quantidade(){
        var lista = new int[]{ 10, 15, 20};
        var gira = new Fila();

        var nova = gira.novaFila(lista);

        Assertions.assertEquals(15, nova[0]);
        

    }

    @Test
    //Deveria retonar a própria lista caso ela possua apenas um item
    public void ListaPequena(){
        var lista = new int[]{ 10 };
        var gira = new Fila();

        var nova = gira.novaFila(lista);

        Assertions.assertEquals(10, nova[0]);
        

    }

    @Test
    public void ListaVazia() {
        Fila fila = new Fila();
        int[] listaVazia = new int[0];
        int[] resultado = fila.novaFila(listaVazia);
        assertArrayEquals(new int[0], resultado);
    }
    
} 

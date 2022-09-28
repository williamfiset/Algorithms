package com.williamfiset.algorithms.math;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class IsPrimeUnitTest {
    @Test
    public void IsPrimeUnitTestPMenor2(){
        //O Primeiro método de teste é para um numero positivo menor que 2, onde o resultado deve ser falso,
       // pois a condição não permite que o teste seja verdadeiro.
         Boolean result = IsPrime.isPrime(1);
        assertThat(result ).isEqualTo( false );
    }
    @Test
    public void IsPrimeUnitTestIgual3(){
        //O Segundo teste é para um valor maior que 2, onde deve ser verdadeiro.
        Boolean result = IsPrime.isPrime(3);
        assertThat(result ).isEqualTo( true );
    }
    @Test
    public void IsPrimeUnitTestIgual5(){
        //O Terceiro Teste é para um numero primo, onde o metodo deve aceitar com um true, já que respeita
        //As regras de numero primo.
        Boolean result = IsPrime.isPrime(5);
        assertThat(result ).isEqualTo( true);
    }
    @Test
    public void IsPrimeUnitTestIgual4(){
        //O Quarto Teste é para um numero não primo, onde o metodo deve retornar false, já que
        // o numero não é primo.
        Boolean result = IsPrime.isPrime(4);
        assertThat(result ).isEqualTo( false);
    }
    @Test
    public void IsPrimeUnitTestIgualN1789(){
        //O quinto Teste é para um numero primo entre 1000 e 2000
        Boolean result = IsPrime.isPrime(1789);
        assertThat(result ).isEqualTo( true);
    }
    @Test
    public void IsPrimeUnitTestIgualN0(){
        //O sexto Teste é para um numero igual a zero
        Boolean result = IsPrime.isPrime(0);
        assertThat(result ).isEqualTo( false);
    }
    @Test
    public void IsPrimeUnitTestIgualGigante(){
        //O sétimo Teste é para um numero aleatorio gigante. O metodo demora um pouco para rodar, mas roda com sucesso.
        Boolean result = IsPrime.isPrime(8763857775536878331L);
        assertThat(result ).isEqualTo( true);
    }
    @Test
    public void IsPrimeUnitTestNegativo(){
        //O oitavo Teste é para um numero negativo. O metodo retorna falso porque não existe primo negativo.
        Boolean result = IsPrime.isPrime(-1);
        assertThat(result ).isEqualTo( false);
    }


}

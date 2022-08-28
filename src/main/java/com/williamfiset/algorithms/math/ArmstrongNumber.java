package main.java.com.williamfiset.algorithms.math;

import java.util.*;

import javax.security.auth.SubjectDomainCombiner;

public class ArmstrongNumber {
    /*
     * Number of n digits that is equal to
     * each of its digits is raised to the
     * nth power.
     * 153 (n = 3 digits) is equal to:
     * 1^3 + 5^3 + 3^3 = 1 + 125 + 27 = 153
     */
    public static boolean isArmstrongNumber(int num){
        int i, n; 
        double sum = 0;
        double aux, temp;
        // Calcule number of digits
        i = num;
        aux = 0;
        while (i != 0){
            i /=10;
            aux++;
        }
        // Calcule the of nth power of all digits
        i = num;
        while(i != 0){
            temp = i % 10;
            sum = sum + Math.pow(temp, aux);
            i/=10;
        }
        //Checks
        if(num == sum){
            return true;
        }
        else{
            return false;
        }
    }
    public static void main(String[] args) {
    System.out.println(isArmstrongNumber(1));   //true
    System.out.println(isArmstrongNumber(10));  //false
    System.out.println(isArmstrongNumber(153)); //true
    System.out.println(isArmstrongNumber(300)); //false
  }  
    
}
package com.williamfiset.algorithms.math;

public class ArmstrongNumber {
    /*
     * An Armstrong number is the one where
     * the sum of each digit of the number to the
     * power of number of the digits in that number
     * will be equal to that number for example
     * 153 (n = 3 digits) is equal to:
     * 1^3 + 5^3 + 3^3 = 1 + 125 + 27 = 153
     */
    public static boolean isArmstrongNumber(int number){
        int numberLength = (String.valueOf(number)).length();
        int tracker = number;
        int answer = 0;
        while (tracker !=0){
            int lastDigit = (int) tracker % 10;
            answer = answer + (int) Math.pow(lastDigit , numberLength);
            tracker = Math.floorDiv(tracker , 10);
        }
        return (answer == number);
    }
}

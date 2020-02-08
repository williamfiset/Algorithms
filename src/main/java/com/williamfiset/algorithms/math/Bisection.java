import java.lang.Math;
public class main {

    static final float golden = 0.01f;

    static double equation(double x1)
    {
        return  Math.cos(x1) - x1;
    }



    public static void main(String[] argas) {

        double a = 1, b = 2;

        if (equation(a) * equation(b) >= 0) {
            System.out.println("There is not a solution between a and b");
        }

        else {
            double half = a;

            while( b-a >= golden ) {
                half = (a+b)/2;

                if (equation(half) == 0.0) {
                    break;
                }
                //Solution between a-half
                else if (equation(half) * equation(a) < 0) {
                    b = half;
                }
                //Solution is between half-b
                else {
                    a = half;
                }


            }
            System.out.println("The solution is here " + half);

        }

    }
}

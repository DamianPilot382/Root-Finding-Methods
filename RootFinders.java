/**
 * @author Damian Ugalde
 * @date 2020-07-15
 * @version 1.0
 *
 * Project 1 - Root-Finding Methods
 * CS 3010 - Numerical Methods
 * California State Polytechnic University, Pomona
 * Computer Science Department
 *
 * Instructor: Dr. Amar Raheja
 *
 */
public class RootFinders {

    /**
     * Takes in a function and a range, with the amount of checks in between the range.
     * Then it will check if a root exists between the smaller range.
     * @param f Function to check
     * @param min lower number in the range
     * @param max upper number in the range
     * @param breaks Amount of checks to perform in the interval
     */
    public static void separator(Equation f, double min, double max, double breaks){
        
        //Calculate the amount to increase x by for each check
        double step = (max-min)/breaks;

        //Keep track of the last x-value checked
        double lastValue = f.equation(min);
        double lastIndex = min;

        //Loop through the range of min and max, incrementing by one step each time.
        for(double x = min+step; x < max; x += step){

            //Get the next value
            double next = f.equation(x);

            if(lastValue * next < 0){ //If f(lastIndex) and f(next) are not the same sign
                //A root lies between lastIndex and x.
                System.out.println("Root found between: [" + lastIndex + ", " + x + "]");

                //Get the approximate value of the root with each method.
                System.out.println("Bissection Root at: " + bisection(f, lastIndex, x));
                System.out.println("Newton Root at: " + newtonRaphson(f, x));
                System.out.println("Secant Root at: " + secant(f, lastIndex, x));
                System.out.println("False Position Root at: " + falsePosition(f, lastIndex, x));
                System.out.println("Modified Secant Root at: " + modifiedSecant(f, x));
            }

            //Update the value of the last x-value checked with the current one
            lastValue = next;
            lastIndex = x;
        }
    }

    /**
     * 
     * @param f
     * @param a
     * @param b
     * @return
     */
    public static double bisection(Equation f, double a, double b){

        double epsilon_a = 0.01;
        int nMax = 100;
        double error;

        double fa = f.equation(a);
        double fb = f.equation(b);

        if(fa * fb > 0){
            //If both f(a) and f(b) have the same sign,
            //Then they're not acceptable to use for this method.
            //System.out.println("Function has same signs at a and b.");
            return Double.MIN_VALUE;
        }

        error = b - a;


        for(int n = 0; n < nMax; n++){
            error = error/2;
            double c = a + error;
            double fc = f.equation(c);

            if(Math.abs(error) < epsilon_a){
                //System.out.println("Convergence");
                return c;
            }
            if(fa * fc < 0){
                b = c;
                fb = fc;
            }else{
                a = c;
                fa = fc;
            }
        }

        return Double.MAX_VALUE;
    }

    public static double newtonRaphson(Equation f, double x){
        int nMax = 100;
        double epsilon = 0.01;
        double delta = 0.01;

        double fx = f.equation(x);

        for(int n = 1; n <= nMax; n++){
            double fp = f.prime(x);
            if(Math.abs(fp) < delta){
                System.out.println("Small Derivative");
                return 0;
            }

            double d = fx/fp;
            x = x - d;
            fx = f.equation(x);

            if(Math.abs(d) < epsilon){
                //System.out.println("Convergence");
                return x;
            }
        }

        return x;
    }

    public static double secant(Equation f, double a, double b){

        int nMax = 100;
        double epsilon = 0.01;

        double fa = f.equation(a);
        double fb = f.equation(b);

        double d = 0;

        for(int n = 2; n <= nMax; n++){
            if(Math.abs(fa) > Math.abs(fb)){
                double temp = a;
                a = b;
                b = temp;

                temp = fa;
                fa = fb;
                fb = temp;
            }

            d = b - (b-a)/(fb-fa) * fb;

            if(Math.abs(d) < epsilon){
                System.out.println("Convergence");
                return d;
            }

            a = b;
            b = d;

            fa = fb;
            fb = f.equation(b);
        }

        return d;
    }

    public static double falsePosition(Equation f, double a, double b){
        double epsilon_a = 0.01;
        int nMax = 100;
        double error;

        double fa = f.equation(a);
        double fb = f.equation(b);

        if(fa * fb > 0){
            System.out.println("Function has same signs at a and b.");
            return Double.MIN_VALUE;
        }

        error = b - a;


        for(int n = 0; n < nMax; n++){
            error = error/2;
            double c = (a * fb - b * fa)/(fb - fa);
            double fc = f.equation(c);

            if(Math.abs(error) < epsilon_a){
                //System.out.println("Convergence");
                return c;
            }
            if(fa * fc < 0){
                b = c;
                fb = fc;
            }else{
                a = c;
                fa = fc;
            }
        }

        return Double.MAX_VALUE;
    }

    public static double modifiedSecant(Equation f, double a){
        int nMax = 100;
        double epsilon = 0.01;
        double delta = 0.01;

        double fa = f.equation(a);

        for(int n = 2; n <= nMax; n++){

            a = a - (fa * delta * a)/(f.equation(a + delta * a) - fa);

            if(Math.abs(a) < epsilon){
                //System.out.println("Convergence");
                return a;
            }

        }

        return a;
    }

}
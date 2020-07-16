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

    public static final int N_MAX = 100;
    public static final double EPSILON = 0.01;

    public static double[][] graph(Equation f, double min, double max, int points){
        
        double[][] data = new double[points][2];

        double step = (max-min)/points;

        int index = 0;

        for(double x = min; x <= max; x += step, index++){
            data[index][0] = x; //Set the x-value
            data[index][1] = f.equation(x); //Set the y-value
        }

        return data;

    }

    /**
     * Takes in a function and a range, with the amount of checks in between the range.
     * Then it will check if a root exists between the smaller range.
     * @param f Function to check
     * @param min Lower number in the range
     * @param max Upper number in the range
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
                System.out.println("Bissection Root at: " + bisection(f, lastIndex, x).approximation);
                System.out.println("Newton Root at: " + newtonRaphson(f, x).approximation);
                System.out.println("Secant Root at: " + secant(f, lastIndex, x).approximation);
                System.out.println("False Position Root at: " + falsePosition(f, lastIndex, x).approximation);
                System.out.println("Modified Secant Root at: " + modifiedSecant(f, x).approximation);
            }

            //Update the value of the last x-value checked with the current one
            lastValue = next;
            lastIndex = x;
        }
    }

    /**
     * 
     * @param f Function
     * @param a Lower number in the range
     * @param b Upper number in the range
     * @return The approximate root found
     */
    public static Result bisection(Equation f, double a, double b){

        Result res = new Result("bisection");

        double fa = f.equation(a);
        double fb = f.equation(b);

        if(fa * fb > 0){
            //If both f(a) and f(b) have the same sign,
            //Then they're not acceptable to use for this method.

            //System.out.println("Function has same signs at a and b.");
            return res.evaluate();
        }


        double error = b - a;

        double previous = 0;


        for(int n = 0; n < N_MAX; n++){
            error = error/2;
            double c = a + error;
            double fc = f.equation(c);

            res.error.add(Math.abs(c - previous)/c);
            previous = c;

            res.values.add(c);

            if(Math.abs(error) < EPSILON){
                //System.out.println("Convergence");
                return res.evaluate();
            }
            if(fa * fc < 0){
                b = c;
                fb = fc;
            }else{
                a = c;
                fa = fc;
            }
        }

        return res.evaluate();
    }

    /**
     * 
     * @param f Function
     * @param x Initial starting guess for the root
     * @return The approximate root found
     */
    public static Result newtonRaphson(Equation f, double x){

        Result res = new Result("newtonRaphson");

        double delta = 0.01;

        double fx = f.equation(x);

        double previous = x;

        for(int n = 1; n <= N_MAX; n++){

            res.values.add(x);

            double fp = f.prime(x);
            if(Math.abs(fp) < delta){
                System.out.println("Small Derivative");
                return res.evaluate();
            }

            double d = fx/fp;
            x = x - d;
            fx = f.equation(x);

            res.error.add(Math.abs(x - previous)/x);
            previous = x;

            if(Math.abs(d) < EPSILON){
                //System.out.println("Convergence");
                return res.evaluate();
            }
        }

        return res.evaluate();
    }

    /**
     * @param f Function
     * @param a Lower number in the range
     * @param b Upper number in the range
     * @return The approximate root found
     */
    public static Result secant(Equation f, double a, double b){

        Result res = new Result("secant");

        double fa = f.equation(a);
        double fb = f.equation(b);

        double d = 0;

        double previous = d;

        for(int n = 2; n <= N_MAX; n++){

            res.values.add(d);

            if(Math.abs(fa) > Math.abs(fb)){
                double temp = a;
                a = b;
                b = temp;

                temp = fa;
                fa = fb;
                fb = temp;
            }

            d = b - (b-a)/(fb-fa) * fb;

            res.error.add(Math.abs(d - previous)/previous);
            previous = d;

            if(Math.abs(d) < EPSILON){
                //System.out.println("Convergence");
                return res.evaluate();
            }

            a = b;
            b = d;

            fa = fb;
            fb = f.equation(b);
        }

        return res.evaluate();
    }

    /**
     * 
     * @param f Function
     * @param a Lower number in the range
     * @param b Upper number in the range
     * @return The approximate root found
     */
    public static Result falsePosition(Equation f, double a, double b){

        Result res = new Result("falsePosition");

        double error;

        double fa = f.equation(a);
        double fb = f.equation(b);

        if(fa * fb > 0){
            //System.out.println("Function has same signs at a and b.");
            return res.evaluate();
        }

        error = b - a;

        double previous = 0;

        for(int n = 0; n < N_MAX; n++){

            error = error/2;
            double c = (a * fb - b * fa)/(fb - fa);
            double fc = f.equation(c);

            res.error.add(Math.abs(c - previous)/c);
            previous = c;

            res.values.add(c);

            if(Math.abs(error) < EPSILON){
                //System.out.println("Convergence");
                return res.evaluate();
            }
            if(fa * fc < 0){
                b = c;
                fb = fc;
            }else{
                a = c;
                fa = fc;
            }
        }

        return res.evaluate();
    }

    /**
     * 
     * @param f Function
     * @param a Initial guess for the root
     * @return The approximate root found
     */
    public static Result modifiedSecant(Equation f, double a){

        Result res = new Result("modifiedSecant");

        double delta = 0.01;

        double fa = f.equation(a);

        double previous = a;

        for(int n = 2; n <= N_MAX; n++){

            res.values.add(a);

            a = a - (fa * delta * a)/(f.equation(a + delta * a) - fa);

            res.error.add(Math.abs(a - previous)/a);
            previous = a;

            if(Math.abs(a) < EPSILON){
                //System.out.println("Convergence");
                return res.evaluate();
            }

        }

        return res.evaluate();
    }

}
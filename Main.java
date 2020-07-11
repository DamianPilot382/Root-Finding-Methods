public class Main {

    public static void main(String[] args){

        Equation A = new Equation(){
            public double equation(double x){
                return (-5 + x*(17.7 + x*(-11.7 + x*(2))));
            }

            public double prime(double x){
                return 17.7 + x*(-23.4 + x*(6));
            }
        };

        Equation B = new Equation(){
            public double equation(double x){
                return x + 10 - x * Math.cosh(50/x);
            }

            public double prime(double x){
                return (50 * Math.sinh(50/x))/x - Math.cosh(50/x) + 1;
            }
        };

        RootFinders.separator(A, 0, 4, 16);

        RootFinders.separator(B, 120, 130, 10);
    
    }
    
}
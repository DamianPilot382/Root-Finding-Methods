public class Main {

    public static void main(String[] args){


        Equation A = new Equation(){
            //f(x) = 2x^3 - 11.7x^2 +17.7x - 5
            public double equation(double x){
                return (-5 + x*(17.7 + x*(-11.7 + x*(2))));
            }

            //f'(x) = 6x^2 - 23.4x + 17.7
            public double prime(double x){
                return 17.7 + x*(-23.4 + x*(6));
            }
        };

        Equation B = new Equation(){
            //f(x) = x + 10 - x cosh(50/x)
            public double equation(double x){
                return x + 10 - x * Math.cosh(50/x);
            }

            //f'(x) = ( 50 sinh(50/x) ) /x  - cosh(50/x) + 1
            public double prime(double x){
                return (50 * Math.sinh(50/x))/x - Math.cosh(50/x) + 1;
            }
        };

        Tester tester = new Tester();
        try{
            tester.changeFileName("graphA");
            tester.writeHeader("X", "Y");
            tester.getGraph(A, -1, 5, 10000);
            tester.closeFile();

            tester.changeFileName("graphB");
            tester.writeHeader("X", "Y");
            tester.getGraph(B, 100, 150, 10000);
            tester.closeFile();
        }catch(Exception e){

        }

        RootFinders.separator(A, 0, 4, 16);

        //RootFinders.separator(B, 120, 130, 10);
    
    }
    
}
import java.util.ArrayList;
import java.io.IOException;

public class Result {

    protected String fileName;

    protected int iterations;
    protected ArrayList<Double> values;
    protected ArrayList<Double> error;

    protected double approximation;

    public Result(String fileName){
        this.fileName = fileName;

        this.values = new ArrayList<Double>();
        this.error = new ArrayList<Double>();
    }

    public Result evaluate() {
        this.iterations = values.size();
        this.approximation = values.get(values.size() - 1);

        try{
            this.printResults();
        }catch(Exception e){

        }

        return this;
    }

    public void printResults() throws IOException{
        Tester tester = new Tester();

        tester.changeFileName(fileName);

        tester.writeHeader("iteration", "error", "value");

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < iterations; i++){

            double err = 0;
            double value = 0;

            if(i < error.size())
                err = error.get(i);
            if(i < values.size())
                value = values.get(i);

            tester.write(i, err, value);
        }


    }
}
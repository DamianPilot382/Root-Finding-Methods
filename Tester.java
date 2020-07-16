import java.io.FileWriter;
import java.io.IOException;

public class Tester {

    private FileWriter writer; //Used to write to a csv file.

    /**
     * Changes the name of the file to be written into.
     * @param name New file name
     * @throws IOException Something went wrong while writing or opening the file.
     */
    public void changeFileName(String name) throws IOException {
        writer = new FileWriter(name+".csv");
    }

    public void getGraph(Equation f, double min, double max, int points) throws IOException {

        double[][] graph = RootFinders.graph(f, min, max, points);

        for(int i = 0; i < graph.length; i++){

            writer.append(i+","+ graph[i][0] +","+ graph[i][1] +",\n");

        }
        //Write the results to the file.
        writer.flush();
    }

    /**
     * Write the headers on this file.
     * @throws IOException Something went wrong when writing to the file.
     */
    public void writeHeader(String... header) throws IOException {
        for(String word : header){
            writer.append(word + ",");
        }

        writer.append("\n");
        writer.flush();
    }

    /**
     * Closes the file
     * @throws IOException something goes wrong closing the file.
     */
    public void closeFile() throws IOException {
        writer.close();
    }

    public void write(Object ... text) throws IOException{
        for(Object obj : text){
            writer.append(obj.toString() + ",");
        }
        writer.append("\n");
        writer.flush();
    }

}
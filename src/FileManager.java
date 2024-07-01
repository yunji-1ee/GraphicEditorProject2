import java.io.*;
import java.util.ArrayList;

public class FileManager {

    public static void saveShapes(ArrayList<Property> box) {
        try (PrintWriter out = new PrintWriter(new FileWriter("shapes.txt"))) {
            for (Property shape : box) {
                out.print(shape.toFileString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Property> loadShapes() {
        ArrayList<Property> box = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader("shapes.txt"))) {
            String line;
            while ((line = in.readLine()) != null) {
                box.add(Property.fromFileString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return box;
    }
}

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Runner {

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("CityData.txt"));
            List<City> cities = new ArrayList<City>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] tabsplit = line.split("\t");
                String city = tabsplit[0].substring(0, tabsplit[0].lastIndexOf(", "));
                String state = tabsplit[0].substring(tabsplit[0].indexOf(", ") + ", ".length());
                String zip = tabsplit[1];
                cities.add(new City(city, state, zip));
            }
            br.close();

            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("CityDataOutput.dat")));
            for (City city : cities) pw.println(city.toString());
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

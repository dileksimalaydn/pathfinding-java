import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CountryMap {

    private String inputMapFileName;
    private String[] cityNames;
    private City[] cities;
    private String computationLine;

    public CountryMap(String inputMapFileName){
        this.inputMapFileName = inputMapFileName;
    }

    public String getComputationLine() {
        return computationLine;
    }

    public City[] getCities() {
        return cities;
    }

    public void readInputFile()
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputMapFileName)))
        {
            // City Count
            String line = reader.readLine();
            int cityCount = Integer.parseInt(line.trim());

            //City Names
            line = reader.readLine();
            cityNames = line.trim().split(" ");

            cities = new City[cityCount];
            for(int i = 0; i < cityCount; i++)
            {
                cities[i] = new City(cityNames[i], new String[cityCount][2]);
            }

            

            

        } catch(IOException e) {

        }
    }

    



    
}

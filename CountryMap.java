import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

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
            String line = skipBlankLines(reader);
            int cityCount = Integer.parseInt(line.trim());

            //City Names
            line = skipBlankLines(reader);
            cityNames = line.trim().split(" ");

            cities = new City[cityCount];
            for(int i = 0; i < cityCount; i++)
            {
                cities[i] = new City(cityNames[i], new String[cityCount][2]);
            }

            //Route Count
            line = skipBlankLines(reader);
            int routeCount = Integer.parseInt(line.trim());

            //Routes
            for (int i = 0; i < routeCount; i++) {
                line = skipBlankLines(reader);
                processRoute(line.trim());
            }

            //Computation Line
            line = skipBlankLines(reader);
            computationLine = line.trim();


            System.out.println("File read is successful!");
        } catch(IOException e) {
            System.out.println("Error reading the file "+ e.getMessage());
        }
    }

    private void processRoute(String line) {
        String[] parts = line.split(" ");
        String sourceCity = parts[0];
        String destinationCity = parts[1];
        String time = parts[2];

        City source = findCityByName(sourceCity);
        addRouteToCity(source, destinationCity, time);

    }

    private void addRouteToCity(City city, String destinationCity, String time)
    {
        String[][] nextCities = city.getNextCities();
        for (int i = 0; i < nextCities.length; i++) {
            if (nextCities[i][0] == null) // Find the next available row
            {
                nextCities[i][0] = destinationCity;
                nextCities[i][1] = time;
                break;
            }
        }
    }

    private City findCityByName(String cityName)
    {
        for (int i = 0; i < cities.length; i++) {
            if (cities[i].getCityName().equals(cityName)) {
                return cities[i];
            }
        }
        throw new NoSuchElementException();
    }

    private String skipBlankLines(BufferedReader reader) throws IOException {
        String line;
        while((line = reader.readLine()) != null) {
            if(!line.isBlank()) {
                return line;
            }
        }
        throw new IOException("Unexpected end of file while skipping blank lines.");
    }

    public void printCountryMap() {
        for (int i = 0; i < cityNames.length; i++) {
            System.out.print(cityNames[i]+" ");
        }
        System.out.println();
        System.out.println("---------------------------");

        for (int i = 0; i < cities.length; i++) {
            cities[i].printCity();
        }
        System.out.println(computationLine);
    }



    
}

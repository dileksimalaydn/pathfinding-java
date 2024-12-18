import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;

public class CountryMap {

    private String inputMapFileName;
    private String[] cityNames;
    private City[] cities;
    private String computationLine;
    private String[] errorMessages;
    private int errorCount;

    public CountryMap(String inputMapFileName){
        this.inputMapFileName = inputMapFileName;
        this.errorMessages = new String[100];
    }

    public String getComputationLine() {
        return computationLine;
    }

    public City[] getCities() {
        return cities;
    }

    public String[] getErrorMessage() {
        return errorMessages;
    }

    public boolean validateFile() {
        try(BufferedReader reader = new BufferedReader(new FileReader(inputMapFileName))) {
            int lineNumber = 0;

            // Validate City Count
            String line = skipBlankLines(reader);
            lineNumber++;
            int cityCount = validateCityCount(line.trim(), lineNumber);

            // Validate City Names
            line = skipBlankLines(reader);
            lineNumber++;
            cityNames = validateCityNames(line.trim(), cityCount, lineNumber);

            // Validate Route Count
            line = skipBlankLines(reader);
            lineNumber++;
            int declaredRouteCount = validateRouteCount(line.trim(), lineNumber);

            // Validate Routes
            int actualRouteCount = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.isBlank()) {
                    continue;
                }

                if(actualRouteCount < declaredRouteCount) {
                    validateRouteFormat(line.trim(), lineNumber);
                    actualRouteCount++;
                } else {
                    computationLine = line.trim();
                    break;
                }
            }

            while ((line = reader.readLine()) != null) {
                if(!line.isBlank()) {
                    addError("Extra route found beyond declared route count", lineNumber);
                }
                lineNumber++;
            }

            // Validate Computation Line
            if (computationLine == null) {
                computationLine = skipBlankLines(reader);
                lineNumber++;
            }
            validateComputationLine(computationLine, lineNumber);
        } catch (IOException e) {
            addError("Error reading the file" + e.getMessage(), -1);
        }

        return errorCount == 0;
    }

    private int validateCityCount(String line, int lineNumber) {
        try {
            int cityCount = Integer.parseInt(line);
            if(cityCount <= 0) {
                addError("Invalid city count.", lineNumber);
            }
            return cityCount;
        } catch (NumberFormatException e) {
            addError("City count is not a valid integer.", lineNumber);
            return 0;
        }
    }

    private String[] validateCityNames(String line, int exceptedCount, int lineNumber) {
        String[] names = line.split(" ");
        if(names.length != exceptedCount) {
            addError("Number of cities does not match city count.", lineNumber);
        }
        return names;
    }

    private boolean isCityValid(String cityName) {
        if(cityNames == null) {
            return false;
        }
        for (int i = 0; i < cityNames.length; i++) {
            if(cityNames[i].equals(cityName)) {
                return true;
            }
        }
        return false;
    }

    private int validateRouteCount(String line, int lineNumber) {
        try {
            int routeCount = Integer.parseInt(line);
            if(routeCount < 0) {
                addError("Route count cannot be negative", lineNumber);
            }
            return routeCount;
        } catch (NumberFormatException e) {
            addError("Route count is not a valid integer", lineNumber);
            return 0;
        }
    }

    private void validateRouteFormat(String line, int lineNumber) {
        String[] parts = line.split(" ");
        if (parts.length != 3) {
            addError("Invalid route format.", lineNumber);
        } else {
            String sourceCity = parts[0];
            String destinationCity = parts[1];
            String distanceString = parts[2];

            if (!isCityValid(sourceCity)) {
                addError("Source city does not exist: " + sourceCity, lineNumber);
            }
            if (!isCityValid(destinationCity)) {
                addError("Destination city does not exist: " + destinationCity, lineNumber);
            }

            try {
                int distance = Integer.parseInt(distanceString);
                if (distance <= 0) {
                    addError("Distance must be a positive integer.", lineNumber);
                }
            } catch (NumberFormatException e) {
                addError("Distance is not a valid integer.", lineNumber);
            }
        }
    }

    private void addError(String errorDescription, int lineNumber) {
        if(errorCount < errorMessages.length) {
            errorMessages[errorCount] = "Error Line: "+ lineNumber + " " + errorDescription;
            errorCount++;
        }
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

public class WayFinder {
    
    private CountryMap countryMap;

    public WayFinder(CountryMap countryMap) {
        this.countryMap = countryMap;
    }

    public String findFastestPath() {
        String[] computationLine = countryMap.getComputationLine().trim().split(" ");
        String startCity = computationLine[0];
        String endCity = computationLine[1];

        City[] cities = countryMap.getCities();
        int cityCount = cities.length;
        
        int[] times = new int[cityCount];
        int[] previous = new int[cityCount];
        boolean[] visited = new boolean[cityCount];

        for (int i = 0; i < cityCount; i++) {
            times[i] = Integer.MAX_VALUE;
            previous[i] = -1;
        }

        int startIndex = findCityIndex(startCity, cities);
        times[startIndex] = 0;

        for (int i = 0; i < cityCount; i++) {
           int currentIndex = findMinTimeIndex(times, visited);
           if(currentIndex == -1 || cities[currentIndex].getCityName().equals(endCity)) {
            break; // It means we reached our destination
           }
           
           visited[currentIndex] = true;
           City currentCity = cities[currentIndex];

           String[][] nextCities = currentCity.getNextCities();
           for(String[] route : nextCities) {
                if(route[0] == null) break; // End of valid routes
                int neighborIndex = findCityIndex(route[0], cities);
                int time = Integer.parseInt(route[1]);

                if(!visited[neighborIndex] && times[currentIndex] + time < times[neighborIndex]) {
                    times[neighborIndex] = times[currentIndex] + time;
                    previous[neighborIndex] = currentIndex;
                }
            }
        }

        int endIndex = findCityIndex(endCity, cities);
        if(times[endIndex] == Integer.MAX_VALUE) {
            return "No path found!";
        }

        return constructPath(previous, endIndex,times[endIndex], cities);
        
    }

    private int findCityIndex(String cityName, City[] cities) {
        for (int i = 0; i < cities.length; i++) {
            if(cities[i].getCityName().equals(cityName)) {
                return i;
            }
        }
        throw new IllegalArgumentException("City not found: "+cityName);
    }

    private int findMinTimeIndex(int[] times, boolean[] visited) {
        int minIndex = -1;
        int minTime = Integer.MAX_VALUE;

        for (int i = 0; i < times.length; i++) {
            if(!visited[i] && times[i] < minTime) {
                minTime = times[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private String constructPath(int[] previous, int endIndex, int time, City[] cities)
    {
        StringBuilder path = new StringBuilder();
        int currentIndex = endIndex;
        
        while (currentIndex != -1) {
            path.insert(0, cities[currentIndex].getCityName() + "->");
            currentIndex = previous[currentIndex];
        }

        return "Fastest Way: " + path.toString().trim() + "\nTotal Time: " + time + " min";
    }
    
    

    
}

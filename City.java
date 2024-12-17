public class City {
    private String cityName;
    private String[][] nextCities;

    public City(String cityName,String[][] nextCities){
        this.cityName = cityName;
        this.nextCities= nextCities;
    }

    public String getCityName() {
        return cityName;
    }
     
    public String[][] getNextCities(){
        return nextCities;
    }

    public void printCity()
    {
        System.out.println(cityName+" : ");
        for (int i = 0; i < nextCities.length; i++) {
            if (nextCities[i][0] != null) {
                System.out.print(cityName + " -> " + nextCities[i][0] + " " + nextCities[i][1] + " ");
            }
        }
        System.out.println();
    }
    
}

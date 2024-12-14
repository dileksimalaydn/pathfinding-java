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

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    
    
}

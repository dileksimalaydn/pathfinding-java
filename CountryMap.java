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



    
}

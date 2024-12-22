public class Main{
    public static void main(String[] args){
        CountryMap countryMap = new CountryMap(args[0]);
        countryMap.validateFile();
        countryMap.printErrors();
        countryMap.readInputFile();
        WayFinder wayFinder = new WayFinder(countryMap);
        System.out.println(wayFinder.findFastestPath());
        wayFinder.writeResultToFile("output.txt");
    }

}
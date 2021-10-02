public class BuilderDesignPattern {
    public static void main(String args[]) {
      Bike bike = BikeBuilder.builder().setBikeId(11).setBikeName("Yamaha").setEngineType("Extreme").build();
      System.out.println(bike);
    }
}

class Bike{
    private int bikeId;
    private String bikeName;
    private String engineType;
   
    public Bike(int bId, String bName, String enType){
        super();
       
        this.bikeId = bId;
        this.bikeName = bName;
        this.engineType = enType;
    }
   
    @Override
    public String toString(){
        return "bikeId : "+bikeId+", bikeName : "+bikeName+", engineType : "+engineType;
    }
}
class BikeBuilder{
    private int bikeId;
    private String bikeName;
    private String engineType;
   
    public static BikeBuilder builder(){
        return new BikeBuilder();
    }
   
    public BikeBuilder setBikeId(int bId){
        this.bikeId = bId;
        return this;
    }
   
    public BikeBuilder setBikeName(String bName){
        this.bikeName = bName;
        return this;
    }
   
    public BikeBuilder setEngineType(String enType){
        this.engineType = enType;
        return this;
    }
   
    public Bike build(){
        return new Bike(bikeId, bikeName, engineType);
    }
}

import java.util.List;
import java.util.ArrayList;

public class ProtoTypeDesignPattern {
    public static void main(String args[])throws CloneNotSupportedException {
      Vehicle v = new Vehicle();
      v.insertData();//from DB : Costly Operation, better to have once
      
      Vehicle v1 = (Vehicle) v.clone();
      List<String> list = v1.getVehicleList();
      list.add("Suzuki");
      
      System.out.println(v);
      System.out.println(v1);
      
      System.out.println(v.getVehicleList());
      System.out.println(list);
      
      v1.getVehicleList().remove("Royal Enfield");
      
      System.out.println(v);
      System.out.println(v1);
      
      System.out.println(v.getVehicleList());
      System.out.println(list);
    }
}

class Vehicle{
    private List<String> vehicleList;
    
    public Vehicle(){
        this.vehicleList = new ArrayList<>();
    }
    
    public Vehicle(List<String> list){
        this.vehicleList = list;
    }
    
    public void insertData(){
        vehicleList.add("Hero Honda");
        vehicleList.add("Royal Enfield");
        vehicleList.add("Yamaha");
    }
    
    public List<String> getVehicleList(){
        return this.vehicleList;
    }
    
    @Override
    public Object clone()throws CloneNotSupportedException {
        List<String> tempList = new ArrayList<>();
        
        for(String s : this.getVehicleList()){
            tempList.add(s);
        }
        
        return new Vehicle(tempList);
    }
    
    @Override
    public String toString(){
        return vehicleList.toString();
    }
}

import java.time.LocalDate;
import java.time.Month;

import java.util.List;
import java.util.Arrays;

import java.util.stream.Collectors;

//www.java2s.com : Java Parallel Streams
public class StreamTest7 {
    public static void main(String args[]) {
      List<Employee> personsList = Employee.persons();
      //Sequential Stream : steram()
      String namesUsingSequentialStream = personsList.stream().filter(Employee::isMale).map(Employee::getName).collect(Collectors.joining(", "));
      System.out.println(namesUsingSequentialStream);
      //Parallel Stream : parallelStream()
      String namesUsingParallelStream = personsList.parallelStream().filter(Employee::isMale).map(Employee::getName).collect(Collectors.joining(", "));
      System.out.println(namesUsingParallelStream);
      //Mix of Sequential & Parallel Streams : stream() & parallel()
      String namesUsingSequentialParallelStream = personsList.stream().filter(Employee::isMale).parallel().map(Employee::getName).collect(Collectors.joining(", "));
      System.out.println(namesUsingSequentialParallelStream);
    }
}

class Employee {
    public static enum Gender {
        MALE, FEMALE, TRANS
    }
    
    private long id;
    private String name;
    private Gender gender;
    private LocalDate dob;
    private double income;
    
    public Employee(long id, String name, Gender gender, LocalDate dob, double income){
        super();
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.dob = dob;
        this.income = income;
    }
    
    public long getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public Gender getGender(){
        return this.gender;
    }
    public double getIncome(){
        return this.income;
    }
    public LocalDate getDob(){
        return this.dob;
    }
    
    public boolean isMale(){
        return this.gender == Gender.MALE;
    }
    
    public static List<Employee> persons(){
        Employee p1 = new Employee(1, "Jake", Gender.MALE, LocalDate.of(1971, Month.JANUARY, 1), 2343.0);
        Employee p2 = new Employee(2, "Jack", Gender.MALE, LocalDate.of(1972, Month.JULY, 21), 7100.0);
        Employee p3 = new Employee(3, "Jane", Gender.FEMALE, LocalDate.of(1973, Month.MAY, 29), 5455.0);
        Employee p4 = new Employee(4, "Jode", Gender.MALE, LocalDate.of(1974, Month.OCTOBER, 16), 1800.0);
        Employee p5 = new Employee(5, "Jeny", Gender.FEMALE, LocalDate.of(1975, Month.DECEMBER, 13), 1234.0);
        Employee p6 = new Employee(6, "Jason", Gender.MALE, LocalDate.of(1976, Month.JUNE, 9), 3211.0);
        Employee p7 = new Employee(7, "John", Gender.MALE, LocalDate.of(1975, Month.JUNE, 4), 3214.0);
        Employee p8 = new Employee(8, "Gem", Gender.TRANS, LocalDate.of(1978, Month.APRIL, 7), 6767.0);
        Employee p9 = new Employee(9, "Zeck", Gender.TRANS, LocalDate.of(1969, Month.FEBRUARY, 9), 8977.0);
        
        //List<Employee> persons = List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9);//Immuable - UnSupportedModificationException for Modification
        List<Employee> persons = Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9);//List is View of Array | Mutable - No Exception for Modification
        return persons;
    }
    
    @Override
    public String toString(){
        String str = String.format("(%s, %s, %s, %s, %.2f)\n", id, name, gender, dob, income);
        return str;
    }
}

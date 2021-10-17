import java.time.LocalDate;
import java.time.Month;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;

import java.util.stream.Collectors;
import java.util.stream.Stream;

//www.java2s.com : java.util.stream Package : Collectors
public class StreamTest8 {
    public static void main(String args[]) {
      List<Employee> personsList = Employee.persons();
      //averagingDouble()
      Stream<String> streamOfStrings = Stream.of("1","2","3");
      double averagingDoubleValue = streamOfStrings.collect(Collectors.averagingDouble(n -> Double.parseDouble(n)));//returns Object/Double/double Type only
      System.out.println(averagingDoubleValue);
      
      streamOfStrings = Stream.of("1","2","3");
      //averagingInt()
      double averagingIntValue = streamOfStrings.collect(Collectors.averagingInt(n -> Integer.parseInt(n)));//returns Object/Double/double Type only
      System.out.println(averagingIntValue);
      
      streamOfStrings = Stream.of("1","2","3");
      //averagingLong()
      double averagingLongValue = streamOfStrings.collect(Collectors.averagingLong(n -> Long.parseLong(n)));//returns Object/Double/double Type only
      System.out.println(averagingLongValue);
      
      //collectingAndThen() : 3 Params
      Map<Month, String> dobCalendar = personsList.stream()
        .collect(
          Collectors.collectingAndThen(
            Collectors.groupingBy(p -> p.getDob().getMonth(), 
            Collectors.mapping(Employee::getName, Collectors.joining(", "))), 
            result -> {
               for(Month m: Month.values()){
                   result.putIfAbsent(m, "None");
               }
               return Collections.unmodifiableMap(new TreeMap<>(result));
            }
          )
        );
      //System.out.println(dobCalendar);
      dobCalendar.entrySet().forEach(System.out::println);
      
      streamOfStrings = Stream.of("1","2","3");
      //counting()
      long streamCount = streamOfStrings.collect(Collectors.counting());//returns Object/Long/long Type only
      System.out.println(streamCount);
      
      //groupingByConcurrent(Function) : returns Concurrent Collector
      Map<Employee.Gender, Long> genderWiseNamesCount = personsList.stream().collect(Collectors.groupingByConcurrent(Employee::getGender, Collectors.counting()));
      System.out.println(genderWiseNamesCount);
      
      //groupingByConcurrent(Function,Collector) : retuns Concurrent Collector
      Map<Employee.Gender, String> genderWiseNames = personsList.stream().collect(Collectors.groupingByConcurrent(Employee::getGender, Collectors.mapping(Employee::getName, Collectors.joining(", "))));
      System.out.println(genderWiseNames);
      
      //groupingBy(Function) : returns Normal Collector
      genderWiseNamesCount = personsList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
      System.out.println(genderWiseNamesCount);
      
      //groupingBy(Function,Collector) : returns Normal Collector implementing cascaded "group by" operation on input
      genderWiseNames = personsList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.mapping(Employee::getName, Collectors.joining(", "))));
      System.out.println(genderWiseNames);
      
      //groupingBy(Function,Supplier,Collector) : returns Normal Collector implementing cascaded "group by" operation on input + additional operation
      //Map<Employee.Gender, Map<Object, String>> genderWiseMonthWiseNames = personsList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.groupingBy(p -> p.getDob().getMonth(), Collectors.mapping(Employee::getName, Collectors.joining(", ")))));
      Map<Employee.Gender, Map<Month, String>> genderWiseMonthWiseNames = personsList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.groupingBy(p -> p.getDob().getMonth(), Collectors.mapping(Employee::getName, Collectors.joining(", ")))));
      System.out.println(genderWiseMonthWiseNames);
      
      //joining() : No Params
      streamOfStrings = Stream.of("a", "b", "c");
      String joinedStreamOfStrings = streamOfStrings.collect(Collectors.joining());
      System.out.println(joinedStreamOfStrings);
      
      //joining(CharSequence) : CharSequence Param for Delimiter
      streamOfStrings = Stream.of("a", "b", "c");
      String joinedwithDelimiterOfStreamOfStrings = streamOfStrings.collect(Collectors.joining(", "));
      System.out.println(joinedwithDelimiterOfStreamOfStrings);
      
      //joining(CharSequence,CharSequence,CharSequence) : 3 Params for Delimiter, Prefix & Suffix
      streamOfStrings = Stream.of("a", "b", "c");
      String joinedwithDelimiterPrefixSuffixOfStreamOfStrings = streamOfStrings.collect(Collectors.joining(", ","Hello ",". Goodbye."));
      System.out.println(joinedwithDelimiterPrefixSuffixOfStreamOfStrings);
      
      //mapping(Function,Collector)
      Map<Employee.Gender,Map<Integer,String>> genderWiseYearWiseNames = personsList.stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.groupingBy(p -> p.getDob().getYear(), Collectors.mapping(Employee::getName, Collectors.joining(", ")))));
      System.out.println(genderWiseYearWiseNames);
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
        Employee p10 = new Employee(7, "Tom", Gender.MALE, LocalDate.of(1975, Month.JUNE, 4), 3214.0);
        
        //List<Employee> persons = List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);//Immuable - UnSupportedModificationException for Modification
        List<Employee> persons = Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);//List is View of Array | Mutable - No Exception for Modification
        return persons;
    }
    
    @Override
    public String toString(){
        String str = String.format("(%s, %s, %s, %s, %.2f)\n", id, name, gender, dob, income);
        return str;
    }
}

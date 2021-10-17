import java.time.LocalDate;
import java.time.Month;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.DoubleSummaryStatistics;
import java.util.Collections;
import java.util.Optional;

import java.util.stream.Collectors;

import java.util.function.Function;

//www.java2s.com : Java Stream Collect Map, Join, Group, Partition / Partitioning, Converter, Find & Match
public class StreamTest6 {
    public static void main(String args[]) {
      //toMap() - 3 Overloaded Methods
      //I-Way
      Map<Long,String> idToNameMap = Employee.persons().stream().collect(Collectors.toMap(Employee::getId, Employee::getName));
      System.out.println(idToNameMap);
      //II-Way
      Map<Employee.Gender, String> genderToNamesMap = Employee.persons().stream().collect(Collectors.toMap(Employee::getGender, Employee::getName, (oldValue, newValue) -> String.join(", ", oldValue, newValue)));
      System.out.println(genderToNamesMap);
      //III-Way
      Map<Employee.Gender, Long> genderToCountMap = Employee.persons().stream().collect(Collectors.toMap(Employee::getGender, p -> 1L, (oldCount, newCount) -> (oldCount+newCount)));
      System.out.println(genderToCountMap);
      Map<Employee.Gender, Employee> genderToMaxSalaryMap = Employee.persons().stream().collect(Collectors.toMap(Employee::getGender, Function.identity(), (oldPerson, newPerson) -> newPerson.getIncome() > oldPerson.getIncome() ? newPerson : oldPerson));
      genderToMaxSalaryMap = Employee.persons().stream().collect(Collectors.toMap(Employee::getGender, p -> p, (oldPerson, newPerson) -> newPerson.getIncome() > oldPerson.getIncome() ? newPerson : oldPerson));//Function.identity() returns output same as as input, so it is equal to p -> p
      System.out.println(genderToMaxSalaryMap);
      
      //joining() - 3 Overloaded Versions
      //I-Way
      String joinedNames = Employee.persons().stream().map(e -> e.getName()).collect(Collectors.joining());
      joinedNames = Employee.persons().stream().map(Employee::getName).collect(Collectors.joining());
      System.out.println(joinedNames);
      //II-Way : Using Delimiter
      String joinedNamesDelimitedWithCommas = Employee.persons().stream().map(Employee::getName).collect(Collectors.joining(", "));
      System.out.println(joinedNamesDelimitedWithCommas);
      //III-Way : Using Delimiter, Prefix & Suffix
      String joinedNamesDelimitedWithCommasPrefixAndSuffix = Employee.persons().stream().map(Employee::getName).collect(Collectors.joining(", ", "Hello ", ". Goodbye!"));
      System.out.println(joinedNamesDelimitedWithCommasPrefixAndSuffix);
      
      //groupingBy() : Similar to group by in SQL - 3 Overloaded Versions
      //Using Collectors.counting()
      Map<Employee.Gender, Long> genderWiseCountMap = Employee.persons().stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
      System.out.println(genderWiseCountMap);
      //Using Collectors.mapping(), Collectors.joining()
      Map<Employee.Gender, String> genderWiseNamesMap = Employee.persons().stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.mapping(Employee::getName, Collectors.joining(", "))));
      System.out.println(genderWiseNamesMap);
      //Group By Gender with List of Names
      Map<Employee.Gender, List<String>> genderWiseNamesListMap = Employee.persons().stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.mapping(Employee::getName, Collectors.toList())));
      System.out.println(genderWiseNamesListMap);
      //Group By Gender with Group By Month with List of Names
      //Map genderWiseBornMonthWiseMap = Employee.persons().stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.groupingBy(p -> p.getDob().getMonth(), Collectors.mapping(Employee::getName, Collectors.joining(", ")))));
      //Map<Employee.Gender,Map<Month,String>> genderWiseBornMonthWiseMap = Employee.persons().stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.groupingBy(p -> p.getDob().getMonth(), Collectors.mapping(Employee::getName, Collectors.joining(", ")))));
      Map<Employee.Gender,Map<Month,List<String>>> genderWiseBornMonthWiseMap = Employee.persons().stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.groupingBy(p -> p.getDob().getMonth(), Collectors.mapping(Employee::getName, Collectors.toList()))));
      System.out.println(genderWiseBornMonthWiseMap);
      //Statistics
      Map<Employee.Gender, DoubleSummaryStatistics> incomeStatsByGender = Employee.persons().stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.summarizingDouble(Employee::getIncome)));
      System.out.println(incomeStatsByGender);
      
      //partitioningBy() : 2 Keys (true & false) - 2 Overloaded Versions
      //I-Way
      Map<Boolean, List<Employee>> partitionedByMaleGenderwithEmployee = Employee.persons().stream().collect(Collectors.partitioningBy(Employee::isMale));
      System.out.println(partitionedByMaleGenderwithEmployee);
      //II-Way
      Map<Boolean, String> partitionedByMaleGenderwithNames = Employee.persons().stream().collect(Collectors.partitioningBy(Employee::isMale, Collectors.mapping(Employee::getName, Collectors.joining(", "))));
      System.out.println(partitionedByMaleGenderwithNames);
      
      //collectingAndThen() : Convert Result of Collector to diff. Type
      List<String> unModifiableListofNames = Employee.persons().stream().map(Employee::getName).collect(Collectors.collectingAndThen(Collectors.toList(), result -> Collections.unmodifiableList(result)));
      System.out.println(unModifiableListofNames);
      //unModifiableListofNames.add("ZZZ");//java.lang.UnsupportedOperationException
      
      //find operations : Terminal / short-circuit O / short-circuitperators (may not process entire stream) : filter with Predicate argument + Converter Function Call
      //findAny()
      Optional<Employee> findAnyMaleEmployee = Employee.persons().stream().filter(Employee::isMale).findAny();
      if(findAnyMaleEmployee.isPresent()){
          System.out.print("findAnyMaleEmployee : "+findAnyMaleEmployee.get());
      }else{
          System.out.print("No Male Found");
      }
      Employee.persons().stream().filter(Employee::isMale).findAny().ifPresent(System.out::print);
      //findFirst()
      Optional<Employee> findFirstMaleEmployee = Employee.persons().stream().filter(Employee::isMale).findFirst();
      if(findFirstMaleEmployee.isPresent()){
          System.out.print("findFirstMaleEmployee : "+findFirstMaleEmployee.get());
      }else{
          System.out.print("No Male Found");
      }
      Employee.persons().stream().filter(Employee::isMale).findFirst().ifPresent(System.out::print);
      
      //Match : Terminal / short-circuit Operators (may not process entire stream) : Match Function with Predicate argument
      //allMatch()
      boolean isAllMalesAvailable = Employee.persons().stream().allMatch(Employee::isMale);
      System.out.println("isAllMalesAvailable : "+isAllMalesAvailable);
      //anyMatch()
      boolean isAnyOneBornIn1971 = Employee.persons().stream().anyMatch(p -> p.getDob().getYear() == 1971);
      System.out.println("isAnyOneBornIn1971 : "+isAnyOneBornIn1971);
      //noneMatch
      boolean isNoneMatchOfBornIn1971 = Employee.persons().stream().noneMatch(p -> p.getDob().getYear() == 1971);
      System.out.println("isNoneMatchOfBornIn1971 : "+isNoneMatchOfBornIn1971);
      boolean isNoneMatchOfBornIn2021 = Employee.persons().stream().noneMatch(p -> p.getDob().getYear() == 2021);
      System.out.println("isNoneMatchOfBornIn2021 : "+isNoneMatchOfBornIn2021);
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

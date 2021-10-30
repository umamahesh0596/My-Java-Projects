import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.IsoChronology;//public final class IsoChronology extends AbstractChronology implements Serializable. The ISO calendar system. This chronology defines the rules of the ISO calendar system. This calendar system is based on the ISO-8601 standard, which is the de facto world calendar.

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.IntSummaryStatistics;
import java.util.LongSummaryStatistics;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.util.function.IntConsumer;//Irrelevant, Unused & for Knowledge Sake

//Practice : www.java2s.com : java.util.stream Package : Collectors
public class StreamTest8 {
    public static void main(String args[]) {
      List<Employee> personsList = Employee.persons();
      //averagingDouble()
      //stream of String of Double Values
      Stream<String> streamOfStrings = Stream.of("1","2","3");
      double averagingDoubleValue = streamOfStrings.collect(Collectors.averagingDouble(n -> Double.parseDouble(n)));//returns Object/Double/double Type only
      System.out.println(averagingDoubleValue);
      //stream of Double Values
      Stream<Double> streamOfDoubleValues = Stream.of(1.0,2.0,3.0,4.0);
      averagingDoubleValue = streamOfDoubleValues.collect(Collectors.averagingDouble(n -> n));//returns Object/Double/double Type only
      System.out.println(averagingDoubleValue);
      
      //averagingInt()
      //stream of String of Integer Values
      streamOfStrings = Stream.of("1","2","3");
      double averagingIntValue = streamOfStrings.collect(Collectors.averagingInt(n -> Integer.parseInt(n)));//returns Object/Double/double Type only
      System.out.println(averagingIntValue);
      //stream of Int Values
      Stream<Integer> streamOfIntegerValues = Stream.of(1,2,3,4);
      averagingIntValue = streamOfIntegerValues.collect(Collectors.averagingInt(n -> n));
      System.out.println(averagingIntValue);
      
      //averagingLong()
      //Stream of String of Long Values
      streamOfStrings = Stream.of("1","2","3");
      double averagingLongValue = streamOfStrings.collect(Collectors.averagingLong(n -> Long.parseLong(n)));//returns Object/Double/double Type only
      System.out.println(averagingLongValue);
      //Stream of Long Values
      Stream<Long> streamOfLongValues = Stream.of(1l, 2L, 3l, 4L);
      averagingLongValue = streamOfLongValues.collect(Collectors.averagingLong(n -> n));
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
      //Stream of Strings
      long streamCount = streamOfStrings.collect(Collectors.counting());//returns Object/Long/long Type only
      System.out.println(streamCount);
      //Stream of Integers
      streamOfIntegerValues = Stream.of(1,2,3,4);
      streamCount = streamOfIntegerValues.collect(Collectors.counting());//returns Object/Long/long Type only
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
      
      //maxBy(Comparator)
      //Stream of Integers
      Stream<Integer> streamOfIntegers = Stream.of(1, 2, 3);
      streamOfIntegers.collect(Collectors.maxBy(Comparator.reverseOrder())).ifPresent(System.out::println);
      streamOfIntegers = Stream.of(1, 2, 3);
      streamOfIntegers.collect(Collectors.maxBy(Integer::compareTo)).ifPresent(System.out::println);
      //Stream of Strings of Integers
      streamOfStrings = Stream.of("1", "2", "3");
      streamOfStrings.collect(Collectors.maxBy(Comparator.reverseOrder())).ifPresent(System.out::println);
      streamOfStrings = Stream.of("1", "2", "3");
      streamOfStrings.collect(Collectors.maxBy(String::compareTo)).ifPresent(System.out::println);
      
      //minBy(Comparator)
      //Stream of Integers
      streamOfIntegers = Stream.of(1, 2, 3);
      streamOfIntegers.collect(Collectors.minBy(Comparator.reverseOrder())).ifPresent(System.out::println);
      streamOfIntegers = Stream.of(1, 2, 3);
      streamOfIntegers.collect(Collectors.minBy(Integer::compareTo)).ifPresent(System.out::println);
      //Stream of Strings of Integers
      streamOfStrings = Stream.of("1", "2", "3");
      streamOfStrings.collect(Collectors.minBy(Comparator.reverseOrder())).ifPresent(System.out::println);
      streamOfStrings = Stream.of("1", "2", "3");
      streamOfStrings.collect(Collectors.minBy(String::compareTo)).ifPresent(System.out::println);
      
      //partitioningBy(Predicate)
      Map<Boolean, List<Employee>> partitionedByMaleGender = Employee.persons().stream().collect(Collectors.partitioningBy(Employee::isMale));
      System.out.println(partitionedByMaleGender);
      //partitioningBy(Predicate,Collector)
      Map<Boolean, String> partitionedByMaleGenderUsingCollector = Employee.persons().stream().collect(Collectors.partitioningBy(Employee::isMale, Collectors.mapping(Employee::getName, Collectors.joining(","))));
      System.out.println(partitionedByMaleGenderUsingCollector);
      
      //reducing(BinaryOperator)
      Map<Employee.Gender, Optional<Employee>> genderWiseMaxSalaryEmployee = Employee.persons().stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.reducing((Employee e1, Employee e2) -> e1.getIncome() > e2.getIncome() ? e1 : e2)));
      System.out.println(genderWiseMaxSalaryEmployee);
      Map<Employee.Gender, Optional<Employee>> genderWiseMinSalaryEmployee = Employee.persons().stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.reducing((Employee e1, Employee e2) -> e1.getIncome() < e2.getIncome() ? e1 : e2)));
      System.out.println(genderWiseMinSalaryEmployee);
      //reducing(identity,Function,BinaryOperator)
      Map<Employee.Gender, Integer> totalAgeByGender = Employee.persons().stream().collect(Collectors.groupingBy(Employee::getGender, Collectors.reducing(0, Employee::getAge, Integer::sum)));
      List<Map.Entry<Employee.Gender, Integer>> totalAgeByGenderList = new ArrayList<>(totalAgeByGender.entrySet());
      totalAgeByGenderList.stream().forEach(e -> System.out.println("Gender : " + e.getKey() + ", Total Age : "+e.getValue()));
      
      //summarizingDouble(ToDoubleFunction)
      DoubleSummaryStatistics incomeDoubleStats = Employee.persons().stream().collect(Collectors.summarizingDouble(Employee::getIncome));
      System.out.println(incomeDoubleStats);
      //summarizingInt(ToIntFunction)
      //IntSummaryStatistics incomeIntStats = Employee.persons().stream().collect(Collectors.summarizingInt(Employee::getIncome));//This invalid bcoz income is double Type. If it is int Type then it is valid.
      IntSummaryStatistics incomeIntStats = Employee.persons().stream().collect(Collectors.summarizingInt(e -> (int)e.getIncome())); 
      System.out.println(incomeIntStats);
      //summarizingLong(ToLongFunction)
      //LongSummaryStatistics incomeLongStats = Employee.persons().stream().collect(Collectors.summarizingLong(e -> (int)e.getIncome()));//Valid
      LongSummaryStatistics incomeLongStats = Employee.persons().stream().collect(Collectors.summarizingLong(e -> (long)e.getIncome()));
      System.out.println(incomeLongStats);
      
      //summingDouble(ToDoubleFunction)
      streamOfStrings = Stream.of("1", "2", "3");
      double doubleSum = streamOfStrings.collect(Collectors.summingDouble(e -> Double.parseDouble(e)));
      System.out.println(doubleSum);
      //summingInt(ToIntFunction)
      streamOfStrings = Stream.of("1", "2", "3");
      int intSum = streamOfStrings.collect(Collectors.summingInt(e -> Integer.parseInt(e)));
      System.out.println(intSum);
      //summingLong(ToLongFunction)
      streamOfStrings = Stream.of("1", "2", "3");
      //double longSum = streamOfStrings.collect(Collectors.summingLong(e -> Integer.parseInt(e)));//Valid
      long longSum = streamOfStrings.collect(Collectors.summingLong(e -> Long.parseLong(e)));
      System.out.println(longSum);
      
      //toCollection(Supplier)
      
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
    public int getAge(){
        return this.dob.until(IsoChronology.INSTANCE.dateNow()).getYears();
    }
    
    public boolean isMale(){
        return this.gender == Gender.MALE;
    }
    
    public static List<Employee> persons(){
        Employee p1 = new Employee(1, "Jake", Gender.MALE, LocalDate.of(1971, Month.JANUARY, 1), 2343.0);
        //Employee p2 = new Employee(2, "Jack", Gender.MALE, LocalDate.of(1972, Month.JULY, 21), 7100.0);
        Employee p2 = new Employee(2, "Jack", Gender.MALE, IsoChronology.INSTANCE.date(1972, 7, 21), 7100.0);
        Employee p3 = new Employee(3, "Jane", Gender.FEMALE, LocalDate.of(1973, Month.MAY, 29), 5455.0);
        Employee p4 = new Employee(4, "Jode", Gender.MALE, LocalDate.of(1974, Month.OCTOBER, 16), 1800.0);
        Employee p5 = new Employee(5, "Jeny", Gender.FEMALE, LocalDate.of(1975, Month.DECEMBER, 13), 1234.0);
        Employee p6 = new Employee(6, "Jason", Gender.MALE, LocalDate.of(1976, Month.JUNE, 9), 3211.0);
        Employee p7 = new Employee(7, "John", Gender.MALE, LocalDate.of(1975, Month.JUNE, 4), 3214.0);
        Employee p8 = new Employee(8, "Gem", Gender.TRANS, LocalDate.of(1978, Month.APRIL, 7), 6767.0);
        //Employee p9 = new Employee(9, "Zeck", Gender.TRANS, LocalDate.of(1969, Month.FEBRUARY, 9), 8977.0);
        Employee p9 = new Employee(9, "Zeck", Gender.TRANS, IsoChronology.INSTANCE.date(1969, 2, 9), 8977.0);
        Employee p10 = new Employee(7, "Tom", Gender.MALE, LocalDate.of(1975, Month.JUNE, 4), 3214.0);
        
        //List<Employee> persons = List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);//Immuable - UnSupportedModificationException for Modification
        List<Employee> persons = Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10);//List is View of Array | Mutable - No Exception for Modification
        return persons;
    }
    
    //Irrelevant : This Method Unused but it is for Knowledge Sake
    public static int compareByAge(Employee a, Employee b) {
        return a.dob.compareTo(b.dob);
    }
    
    @Override
    public String toString(){
        String str = String.format("(%s, %s, %s, %s, %.2f)\n", id, name, gender, dob, income);
        return str;
    }
}
//Irrelevant : Below Code Unused but it is for Knowledge Sake
class Averager implements IntConsumer{
    private int total = 0;
    private int count = 0;
    
    public double average(){
        return count > 0 ? ((double)total)/count : 0;
    }
    
    public void accept(int i) { total += i; count++; }
    
    public void combine(Averager other){
        total += other.total;
        count += other.count;
    }
}

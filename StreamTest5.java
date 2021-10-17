import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.DoubleSummaryStatistics;

import java.util.stream.Collectors;
import java.util.function.Supplier;
import java.util.function.BiConsumer;

import java.time.LocalDate;
import java.time.Month;

//www.java2s.com : Java Stream Collectors & Statistics
public class StreamTest5 {
    public static void main(String args[]) {
        List<Employee> persons = Employee.persons();
        
        //Collectors
        Supplier<ArrayList<String>> supplier = () -> new ArrayList<>();
        supplier = ArrayList::new;//Or
        
        BiConsumer<ArrayList<String>, String> accumulator = (list, name) -> list.add(name);
        accumulator = ArrayList::add;//Or
        
        persons.stream().map(Employee::getName).collect(ArrayList::new, ArrayList::add, ArrayList::addAll).forEach(System.out::println);//1st-Way
        persons.stream().map(Employee::getName).collect(Collectors.toList()).forEach(System.out::println);//2nd-Way
        persons.stream().map(Employee::getName).collect(Collectors.toSet()).forEach(System.out::println);//No Duplicates & No Insertion Order
        persons.stream().map(Employee::getName).collect(Collectors.toCollection(TreeSet::new)).forEach(System.out::println);//Sorting Order
        
        //Statistics
        DoubleSummaryStatistics doubleStats = new DoubleSummaryStatistics();//LongSummaryStatistcs, IntSummaryStatistics
        doubleStats.accept(100.0);
        doubleStats.accept(300.0);
        doubleStats.accept(230.0);
        doubleStats.accept(532.0);
        doubleStats.accept(422.0);
        
        long count = doubleStats.getCount();
        double sum = doubleStats.getSum();
        double min = doubleStats.getMin();
        double avg = doubleStats.getAverage();
        double max = doubleStats.getMax();
        
        System.out.printf("count=%d, sum=%.2f, min=%.2f, average=%.2f, max=%.2f%n", count, sum, min, avg, max);
        
        DoubleSummaryStatistics incomeStats = persons.stream().map(Employee::getIncome).collect(DoubleSummaryStatistics::new, DoubleSummaryStatistics::accept, DoubleSummaryStatistics::combine);
        System.out.println(incomeStats);
        
        incomeStats = persons.stream().collect(Collectors.summarizingDouble(Employee::getIncome));//Collectors.summarizingLong(), Collectors.summarizingInt()
        System.out.println(incomeStats);
        
        //Collectors Class contains Methods such as counting(), summingDouble(), summingInt(), summingLong(), averagingDouble(), averagingInt(), averagingLong(), minBy() & maxBy()
    }
}

class Employee {
  public static enum Gender {
    MALE, FEMALE
  }

  private long id;
  private String name;
  private Gender gender;
  private LocalDate dob;
  private double income;

  public Employee(long id, String name, Gender gender, LocalDate dob,
      double income) {
    this.id = id;
    this.name = name;
    this.gender = gender;
    this.dob = dob;
    this.income = income;
  }
  public String getName() {
    return name;
  }
  
  public double getIncome(){
    return income;    
  }
  
  public static List<Employee> persons() {
    Employee p1 = new Employee(1, "Jake", Gender.MALE, LocalDate.of(1971,
        Month.JANUARY, 1), 2343.0);
    Employee p2 = new Employee(2, "Jack", Gender.MALE, LocalDate.of(1972,
        Month.JULY, 21), 7100.0);
    Employee p3 = new Employee(3, "Jane", Gender.FEMALE, LocalDate.of(1973,
        Month.MAY, 29), 5455.0);
    Employee p4 = new Employee(4, "Jode", Gender.MALE, LocalDate.of(1974,
        Month.OCTOBER, 16), 1800.0);
    Employee p5 = new Employee(5, "Jeny", Gender.FEMALE, LocalDate.of(1975,
        Month.DECEMBER, 13), 1234.0);
    Employee p6 = new Employee(6, "Jason", Gender.MALE, LocalDate.of(1976,
        Month.JUNE, 9), 3211.0);

    List<Employee> persons = Arrays.asList(p1, p2, p3, p4, p5, p6);

    return persons;
  }
}

import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import java.util.Comparator;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import java.time.LocalDate;
import java.time.Month;

//www.java2s.com : Java Steam Operations, Filter, Map, Reduce, Aggregation & Count
public class StreamTest4 {
    public static void main(String args[]) {
        Employee.persons().stream().filter(Employee::isFemale).forEach(System.out::println);
        
        List<Employee> persons = Employee.persons();
        System.out.println("Before increasing the   income:   " + persons);
        persons.stream().filter(Employee::isFemale).forEach(p -> p.setIncome(p.getIncome() * 1.10));
        System.out.println("After increasing the   income:   " + persons);
        
        persons.stream().filter(Employee::isFemale).forEach(p -> System.out.println(p.getName()));
        persons.stream().filter(Employee::isFemale).map(Employee::getName).forEach(System.out::println);
        
        persons.stream().filter(Employee::isMale).filter(p -> p.getIncome() > 5000).map(Employee::getName).forEach(System.out::println);
        persons.stream().filter(p -> p.isMale() && p.getIncome() > 5000).map(Employee::getName).forEach(System.out::println);
        
        IntStream.rangeClosed(1, 5).map(n -> n * n).forEach(System.out::print);
        System.out.println();
        
        persons.stream().map(Employee::getName).forEach(System.out::println);
        
        Stream.of(1,2,3).flatMap(n -> Stream.of(n, n+1)).forEach(System.out::print);
        System.out.println();
        
        Stream.of("XML","CSS","HTML").map(name -> name.chars()).flatMap(intStream -> intStream.mapToObj(n -> (char)n)).forEach(System.out::println);
        Stream.of("XML","CSS","HTML").flatMap(name -> IntStream.range(0,name.length()).mapToObj(name::charAt)).forEach(System.out::println);
        
        int sum = Arrays.asList(1,2,3,4,5).stream().reduce(0,Integer::sum);
        System.out.println(sum);
        sum = Stream.of(1,2,3,4,5).reduce(0,Integer::sum);
        System.out.println(sum);
        
        double sum1 = persons.stream().map(Employee::getIncome).reduce(0.0,Double::sum);
        System.out.println(sum1);
        sum1 = persons.stream().reduce(0.0, (partial, person) -> partial + person.getIncome(), Double::sum);//Version2
        System.out.println(sum1);
        sum1 = persons.parallelStream().reduce(0.0, (partial, person) -> partial + person.getIncome(), Double::sum);//Version2 with Parallel Stream
        System.out.println(sum1);
        //Version2 in-detail
        sum1 = persons.stream().reduce(
            0.0, 
            (Double partialSum, Employee p) -> {
                double accumulated = partialSum + p.getIncome();
                System.out.println(Thread.currentThread().getName()+" - Accumulator: partialSum = " + partialSum+", person = "+p+", accumulated = "+accumulated);
                return accumulated;
            },
            (a,b) -> {
                double combined = a + b;
                System.out.println(Thread.currentThread().getName()+" - Combiner: a = "+a+", b = "+b+", combined = "+combined);
                return combined;
            });
        System.out.println(sum1);
        //Version2 in-detail with Parallel Stream
        sum1 = persons.parallelStream().reduce(
            0.0, 
            (Double partialSum, Employee p) -> {
                double accumulated = partialSum + p.getIncome();
                System.out.println(Thread.currentThread().getName()+" - Accumulator: partialSum = " + partialSum+", person = "+p+", accumulated = "+accumulated);
                return accumulated;
            },
            (a,b) -> {
                double combined = a + b;
                System.out.println(Thread.currentThread().getName()+" - Combiner: a = "+a+", b = "+b+", combined = "+combined);
                return combined;
            });
        System.out.println(sum1);
        //Version3
        Optional<Integer> optionalIntegerSum = Stream.of(1,2,3,4,5).reduce(Integer::sum);
        if(optionalIntegerSum.isPresent()){
            System.out.println(optionalIntegerSum.get());
        }
        Stream.of(1,2,3,4,5).reduce(Integer::max).ifPresent(System.out::println);
        Stream.<Integer> empty().reduce(Integer::max).ifPresent(System.out::println);
        
        persons.stream().reduce((p1,p2) -> p1.getIncome() > p2.getIncome()?p1:p2).ifPresent(System.out::println);
        
        //Aggregation
        sum1 = persons.stream().mapToDouble(Employee::getIncome).sum();
        System.out.println(sum1);
        persons.stream().max(Comparator.comparing(Employee::getIncome)).ifPresent(System.out::println);
        persons.stream().min(Comparator.comparing(Employee::getIncome)).ifPresent(System.out::println);
        persons.stream().mapToDouble(Employee::getIncome).max().ifPresent(System.out::println);
        persons.stream().mapToDouble(Employee::getIncome).min().ifPresent(System.out::println);
        
        //Count
        long count = persons.stream().count();
        System.out.println(count);
        count = persons.stream().mapToLong(p -> 1L).sum();
        System.out.println(count);
        count = persons.stream().mapToLong(p -> 1L).reduce(0L, Long::sum);
        System.out.println(count);
        count = persons.stream().reduce(0L, (partialCount, person) -> partialCount+1L, Long::sum);
        System.out.println(count);
    }
}

class Employee {
  public static enum Gender{
      MALE, FEMALE
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
  
  @Override
  public String toString(){
      //return "id : "+id+", name : "+name+", gender : "+gender+", dob : "+dob+", income : "+income;
      return String.format("%s, %s, %s, %s, %.2f", id, name, gender, dob, income);
  }
  
  public String getName(){
      return this.name;
  }
  
  public boolean isFemale(){
      return this.gender == Gender.FEMALE;
  }
  
  public boolean isMale(){
      return this.gender == Gender.MALE;
  }
  
  public void setIncome(double income){
      this.income = income;
  }
  
  public double getIncome(){
      return this.income;
  }
  
  public static List<Employee> persons(){
      Employee p1 = new Employee(1, "Jake", Gender.MALE, LocalDate.of(1971, Month.JANUARY, 1), 2343.0);
      Employee p2 = new Employee(2, "Jack", Gender.MALE, LocalDate.of(1972, Month.JULY, 21), 7100.0);
      Employee p3 = new Employee(3, "Jane", Gender.FEMALE, LocalDate.of(1973, Month.MAY, 29), 5455.0);
      Employee p4 = new Employee(4, "Jode", Gender.MALE, LocalDate.of(1974, Month.OCTOBER, 16), 1800.0);
      Employee p5 = new Employee(5, "Jeny", Gender.FEMALE, LocalDate.of(1975, Month.DECEMBER, 13), 1234.0);
      Employee p6 = new Employee(6, "Jason", Gender.MALE, LocalDate.of(1976, Month.JUNE, 9), 3211.0);
      
      List<Employee> persons = List.of(p1, p2, p3, p4, p5, p6);//Arrays.asList(p1, p2, p3, p4, p5, p6);
      
      return persons;
  }
}

import java.util.List;
import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

//www.java2s.com : Java Stream Introduction, Operation, API & Optional
public class StreamTest1 {
    public static void main(String args[]) {
      List<Integer> numbers = List.of(1, 2, 3, 4, 5);
     
      int sum = numbers.stream().filter(n -> n%2 != 0).mapToInt(n -> n*n).sum();
      sum = numbers.stream().filter(n -> n%2 != 0).mapToInt(n -> n*n).reduce(0,Integer::sum);
      sum = numbers.parallelStream().filter(n -> n%2 != 0).mapToInt(n -> n*n).reduce(0,Integer::sum);
     
      sum = numbers.stream().reduce(0, (n1, n2) -> n1 + n2);
      sum = numbers.stream().reduce(0, Integer::sum);
     
      System.out.println("sum : "+sum);
     
      numbers = Arrays.asList(3,7,9,3,1,2,1, 2, 3, 4, 5);
      numbers.stream().sorted().forEach(System.out::print);

      Optional<Integer> optionalInteger = Optional.empty();
      //System.out.println("\noptionalInteger : "+optionalInteger.get());//java.util.NoSuchElementException
      System.out.println("\noptionalInteger : "+optionalInteger.orElse(2));//2 = Default Value
      optionalInteger = Optional.of(1024);
      System.out.println("optionalInteger : orElse : "+optionalInteger.orElse(2));
      System.out.println("optionalInteger : "+optionalInteger);
      System.out.println("optionalInteger : "+optionalInteger.get());
     
      Optional<String> optionalString = Optional.empty();
      //optionalString = Optional.of(null);//java.lang.NullPointerException
      optionalString = Optional.of("");
     
      optionalString = Optional.ofNullable(null);
      System.out.println("optionalString : "+optionalString);
     
      optionalString = Optional.ofNullable("hello");
      if(optionalString.isPresent()){
          System.out.println("optionalString : "+optionalString);
      }else{
          System.out.println("optionalString is empty");
      }
     
      optionalString = Optional.ofNullable("hello");
      optionalString = Optional.of("hello");
      optionalString = Optional.ofNullable(null);
      optionalString.ifPresent(value -> System.out.println("optionalString is "+value));
      optionalString.ifPresent(System.out::println);
     
      OptionalInt optionalInt = OptionalInt.of(3);//OptionalLong, OptionalDouble
      //optionalInt = OptionalInt.ofNullable(3);//Wrong : Not Available
      //optionalInt = OptionalInt.of(null);//null cannot be converted to int
      //optionalInt = OptionalInt.of(new Integer(null));//java.lang.NumberFormatException
      int intValue = optionalInt.getAsInt();//getAsDouble, getAsLong
      if(optionalInt.isPresent()) intValue = optionalInt.getAsInt();
      optionalInt.ifPresent(System.out::println);
      System.out.println("intValue : "+intValue);
     
      OptionalInt optionalIntMaxValue = IntStream.of(10,20,30,33).filter(n -> n%2 == 1).min();//max
      optionalIntMaxValue.ifPresent(System.out::println);
    }
}

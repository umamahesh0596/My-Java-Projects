import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//www.java2s.com : Java Steam Operations - I
public class StreamTest3 {
    public static void main(String args[]) {
      List<Integer> list = List.of(5,3,5,7,7,2,8,2,2,6,3,5,8,9,7);
      list.stream().distinct().map(n -> n+1).filter(n -> n%2 ==0).sorted().skip(1).limit(2).forEach(System.out::print);
      
      System.out.println();
      List<String> list1 = Arrays.asList("zzz","bbb","ggg");
      List<String> list2 = List.of("zzz","bbb","ggg");
      List<List<String>> list3=new ArrayList<List<String>>();
      list3.add(list1);
      list3.add(list2);
      System.out.println("Before Fattening : "+list3);
      
      List<String> flattenedList = list3.stream().flatMap(e -> e.stream()).collect(Collectors.toList());
      System.out.println("After Fattening : "+flattenedList);
      
      IntStream intStream=Arrays.stream(new int[]{9,5,8});//Not Stream<Integer>
      intStream.forEach(System.out::print);
      
      System.out.println();
      Stream<String> streamOfStrings = Arrays.stream(new String[]{"dd","aa","hh"});
      streamOfStrings.forEach(System.out::print);
      
      System.out.println();
      int sum = Stream.of(1, 2, 3, 4, 5)
      .peek(i -> System.out.println("Taking Integer : "+i))
      .filter(i -> i%2==1)
      .peek(i -> System.out.println("Filtering Integer : "+i))
      .map(i -> i*i)
      .peek(i -> System.out.println("Mapping Integer : "+i))
      .reduce(0,Integer::sum);
       System.out.println("Sum = " + sum);
       
       Stream.of(5,1,4,2,9,6).forEach(System.out::print);//doesn't follow insertion order
       System.out.println();
       Stream.of(5,1,4,2,9,6).forEachOrdered(System.out::print);//follow insertion order & slow down processing in Parallel Stream
    }
}

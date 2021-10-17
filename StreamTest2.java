import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.Arrays;

import java.util.regex.Pattern;

import java.util.stream.Stream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.DoubleStream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//www.java2s.com : Java Stream Create, Stream from Functions, Collections, String & Files
public class StreamTest2 {
    public static void main(String args[]) {
        Stream<String> streamOfStrings = Stream.of("java2s.com");//single
        streamOfStrings = Stream.of("ccc","aaa","bbb");//Multiple
        String[] strArr = {"Java", "SQL", "Phython", "Scala"};
        streamOfStrings = Stream.of(strArr);//Array
        streamOfStrings = Stream.of(new String[]{"Java", "SQL", "Phython", "Scala"});//Array
        streamOfStrings.forEach(System.out::println);
       
        //Stream Builder
        Stream<String> streamBuilder = Stream.<String>builder()
            .add("XML")
            .add("YAML")
            .add("JSON")
            .build();
        streamBuilder.forEach(System.out::println);
       
        IntStream oneToSixExclusive = IntStream.range(1,6);//LongStream
        oneToSixExclusive.forEach(System.out::print);
        System.out.println();
        IntStream oneToFiveInclusive = IntStream.rangeClosed(1,5);
        oneToFiveInclusive.forEach(System.out::print);
        //oneToFiveInclusive.forEach(System.out::print);//java.lang.IllegalStateException: stream has already been operated upon or closed
        System.out.println();
        Stream<Integer> oneToFiveInclusiveStream = IntStream.rangeClosed(1,5).boxed();
        oneToFiveInclusiveStream = IntStream.rangeClosed(-111,52).mapToObj(e -> e);
        oneToFiveInclusiveStream = IntStream.rangeClosed(52,11).mapToObj(e -> e);
        oneToFiveInclusiveStream.forEach(System.out::print);
       
        Stream<Integer> emptyStreamOfIntegers = Stream.empty();
        IntStream emptyIntStream = IntStream.empty();//LongStream, DoubleStream
       
        Stream<Long> streamOfNaturalNumbers = Stream.iterate(1L, n -> n+1).limit(10);
        streamOfNaturalNumbers.forEach(System.out::print);
        System.out.println();
        Stream<Long> streamOfNaturalOddNumbers = Stream.iterate(2L, n -> n+1).limit(10).filter(MyClass::isOdd);
        streamOfNaturalOddNumbers.forEach(System.out::print);
        System.out.println();
        MyClass mc = new MyClass();
        Stream<Long> streamOfNaturalEvenNumbers = Stream.iterate(2L, n -> n+1).limit(10).filter(mc::isEven);
        streamOfNaturalEvenNumbers = Stream.iterate(2L, n -> n+1).filter(new MyClass()::isEven).skip(100).limit(10);
        streamOfNaturalEvenNumbers.forEach(System.out::print);
       
        System.out.println();
        Stream.generate(Math::random).map(n -> (int)(n*10)).limit(6).forEach(System.out::print);//OTP
       
        System.out.println();
        Stream.generate(MyClass::next).limit(5).forEach(System.out::print);
       
        System.out.println();
        Stream.generate(new MyClass()::nextValue).limit(5).forEach(System.out::print);
       
        System.out.println();
        new Random().ints().limit(3).forEach(System.out::println);//ints() return IntStream, longs() return LongStream & doubles() return DoubleStream
       
        Stream.generate(new Random()::nextInt).limit(2).forEach(System.out::println);
        IntStream.generate(new Random()::nextInt).limit(3).forEach(System.out::println);
        IntStream.generate(() -> 0).limit(4).forEach(System.out::print);
       
        System.out.println();
        IntStream is = Arrays.stream(new int[]{1, 2, 3});
        is.forEach(System.out::print);
        System.out.println();
        LongStream ls = Arrays.stream(new long[]{3, 2, 1});
        ls.forEach(System.out::print);
        System.out.println();
        double[] da = {1, 2.5, 3};
        DoubleStream ds = Arrays.stream(da);
        ds.forEach(System.out::print);
        System.out.println();
        Stream<String> ss = Arrays.stream(new String[]{"mmm", "aaa", "fff"});
        ss.forEach(System.out::print);
       
        Set<String> set = new HashSet();
        set.add("bbb");
        set.add("aaa");
       
        Stream<String> s1 = set.stream();
        Stream<String> s2 = set.parallelStream();
       
        String str = "5 123,123,qwe,1,123, 25";
        str.chars().filter(c -> !Character.isDigit((char)c) && !Character.isWhitespace((char)c)).forEach(c -> System.out.println((char)c));
       
        str = "XML,CSS,HTML";
        Pattern.compile(",").splitAsStream(str).forEach(System.out::println);
       
        Path path = Paths.get("./StreamTest2.java");
        try (Stream<String> lines = Files.lines(path)){
            lines.forEach(System.out::println);
        }catch(IOException e){
            e.printStackTrace();
        }
       
        Path dir = Paths.get(".");
        System.out.printf("%nThe file tree for %s%n", dir.toAbsolutePath());
        try (Stream<Path> fileTree = Files.walk(dir)){
            fileTree.forEach(System.out::println);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
   
    private static boolean isOdd(long n){
        if(n%2==1) return true;
        return false;
    }
   
    protected boolean isEven(long n){
        if(n%2==0) return true;
        return false;
    }
   
    static int i=0;
    public static int next(){
        i++;
        return i;
    }
   
    int j=6;
    private int nextValue(){
        j--;
        return j;
    }
}

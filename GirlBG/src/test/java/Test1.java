import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test1 {
    static List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
    public static void main(String[] args) {
        demo3();
   }

    private static void demo1(){
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
// 获取对应的平方数
        numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList()).forEach(System.out::println);
    }

    private static void demo2(){

// 获取空字符串的数量
        long count = strings.stream().filter(string -> string.isEmpty()).count();
        System.out.println(count);
    }

    private static void demo3(){
        String sr = strings.stream().filter(s -> !s.isEmpty()).collect(Collectors.joining(", "));
        System.out.println(sr);
    }
}

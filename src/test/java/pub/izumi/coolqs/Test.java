package pub.izumi.coolqs;

import java.util.ArrayList;
import java.util.List;

public class Test {

    @org.junit.jupiter.api.Test
    public void test() {
        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            integers.add(i);
        }
        List<Integer> temp = integers.subList(0,10);
        System.out.println(temp);
        System.out.println(integers);
        integers.removeAll(temp);
        System.out.println(integers);
    }
}

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
        new Thread(() -> {
            for (int i = 10; i < 100; i++) {
                integers.add(i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Integer> temp = integers.subList(0,8);
        System.out.println(temp);
        integers.add(123);
        System.out.println(integers);
        integers.removeAll(integers.subList(0,8));
        System.out.println(integers);
    }
}

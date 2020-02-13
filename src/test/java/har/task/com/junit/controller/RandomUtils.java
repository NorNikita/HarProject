package har.task.com.junit.controller;

public class RandomUtils {

    static Long randomLong() {
        return (long) (Math.random() * 100000);
    }

    static byte[] randomContent() {
        int size = (int) (Math.random() * 10000);
        byte[] content = new byte[size];
        for(int i = 0; i < size; i++) content[i] = (byte)(Math.random()*10);
        return content;
    }
}

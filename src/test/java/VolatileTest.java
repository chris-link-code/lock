/**
 * @author chris
 * @create 2022/10/6
 */
public class VolatileTest {
    static boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            while (flag) {
                System.out.println(Thread.currentThread().getName() + " flag is true");
            }
        }, "thread_1").start();

        new Thread(() -> {
            flag = false;
            System.out.println(Thread.currentThread().getName() + " flag is false");
        }, "thread_2").start();
    }
}

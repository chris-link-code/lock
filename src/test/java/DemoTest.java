import com.demo.extend.SubClass;
import com.demo.extend.SubClass2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author chris
 * @create 2022/7/23
 */
@Slf4j
public class DemoTest {

    @Test
    /**打印字符ASCII值*/
    public void acsTest() {
        int m = 'm';
        log.info(String.valueOf(m));
    }

    @Test
    /**获得随机数*/
    public void randomTest() {
        double v = ThreadLocalRandom.current().nextDouble();
        log.info(String.valueOf(v));
    }

    // TODO 创建没有构造函数的对象时会怎么样

    /**
     * 测试继承
     * 子类会自动调用父类的无参构造
     * 调用父类的有参构造需要用super()
     * 如果子类调用了父类得有参构造，则不会自动调用父类的无参构造
     */
    @Test
    public void extendsTest() {
        log.info("------SubClass 类继承------");
        log.info("new SubClass():");
        SubClass sc1 = new SubClass();
        log.info("");

        log.info("new SubClass(100):");
        SubClass sc2 = new SubClass(100);
        log.info("");

        log.info("------SubClass2 类继承------");
        log.info("new SubClas2s():");
        SubClass2 sc3 = new SubClass2();
        log.info("");

        log.info("new SubClass(200):");
        SubClass2 sc4 = new SubClass2(200);
    }

    @Test
    void binaryTest() {
        int i = 7;
        System.out.println(Integer.toBinaryString(i));

        String b = "111";
        // 二进制转十进制
        System.out.println(Integer.parseInt(b, 2));

        String c = "11";
        // 八进制转十进制
        System.out.println(Integer.parseInt(c, 8));
    }

    @Test
    void moveTest() {
        //int i = 7;
        //System.out.println(i >>> 1);

        int c = -1;
        System.out.println(c >> 1);
        // 无符号右移
        System.out.println(c >>> 1);

        Object o = new Object();
        int hashCode = o.hashCode();
        String binaryString = Integer.toBinaryString(hashCode);
        System.out.println(binaryString);
        System.out.println(hashCode & 31);
    }

    @Test
    void hashTest() {
        Object o = new Object();
        int hashCode = o.hashCode();
        int d = hashCode ^ (hashCode >>> 16);
        System.out.println(hashCode);
        System.out.println(d);
        String binaryString = Integer.toBinaryString(hashCode);
        String dBinaryString = Integer.toBinaryString(d);
        System.out.println(binaryString);
        System.out.println(dBinaryString);
        System.out.println(binaryString.length());
    }

    @Test
    void hashMapTest() {
        // 初始化一组字符串
        List<String> list = new ArrayList<>();
        list.add("jlkk");
        list.add("lopi");
        list.add("uuio");
        list.add("e4we");
        list.add("alpo");
        list.add("yhjk");
        list.add("plop");

        // 定义要存放的数组
        String[] tab = new String[8];

        // 循环存放
        for (String key : list) {
            int idx = key.hashCode() & (tab.length - 1);  // 计算索引位置
            System.out.println(String.format("key值=%s Idx=%d", key, idx));
            if (null == tab[idx]) {
                tab[idx] = key;
                continue;
            }
            tab[idx] = tab[idx] + "->" + key;
        }
        // 输出测试结果
        System.out.println(tab.toString());
    }

    @Test
    public void intTest() {
        Long k = 1L << 10;
        Long m = 1L << 20;
        Long g = 1L << 30;
        Long t = 1L << 40;
        log.info("1 << 10 : {}", k);
        log.info("1 << 20 : {}", m);
        log.info("1 << 30 : {}", g);
        log.info("1 << 40 : {}", t);
        log.info("1 << 31 : {}", 1L << 31);
        log.info("Int__MAX: {}", Integer.MAX_VALUE);
    }
}

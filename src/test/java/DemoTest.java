import com.test.extend.SubClass;
import com.test.extend.SubClass2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

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
}

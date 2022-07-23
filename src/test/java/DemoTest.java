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
}

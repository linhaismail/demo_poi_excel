package com.example.demo;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

/**
 * 测试BigDecimal的格式化
 *
 * @author Vincent
 * @date 2018-04-08 10:02
 */
@SpringBootTest
public class BigDicamalTest {

    @Test
    public void test1() {
        BigDecimal m1 = new BigDecimal(12.34444);
        m1 = m1.setScale(2, BigDecimal.ROUND_HALF_UP);

        System.out.println(m1);
    }
}

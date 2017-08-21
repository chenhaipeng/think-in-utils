package com.thinkme.utils.misc;

import org.junit.Test;

/**
 * @author chenhaipeng
 * @version 1.0
 * @date 2017/08/17 12:11
 */
public class SnowFlakeTest {
    @Test
    public void nextId() throws Exception {
        SnowFlake snowFlake = new SnowFlake(2, 3);
        for (int i = 0; i < (1 << 12); i++) {
            System.out.println(snowFlake.nextId()+" "+ (snowFlake.nextId()+"").length());
        }

    }

}
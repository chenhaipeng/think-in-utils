package com.thinkme.utils.id;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 1.针对IPV4:
 * ….IP最大 255.255.255.255。而（255+255+255+255) < 1024。
 * ….因此采用IP段数值相加即可生成唯一的workerId，不受IP位限制。
 * <p>
 * 针对IPV6:
 * ….IP最大 ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff
 * ….为了保证相加生成出的工程进程编号 < 1024,思路是将每个 Bit 位的后6位相加。这样在一定程度上也可以满足workerId不重复的问题。
 * 使用这种 IP 生成工作进程编号的方法,必须保证IP段相加不能重复
 * <p>
 * 加入初始化端口，防止一个应用多个实例
 *
 * @author chenhaipeng
 * @version 1.0
 * @mail donotcoffee@gmail.com
 * @date 2018/01/01 下午10:15
 */
public class IPSectionKeyGenerator implements IdGenerator {

    private static final CommonIdGenerator commonIdGenerator = new CommonIdGenerator();

    private static final int workId = 0;

    // IPSectionKeyGenerator.java
    public static void initWorkerId() {
        InetAddress address;
        try {
            address = InetAddress.getLocalHost();
        } catch (final UnknownHostException e) {
            throw new IllegalStateException("Cannot get LocalHost InetAddress, please check your network!");
        }
        byte[] ipAddressByteArray = address.getAddress();
        long workerId = 0L;
        // IPV4
        if (ipAddressByteArray.length == 4) {
            for (byte byteNum : ipAddressByteArray) {
                workerId += byteNum & 0xFF;
            }
            // IPV6
        } else if (ipAddressByteArray.length == 16) {
            for (byte byteNum : ipAddressByteArray) {
                workerId += byteNum & 0B111111;
            }
        } else {
            throw new IllegalStateException("Bad LocalHost InetAddress, please check your network!");
        }
        commonIdGenerator.setWorkerId(workerId);
    }


    @Override
    public long generateId() {
        return commonIdGenerator.generateId();
    }

}

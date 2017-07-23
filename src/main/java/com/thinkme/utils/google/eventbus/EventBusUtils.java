package com.thinkme.utils.google.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executors;

/**
 *抽象于event事件发布类
 * @author chenhaipeng
 * @version 1.0
 * @date 2016/08/19 1:37
 */
public class EventBusUtils {

    public static EventBus eventBus = new EventBus();

    /**
     * 最大起50个线程
     */
    public static AsyncEventBus asyncEventBus = new AsyncEventBus("EventBusUtils", Executors.newFixedThreadPool(50));

    public static void post(Object event, Class clz) {
        if (clz.getSuperclass().getName().equals(AsyncListener.class.getName())) {
            asyncEventBus.post(event);
        } else {
            eventBus.post(event);
        }
    }
}
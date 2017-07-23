package com.thinkme.utils.google.eventbus;

import com.google.common.eventbus.EventBus;
import org.junit.Test;

/**
 * @author chenhaipeng
 * @version 1.0
 * @date 2017/07/15 13:56
 */
public class EventBusTest {
    @Test
    public void should_recv_event_message() throws Exception {
        EventBus eventBus = new EventBus();
        CookieContainer cookieContainer=new CookieContainer(eventBus);
        HandlerService cookieSeller = new CookieSeller(eventBus);
        HandlerService cookieMallBoss = new CookieMallBoss(eventBus);

        //设置cookie的数量为3
        cookieContainer.setNumberOfCookie(3);
        //用户取三次之后cookie数量为空
        cookieContainer.getACookie();
        cookieContainer.getACookie();
        cookieContainer.getACookie();
        System.out.println("=======再次取cookie, 触发Empty事件发布============");
        cookieContainer.getACookie();

//        Thread.sleep(1000);



    }

    @Test
    public void testEvent() throws InterruptedException {
        User user = new User();
        user.setName("user1");
        User2 user2 = new User2();
        user2.setName("user2");
        //如果是spring 管理不需要以下
        EventBusUtils.asyncEventBus.register(new UserListener());
        EventBusUtils.asyncEventBus.register(new UserListener2());

        EventBusUtils.post(user,UserListener.class);
        EventBusUtils.post(user2,UserListener2.class);

    }
}

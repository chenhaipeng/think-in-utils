package com.thinkme.utils.google.eventbus;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * @author chenhaipeng
 * @version 1.0
 * @date 2017/07/15 14:56
 */
public class UserListener2 extends AsyncListener<User2> {

    @Subscribe
    @AllowConcurrentEvents
    protected void doSubscibe(User2 user2) {
        System.out.println("xxxxxxxxxxxxxxxx2"+user2.getName());
    }
}

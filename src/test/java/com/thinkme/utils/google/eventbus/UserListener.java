package com.thinkme.utils.google.eventbus;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * @author chenhaipeng
 * @version 1.0
 * @date 2017/07/15 14:28
 */
public class UserListener extends AsyncListener<User> {


    @Subscribe
    @AllowConcurrentEvents
    protected void doSubscibe(User user) {
        System.out.println("xxxxxxxxxxxxxxxx"+user.getName());
    }
}

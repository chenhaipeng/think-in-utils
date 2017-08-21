package com.thinkme.utils.google.eventbus;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

public interface HandlerService {
    @Subscribe
    @AllowConcurrentEvents
    void handler(EmptyEvent emptyEvent);
}
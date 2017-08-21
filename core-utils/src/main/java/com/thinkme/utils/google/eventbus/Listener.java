package com.thinkme.utils.google.eventbus;

import javax.annotation.PostConstruct;

/**
 * 同步订阅者
 * 继承者需在方法加上    @Subscribe @AllowConcurrentEvents
 *
 * @author chenhaipeng
 * @version 1.0
 * @date 2016/08/19 0:44
 */
public abstract class Listener<T> {

    @PostConstruct
    public void init() {
        EventBusUtils.eventBus.register(this);
    }
}

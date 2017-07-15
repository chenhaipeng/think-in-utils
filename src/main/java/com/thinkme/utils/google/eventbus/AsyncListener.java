package com.thinkme.utils.google.eventbus;

import javax.annotation.PostConstruct;

/**
 * 异步订阅者
 * 继承者需在方法加上    @Subscribe @AllowConcurrentEvents
 *
 * @author chenhaipeng
 * @version 1.0
 * @date 2016/08/19 1:37
 */
public abstract class AsyncListener<T> {

    @PostConstruct
    public void init() {
        EventBusUtils.asyncEventBus.register(this);
    }

}

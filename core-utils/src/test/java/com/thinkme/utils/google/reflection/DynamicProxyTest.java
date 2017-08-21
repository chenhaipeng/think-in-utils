package com.thinkme.utils.google.reflection;

import com.google.common.reflect.Reflection;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by outofmemory.cn  on 2014/7/31.
 */
public class DynamicProxyTest {
    public static void main(String[] args) {
        InvocationHandler invocationHandler = new MyInvocationHandler();

        // Guava Dynamic Proxy implement
        IFoo foo = Reflection.newProxy(IFoo.class, invocationHandler);
        foo.doSomething();
        //jdk Dynamic proxy implement
        IFoo jdkFoo = (IFoo) Proxy.newProxyInstance(
                IFoo.class.getClassLoader(),
                new Class<?>[]{IFoo.class},
                invocationHandler);
        jdkFoo.doSomething();
    }

    public static class MyInvocationHandler implements InvocationHandler{
    public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            System.out.println("proxy println something");
            return null;
        }
    }

    public static interface IFoo {
        void doSomething();
    }
}
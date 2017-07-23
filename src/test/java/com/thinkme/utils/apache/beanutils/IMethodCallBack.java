package com.thinkme.utils.apache.beanutils;

public interface IMethodCallBack {

    String getMethodName();

    ToBean callMethod(FromBean frombean)  throws Exception;

}
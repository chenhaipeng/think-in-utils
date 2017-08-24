package com.thinkme.utils.lock.exception;

/**
 * 锁异常基类
 *
 * Created by liangqq on 2017/2/28.
 */
public class LockException extends RuntimeException{

    public LockException(String errorMessage){
        super(errorMessage);
    }

    public LockException(String errorMessage, Throwable cause){
        super(errorMessage, cause);
    }
}

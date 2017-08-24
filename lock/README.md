# 分布式锁

基于redission实现的分布式锁，算法是Redlock

## 快速开始

### 1. 加入依赖

```xml
```

### 2. 配置文件redissionConf.yml

文件放置在/resources文件夹下

```yml
singleServerConfig:
  idleConnectionTimeout: 10000
  pingTimeout: 1000
  connectTimeout: 10000
  timeout: 3000
  retryAttempts: 3
  retryInterval: 1500
  reconnectionTimeout: 3000
  failedAttempts: 3
  password: null
  subscriptionsPerConnection: 5
  clientName: null
  address:
  -
   "redis://192.168.1.39:6379"
  subscriptionConnectionMinimumIdleSize: 1
  subscriptionConnectionPoolSize: 50
  connectionMinimumIdleSize: 5
  connectionPoolSize: 250
  database: 0
  dnsMonitoring: false
  dnsMonitoringInterval: 5000
threads: 0
nettyThreads: 3
codec: null
useLinuxNativeEpoll: false
eventLoopGroup: null
```

### 3. 接口

```java
public interface DistributedLock {

    /**
     * 加锁，在指定时间内获取锁失败会抛出
     * @param waitTime 最长等待锁时间
     * @param timeUnit 时间单位
     * @exception LockException
     */
    void lock(long waitTime, TimeUnit timeUnit);

    /**
     * 加锁
     * @param waitTime 最长等待锁时间
     * @param leaseTime 持有锁最长时间
     * @param timeUnit 时间单位
     * @exception LockException
     */
    void lock(long waitTime, long leaseTime, TimeUnit timeUnit);

    /**
     * 解锁
     */
    void unLock();

    /**
     * 判断锁对象是否被人持有
     */
    boolean isLock();

    /**
     * 判断是否被当前线程持有
     * @return
     */
    boolean isHeldByCurrentThread();
```

### 4. 示例代码

获取锁，只等待10秒。锁获取成功后，将会一直持有，直到显示调用了unLock()方法。

```java
DistributedLock lock = DistributedLockFactory.getLock("lock_key");
try{
    // 阻塞接口，获取锁等待10秒钟
    // 10秒钟依然没有获取到锁的话过后会抛出 LockException 异常
    lock.lock(10, TimeUnit.SECONDS);
}catch(LockException e){
    // ...
}finally {
    lock.unLock();
}
```

获取锁，等待10秒，持有最长30min，到了30min假如锁还未释放，则redission会自动帮助我们释放

```java
DistributedLock lock = DistributedLockFactory.getLock("lock_key");
try{
    // 获取锁成功后
    lock.lock(10, 60 * 30, TimeUnit.SECONDS);
}catch(LockException e){
    // ...
}finally {
    lock.unLock();
}
```

### Q&A

Q1：为什么设置leaseTime？

A：为了避免获取锁的client无响应而导致**严重的死锁**问题，我们推荐业务使用时根据业务的复杂度设置合理的超时时间。

Q2：如果由于leaseTime触发了解锁，但是业务工作未完成，是否会导致并发安全问题？

A：很有可能会导致。在分布式世界没有十全十美的解决方案，我们建议

1. 业务需要对业务时间做一个合理的估算
2. 拆分需要锁的粒度，不要太大

## 细粒度内存锁

JDK默认的锁粒度比较大，比较难以做到类似于数据库的行锁，此处提供一个默认实现，解决细粒度锁的问题。

需要注意的是，内存锁**非进程间安全**。

### 分段锁

```java
Lock lock = new MemLockBuilder()
                .lockType(MemLockBuilder.MemLockType.SEGMENT)
                .lockKey(key)
                .build();
```

### 弱哈希锁（粒度更细）

```java
Lock lock = new MemLockBuilder()
                .lockType(MemLockBuilder.MemLockType.WEAK_HASH)
                .lockKey(key)
                .build();
```
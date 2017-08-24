# 公用utils

## 工程目的

针对常用的功能做了封装

涵盖如下方面

- base
- collection
- concurrent
- io
- misc
- net
- number
- reflect
- security
- text
- time

Q: 是否存在重复造轮子的工作？比如MapUtil等。

A: 我们的目标1）降低大家的选择压力（guava还是apache）；2）尽量避免一些公共性坑

> 项目中大量的工具类来自于江南白衣的项目[SpringSide4](https://github.com/springside/springside4)
在此基础上对整体的utils做了修订和补充

## 快速开始

```java

```

## 各模块包说明

### base

`ExceptionUtil`

提供了常见的异常封装

- `isCausedBy` 判断是否由某个异常引起
- `getRootCause` 获取根异常
- `stackTraceText` 获取异常栈

`Platforms`

提供了常见os相关常量的整合

- 文件路径分隔符
- ClassPath分隔符
- 换行符
- 临时目录
- 当前应用的工作目录
- 用户 HOME目录
- Java HOME目录
- Java版本
- 操作系统类型及版本

#### `PropertiesUtil`

提供对Properties属性文件的访问封装

#### `SystemPropertiesUtil`

提供对系统变量的读取能力，包括系统变量(-D)和环境变量

#### `ValidateUtil` 

防御式编程必备，对于方法入参的强校验，推荐优先使用guava Preconditions > apacha Common Validate解决，这个工具类补充了对数值层面校验的更友好的api

### collection

#### `ArrayUtil`, `CollectionUtil`, `ListUtil`, `MapUtil`, `QueueUtil`, `SetUtil` 

提供对guava和apache common的常用api的封装

移植基本类型作为key的map，`IntObjectMap`和`LongObjectMap`，在性能和内存占用上比包装类的map实现由更好的表现能力

#### `ConcurrentHashSet` 

JDK默认提供了`ConcurrentHashMap`，这里补充对HashSet的封装

#### `SortedArrayList` 

提供支持有序的ArrayList实现

###  concurrent

#### `BasicFuture` 

移植自apache HttpClient 一个Future的抽象实现类，使用者可以只需要实现`onCompleted`，`onFailed`和`onCancelled`即可

#### `ConcurrentTools` 

提供了对`CountDownLatch`, `CyclicBarrier`, 限流`RateLimiter`，采样器`Sampler`的获取能力封装

#### `ThreadDumpper` 

线程dump工具类

#### `ThreadUtil` 

提供对线程的常用操作的封装，包括sleep，优雅关闭线程池等。

请使用此工具类的sleep代替原生的Thread.sleep()

#### `ThreadPoolBuilders` 

提供对线程池创建的流式接口封装，简化创建复杂线程池，同时移植tomcat的QueuableCachedThreadPool

传统的FixedThreadPool有Queue但线程数量不变，而CachedThreadPool线程数可变但没有Queue
Tomcat的线程池，通过控制TaskQueue，线程数，但线程数到达最大时会进入Queue中。

同时，非常不建议Executors工具类的创建，因为很多开发者搞不清楚不同线程池的内在区别而导致误用。

```java
// 创建线程数为10，等待队列为100，线程名称kaka开始的固定线程池
ThreadPoolBuilders
    .fixedPool()
    .setPoolSize(10)
    .setQueueSize(100)
    .setThreadFactory(ThreadUtil.buildThreadFactory("kaka"))
    .build();
```

### io

- `FilePathUtil`
- `IOUtil`
- `FileUtil`
- `ResourceUtil` 提供对jar里边资源的读取封装

#### `StringBuilderWriter`

 JDK的java.io.StringWriter使用StringBuffer，移植Commons IO使用StringBuilder的版本

###  misc

#### UUIDGenerator 

生成UUID的封装

### net

#### `IPUtil` 

提供对ip地址的装换

#### `OkHttpUtil` 

http常用请求的统一封装，基于OkHttp（技术雷达推荐采用），对于HttpClient，语法更加的简洁

### reflect

#### `BeanMapper`

 bean拷贝工具类，基于orika，性能表现好，不要使用apache的BeanUtils.copyProperties()方法，性能低
 
 使用需要增加依赖
 
 ```java
        <dependency>
            <groupId>ma.glasnost.orika</groupId>
            <artifactId>orika-core</artifactId>
        </dependency>
```

#### `ClassUtil`, `FastMethodInvoker`, `MethodInvoker`和`ReflectionUtil` 

提供常用的反射api的封装

`FastMethodInvoker`类需要增加以下依赖

```java
        <dependency>
            <groupId>cglib</groupId>
            <artifactId>cglib</artifactId>
        </dependency>
```

#### 集成joor

集成了joor，支持流式语法操作反射，可读性高，推荐此写法

```java
// All examples assume the following static import:
import static org.joor.Reflect.*;

String world = on("java.lang.String")  // Like Class.forName()
                .create("Hello World") // Call most specific matching constructor
                .call("substring", 6)  // Call most specific matching substring() method
                .call("toString")      // Call toString()
                .get();                // Get the wrapped object, in this case a String
```

原生api的对比

joor style

```java
Employee[] employees = on(department).call("getEmployees").get();

for (Employee employee : employees) {
  Street street = on(employee).call("getAddress").call("getStreet").get();
  System.out.println(street);
}
```

java api style

```java
try {
  Method m1 = department.getClass().getMethod("getEmployees");
  Employee employees = (Employee[]) m1.invoke(department);

  for (Employee employee : employees) {
    Method m2 = employee.getClass().getMethod("getAddress");
    Address address = (Address) m2.invoke(employee);

    Method m3 = address.getClass().getMethod("getStreet");
    Street street = (Street) m3.invoke(address);

    System.out.println(street);
  }
}

// There are many checked exceptions that you are likely to ignore anyway 
catch (Exception ignore) {

  // ... or maybe just wrap in your preferred runtime exception:
  throw new RuntimeException(e);
}
```

### security

#### `CryptoUtil`

提供常用加密算法的封装，包括AES，HmacSHA1，DES

### text

#### `Charsets` 

常用字符集的常量定义，可以使用JDK的StandardCharsets替代

#### `EncodeUtil`

提供对hex和base64的编码支持

#### `EscapeUtil`

1. 支持urlEncode和urlDeCode
2. 支持escapeXml和unescapeXml
3. 支持escapeHtml和unescapeHtml

#### `HashUtil`

提供常用散列算法的封装

1. crc32
2. md5
3. murmur32
4. sha1

#### `JsonMapper`

基于jackson封装的json工具类

#### `StringBuilderHolder`

参考Netty的InternalThreadLocalMap 与 BigDecimal, 放在threadLocal中重用的StringBuilder, 节约StringBuilder内部的char[]

参考文章：《StringBuilder在高性能场景下的正确用法》http://calvin1978.blogcn.com/articles/stringbuilder.html

不过仅在String对象较大时才有明显效果，否则抵不上访问ThreadLocal的消耗.

在Netty环境中，使用Netty提供的基于FastThreadLocal的版本。

#### `TextValidator`

通过正则表达判断是否正确的手机号，固定电话，身份证，邮箱等.

### time

#### `CachingDateFormatter` 

DateFormat.format()消耗较大，如果时间戳是递增的，而且同一单位内有多次format()，使用用本类减少重复调用. From Log4j2 DatePatternConverter，进行了优化，根据输出格式是否毫秒级，决定缓存在秒级还是毫秒级.

#### `ClockUtil`

日期提供者, 使用它而不是直接取得系统时间, 方便测试. 平时使用DEFAULT，测试时替换为DummyClock，可准确控制时间变化而不用Thread.sleep()等待时间流逝.

#### `DateFormatUtil`

Date的parse()与format(), 采用Apache Common Lang中线程安全, 性能更佳的FastDateFormat

1. 常用格式的FastDateFormat定义 
2. 日期格式不固定时的String<->Date 转换函数.
3. 打印时间间隔，如"01:10:10"，以及用户友好的版本，比如"刚刚"，"10分钟前"

`DateUtil`

日期工具类。在不方便使用joda-time时，使用本类降低Date处理的复杂度与性能消耗, 封装Common Lang及移植Jodd的最常用日期方法。同时也推荐使用jdk8的日期api
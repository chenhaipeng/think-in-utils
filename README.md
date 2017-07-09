# think-in-utils
-----
> * 平时搜集的工具集，加速开发速度
> * 减少造轮子

[TOC]

## 1. 通用
### 1.1 apache
#### Validate
可用于通于的空值判断，类似google Preconditions
#### MoreValidate
参数校验统一使用Apache Common Lange Validate, 补充一些缺少的.为什么不用Guava的Preconditions
作者认为打少几个字母是好事，哥认同。

### 1.2 google
#### Preconditions
作参数检查的工具类--Preconditions类，这种方法能够更好的检查这样的参数
#### optional
Optional迫使你积极思考引用缺失的情况，因为你必须显式地从Optional获取引用
```
public class OptionalDemo {
  public static void main(String[] args) {
      Optional<Student> possibleNull = Optional.absent();
      Student jim = possibleNull.get();
  }
  public static class Student { }
}
```
如同输入参数，方法的返回值也可能是null。和其他人一样，你绝对很可能会忘记别人写的方法method(a,b)会返回一个null，就好像当你实现method(a,b)时，也很可能忘记输入参数a可以为null。将方法的返回类型指定为Optional，也可以迫使调用者思考返回的引用缺失的情形。


### 1.3 自封装
#### BooleanUtil
#### EnumUtil
将若干个枚举值转换为long，用于使用long保存多个选项的情况.
 > 感觉用处不大

#### ObjectUtil
对象的toString()，处理了对象为数组和集合的情况.仅仅为了处理toString 有点浪费这个类名，未来会加入更多的方法

#### Platforms
获取系统参数
#### PropertiesUtil
统一读取Properties,从文件或字符串装载Properties
#### SystemPropertiesUtil
关于系统Properties的工具类

#### ExceptionUtil
关于异常的工具类.
> 感觉用处不大


## 2. 集合
### 2.1 apache
### 2.2 google

#### Immutable
```
  //不可变集合的使用和普通集合一样，只是不能使用他们的add，remove等修改集合的方法。
  ImmutableList<String> of = ImmutableList.of("a", "b", "c", "d");
  ImmutableMap<String,String> map = ImmutableMap.of("key1", "value1", "key2", "value2");

//1.
  Set<String> immutableNamedColors = ImmutableSet.<String>builder()
               .add("red", "green","black","white","grey")
               .build();
//2.               
  ImmutableSet.of("red","green","black","white","grey");

//3.
  ImmutableSet.copyOf(new String[]{"red","green","black","white","grey"});

```
### 2.3 自封装




## 网络

package com.thinkme.utils.google.reflection;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author chenhaipeng
 * @version 1.0
 * @date 2017/07/15 15:27
 */
public class ReflectionTest {

    @Test
    public void testTypeToken(){
        ArrayList<String> stringList = Lists.newArrayList();
        ArrayList<Integer> intList = Lists.newArrayList();
        System.out.println(stringList.getClass().isAssignableFrom(intList.getClass()));
//        returns true, even though ArrayList<String> is not assignable from ArrayList<Integer>

        TypeToken<ArrayList<String>> typeToken = new TypeToken<ArrayList<String>>() {};
        TypeToken<?> genericTypeToken = typeToken.resolveType(ArrayList.class.getTypeParameters()[0]);
        System.out.println(genericTypeToken.getType());
    }

    @Test
    public void testInvokable(){
//        方法的第一个参数是否被定义了注解@Nullable？
/*        for (Annotation annotation : method.getParameterAnnotations[0]) {
            if (annotation instanceof Nullable) {
                return true;
            }
        }
        return false;*/


    }



}

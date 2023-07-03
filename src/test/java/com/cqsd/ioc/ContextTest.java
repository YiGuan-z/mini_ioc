package com.cqsd.ioc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author caseycheng
 * @date 2023/7/2-13:48
 **/
public class ContextTest {
    @Test
    void testContextCreate() {
        final var context = AppContent.create("app")
                .provide("卧槽", "config")
                .provide("2")
                .provide(1)
                .provide(3)
                .provide(A.class, "A");
        final var strings = context.collect(String.class, ArrayList::new);
        assertArrayEquals(strings.toArray(), new String[]{"卧槽", "2"});
        final var integers = context.collect(Integer.class, ArrayList::new);
        final var charSequences = context.collect(Comparable.class, ArrayList::new);
        System.out.println(Arrays.toString(charSequences.toArray()));
        assertArrayEquals(integers.toArray(), new Integer[]{1, 3});
        final var solver = context.solver(A.class);
        System.out.println(solver);
        final var config = context.solver(String.class, "config");
        assertEquals(config, "卧槽");
    }

    @Test
    public void testProvide() {
        Context context = AppContent.create("test");
        Object obj = new Object();
        context.provide(obj);
        assertEquals(obj, context.solver(Object.class));
    }

    @Test
    public void testCollect() {
        Context context = AppContent.create("test");
        String str1 = "string1";
        String str2 = "string2";
        context.provide(str1);
        context.provide(str2);
        List<String> result = context.collect(String.class, ArrayList::new);
        assertTrue(result.contains(str1));
        assertTrue(result.contains(str2));
    }

    @Test
    public void testSolver() {
        Context context = AppContent.create("test");
        String str1 = "string1";
        String str2 = "string2";
        context.provide(str1);
        context.provide(str2, "name");

        // Test solver(Class<T> type)
        assertEquals(str1, context.solver(String.class));

        // Test solver(String name)
        assertEquals(str2, context.solver("name"));

        // Test solver(Class<T> type, String name)
        assertEquals(str2, context.solver(String.class, "name"));
    }
}

class A {
    public A(String a, Integer b, B bc) {
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println(bc);
        final var list = Stream.of(1, 2, 3, 4, 5, 6, 7).filter(it -> it % 2 == 0).toList();
        Stream.of(new Object(),new Object(),new Object()).map(Object::hashCode).forEach(System.out::println);
        System.out.println(list);
    }
}

class B {
    public B() {
        System.out.println("B created");
    }
}

package com.y3tu.cloud.upms;

import com.y3tu.tool.core.lang.Console;
import com.y3tu.tool.db.ds.DsFactoryEnum;
import com.y3tu.tool.web.codegen.util.GenUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import static com.y3tu.tool.core.lang.Console.print;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author y3tu
 * @date 2018/9/4
 */
public class Test {

    @org.junit.Test
    public void test() {
        List<String> list = Arrays.asList("java", "scala", "python", "shell", "ruby");
        long num = list.parallelStream().filter(x -> x.length() < 5).count();
        print(num);

        new File(".").listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return false;
            }
        });
    }

    @org.junit.Test
    public void test1() {
        List<String> str = Arrays.asList("a", "b", "A", "B");
        str.forEach(System.out::println);
        str.stream().filter(d -> d.equalsIgnoreCase("a")).sorted(String::compareToIgnoreCase).forEach(System.out::print);
        str.stream().collect(groupingBy(String::length));
    }

    @org.junit.Test
    public void code() throws IOException {
        GenUtils.startGeneratorCode("sys_user",null, DsFactoryEnum.Druid);
    }
}

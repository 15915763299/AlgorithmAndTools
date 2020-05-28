package com.demo.design;

/**
 * @author 尉涛
 * @date 2020-03-23 09:33
 **/
public class Utils {

    public static void main(String[] args) {
        int i = Integer.MAX_VALUE + 1;
        System.out.println(Integer.toBinaryString(i));
        i = i - 1;
        System.out.println(Integer.toBinaryString(i));
    }

}

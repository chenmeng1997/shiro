package com.cm.shirotest.utils;

import java.util.Random;

/**
 * @author 陈萌
 * @Date 2021/12/7 0007 23:24
 * @ProjectName shiro-test
 */
public class GeneratorUtil {

    private static Random random = new Random();
    private static final char ALPHA = 'a';
    private static final char NUMBER = '0';
    private static final char UPPERCASEALPHA = 'A';
    private static final int ALPHABOUND = 26;
    private static final int NUMBERBOUND = 10;
    private static final int RANDOMTYPEBOUND = 3;

    public static String randomSequence(int size) {
        StringBuilder sequence = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sequence.append(getRandomValue());
        }
        return sequence.toString();
    }

    private static char getRandomValue() {
        int index = random.nextInt(RANDOMTYPEBOUND);
        if (index == 0) {
            return randomAlpha();
        } else if (index == 1) {
            return randomNumber();
        } else {
            return randomUpperAlpha();
        }
    }

    private static char randomAlpha() {
        return randomValue(ALPHA, ALPHABOUND);
    }

    private static char randomNumber() {
        return randomValue(NUMBER, NUMBERBOUND);
    }

    private static char randomUpperAlpha() {
        return randomValue(UPPERCASEALPHA, ALPHABOUND);
    }

    private static char randomValue(int start, int bound) {
        int index = random.nextInt(bound);
        return (char) (start + index);
    }

    /*    public static void main(String[] args) {
        System.out.println(randomSequence(6));
    }
    */

}

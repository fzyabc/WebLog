package com.fzy.weblog.admin.utils;

import java.util.Random;

public class TestCode {
    public static void main(String[] args) {
        String code=randomCode(4);
        System.out.println("验证码:"+code);
    }

    public static String randomCode(int number) {
        //1.定义字符数组，存储的是验证码
        char[] chs = new char[62];
        int index = 0;
        for (char ch = '0'; ch <= '9'; ch++) {
            chs[index] = ch;
            index++;
        }
        for (char ch = 'a'; ch <= 'z'; ch++) {
            chs[index] = ch;
            index++;
        }
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            chs[index] = ch;
            index++;
        }

        //随机生成验证码
        String code="";//记录最终的验证码
        Random random = new Random();
        for (int i = 0; i < number; i++){
            //生成随机索引
            int randomIndex=random.nextInt(chs.length);
code+=chs[randomIndex];
        }
        return code;
    }
}
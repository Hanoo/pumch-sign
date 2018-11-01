package cn.pumch.core.util;

import java.util.Random;

/**
 *生成随机验证码，供忘记密码、修改密码使用
 */
public class CodeUtils {
    public static String generateCode(){

        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for(int i=0; i<6; i++){
            int valueItme = random.nextInt(10);
            builder.append(valueItme);
        }
        return builder.toString();
    }
}

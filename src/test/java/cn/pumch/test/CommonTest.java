package cn.pumch.test;

import cn.pumch.web.util.AESEncrypUtil;
import cn.pumch.web.util.CommonUtils;
import cn.pumch.web.util.ParseSystemUtil;
import cn.pumch.web.util.QRCodeUtil;
import net.sf.json.JSONArray;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommonTest {
    @Test
    public void qrCodeTest() {
        String text = "http://www.baidu.com"; // 随机生成验证码
        String basePath = "/var/pumchSign/qrCode/";
        File directory = new File(basePath);
        if(!directory.exists()) {
            directory.mkdir();
        }
        System.out.println("随机码： " + text);
        int width = 100; // 二维码图片的宽
        int height = 100; // 二维码图片的高
        String format = "png"; // 二维码图片的格式

        try {
            // 生成二维码图片，并返回图片路径
            String pathName = QRCodeUtil.generateQRCode(text, width, height, format, basePath+"qrCode.png");
            System.out.println("生成二维码的图片路径： " + pathName);

            String content = QRCodeUtil.parseQRCode(pathName);
            System.out.println("解析出二维码的图片的内容为： " + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEncrypt() {
        String passwod = "pumch2018";
        String content = "gyy:"+passwod.hashCode();

        System.out.println("加密之前：" + content);
        // 加密
        //如果想要加密内容不显示乱码，可以先将密文转换为16进制
        String hexStrResult = AESEncrypUtil.encrypt(content);
        System.out.println("16进制的密文："  + hexStrResult);

//        如果的到的是16进制密文，别忘了先转为2进制再解密
        byte[] twoStrResult = ParseSystemUtil.parseHexStr2Byte(hexStrResult);

        // 解密
        byte[] decrypt = AESEncrypUtil.decrypt(twoStrResult);
        System.out.println("解密后的内容：" + new String(decrypt));
    }

    @Test
    public void testJSONArray() {
        List<Integer> list = new ArrayList<>();
        list.add(111);
        list.add(222);
        list.add(333);

        System.out.println(JSONArray.fromObject(list).toString());

        Integer[] integers = new Integer[3];
        JSONArray.fromObject(list).toArray(integers);

        for(Integer i : integers) {
            System.out.println(i);
        }
    }

    @Test
    public void pinyinTest() {

        String input = "阿里巴巴";


        System.out.println(CommonUtils.pinyinTrans(input));
    }
}

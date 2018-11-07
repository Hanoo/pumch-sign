package cn.pumch.test;


import cn.pumch.web.util.QRCodeUtil;
import org.junit.Test;

public class CommonTest {
    @Test
    public void qrCodeTest() {
        String text = "http://www.baidu.com"; // 随机生成验证码
        System.out.println("随机码： " + text);
        int width = 100; // 二维码图片的宽
        int height = 100; // 二维码图片的高
        String format = "png"; // 二维码图片的格式

        try {
            // 生成二维码图片，并返回图片路径
            String pathName = QRCodeUtil.generateQRCode(text, width, height, format, "D:/new.png");
            System.out.println("生成二维码的图片路径： " + pathName);

            String content = QRCodeUtil.parseQRCode(pathName);
            System.out.println("解析出二维码的图片的内容为： " + content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

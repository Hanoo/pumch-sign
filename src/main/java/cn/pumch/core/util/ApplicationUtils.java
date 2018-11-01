package cn.pumch.core.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.net.util.Base64;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * ApplicationUtils : 程序工具类，提供大量的便捷方法
 *
 */
public class ApplicationUtils {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(ApplicationUtils.class);
    public static final String separator = System.getProperty("file.separator");

    private String uploadFileBasePath;
    /**
     * 产生一个36个字符的UUID
     *
     * @return UUID
     */
    public static String randomUUID() {
        String oUUID = UUID.randomUUID().toString();
        return oUUID.replace("-","");
    }

    /**
     * md5加密
     *
     * @param value 要加密的值
     * @return md5加密后的值
     */
    public static String md5Hex(String value) {
        return DigestUtils.md5Hex(value);
    }

    /**
     * sha1加密
     *
     * @param value 要加密的值
     * @return sha1加密后的值
     */
    public static String sha1Hex(String value) {
        return DigestUtils.sha1Hex(value);
    }

    /**
     * sha256加密
     *
     * @param value 要加密的值
     * @return sha256加密后的值
     */
    public static String sha256Hex(String value) {
        return DigestUtils.sha256Hex(value);
    }

    /**
     * 将源对象的非空域值赋给目标对象
     * @param sourceBean 源对象
     * @param targetBean 目标对象
     * @throws IllegalAccessException
     */
    public static void merge(Object sourceBean, Object targetBean) {
        Class sourceBeanClass = sourceBean.getClass();
        Class targetBeanClass = targetBean.getClass();

        Field[] sourceFields = sourceBeanClass.getDeclaredFields();
        Field[] targetFields = targetBeanClass.getDeclaredFields();
        if(!sourceBeanClass.getSimpleName().equals(targetBeanClass.getSimpleName())
                || sourceFields.length!=targetFields.length) {
            throw new IllegalArgumentException();
        }

        try {
            for(int i=0; i<sourceFields.length; i++){
                Field sourceField = sourceFields[i];
                Field targetField = targetFields[i];
                sourceField.setAccessible(true);
                targetField.setAccessible(true);
                if (!(sourceField.get(sourceBean) == null)
                        && !"serialVersionUID".equals(sourceField.getName())
                        && !Modifier.isStatic(sourceField.getModifiers())) {
                    targetField.set(targetBean, sourceField.get(sourceBean));
                }
            }
        } catch (IllegalAccessException ie) {
            logger.error("合并实体失败", ie);
        }
    }

    public static void exportFile(Map data, String attachmentName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");

        // 提示：在调用工具类生成Word文档之前应当检查所有字段是否完整
        // 否则Freemarker的模板引擎在处理时可能会因为找不到值而报错 这里暂时忽略这个步骤了
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;
        try {
            // 调用工具类WordGenerator的createDoc方法生成Word文档
            file = MSWordUtil.createDoc(data, "0".equals(data.get("hasCooperated")));// 没有合作历史的生成全部文档
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");

            String agent = request.getHeader("USER-AGENT");
//            String attachmentName = data.get("eName").toString()+".doc";
            String fileName;
            if(null!=agent && agent.toLowerCase().indexOf("firefox") > 0) { //解决firefox下导出文件中文名乱码的问题
                fileName= "=?UTF-8?B?" + (new String(Base64.encodeBase64(attachmentName.getBytes("UTF-8")))) + "?=";
            } else {
                fileName = URLEncoder.encode(attachmentName, "UTF-8");
            }
            response.addHeader("Content-Disposition", "attachment;filename="+fileName);

            out = response.getOutputStream();
            byte[] buffer = new byte[512];  // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } finally {
            if(fin != null) fin.close();
            if(out != null) out.close();
            if(file != null) file.delete(); // 删除临时文件
        }
    }

    public static boolean isMobileAgent(String agent){
        String[] mobile_agents = {"Android", "iPhone", "SymbianOS", "Windows Phone", "iPad", "iPod"};
        for(String mAgent : mobile_agents) {
            if(agent.indexOf(mAgent)>0) {
                return true;
            }
        }
        return false;
    }

    /*
     * 返回长度为【strLength】的随机数，在前面补0
     */
    public static String getFixLenthString(int strLength) {
        double pross = (1 + new Random().nextDouble()) * Math.pow(10, strLength);
        String fixLenthString = String.valueOf(pross);
        return fixLenthString.substring(2, strLength + 1);
    }

    /**
     * 生成上传文件保存的路径
     * @param basePath 基本路径
     * @param appId
     * @param eName
     * @return
     */
    public static String getUploadFilePath(String basePath, String appId, String eName) {
        StringBuffer dirPath = new StringBuffer(basePath);
        dirPath.append(separator).append("upload").append(separator);
        dirPath.append(appId).append(separator);
        dirPath.append(eName).append(separator);
        return dirPath.toString();
    }

    /**
     * 生成上传文件保存的路径
     * @param appId
     * @param eName
     * @return
     */
    public static String getUploadFilePath(String appId, String eName) {
        StringBuffer dirPath = new StringBuffer();
        dirPath.append(separator).append("upload").append(separator);
        dirPath.append(appId).append(separator);
        dirPath.append(eName).append(separator);
        return dirPath.toString();
    }

    public String getUploadFileBasePath() {
        return uploadFileBasePath;
    }

    public void setUploadFileBasePath(String uploadFileBasePath) {
        this.uploadFileBasePath = uploadFileBasePath;
    }

    public boolean isLocalDevEnv() {
        return this.uploadFileBasePath.equals("local");
    }
}

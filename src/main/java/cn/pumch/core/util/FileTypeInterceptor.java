package cn.pumch.core.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 上传文件
 * Created by a on 2017/7/13.
 */
public class FileTypeInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(FileTypeInterceptor.class);

    public static String fileUpload(MultipartFile[] multipartFile, String dirPath) throws Exception {
        if (multipartFile[0].getOriginalFilename() == "") {
            return "fileIsNull";
        }
        for (MultipartFile image : multipartFile) {
            //获取上传文件的原始名称
            String originalFilename = image.getOriginalFilename();
            if(!checkFile(originalFilename)){
                return "errorType";
            }
        }
        for (MultipartFile image : multipartFile) {
            //获取上传文件的原始名称
            String originalFilename = image.getOriginalFilename();
            File file = new File(dirPath);
            //如果保存文件的地址不存在，就先创建目录
            if (!file.exists()) {
                file.mkdirs();
            }
            //使用UUID重新命名上传的文件名称（看公司需求，也可以用日期时间）
//                String newFilename= UUID.randomUUID()+originalFilename.substring(originalFilename.lastIndexOf("."));
            try {
                //使用MultipartFile接口的方法完成文件上传到指定位置
                image.transferTo(new File(dirPath + originalFilename));
            } catch (Exception e) {
                logger.debug("文件上传失败，未知错误。");
                e.printStackTrace();
                return "error1";
            }
        }
        return "success";
    }

    /**
     * 判断是否为允许的上传文件类型,true表示允许
     * 改为前台判断，暂时不用
     */
    private static boolean checkFile(String fileName) {
        //设置允许上传文件类型
        //String suffixList = "zip,rar,7z";
        String suffixList = "gif,jpg,jpeg,png,GIF,JPG,PNG,pdf,zip,avi,wmv,mpeg,mp4,mov,mkv,flv,f4v,m4v,rmvb,rm,3gp,dat,ts,mts,vob";

        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            return true;
        }
        return false;
    }

}

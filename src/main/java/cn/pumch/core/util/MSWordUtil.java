package cn.pumch.core.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

public class MSWordUtil {
    private static Configuration configuration = null;

    public static File createDoc(Map<String, Object> dataMap, boolean fullVersion) throws IOException {
        if(null==configuration) {
            init();
        }

        File f = new File("application");
        try {
            Template t = configuration.getTemplate(fullVersion?"wwfTemplateFull.ftl":"wwfTemplate.ftl");
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
            Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
            t.process(dataMap, w);
            w.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return f;
    }

    private static void init() throws IOException {
        configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding("utf-8");
        configuration.setClassForTemplateLoading(MSWordUtil.class, "/template");
    }
}

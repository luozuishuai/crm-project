package com.shangma.cn.common.untils;

import com.shangma.cn.common.container.SpringBeanUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class TemplateUtils {

    public static String getTemplateStr(String templateName,Map<String,Object> map){
        try {
            FreeMarkerConfigurer freeMarkerConfigurer = SpringBeanUtils.getBean(FreeMarkerConfigurer.class);
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            Template template = configuration.getTemplate(templateName, "utf-8");
            StringWriter writer = new StringWriter();
            template.process(map,writer);
            writer.flush();
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

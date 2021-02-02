package com.shangma.cn.common.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.common.http.AxiosStatus;
import com.shangma.cn.exception.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@PropertySource("classpath:aliyun.properties")
public class UploadService {

    @Value("${aliyun_key}")
    public String aliyun_key;

    @Value("${aliyun_secret}")
    public String aliyun_secret;

    @Value("${aliyun_bucketName}")
    public String aliyun_bucketName;

    @Value("${aliyun_baseUrl}")
    public String aliyun_baseUrl;

    @Value("${aliyun_endpoint}")
    public String aliyun_endpoint;

    @Value("${file_ext}")
    public List<String> file_ext;

    public String uploadFileByFormDataToAliyun(String fileName, InputStream in) throws IOException {
        //先判断是不是一个图片
        byte[] buffer = new byte[in.available()];
        in.read(buffer);
        BufferedImage read = ImageIO.read(new ByteArrayInputStream(buffer));
        if(read != null){
            //是个图片文件
            //再判断文件后缀是不是jpg或png
            if(file_ext.contains(StringUtils.getFilenameExtension(fileName))){
                //是jpg或png
                //再判断文件大小是否超过200kb
                if(buffer.length/1024<200){
                    //不超过200kb
                    OSS ossClient = new OSSClientBuilder().build(aliyun_endpoint, aliyun_key, aliyun_secret);
                    //第一个参数表示bucket名称
                    //第二个参数表示文件名称 带后缀
                    //第三个参数表示文件输入流
                    ossClient.putObject(aliyun_bucketName,fileName,new ByteArrayInputStream(buffer));
                    //关闭oss流
                    ossClient.shutdown();
                    return aliyun_baseUrl + fileName;
                }else{
                    //超过200kb
                    throw new FileUploadException(AxiosStatus.FILEUPLOAD_FILE_MAX);
                }
            }else{
                //不是jpg或png
                throw new FileUploadException(AxiosStatus.FILEUPLOAD_EXT_ERROR);
            }
        }else{
            //不是图片
            throw new FileUploadException(AxiosStatus.FILEUPLOAD_NOT_IMAGE);
        }




    }

}

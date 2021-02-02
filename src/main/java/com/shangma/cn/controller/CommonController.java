package com.shangma.cn.controller;

import com.shangma.cn.common.cache.RedisCacheUtils;
import com.shangma.cn.common.constant.RedisKeyConstant;
import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.common.http.AxiosStatus;
import com.shangma.cn.common.service.TokenService;
import com.shangma.cn.common.service.UploadService;
import com.shangma.cn.common.untils.CaptchaCacheUtils;
import com.shangma.cn.common.untils.ServletUtils;
import com.shangma.cn.dto.LoginFormDTO;
import com.shangma.cn.entity.Admin;
import com.shangma.cn.service.AdminService;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("common")
public class CommonController {

    @Autowired
    private UploadService uploadService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenService tokenService;


    @PostMapping("upload")
    public AxiosResult<String> upload(@RequestPart Part file) throws Exception {

        //配置文件名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + StringUtils.getFilenameExtension(file.getSubmittedFileName());
        //上传到阿里云
        String s = uploadService.uploadFileByFormDataToAliyun(fileName, file.getInputStream());
        //返回成功提示和图片链接
        return AxiosResult.success(s);
    }

    /**
     * 生成验证码
     * @return
     */
    @GetMapping("getCode")
    public AxiosResult<String> getCode(String uuid){
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        String verCode = specCaptcha.text().toLowerCase();
//        String key = UUID.randomUUID().toString();
//        System.out.println("生成的验证码值为：" + verCode);
//        // 存入redis key是前端的uuid value是验证码的值
        CaptchaCacheUtils.setCaptcha(uuid,verCode);
//        redisUtil.setEx(key, verCode, 30, TimeUnit.MINUTES);
        // 将key和base64返回给前端
        return AxiosResult.success(specCaptcha.toBase64());
    }

    /**
     * 登录
     */
    @PostMapping("login")
    public AxiosResult<String> login(@RequestBody @Valid LoginFormDTO loginForm){
        //从redis中获取验证码

//        String code = redisTemplate.opsForValue().get(loginForm.getUuid());
        String code = CaptchaCacheUtils.getCaptcha(loginForm.getUuid());
//        System.out.println("Redis数据库中验证码的值为：" + code);
//        System.out.println("前端用户验证码输入框的值为：" + loginForm.getCaptcha());
//        判断验证码是否正确
        if(!loginForm.getCaptcha().equalsIgnoreCase(code)){
            //验证码错误
            return AxiosResult.error(AxiosStatus.CAPTCHA_ERROR);
        }
        //去数据库查询用户名
        Admin admin = adminService.existAdminAccount(loginForm.getUserName());
        if(admin == null){
            //用户名不存在
            return AxiosResult.error(AxiosStatus.ADMINACCOUNT_ERROR);
        }
        //验证密码是否正确
        boolean matches = bCryptPasswordEncoder.matches(loginForm.getPassword(), admin.getAdminPassword());
        if(!matches){
            //密码错误
            return AxiosResult.error(AxiosStatus.PASSWORD_ERROR);
        }

//        //登录成功,存入session
//        ServletUtils.setSession("loginUser",admin);

//        //登录成功，存入token
//        String token = tokenService.creatToken(admin);

        //登录成功，存入redis缓存
        String token = tokenService.creatTokenAndCacheLoginAdmin(admin);

        return AxiosResult.success(token);
    }
}

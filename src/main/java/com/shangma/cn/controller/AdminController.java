package com.shangma.cn.controller;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.shangma.cn.common.http.AxiosResult;
import com.shangma.cn.common.pool.AsyncFactory;
import com.shangma.cn.common.pool.AsyncManager;
import com.shangma.cn.common.service.TokenService;
import com.shangma.cn.common.untils.ReflectionUtils;
import com.shangma.cn.common.untils.ServletUtils;
import com.shangma.cn.common.untils.TemplateUtils;
import com.shangma.cn.controller.base.BaseController;
import com.shangma.cn.entity.Admin;
import com.shangma.cn.entity.Role;
import com.shangma.cn.service.AdminService;
import com.shangma.cn.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("admin")
public class AdminController extends BaseController {


    @Autowired
    private AdminService adminService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * 分页查询
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping
    public AxiosResult<PageVo<Admin>> findPage(
            @RequestParam(defaultValue = "1") int currentPage,
            @RequestParam(defaultValue = "5") int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        PageVo<Admin> pageVo = adminService.findAll();
        return AxiosResult.success(pageVo);
    }

    /**
     * 通过id查询
     */
    @GetMapping("{id}")
    public AxiosResult<Admin> findById(@PathVariable("id") Long id) {
        return AxiosResult.success(adminService.findById(id));
    }

    /**
     * 通过id查询员工的角色信息
     */
    @GetMapping("{id}/roles")
    public AxiosResult<List<Role>> findAdminRoles(@PathVariable("id") Long id) {
        List<Role> list = adminService.findAdminRoles(id);
        return AxiosResult.success(list);
    }

    /**
     * 新增数据
     */
    @PostMapping
    public AxiosResult insertEntity(@RequestBody @Valid Admin entity) {
        //初始化密码
        entity.setAdminPassword(bCryptPasswordEncoder.encode("123456"));
        //添加到数据库
//        int i = adminService.addEntity(entity);
        //添加员工信息和角色信息到数据库
        System.out.println(entity);
        int i = adminService.addAdminAndRoles(entity);
        //将数据对象转为map
        Map<String, Object> map = ReflectionUtils.obj2Map(entity);
        //将map数据渲染到页面模板上
        String templateStr = TemplateUtils.getTemplateStr("mail.ftlh", map);
        //发送邮件
        AsyncManager.getInstance().execute(AsyncFactory.sendHtmlMail("新员工入职邮件", entity.getAdminEmail(), templateStr));
        //返回response结果
        return toAxios(i);
    }

    /**
     * 修改数据
     */
    @PutMapping
    public AxiosResult<Void> updateEntity(@RequestBody @Valid Admin entity) {
        int i = adminService.updateAdminAndRole(entity);
        return toAxios(i);
    }


    /**
     * 批量删除数据
     */
    @DeleteMapping("{ids}")
    public AxiosResult<Void> deleteEntity(@PathVariable("ids") List<Long> ids) {
        int i = adminService.batchDeleteByIds(ids);
        return toAxios(i);
    }

    /**
     * 批量导出
     */
    @GetMapping("excelExport")
    public ResponseEntity<byte[]> excelExport() throws UnsupportedEncodingException {
        List<Admin> adminList = adminService.getAllData();

        adminList.forEach(item -> {
            try {
                item.setUrl(new URL(item.getAdminAvatar()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        EasyExcel.write(out, Admin.class).sheet("员工信息").doWrite(adminList);
        byte[] bytes = out.toByteArray();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachment", URLEncoder.encode("员工信息表.xlsx", "utf-8"));
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, httpHeaders, HttpStatus.OK);
        return responseEntity;

    }

    /**
     * 删除用户角色信息
     */
    @DeleteMapping("{adminId}/role/{roleId}")
    public AxiosResult<Void> deleteAdminRole(@PathVariable Long adminId, @PathVariable Long roleId) {
        int row = adminService.deleteAdminRole(adminId, roleId);
        return toAxios(row);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("getAdminInfo")
    public AxiosResult<Map<String, Object>> getAdminInfo() {
        Map<String, Object> map = adminService.getAdminInfo();
        return AxiosResult.success(map);
    }

}

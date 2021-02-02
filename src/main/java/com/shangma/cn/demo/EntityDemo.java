package com.shangma.cn.demo;

import com.shangma.cn.entity.Admin;
import com.shangma.cn.mapper.AdminMapper;
import com.shangma.cn.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntityDemo {

    @Autowired
    private static AdminService adminService;


    public static void main(String[] args) {
        List<Admin> allData = adminService.getAllData();
        System.out.println(allData.size());
        allData.forEach(item -> {
            System.out.println(item);
        });
    }
}

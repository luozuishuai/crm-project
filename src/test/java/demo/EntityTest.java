package demo;

import com.shangma.cn.entity.Admin;
import com.shangma.cn.mapper.AdminMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntityTest {

    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void test1(){
        List<Admin> adminList = adminMapper.selectByExample(null);
        adminList.forEach(item -> {
            System.out.println(item);
        });
    }
}

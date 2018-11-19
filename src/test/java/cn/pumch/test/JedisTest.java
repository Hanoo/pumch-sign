package cn.pumch.test;

import cn.pumch.web.redis.JedisWrap;
import cn.pumch.web.util.CommonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class JedisTest {


    @Resource
    private JedisWrap jedisService;


    @Test
    public void testSet() {
        jedisService.setString("Test_key","Test_value");
    }

    @Test
    public void testGet() {
        System.out.println(jedisService.getString("Test_key"));
    }

    @Test
    public void testBuildOneSet() {
        String key = "sign:20181116";
        String[] values = new String[50];
        for(int i=0;i<50;i++) {
            values[i] = CommonUtils.getUUId();
        }
        Long result = jedisService.addSetEle(key, values);
        System.out.println("Set build with a result:" + result);
        jedisService.setExpire(key, 86400);
    }

    @Test
    public void getOneSet() {
        String key = "sign:20181116";
        Set<String> set = jedisService.getSetMembers(key);
        for(String value :set) {
            System.out.println(value);
        }
    }
}
package cn.pumch.test;

import cn.pumch.web.model.PsUser;
import cn.pumch.web.service.PsUserService;
import cn.pumch.web.util.CommonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class ServiceTest {

    @Autowired
    PsUserService userService;

    @Test
    public void getUserTest() {
        String loginName = "admin";
        PsUser user = userService.getUserByLoginName(loginName);
        System.out.println("User admin's nickName is" + user.getNickName());
    }

    @Test
    public void batchRegTest() {
        int quantity = 100000;
        char[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        Random random = new Random();
        for(int i=0;i<quantity;i++) {
            int nameLength = random.nextInt(4)+10;
            String loginName = "";
            for(int j=0;j<nameLength;j++) {
                int offSet = random.nextInt(25);
                loginName += letters[offSet];
            }
            PsUser user = new PsUser();
            user.setLoginName(loginName);
            user.setPassword("abcedef");
            user.setNickName(loginName);
            user.setIdNo(CommonUtils.getUUId().substring(0,10));
            user.setuState("1");
            user.setCreateTime(new Date());

            try {
                if(userService.doReg(user)) {
                    System.out.println("No."+i+" user " + loginName + " create successfully.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package cn.pumch.web.service.impl;

import cn.pumch.web.dao.SignInMapper;
import cn.pumch.web.model.SignIn;
import cn.pumch.web.service.SignInService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SignInServiceImpl implements SignInService {

    @Autowired
    private SignInMapper signInMapper;

    @Override
    public SignIn getPrettyInfoById(Long signInId) {
        return signInMapper.selectFullSignIn(signInId);
    }

    @Override
    public List<SignIn> getTSignInListInPage(int page, int pageSize, Long tId,
                                          String courseName, String sName) {
        int start;
        if(page<1) {
            page = 1;
        }
        if(pageSize<10) {
            pageSize = 10;
        }
        start = (page -1) * pageSize;

        if(StringUtils.isNotEmpty(courseName)) {
            courseName = "%" + courseName + "%";
        }

        if(StringUtils.isNotEmpty(sName)) {
            sName = "%" + sName + "%";
        }

        return signInMapper.selectByTIdInPage(start, pageSize, tId, courseName, sName);
    }

    @Override
    public int getTSignInCount(Long tId, String courseName, String sName) {
        if(StringUtils.isNotEmpty(courseName)) {
            courseName = "%" + courseName + "%";
        }

        if(StringUtils.isNotEmpty(sName)) {
            sName = "%" + sName + "%";
        }

        return signInMapper.selectCountByTId(tId, courseName, sName);
    }

    @Override
    public List<SignIn> getSSignInListInPage(int page, int pageSize, Long signerId, String courseName, String sName) {
        int start;
        if(page<1) {
            page = 1;
        }
        if(pageSize<10) {
            pageSize = 10;
        }
        start = (page -1) * pageSize;

        if(StringUtils.isNotEmpty(courseName)) {
            courseName = "%" + courseName + "%";
        }

        if(StringUtils.isNotEmpty(sName)) {
            sName = "%" + sName + "%";
        }
        return signInMapper.selectBySIdInPage(start, pageSize, signerId, courseName, sName);
    }

    @Override
    public int getSSignInCount(Long signerId, String courseName, String sName) {
        if(StringUtils.isNotEmpty(courseName)) {
            courseName = "%" + courseName + "%";
        }

        if(StringUtils.isNotEmpty(sName)) {
            sName = "%" + sName + "%";
        }
        return signInMapper.selectCountBySId(signerId, courseName, sName);
    }

    @Override
    public boolean doSignIn(Long signerId, Long courseId) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 6);
        Date startTime = today.getTime();
        today.set(Calendar.HOUR_OF_DAY,21);
        Date endTime = today.getTime();
        List<SignIn> signIns = this.getSignInListByDate(signerId, courseId, startTime, endTime);
        if(signIns.size()>0) {//已经签到成功直接返回成功
            return true;
        } else {
            SignIn signIn = new SignIn();
            signIn.setSignerId(signerId);
            signIn.setCourseId(courseId);
            signIn.setSignInTime(new Date());

            return signInMapper.insertSelective(signIn)>0;
        }
    }

    @Override
    public List<SignIn> getSignInListByDate(Long signerId, Long courseId, Date startTime, Date endTime) {
        if(null==startTime || null==endTime) {
            return null;
        }
        return signInMapper.selectByDate(signerId, courseId, startTime, endTime);
    }

    @Override
    public boolean doScore(Long signInId, Integer[] scores) {
        if(signInId==0 || scores.length!=8) {
            return false;
        }
        SignIn signIn = new SignIn();
        signIn.setId(signInId);
        signIn.setScore1(scores[0]);
        signIn.setScore2(scores[1]);
        signIn.setScore3(scores[2]);
        signIn.setScore4(scores[3]);
        signIn.setScore5(scores[4]);
        signIn.setScore6(scores[5]);
        signIn.setScore7(scores[6]);
        signIn.setScore8(scores[7]);
        signIn.setScoreTime(new Date());

        return signInMapper.updateByPrimaryKeySelective(signIn)>0;
    }

    @Override
    public List<SignIn> getSignInListInPage(int page, int pageSize, String courseName, String sName, Date startTime, Date endTime) {
        int start;
        if(page<1) {
            page = 1;
        }
        if(pageSize<10) {
            pageSize = 10;
        }
        start = (page -1) * pageSize;

        if(StringUtils.isNotEmpty(courseName)) {
            courseName = "%" + courseName + "%";
        }

        if(StringUtils.isNotEmpty(sName)) {
            sName = "%" + sName + "%";
        }
        return signInMapper.selectByConditionsInPage(start, pageSize, courseName, sName, startTime, endTime);
    }

    @Override
    public int getSignInCount(String courseName, String sName, Date startTime, Date endTime) {
        if(StringUtils.isNotEmpty(courseName)) {
            courseName = "%" + courseName + "%";
        }

        if(StringUtils.isNotEmpty(sName)) {
            sName = "%" + sName + "%";
        }
        return signInMapper.selectCountByConditions(courseName, sName, startTime, endTime);
    }

    @Override
    public HSSFWorkbook buildWorkbook(String courseName, Date startTime, Date endTime) {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = workbook.createSheet("sheet1");

        HSSFRow row = hssfSheet.createRow(0);
        HSSFCellStyle hssfCellStyle = workbook.createCellStyle();
        hssfSheet.setColumnWidth(3, 15*256);
        hssfSheet.setColumnWidth(4, 15*256);
        hssfSheet.setColumnWidth(5, 30*256);
        hssfSheet.setColumnWidth(6, 30*256);
        hssfSheet.setColumnWidth(7, 30*256);
        hssfSheet.setColumnWidth(8, 30*256);
        hssfSheet.setColumnWidth(9, 30*256);
        hssfSheet.setColumnWidth(11, 30*256);
        hssfSheet.setColumnWidth(12, 40*256);
        hssfSheet.setColumnWidth(13, 40*256);

        //居中样式
        hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        String[] titles = { "课程名称", "任课教师", "学生姓名", "签到时间", "填写时间", "1、课程总体质量", "2、课前对授课内容的掌握程度", "3、课后对授课内容的掌握程度",
                "4、课程对临床工作的帮助", "5、您觉得教师准备是否充分(不充分到充分1-5分)", "6、教师准备教材PPT是否重点突出，安排得当",
                "7、教师讲课的语音、语调、语速适中，讲课生动，容易理解", "8、我愿意参加该讲师主讲的课程"};
        HSSFCell hssfCell = null;
        for (int i = 0; i < titles.length; i++) {
            hssfCell = row.createCell(i);//列索引从0开始
            hssfCell.setCellValue(titles[i]);//列名1
            hssfCell.setCellStyle(hssfCellStyle);//列居中显示
        }

        List<SignIn> dataList = this.getSignInListInPage(0, 1000, courseName, null, startTime, endTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < dataList.size(); i++) {
            row = hssfSheet.createRow(i+1);
            row.createCell(0).setCellValue(dataList.get(i).getCourseName());
            row.createCell(1).setCellValue(dataList.get(i).gettName());
            row.createCell(2).setCellValue(dataList.get(i).getNickName());
            row.createCell(3).setCellValue(sdf.format(dataList.get(i).getSignInTime()));
            if(null!=dataList.get(i).getScoreTime()) {
                row.createCell(4).setCellValue(sdf.format(dataList.get(i).getScoreTime()));

                if(null!=dataList.get(i).getScore1()) {
                    row.createCell(5).setCellValue(dataList.get(i).getScore1());
                }
                if(null!=dataList.get(i).getScore2()) {
                    row.createCell(6).setCellValue(dataList.get(i).getScore2());
                }
                if(null!=dataList.get(i).getScore3()) {
                    row.createCell(7).setCellValue(dataList.get(i).getScore3());
                }
                if(null!=dataList.get(i).getScore4()) {
                    row.createCell(8).setCellValue(dataList.get(i).getScore4());
                }
                if(null!=dataList.get(i).getScore5()) {
                    row.createCell(9).setCellValue(dataList.get(i).getScore5());
                }
                if(null!=dataList.get(i).getScore6()) {
                    row.createCell(10).setCellValue(dataList.get(i).getScore6());
                }
                if(null!=dataList.get(i).getScore7()) {
                    row.createCell(11).setCellValue(dataList.get(i).getScore7());
                }
                if(null!=dataList.get(i).getScore8()) {
                    row.createCell(12).setCellValue(dataList.get(i).getScore8());
                }
            }
        }

        return workbook;
    }
}

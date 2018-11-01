package cn.pumch.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 学生页面专属分发器
 */
@Controller
@RequestMapping("/s")
public class SController {
    private final static Logger logger = LoggerFactory.getLogger(SController.class);
}

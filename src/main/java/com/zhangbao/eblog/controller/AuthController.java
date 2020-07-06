package com.zhangbao.eblog.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.google.code.kaptcha.Producer;
import com.zhangbao.eblog.common.lang.Result;
import com.zhangbao.eblog.entity.User;
import com.zhangbao.eblog.service.UserService;
import com.zhangbao.eblog.shiro.EasyTypeToken;
import com.zhangbao.eblog.util.ValidationUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class AuthController extends BaseController {

    private static final String CAPtCHA_KEY = "captcha_key";

    @Autowired
    Producer producer;
    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(){
        return "auth/login";
    }
    @PostMapping("/login")
    @ResponseBody
    public Result doLogin(String email,String password,boolean remember){

        if(StrUtil.isEmpty(email)||StrUtil.isEmpty(password)){
            return Result.fail("用户名或密码不能为空");
        }

        EasyTypeToken token = new EasyTypeToken(email, SecureUtil.md5(password));
        Subject subject = SecurityUtils.getSubject();

        try {
            // 判断是否自动登录
            if (remember) {
                token.setRememberMe(true);
            } else {
                token.setRememberMe(false);
            }
            subject.login(token);
        }catch (Exception e){
            return Result.fail(e.getMessage());
        }

        return Result.succ().action("/");
    }


    @GetMapping("/register")
    public String reg(){
        return "auth/reg";
    }

    @ResponseBody
    @PostMapping("/register")
    public Result doRegister(User user,String repass,String vercode){
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(user);
        if(validResult.hasErrors()){
            return Result.fail(validResult.getErrors());
        }
        if(!repass.equals(user.getPassword())){
            return Result.fail("两次输入密码不相同");
        }

        String captcha = (String) req.getSession().getAttribute(CAPtCHA_KEY);
        System.out.println(captcha);
        if(vercode==null || !vercode.equalsIgnoreCase(captcha)){
            return Result.fail("验证码输入不正确");
        }

        Result result = userService.register(user);

        return result.action("/login");
    }

    @GetMapping("/user/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:/";
    }

    @GetMapping("/captcha.jpg")
    public void capche(HttpServletResponse response) throws IOException {
        String text = producer.createText();
        req.getSession().setAttribute(CAPtCHA_KEY,text);
        BufferedImage image = producer.createImage(text);
        response.setHeader("Cache-Control","no-store,no-cache");
        response.setContentType("image/jpeg");
        OutputStream out = response.getOutputStream();
        ImageIO.write(image,"jpg",out);
    }

}

package edu.cn.controller;
import edu.cn.redis.UserKey;
import edu.cn.result.CodeMsg;
import edu.cn.result.Message;
import edu.cn.service.MsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.omg.CORBA.StringHolder;
import org.slf4j.Logger;
import edu.cn.redis.RedisService;
import edu.cn.result.Result;
import edu.cn.vo.LoginVo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
@RequestMapping("/login")
@Api(value = "登录接口",tags = {"登录相关业务controller"})
public class LoginController {

    private static Logger log= LoggerFactory.getLogger(LoginController.class);
    @Autowired
    RedisService redisService;
    @Autowired
    MsService msService;
    @ApiOperation(value = "登录界面")
    @RequestMapping("/to_login")

    public String toLogin(){
        return "login";
    }
    @ApiOperation(value = "手机登录界面")
    @RequestMapping("/tz")
    public String tz() {
        return "mobile";
    }
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){
        log.info(loginVo.toString());
        //登录
        msService.login(response, loginVo);
        return Result.success(true);
    }
    @RequestMapping("/do_authcode")
    @ApiOperation(value = "获取验证码存入redis")
    @ResponseBody
    public Result<Boolean> authcode_get(LoginVo loginVo) {
        String mobile=loginVo.getMobile();
        String password = "1" + RandomStringUtils.randomNumeric(5);//生成随机数,我发现生成5位随机数时，如果开头为0，发送的短信只有4位，这里开头加个1，保证短信的正确性
        redisService.set(UserKey.getBymobile,mobile, password);//将验证码存入缓存
        Message.messagePost(mobile, password);//发送短息
        return Result.success(true);
    }
    @ApiOperation(value = "判断手机登录信息是否正确")
    @RequestMapping("/authcode_login")
    @ResponseBody
    public Result<Boolean>  authcode_login(LoginVo loginVo) {
        String mobile=loginVo.getMobile();
        String password=loginVo.getPassword();
        String s1=redisService.get(UserKey.getBymobile,mobile,String.class);
        System.out.println(s1);
        if (s1.equals(password)) {
            return Result.success(true);
        } else {
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }

    }

}

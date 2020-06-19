package edu.cn.controller;

import edu.cn.pojo.MsUser;
import edu.cn.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("/user")
public class TestController {
    @RequestMapping("/info")
    @ResponseBody
    public Result<MsUser> info(Model model, MsUser user){
        return Result.success(user);
    }
}

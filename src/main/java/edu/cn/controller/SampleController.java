package edu.cn.controller;


import edu.cn.pojo.User;
import edu.cn.redis.RedisService;
import edu.cn.redis.UserKey;
import edu.cn.result.Result;
import edu.cn.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/demo")
public class SampleController {
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name","Joshua");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User user =userService.getById(1);
        return Result.success(user);
       /* return Result.success(userService.getById(1));*/
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        userService.tx();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
        User user = redisService.get(UserKey.getById, ""+1, User.class);  //单一的键名容易重复被覆盖，应该拼装为更复杂的键，比如加前缀，键id变为UserKey:id1
        return Result.success(user);
    }


    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user = new User();
        user.setId(1);
        user.setName("111");
        redisService.set(UserKey.getById, ""+1, user);   //键id变为UserKey:id1
        return Result.success(true);
    }


}

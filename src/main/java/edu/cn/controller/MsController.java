package edu.cn.controller;

import edu.cn.pojo.MiaoshaOrder;
import edu.cn.pojo.MsUser;
import edu.cn.pojo.Orderinfo;
import edu.cn.result.CodeMsg;
import edu.cn.result.Result;
import edu.cn.service.GoodsService;
import edu.cn.service.MsProductService;
import edu.cn.service.OrderService;
import edu.cn.vo.GoodsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/miaosha")
@Api(value = "秒杀商品接口",tags = {"秒杀商品的相关业务controller"})
public class MsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    MsProductService msProductService;
    @Autowired
    OrderService orderService;
    @ApiOperation(value = "秒杀活动详情")
    @RequestMapping(value="/do_miaosha",method = RequestMethod.POST)
    @ResponseBody
    public Result<Orderinfo> doMiaosha(Model model, MsUser user, @RequestParam("goodsId")long goodsId){
        model.addAttribute("user", user);
        if (user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //判断商品库存
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getStockCount();
        if (stock <= 0){
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (miaoshaOrder != null){
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return  Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //进行秒杀
        Orderinfo orderInfo = msProductService.miaosha(user, goodsVo);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goodsVo", goodsVo);
        return  Result.success(orderInfo);
    }
}

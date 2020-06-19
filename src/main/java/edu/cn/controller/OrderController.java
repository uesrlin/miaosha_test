package edu.cn.controller;

import edu.cn.pojo.MsUser;
import edu.cn.pojo.Orderinfo;
import edu.cn.result.CodeMsg;
import edu.cn.result.Result;
import edu.cn.service.GoodsService;
import edu.cn.service.OrderService;
import edu.cn.vo.GoodsVo;
import edu.cn.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> detail(MsUser msUser, @RequestParam("orderId") long orderId){
        if (msUser == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        Orderinfo orderinfo = orderService.getOrderById(orderId);
        if (orderinfo == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = orderinfo.getGoodsId();
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo orderDetailVo = new OrderDetailVo();
        orderDetailVo.setOrderInfo(orderinfo);
        orderDetailVo.setGoodsVo(goodsVo);
        return Result.success(orderDetailVo);
    }


}

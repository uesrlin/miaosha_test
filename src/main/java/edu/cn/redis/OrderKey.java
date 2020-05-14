package edu.cn.redis;

import org.springframework.core.annotation.Order;

public class OrderKey extends BasePrefix {
    public OrderKey(String prefix) {
        super(prefix);
    }
    public OrderKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
}

package edu.cn.service;

import edu.cn.dao.UserDao;
import edu.cn.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserDao userDao;
    public User getById(int id){
        return userDao.getById(id);
    }

    @Transactional    //注释掉后报错，但是仍然可以插入数据库
    public Boolean tx(){
        User user1 = new User();
        user1.setId(2);
        user1.setName("2222");
        userDao.insert(user1);

        User user2 = new User();
        user2.setId(1);
        user2.setName("1111");
        userDao.insert(user2);

        return true;
    }
}

package edu.cn.dao;

import edu.cn.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
@Mapper

public interface UserDao {
    @Select("select * from User where id=#{id}")
    public  User getById(@Param("id") int id);
    @Insert("insert into User(id,name) values(#{id}, #{name})")
    public int insert(User user);
}

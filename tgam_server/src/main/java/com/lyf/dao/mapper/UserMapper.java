package com.lyf.dao.mapper;

import com.lyf.dao.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @AUTHOR lyf
 * @DATE 2020-12-10
 * @VERSION tgam1.0
 * @TYPE
 */


@Repository
public interface UserMapper {

    // 1.query
    // 查询所有 (用于展现)
    List<User> queryAll();
    // 查询ByID, 返回单个User,若为空则为空
    User queryByUserId(Long userId);// ID唯一（注册）
    // 查询ByName
    User queryByUserName(String userName);// 用户名不能重，realName可以重

    // 2.insert
    // 用于注册
    // 插入一个用户
    boolean insertOne(User user);
    // 批量插入（Excel导入插入）
    boolean insertBatchUser(List<User> list);

    // 3.update
    // 更新用户的某个或者多个信息(必须拥有ID)
    boolean updateUserInfo(User user);

    // 4.delete 删除成功返回true，删除失败返回false
    boolean deleteById(Long userId);

    boolean deleteByUserName(String userName);


}

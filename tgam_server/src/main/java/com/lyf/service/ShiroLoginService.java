package com.lyf.service;

import com.lyf.dao.realm.User;

public interface ShiroLoginService {
    public User getUserByName(String name);

}

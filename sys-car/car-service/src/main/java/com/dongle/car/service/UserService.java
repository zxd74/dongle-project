package com.dongle.car.service;

import com.dongle.car.model.UserModel;

/**
 * 用户管理服务：用户管理，密码更改，用户信息更改,用户车辆信息维护
 */
public interface UserService {
    
    UserModel queryModel(UserModel user);
    void update(UserModel user);

}

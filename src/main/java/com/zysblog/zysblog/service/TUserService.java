package com.zysblog.zysblog.service;

import com.zysblog.zysblog.dto.request.RegisterAdminRequest;
import com.zysblog.zysblog.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zy
 * @since 2024-11-20
 */
public interface TUserService extends IService<TUser> {
    public String userLogin(TUser tUser);

    void registerUser(RegisterAdminRequest tUserRequest);
}

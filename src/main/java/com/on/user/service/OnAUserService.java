package com.on.user.service;

import com.on.user.entity.OnAUser;

public interface OnAUserService {

    OnAUser findByName(String userCode);

    OnAUser saveUserShip(OnAUser user);

}

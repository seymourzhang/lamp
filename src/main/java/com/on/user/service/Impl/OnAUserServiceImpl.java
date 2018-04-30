package com.on.user.service.Impl;

import com.on.user.entity.OnAUser;
import com.on.user.entity.OnAUserRole;
import com.on.user.repository.OnAUserRepository;
import com.on.user.service.OnAUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OnAUserServiceImpl implements OnAUserService {

    @Autowired
    private OnAUserRepository onAUserRepository;

    public OnAUser findByName(String userCode){
        return onAUserRepository.verifyUser(userCode);
    }

    @Transactional
    public OnAUser saveUserShip(OnAUser user) {
        OnAUser newUser = onAUserRepository.save(user);
        Long userId = newUser.getId();
        OnAUserRole userRole = new OnAUserRole();
        userRole.setUserId(userId);
        userRole.setUserCode(user.getUserCode());
        return newUser;
    }
}

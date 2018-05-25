package com.on.mygirl.service.Impl;

import com.on.mygirl.Entity.MyGirl;
import com.on.mygirl.repository.MyGirlReposity;
import com.on.mygirl.service.MyGirlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyGirlServiceImpl implements MyGirlService {

    @Autowired
    private MyGirlReposity myGirlReposity;

    public MyGirl save(MyGirl myGirl) {
        return myGirlReposity.save(myGirl);
    }

    public List<MyGirl> queryAll() {
        return myGirlReposity.findAll();
    }

    public int deleteById(String id) {
        return myGirlReposity.deleteAllById(id);
    }

    public List<MyGirl> findByUserId(Long userId) {
        return myGirlReposity.findByUserId(userId);
    }
}

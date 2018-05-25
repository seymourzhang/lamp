package com.on.mygirl.service;

import com.on.mygirl.Entity.MyGirl;

import java.util.List;

public interface MyGirlService {

    MyGirl save(MyGirl myGirl);

    List<MyGirl> queryAll();

    int deleteById(String id);

    List<MyGirl> findByUserId(Long userId);

}

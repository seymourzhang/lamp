package com.on.GWData.service.Impl;

import com.on.GWData.Entity.OnAGWData;
import com.on.GWData.repository.OnADealDataRepository;
import com.on.GWData.repository.OnAGWDataRepository;
import com.on.GWData.service.DeviceService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.result.ResultSetOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.ParameterMode;
import java.sql.ResultSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by seymour on 05/10/2018.
 */
@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private OnAGWDataRepository onAGWDataRepository;

    private final ReadWriteLock lock=new ReentrantReadWriteLock();

    /**
     * 启动服务
     */
    public void start() {

    }

    @Transactional
    public OnAGWData insert(OnAGWData onAGWData) {
        return onAGWDataRepository.save(onAGWData);
    }

    /*@Override
    public String on_a_TransGWData() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        ProcedureCall procedureCall = session.createStoredProcedureCall("on_a_TransGWData");
        procedureCall.registerParameter(1, ResultSet.class, ParameterMode.OUT);
        ResultSetOutput rso = (ResultSetOutput) procedureCall.getOutputs().getCurrent();
        tx.commit();
        session.close();
        factory.close();
        return rso.toString();
    }*/

}

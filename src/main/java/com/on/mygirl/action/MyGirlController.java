package com.on.mygirl.action;

import com.alibaba.fastjson.JSONObject;
import com.on.GWData.Entity.OnADealData;
import com.on.mygirl.Entity.MyGirl;
import com.on.mygirl.service.MyGirlService;
import com.on.user.entity.OnAUser;
import com.on.util.Entity.OnACode;
import com.on.util.action.BaseAction;
import com.on.util.common.PageData;
import com.on.util.service.OnACodeService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@SpringBootApplication
@RequestMapping("/my")
public class MyGirlController extends BaseAction {

    @Autowired
    private MyGirlService myGirlService;

    @Autowired
    private OnACodeService onACodeService;

    @RequestMapping("/saveMyGirl")
    public void saveMyGirl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        JSONObject jsonObject = new JSONObject();

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        OnAUser sessionUser = (OnAUser) session.getAttribute("user");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MyGirl myGirl = new MyGirl();
        if (!pd.get("id").toString().equals("")) {
            myGirl.setId(Long.parseLong(pd.get("id").toString()));
        }
        myGirl.setUserId(sessionUser.getId());
        myGirl.setName(pd.get("name").toString());
        myGirl.setPhoneNo(pd.get("phoneNo").toString());
        myGirl.setAddress(pd.get("address").toString());
        myGirl.setVarietyId(pd.get("varietyId").toString());
        myGirl.setVarietyName(pd.get("varietyName").toString());
        myGirl.setPrice(Double.parseDouble(pd.get("price").toString()));
        myGirl.setTransCast(Double.parseDouble(pd.get("transCast").toString()));
        myGirl.setPackageCast(Double.parseDouble(pd.get("packageCast").toString()));
        myGirl.setIncubatorCast(Double.parseDouble(pd.get("incubatorCast").toString()));
        myGirl.setTotalCast(Double.parseDouble(pd.get("totalCast").toString()));
        myGirl.setNum(Integer.parseInt(pd.get("num").toString()));
        myGirl.setActualIn(Double.parseDouble(pd.get("actualIn").toString()));
        myGirl.setSubPrice(Double.parseDouble(pd.get("subPrice").toString()));
        myGirl.setTransNo(pd.get("transNo").toString());
        myGirl.setOriginPlace(pd.get("originPlace").toString());
        myGirl.setAcceptDate(sdf.parse(pd.get("acceptDate").toString()));
        myGirl.setDeliverDate(sdf.parse(pd.get("deliverDate").toString()));
        myGirl.setTips(pd.get("tips").toString());
        myGirl.setCreateDateTime(new Date());
        myGirl.setModifyDateTime(new Date());
        MyGirl upMygirl = myGirlService.save(myGirl);
        jsonObject.put("Result", "Success");
        jsonObject.put("Error", "");
        jsonObject.put("data", upMygirl);
        super.writeJson(jsonObject, response);
    }

    @RequestMapping("/queryMyGirl")
    public void queryMyGirl(HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();

        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        OnAUser sessionUser = (OnAUser) session.getAttribute("user");
        Long userId = sessionUser.getId();
        List<MyGirl> lmg = myGirlService.findByUserId(userId);
        JSONObject json = new JSONObject();
        json.put("Result", "Success");
        json.put("Error", "");
        json.put("data", lmg);
        super.writeJson(json, response);
    }

    @RequestMapping("/deleteMyGirl")
    public void deleteMyGirl(HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        int deleteNum = myGirlService.deleteById(pd.get("id").toString());
        JSONObject json = new JSONObject();
        json.put("Result", "Success");
        json.put("Error", "");
        json.put("data", deleteNum);
        super.writeJson(json, response);
    }

    @RequestMapping("/queryCherryCode")
    public void queryCherryCode(HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        List<OnACode> codes = onACodeService.findByCodeType("cherry_type");
        JSONObject json = new JSONObject();
        json.put("Result", "Success");
        json.put("Error", "");
        json.put("data", codes);
        super.writeJson(json, response);
    }
}

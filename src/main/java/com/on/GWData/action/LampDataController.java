package com.on.GWData.action;

import com.alibaba.fastjson.JSONObject;
import com.on.GWData.Entity.OnADealData;
import com.on.GWData.Entity.OnAGWData;
import com.on.GWData.service.OnAGwDataService;
import com.on.util.action.BaseAction;
import com.on.util.common.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@SpringBootApplication
@RequestMapping("/show")
public class LampDataController extends BaseAction {

    @Autowired
    private OnAGwDataService onAGwDataService;

    @RequestMapping("/lampData")
    public void lampData(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageData pd = this.getPageData();
        JSONObject jsonObject = new JSONObject();
        List<OnADealData> lpd = onAGwDataService.findByOrder();
        jsonObject.put("Result", "Success");
        jsonObject.put("Error", "");
        jsonObject.put("data", lpd);
        super.writeJson(jsonObject, response);
    }
}

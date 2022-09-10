package com.pt.vx.service;

import cn.hutool.json.JSONUtil;
import com.pt.vx.domain.DataInfo;
import com.pt.vx.domain.VxMessageDto;
import com.pt.vx.utils.DateUtil;
import com.pt.vx.utils.VxUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MessageService {


    /**
     * 这里是每天9点发送消息。
     * 对应秒、分、时、天、月、星期。
     *
     * 当前模板为：可以复制到你的模板里面
     *
     * 	{{userName.DATA}}，
     * 	今天是我们在一起的{{holdDay.DATA}}天
     * 	你的生日还有{{yourBirthDay.DATA}}天
     * 	我的生日还有{{myBirthDay.DATA}}天
     * 	距离我们的下一次纪念还有{{loveDay.DATA}}天
     *
     * 	最后，开心每一天！
     */
    @Scheduled(cron = "0 0 9 * * ?")
    public void sendMessage(){
        VxMessageDto dto = new VxMessageDto();
        dto.setTemplate_id("你的模板ID");
        dto.setTouser("用户ID");
        HashMap<String, DataInfo> map = new HashMap<>();
        setMap(map,"userName","名字","#FFCCCC");
        setMap(map,"holdDay", DateUtil.passDay(2020,7,8),"#FFCCCC");
        setMap(map,"yourBirthDay",DateUtil.getNextBirthDay(8,11),"#FFCCCC");
        setMap(map,"myBirthDay", DateUtil.getNextChineseBirthDay(2,15),"#FFCCCC");
        setMap(map,"loveDay",DateUtil.getNextBirthDay(7,8),"#FFCCCC");
        dto.setData(map);
        String message = JSONUtil.toJsonStr(dto);
        VxUtil.sendMessage(message);
    }


    /**
     *
     * @param key 模板的每项key
     * @param value 展示的内容
     * @param color 展示的颜色
     */
    private void setMap(HashMap<String, DataInfo> map,String key,String value,String color){
        DataInfo dataInfo=new DataInfo();
        dataInfo.setColor(color);
        dataInfo.setValue(value);
        map.put(key,dataInfo);
    }


}

package com.knowology.schedule;

import com.alibaba.fastjson.JSONObject;
import com.knowology.entity.ActiveMQContaint;
import com.knowology.model.ShortMsgDetail;
import com.knowology.model.ShortMsgSend;
import com.knowology.provider.Provider;
import com.knowology.service.ShortMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class DealShortMsgTask {

    private static final Logger logger = LoggerFactory.getLogger(DealShortMsgTask.class);
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private Provider provider;

    @Value("${task.shortMsgJobDetail.num}")
    private Integer dealNum;

    //@Scheduled(cron = "0/2 * * * * ?")
//    public void execute() {
//        List<ShortMsgDetail> list = shortMsgService.selectShortMsgByDealStatus("未下发",dealNum);
//        for(ShortMsgDetail shortMsgDetail : list){
//            ShortMsgSend shortMsgSend = shortMsgService.selectShortMsgSendById(shortMsgDetail.getShortmsgId());
//            provider.sendMessage(ActiveMQContaint.QUEUE_SMS,packShortMsg(shortMsgDetail.getTelenum(),shortMsgSend.getContent(),shortMsgDetail.getUuid()));
//            shortMsgDetail.setDealStatus("已下发");
//            shortMsgService.updateShortMsgDetail(shortMsgDetail);
//        }
//    }
    /**
     * 根据短信模板和电话详细信息生成具体短信内容
     * @param phoneMum
     * @param msgTemplate
     * @return
     */
    private String packShortMsg(String phoneMum, String msgTemplate, String uuid) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid" , uuid);
        jsonObject.put("phoneNum",phoneMum);
        jsonObject.put("msgContent",msgTemplate);
        logger.info("【短信下发】" + jsonObject.toJSONString());
        return jsonObject.toJSONString();
    }
}
package com.knowology.async.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.knowology.model.PassiveNumDetail;
import com.knowology.model.ShortMsgDetail;
import com.knowology.model.ShortMsgSend;
import com.knowology.service.PassiveNumService;
import com.knowology.service.ShortMsgService;
import com.knowology.util.DBDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : Conan
 * @Description 异步发送短信
 * @date : 2019-04-30 14:37
 **/
@Service
public class ShortMsgAsyncService {
    @Autowired
    private ShortMsgService shortMsgService;
    @Autowired
    private PassiveNumService passiveNumService;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    @Async("asyncPoolTaskExecutor")
    public void sendMsg(String memberName,ShortMsgSend entity) {

        List<PassiveNumDetail> passiveNumDetails = new ArrayList<>();
        List<String> telenumNames = DBDataUtil.arrayListTransformArray(entity.getTelenumNameList());
        if(telenumNames != null && telenumNames.size() > 0){
            for(int i=0;i<telenumNames.size();i++){
                List<PassiveNumDetail> passiveNumDetailsList = passiveNumService.selectByTelenumName(telenumNames.get(i));
                passiveNumDetails.addAll(passiveNumDetailsList);
            }
        }
        int shortMsgId = shortMsgService.insertShortMsg(entity,memberName);

        //短信任务的所有号码详情
        List<ShortMsgDetail> smsList = new ArrayList<ShortMsgDetail>();
        for (PassiveNumDetail p: passiveNumDetails) {
            if (p.getPhoneNum() == null) {
                continue;
            }
            ShortMsgDetail shortMsgDetail = new ShortMsgDetail();
            shortMsgDetail.setShortmsgId(shortMsgId);
            shortMsgDetail.setSendTime(new Date());
            shortMsgDetail.setTelenum(p.getPhoneNum());
            shortMsgDetail.setUuid(p.getId().toString());
            shortMsgDetail.setDealStatus("未下发");
            smsList.add(shortMsgDetail);
//            provider.sendMessage(ActiveMQContaint.QUEUE_SMS,packShortMsg(p,entity.getContent(),p.getId()));
        }
        if (null != smsList) {
            shortMsgService.insertBatchShortMsgDetail(smsList);
        }
    }

    /**
     * 根据短信模板和电话详细信息生成具体短信内容
     * @param passiveNumDetail
     * @param msgTemplate
     * @return
     */
    @SuppressWarnings("unused")
	private String packShortMsg(PassiveNumDetail passiveNumDetail, String msgTemplate,Integer uuid) {
        if (passiveNumDetail.getCreateTime() == null) {
            passiveNumDetail.setCreateTime(new Date());
        }
        if (passiveNumDetail.getBussinessType() == null) {
            passiveNumDetail.setBussinessType("");
        }
        if (passiveNumDetail.getBusinessHallName() == null) {
            passiveNumDetail.setBusinessHallName("");
        }
        if (passiveNumDetail.getClientName() == null) {
            passiveNumDetail.setClientName("");
        }

        String msg = msgTemplate.replace("[姓名]", passiveNumDetail.getClientName())
                .replace("[订单日期]", sdf.format(passiveNumDetail.getCreateTime()))
                .replace("[营业厅名称]", passiveNumDetail.getBusinessHallName())
                .replace("[业务名称]", passiveNumDetail.getBussinessType());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid","000" + uuid);
        jsonObject.put("phoneNum",passiveNumDetail.getPhoneNum());
        jsonObject.put("msgContent",msg);
        return jsonObject.toJSONString();
    }
}

package com.knowology.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.knowology.dao.ShortMsgDetailMapper;
import com.knowology.dao.ShortMsgMapper;
import com.knowology.dao.ShortMsgSendMapper;
import com.knowology.model.ShortMsgModel;
import com.knowology.model.ShortMsgDetail;
import com.knowology.model.ShortMsgSend;
import com.knowology.service.ShortMsgService;
import com.knowology.util.DBDataUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 短信模板实现类
 * @author xullent
 */
@Transactional(rollbackFor = Exception.class)
@Service
        public class ShortMsgServiceImpl implements ShortMsgService {

    @Resource
    private ShortMsgMapper shortMsgMapper;
    @Resource
    private ShortMsgSendMapper shortMsgSendMapper;
    @Resource
    private ShortMsgDetailMapper shortMsgDetailMapper;

    @Override
    public PageInfo<ShortMsgModel> list(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ShortMsgModel> passiveNums = shortMsgMapper.selectShortMsgModelList();
        PageInfo<ShortMsgModel> info = new PageInfo<>(passiveNums);
        return info;
    }

    @Override
    public int delete(Long id) {
        return shortMsgMapper.deleteShortMsg(id);
    }

    @Override
    public int addShortMsg(ShortMsgModel shortMsgModel) {
        return shortMsgMapper.insert(shortMsgModel);
    }

    @Override
    public List<String> shortMsgNameList() {
        Example example = new Example(ShortMsgModel.class);
        example.selectProperties("shortmsgName");
        List<ShortMsgModel> shortMsgModels = shortMsgMapper.selectByExample(example);
        return shortMsgModels.stream().map(ShortMsgModel::getShortmsgName).collect(Collectors.toList());
    }

    @Override
    public int insertShortMsg(ShortMsgSend entity, String memberName) {
        entity.setCreator(memberName);
        entity.setCreateTime(new Date());
        String telenumNames = DBDataUtil.arrayTransformString(entity.getTelenumNameList());
        entity.setTelenumName(telenumNames);
//        if( telenumNames != null && telenumNames.size() > 0){
//            for(int i=0;i < telenumNames.size();i++){
//                entity.setTelenumName(telenumNames.get(i));
//                shortMsgSendMapper.insertShortMsgSend(entity);
//            }
//        }else{
//            shortMsgSendMapper.insertShortMsgSend(entity);
//        }
        shortMsgSendMapper.insertShortMsgSend(entity);
        //返回主键
        return entity.getId();
    }

    @Override
    public void insertBatchShortMsgDetail(List<ShortMsgDetail> list) {
        shortMsgDetailMapper.insertBatchShortMsgDetail(list);
    }

    @Override
    public PageInfo<ShortMsgSend> select(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ShortMsgSend> shortMsgs = shortMsgSendMapper.selectShortMsgSend();
        PageInfo<ShortMsgSend> info = new PageInfo<>(shortMsgs);
        return info;
    }

    @Override
    public PageInfo<ShortMsgDetail> selectDetail(Integer pageNum, Integer pageSize, Long id) {
        PageHelper.startPage(pageNum, pageSize);
        List<ShortMsgDetail> shortMsgDetails = shortMsgDetailMapper.selectShortMsgDetailByShortMsgId(id);
        DBDataUtil.desensitizationShortMsgDetail(shortMsgDetails);
        PageInfo<ShortMsgDetail> info = new PageInfo<>(shortMsgDetails);
        return info;
    }

    @Override
    public ShortMsgSend selectShortMsgSendById(Integer id) {
        return shortMsgSendMapper.selectShortMsgSendById(id);
    }

    @Override
    public List<ShortMsgDetail> selectShortMsgDetailById(Long id) {
        return shortMsgDetailMapper.selectShortMsgDetailByShortMsgId(id);
    }

    @Override
    public List<ShortMsgDetail> selectShortMsgByDealStatus(String dealStatus,Integer dealNum) {
        return shortMsgDetailMapper.selectShortMsgByDealStatus(dealStatus,dealNum);
    }

    @Override
    public void updateShortMsgDetail(ShortMsgDetail shortMsgDetail) {
        Example example = new Example(ShortMsgDetail.class);
        example.createCriteria().andEqualTo("id",shortMsgDetail.getId());
        shortMsgDetailMapper.updateByExampleSelective(shortMsgDetail,example);
    }


}

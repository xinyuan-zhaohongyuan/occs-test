package com.knowology.controller.scene;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.knowology.config.annotation.FullName;
import com.knowology.model.BlackListCall;
import com.knowology.model.ExampleData;
import com.knowology.service.BlackListCallService;
import com.knowology.service.ExampleDataService;
import com.knowology.service.SceneService;
import com.knowology.thread.TaskAsyncService;
import com.knowology.util.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @description:
 * @author: Conan
 * @create: 2019-03-07 17:08
 **/
@RestController()
@RequestMapping("example")
@Api(description = "外呼示例")
public class ExampleDataController {
    private static final Logger logger = LoggerFactory.getLogger(ExampleDataController.class);

    @Autowired
    private ExampleDataService exampleService;
    @Autowired
    private TaskAsyncService asyncService;
    @Autowired
    private SceneService sceneService;

    @Autowired
    private BlackListCallService blackListCallService;

    /**
     * @param userId     用户ID，客户端唯一标识，从token中获取,用于区分websocket客户端显示通话内容
     * @param fullName   用户姓名
     * @param passiveNum 被叫号码
     * @param scene      场景名称
     * @param mode       放音类型
     * @return
     */
    @PostMapping("/call")
    public Object call(Integer userId, @FullName String fullName, String passiveNum, String scene, String mode) {
        if(null == passiveNum || "".equals(passiveNum)){
            return ResponseUtil.fail("号码不能为空");
        }
        if(null == scene || "".equals(scene)){
            return ResponseUtil.fail("场景不能为空");
        }
        if(null == mode || "".equals(mode)){
            return ResponseUtil.fail("放音类型不能为空");
        }
        List<BlackListCall>  blackListCallList = blackListCallService.selectBlackList(passiveNum);
        logger.info("blackListCallList :" + blackListCallList);
        if(blackListCallList.size() > 0){
            return ResponseUtil.fail("该号码是黑名单号码");
        }
        ExampleData data = new ExampleData();
        data.setPassiveNum(passiveNum);
        data.setScene(scene);
        data.setMode(mode);
        data.setStartTime(new Date());
        data.setCreator(fullName);
        exampleService.insertExampleData(data);
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("outCallNum", passiveNum);
        params.put("vrSelected", scene);
        params.put("playWayOfRobet", mode);
//        params.put("uuid", UUID.randomUUID().toString().replace("-", ""));
        params.put("uuid", data.getId().toString());
        params.put("sid", userId.toString());
        // TODO: 2019/7/31 改成通过kafka消息
//        logger.info("外呼调用请求参数为:"+params.toString());
//        provider.sendMessage(ActiveMQContaint.QUEUE_EXAMPLE,params.toString());
        asyncService.callTask(params);
        return ResponseUtil.ok("任务启动");
    }

    @PostMapping("list")
    public Object list(@RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<ExampleData> info = exampleService.list(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>(4);
        map.put("total", info.getTotal());
        map.put("items", info.getList());
        return ResponseUtil.ok(map);
    }

    @ApiOperation(value = "外呼示例记录查询接口",
            httpMethod = "POST",
            response = ExampleData.class)
    @ApiImplicitParams({
            @ApiImplicitParam(required = true, name = "startTime", value = "开始时间"),
            @ApiImplicitParam(required = true, name = "endTime", value = "结束时间")
    })

    @PutMapping("/delete")
    public Object deleteById(String id) {
        if (id == null || "".equals(id)) {
            return ResponseUtil.fail("请勾选删除条目");
        }
        exampleService.delete(id);
        return ResponseUtil.ok("删除成功");
    }

    // TODO: 2019/7/31 接口需要和侯哥重新核对一下
    @PostMapping("resultBack")
    public Object resultBack(@RequestBody JSONObject jsonObject) {
        logger.info("回调接口接收的通话数据为:" + jsonObject.toJSONString());
        ExampleData data = JSONObject.parseObject(jsonObject.toJSONString(), ExampleData.class);
        if (data == null || StringUtils.isBlank(data.getId().toString())) {
            logger.error("回调接口解析通话内容异常,JSONObject为:" + jsonObject.toJSONString());
            return ResponseUtil.fail("解析通话内容异常");
        }
        exampleService.update(data);
        return ResponseUtil.ok();
    }

    /**
    *  加载场景列表
    */
    @PostMapping("listScenes")
    public Object listScenes() {
        List<String> scene = sceneService.listScene();
        return ResponseUtil.ok(scene);
    }
}

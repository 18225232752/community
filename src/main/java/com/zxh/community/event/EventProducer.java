package com.zxh.community.event;

import com.alibaba.fastjson.JSONObject;
import com.zxh.community.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/29 23:03
 */
@Component
public class EventProducer {

    @Resource(name = "kafkaTemplate")
    private KafkaTemplate kafkaTemplate;

    // 处理事件
    public void fireEvent(Event event) {
        // 将事件发送到指定的主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }
}

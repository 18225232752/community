package com.zxh.community.controller;

import com.zxh.community.entity.Event;
import com.zxh.community.event.EventProducer;
import com.zxh.community.util.CommunityConstant;
import com.zxh.community.util.CommunityUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/9/2 22:09
 */
@Controller
public class ShareController implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(ShareController.class);

    @Resource(name = "eventProducer")
    private EventProducer eventProducer;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${wk.image.storage}")
    private String wkImageStorage;

    @Value("${qiniu.bucket.share.url}")
    private String shareBucketUrl;

    @GetMapping("/share")
    @ResponseBody
    public String share(String htmlUrl) {
        // 文件名
        String fileName = CommunityUtil.generateUUID();

        // 异步生成长图
        Event event = new Event()
                .setTopic(TOPIC_SHARE)
                .setData("htmlUrl", htmlUrl)
                .setData("fileName", fileName)
                .setData("suffix", ".png");
        eventProducer.fireEvent(event);

        // 返回访问路径
        Map<String, Object> map = new HashMap<>();
        // map.put("shareUrl", domain + contextPath + "/share/image/" + fileName);
        map.put("shareUrl", shareBucketUrl + "/" + fileName + ".png");

        return CommunityUtil.getJSONString(0, null, map);
    }

    // 废弃
    // 获取长图
    @GetMapping("/share/image/{fileName}")
    public void getShareImage(@PathVariable String fileName, HttpServletResponse resp) {
        if (StringUtils.isBlank(fileName)) {
            throw new IllegalArgumentException("文件名不能为空！");
        }

        resp.setContentType("image/png");
        File file = new File(wkImageStorage + "/" + fileName + ".png");
        try {
            ServletOutputStream os = resp.getOutputStream();
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("获取长图失败：" + e.getMessage());
        }
    }

}

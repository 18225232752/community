package com.zxh.community.controller;

import com.zxh.community.annotation.LoginRequired;
import com.zxh.community.entity.User;
import com.zxh.community.service.FollowService;
import com.zxh.community.service.LikeService;
import com.zxh.community.service.UserService;
import com.zxh.community.util.CommunityConstant;
import com.zxh.community.util.CommunityUtil;
import com.zxh.community.util.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/23 19:29
 */
@Controller
@RequestMapping("/user")
public class UserController implements CommunityConstant {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "hostHolder")
    private HostHolder hostHolder;

    @Resource(name = "likeServiceImpl")
    private LikeService likeService;

    @Resource(name = "followServiceImpl")
    private FollowService followService;

    @LoginRequired
    @GetMapping("/setting")
    public String getSettingPage() {
        return "/site/setting";
    }


    @LoginRequired
    @PostMapping("/upload")
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片！");
            return "/site/setting";
        }

        String filename = headerImage.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            model.addAttribute("error", "文件格式不正确！");
            return "/site/setting";
        }

        // 生成随机文件名
        filename = CommunityUtil.generateUUID() + suffix;
        // 确定文件存放的路径
        File dest = null;
        try {
            dest = ResourceUtils.getFile(uploadPath + filename);
        } catch (FileNotFoundException e) {
            logger.error("路径错误：" + e.getMessage());
            throw new RuntimeException("路径错误：", e);
        }
        // File dest = new File(uploadPath + "/" + filename);
        try {
            // 存储文件
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("文件上传失败：" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常！", e);
        }

        // 更新当前用户的头像url
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/user/header/" + filename;
        userService.updateHeader(user.getId(), headerUrl);

        return "redirect:/index";
    }

    @GetMapping("/header/{fileName}")
    public void getHeader(@PathVariable String fileName, HttpServletResponse resp) {
        // 服务器存放路径
        fileName = uploadPath + "/" + fileName;
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 设置响应类型
        resp.setContentType("image/" + suffix);
        try (
                FileInputStream fis = new FileInputStream(fileName);
                OutputStream os = resp.getOutputStream()
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败：" + e.getMessage());
        }
    }

    @PostMapping("/updatePwd")
    public String updatePwd(String oldPwd, String newPwd, String repeat, Model model) {
        // 校验非空性
        if (StringUtils.isBlank(oldPwd)) {
            model.addAttribute("oldPwdMsg", "原密码不能为空！");
            return "/site/setting";
        }
        if (StringUtils.isBlank(newPwd)) {
            model.addAttribute("newPwdMsg", "新密码不能为空！");
            return "/site/setting";
        }
        if (StringUtils.isBlank(repeat)) {
            model.addAttribute("repeatMsg", "重复新密码不能为空！");
            return "/site/setting";
        }

        // 获取当前用户信息
        User user = hostHolder.getUser();

        // 校验oldPwd
        oldPwd = CommunityUtil.md5(oldPwd + user.getSalt());
        if (!oldPwd.equals(user.getPassword())) {
            model.addAttribute("oldPwdMsg", "原密码错误！");
            return "/site/setting";
        }

        if (!newPwd.equals(repeat)) {
            model.addAttribute("repeatMsg", "两次输入的密码不一致！");
            return "/site/setting";
        }

        newPwd = CommunityUtil.md5(newPwd + user.getSalt());
        // 校验newPwd
        if (newPwd.equals(oldPwd)) {
            model.addAttribute("newPwdMsg", "新密码不能与原密码相同！");
            return "/site/setting";
        }

        // 更新密码
        userService.updatePassword(user.getId(), newPwd);

        // 重定回index使得，拦截器获取更新密码后的用户信息
        return "redirect:/index";
    }

    // 个人主页
    @GetMapping("/profile/{userId}")
    public String getProfilePage(@PathVariable int userId, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在！");
        }

        // 用户
        model.addAttribute("user", user);
        // 点赞数量
        int likeCount = likeService.findUserLikeCount(userId);
        model.addAttribute("likeCount", likeCount);

        // 关注数量
        long followeeCount = followService.findFolloweeCount(userId, ENTITY_TYPE_USER);
        model.addAttribute("followeeCount", followeeCount);

        // 粉丝数量
        long followerCount = followService.findFollowerCount(ENTITY_TYPE_USER, userId);
        model.addAttribute("followerCount", followerCount);

        // 是否已关注
        boolean hasFollowed = false;
        if (hostHolder.getUser() != null) {
            hasFollowed = followService.hasFollowed(hostHolder.getUser().getId(), ENTITY_TYPE_USER, userId);
        }
        model.addAttribute("hasFollowed", hasFollowed);


        return "/site/profile";
    }
}

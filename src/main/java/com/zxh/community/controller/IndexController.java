package com.zxh.community.controller;

import com.zxh.community.entity.DiscussPost;
import com.zxh.community.entity.Page;
import com.zxh.community.entity.User;
import com.zxh.community.service.DiscussPostService;
import com.zxh.community.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author taehyang
 * @date 2023/8/21 18:50
 */
@Controller
public class IndexController {

    @Resource(name = "discussPostServiceImpl")
    private DiscussPostService discussPostService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @GetMapping("/index")
    public String index(Model model, Page page) {
        // SpringMVC会自动实例化Model与Page，并将Page注入Model
        // 获取记录总行数
        page.setRows(discussPostService.findDiscussPostRows(0));
        // 设置分页查询路径
        page.setPath("/index");

        List<DiscussPost> discussPostList = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (discussPostList != null) {
            for (DiscussPost post : discussPostList) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.findUserById(post.getUserId());
                map.put("user", user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        return "/index";
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "/error/500";
    }
}

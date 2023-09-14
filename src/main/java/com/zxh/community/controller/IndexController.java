package com.zxh.community.controller;

import com.zxh.community.entity.DiscussPost;
import com.zxh.community.entity.Page;
import com.zxh.community.entity.User;
import com.zxh.community.service.DiscussPostService;
import com.zxh.community.service.LikeService;
import com.zxh.community.service.UserService;
import com.zxh.community.util.CommunityConstant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class IndexController implements CommunityConstant {

    @Resource(name = "discussPostServiceImpl")
    private DiscussPostService discussPostService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "likeServiceImpl")
    private LikeService likeService;

    @GetMapping("/")
    public String root() {
        return "forward:/index";
    }

    @GetMapping("/index")
    public String index(Model model, Page page,
                        @RequestParam(defaultValue = "0") int orderMode) {
        // SpringMVC会自动实例化Model与Page，并将Page注入Model
        // 获取记录总行数
        page.setRows(discussPostService.findDiscussPostRows(0));
        // 设置分页查询路径
        page.setPath("/index?orderMode=" + orderMode);

        List<DiscussPost> discussPostList = discussPostService
                .findDiscussPosts(0, page.getOffset(), page.getLimit(), orderMode);
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (discussPostList != null) {
            for (DiscussPost post : discussPostList) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.findUserById(post.getUserId());
                map.put("user", user);

                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId());
                map.put("likeCount", likeCount);

                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        model.addAttribute("orderMode", orderMode);
        return "index";
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "error/500";
    }

    // 拒绝访问(未获得授权)
    @GetMapping("/denied")
    public String getDeniedPage() {
        return "error/404";
    }
}

package com.zhangbao.eblog.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangbao.eblog.common.lang.Result;
import com.zhangbao.eblog.common.lang.ResultData;
import com.zhangbao.eblog.entity.*;
import com.zhangbao.eblog.service.UserMessageService;
import com.zhangbao.eblog.util.UploadUtil;
import com.zhangbao.eblog.vo.PostVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    UserMessageService userMessageService;

    /**
     * 我的主页
     * @return
     */
    @GetMapping("/home")
    public String home(Long id){

        Map<String ,Object> map = new HashMap<>();
        if(id==null){
            map.put("userId",id);
        }else {
            map.put("userId",getUserId());
        }
//        map.put("gtCreated",DateUtil.offsetDay(new Date(),-30));
        IPage<PostVo> page = postService.pagingByMap(null, map);
        req.setAttribute("user",getUser());
        req.setAttribute("posts",page.getRecords());

        return "user/home";
    }

    /**
     * 基本设置
     * @return
     */
    @GetMapping("/set")
    public String set(){
        req.setAttribute("user",getUser());
        return "user/set";
    }

    @ResponseBody
    @PostMapping("/set")
    public Result doSet(User user){

        //上传头像
        if(StrUtil.isNotEmpty(user.getAvatar())){
            User temp = userService.getById(getUserId());
            temp.setAvatar(user.getAvatar());
            userService.updateById(temp);
            User shiroUser = getUser();
            shiroUser.setAvatar(user.getAvatar());
            SecurityUtils.getSubject().getSession().setAttribute("profile",temp);
            return Result.succ().action("/user/set#avatar");
        }

        if(StrUtil.isEmpty(user.getUsername())){
            return Result.fail("昵称不能为空");
        }
        int count = userService.count(new QueryWrapper<User>().lambda().eq(User::getId, getUserId()).ne(User::getId, getUserId()));
        if(count>0){
            return  Result.fail("昵称已被占用");
        }

        User temp = userService.getById(getUserId());
        temp.setUsername(user.getUsername());
        temp.setSign(user.getSign());
        temp.setGender(user.getGender());
        userService.updateById(temp);
        User shiroUser = getUser();
        shiroUser.setUsername(user.getUsername());
        shiroUser.setSign(user.getSign());
        shiroUser.setGender(user.getGender());

        SecurityUtils.getSubject().getSession().setAttribute("profile",temp);
        return Result.succ().action("/user/set#info");
    }

    /**
     * 我的消息
     * @return
     */
    @GetMapping("/message")
    public String message(){

        IPage<UserMessageVo> iPage = userMessageService.paging(getPage(),new QueryWrapper<UserMessage>()
                .lambda().eq(UserMessage::getToUserId,getUserId())
                .eq(UserMessage::getDelFlag,0)
                .orderByDesc(UserMessage::getCreated)
        );

        List<Long> ids = new ArrayList<>();
        for (UserMessageVo record : iPage.getRecords()) {
            if(record.getStatus()==0){
                ids.add(record.getId());
            }
        }
        userMessageService.updateToReaded(ids);

        req.setAttribute("pageData",iPage);

        return "user/message";
    }

    /**
     * 删除消息
     * @param id
     * @param all
     * @return
     */
    @ResponseBody
    @PostMapping("/message/remove/")
    public Result remove(Long id,@RequestParam(defaultValue = "false") Boolean all){

        boolean remove = userMessageService.remove(new QueryWrapper<UserMessage>()
                .lambda().eq(UserMessage::getToUserId, getUserId())
                .eq(!all, UserMessage::getId, id)
        );
        return remove ? Result.succ():Result.fail("删除失败");

    }

    /**
     * 未读信息提醒
     */
    @ResponseBody
    @PostMapping("/message/nums")
    public ResultData nums(){

        int count = userMessageService.count(new QueryWrapper<UserMessage>()
                .lambda().eq(UserMessage::getToUserId, getUserId())
                .eq(UserMessage::getStatus,0)
                .eq(UserMessage::getDelFlag, 0)
        );
        return ResultData.ok().put("count",count);

    }



    /**
     * 用户中心
     * @return
     */
    @GetMapping("/index")
    public String index(){

        //我发布总数
        int postTotal = postService.count(new QueryWrapper<Post>().lambda().eq(Post::getUserId, getUserId()));
        req.setAttribute("postTotal",postTotal);
        //我收藏总数
        int collectTotal = userCollectionService.count(new QueryWrapper<UserCollection>().lambda().eq(UserCollection::getUserId, getUserId()));
        req.setAttribute("collectTotal",collectTotal);
        return "user/index";
    }

    @ResponseBody
    @GetMapping("/public")
    public Result userPublic(){

        Page page = postService.page(getPage(), new QueryWrapper<Post>()
                .lambda().eq(Post::getUserId, getUserId())
                .orderByDesc(Post::getCreated)
        );

        return Result.succ(page);
    }

    @ResponseBody
    @GetMapping("/collection")
    public Result userCollection(){

        Page page = postService.page(getPage(), new QueryWrapper<Post>()
                .inSql("id","select post_id from m_user_collection where user_id ="+getUserId())
        );

        return Result.succ(page);
    }

    @ResponseBody
    @PostMapping("/repass")
    public Result repass(String nowpass, String pass, String repass) {
        if(!pass.equals(repass)) {
            return Result.fail("两次密码不相同");
        }

        User user = userService.getById(getUserId());

        String nowPassMd5 = SecureUtil.md5(nowpass);
        if(!nowPassMd5.equals(user.getPassword())) {
            return Result.fail("密码不正确");
        }

        user.setPassword(SecureUtil.md5(pass));
        userService.updateById(user);

        return Result.succ().action("/user/set#pass");

    }

    @ResponseBody
    @RequestMapping("/upload")
    public Result upload(@RequestParam("file")MultipartFile file) throws IOException {

        Result upload = uploadUtil.upload(UploadUtil.type_avatar, file);
        return upload;
    }



}

package com.zhangbao.eblog.util;

import com.zhangbao.eblog.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class UserUtils {


    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }
    public static User getUser() {
        Object object = getSubjct().getPrincipal();
        return (User)object;
    }
    public static Long getUserId() {
        return getUser().getId();
    }

}

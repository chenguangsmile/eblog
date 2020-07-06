package com.zhangbao.eblog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhangbao.eblog.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("m_user_action")
public class UserAction extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 动作类型
     */
    private String action;

    /**
     * 得分
     */
    private Integer point;

    /**
     * 关联的帖子ID
     */
    private String postId;

    /**
     * 关联的评论ID
     */
    private String commentId;


}

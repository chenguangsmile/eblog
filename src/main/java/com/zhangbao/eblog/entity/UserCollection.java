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
@TableName("m_user_collection")
public class UserCollection extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private Long postId;

    private Long postUserId;
    private Integer status;


}

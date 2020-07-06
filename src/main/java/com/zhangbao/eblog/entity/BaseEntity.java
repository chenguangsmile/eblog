package com.zhangbao.eblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Sunny
 * @create 2020-06-2020/6/16-12:56
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Date created;
    private Date modified;
    @TableLogic
    private Integer delFlag;

    public void preInsert(){
        this.created = new Date();
        this.modified = new Date();
    }
}

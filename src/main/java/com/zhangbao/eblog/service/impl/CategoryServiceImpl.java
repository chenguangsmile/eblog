package com.zhangbao.eblog.service.impl;

import com.zhangbao.eblog.entity.Category;
import com.zhangbao.eblog.mapper.CategoryMapper;
import com.zhangbao.eblog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张豹
 * @since 2020-06-16
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}

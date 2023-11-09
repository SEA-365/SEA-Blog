package com.sea.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sea.dao.ArticleTagDao;
import com.sea.entity.ArticleTag;
import com.sea.service.ArticleTagService;
import org.springframework.stereotype.Service;

/**
 * @author: sea
 * @date: 2023/11/9 19:57
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagDao, ArticleTag> implements ArticleTagService {
    //ServiceImpl 是 MyBatis-Plus 框架提供的通用 Service 类。
    // 它实现了常见的数据库操作，如插入、查询、更新和删除，以帮助简化在服务层进行数据操作的代码编写。
}

# SEA-Blog-项目需求

- 2023年10月4日，着手开始准备个人博客网站的开发，mark一下；

- 整体参考github开源项目：[linhaojun857/aurora: 基于SpringBoot+Vue开发的个人博客系统 (github.com)](https://github.com/linhaojun857/aurora/tree/master)

- 分为前台展示端和后台管理端；

- 当前版本实现：:white_check_mark:
- 下一版本实现：:bell:
 
## 前台展示端

- 参考：[花未眠的个人博客 (linhaojun.top)](https://www.linhaojun.top/)

- 前端页面：

    - 首页:white_check_mark:：首页展示推荐文章、文章列表、个人简介信息、网站信息、最近评论、标签目录；

      ![Blog-首页_1](https://cdn.jsdelivr.net/gh/SEA-365/imgList@main/imgList/Blog-%E9%A6%96%E9%A1%B5_1.png)

      ![Blog-首页_2](https://cdn.jsdelivr.net/gh/SEA-365/imgList@main/imgList/Blog-%E9%A6%96%E9%A1%B5_2.png)

    - 归档:white_check_mark:：根据博客文章的发布时间顺序构造进度树；

      ![Blog-归档_1](https://cdn.jsdelivr.net/gh/SEA-365/imgList@main/imgList/Blog-%E5%BD%92%E6%A1%A3_1.png)

    - 分类:white_check_mark:：

      ![Blog-分类_1](https://cdn.jsdelivr.net/gh/SEA-365/imgList@main/imgList/Blog-%E5%88%86%E7%B1%BB_1.png)

    - 标签:white_check_mark:：展示文章标签词云

      ![Blog-标签_1](https://cdn.jsdelivr.net/gh/SEA-365/imgList@main/imgList/Blog-%E6%A0%87%E7%AD%BE_1.png)

    - 关于:white_check_mark:：展示个人介绍和联系方式；

      ![Blog-关于_1](https://cdn.jsdelivr.net/gh/SEA-365/imgList@main/imgList/Blog.png)

    - 留言:white_check_mark:：增加

      ![image-20231004153147579](https://cdn.jsdelivr.net/gh/SEA-365/imgList@main/imgList/Blog-%E5%85%B3%E4%BA%8E_1.png)

    - 友链:bell:：后续增加；

- 登录注册:white_check_mark:：用户登录、用户注册；

- 后端提供接口:white_check_mark:

## 后台管理端

- 参考[后台管理系统 (linhaojun.top)](https://admin.linhaojun.top/)

- 包括：

    - 内容管理；
    - 可视化展示；
    - 在线编辑md文档

![Blog-后台_1](https://cdn.jsdelivr.net/gh/SEA-365/imgList@main/imgList/Blog-%E5%90%8E%E5%8F%B0_1.png)



## 思维导图

![SEA-Blog-总体设计_1](https://cdn.jsdelivr.net/gh/SEA-365/imgList@main/imgList/SEA-Blog-%E6%80%BB%E4%BD%93%E8%AE%BE%E8%AE%A1_1.png)
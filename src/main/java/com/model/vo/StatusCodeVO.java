package com.model.vo;

/**
 * 状态码
 * @author: SEA
 * @date: 2023/9/12
 */
public class StatusCodeVO {
    public static int SAVE_OK = 1000; // 保存成功的状态码，值为1000
    public static int SAVE_ERROR = 1001; // 保存失败的状态码，值为1001
    public static int DELETE_OK = 2000; // 删除成功的状态码，值为2000
    public static int DELETE_ERROR = 2001; // 删除失败的状态码，值为2001
    public static int UPDATE_OK = 3000; // 更新成功的状态码，值为3000
    public static int UPDATE_ERROR = 3001; // 更新失败的状态码，值为3001
    public static int SELECT_OK = 4000; // 查询成功的状态码，值为4000
    public static int SELECT_ERROR = 4001; // 查询失败的状态码，值为4001
}
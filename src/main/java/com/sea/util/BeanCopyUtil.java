package com.sea.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 类对象转换工具类，例如UserVO => User
 * @author: sea
 * @date: 2023/10/19 15:32
 */
public class BeanCopyUtil {
    /**
     * 单个对象的转换
     * @param source 源对象
     * @param target 目标对象的类对象xxx.Class
     * @return 返回转换后的对象
     * @param <T> 用于确定返回值的类型【当target参数传递过来后，T 的具体类型被确定！】
     */
    public static <T> T copyObject(Object source, Class<T> target) {
        T res = null;
        try {
            //根据类对象构建该类的实例
            res = target.newInstance();
            if(source != null){
                // Spring Framework 提供的一个用于复制对象属性的方法。
                // 这个方法会自动匹配源对象和目标对象中具有相同名称的属性，并复制它们的值。
                //     （1）如果源对象和目标对象具有不同数据类型的属性，它会尝试进行类型转换。
                //     （2）如果属性名称不匹配，它将忽略不匹配的属性。
                // 常用于 DTO（Data Transfer Object）和实体类之间的属性复制，以减少手动编写属性赋值代码的工作量
                org.springframework.beans.BeanUtils.copyProperties(source, res);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 源对象类型的List转换为目标对象类型的List
     * @param source 源List
     * @param target 目标类型
     * @return 目标类型的List
     * @param <S> 源类型
     * @param <T> 目标类型
     */
    public static <S, T> List<T> copyObjectList(List<S> source, Class<T> target){
        List<T> resList = new ArrayList<>();
        if(source != null && source.size() > 0){
            for (S s : source) {
                resList.add(BeanCopyUtil.copyObject(s, target));
            }
        }
        return resList;
    }
}

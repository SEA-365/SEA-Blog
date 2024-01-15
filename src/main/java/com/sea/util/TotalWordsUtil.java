package com.sea.util;

/**
 * @author: sea
 * @date: 2024/1/15 20:25
 */
public class TotalWordsUtil {
    /**
     * 统计字数, 空格不统计
     * @param content
     * @return
     */
    public static Integer wordCount(String content) {
        if(content == null){
            return 0;
        }
        Integer letterTotal = 0;
        Integer numTotal = 0;
        Integer otherTotal = 0;

        String str = content.trim();

        char[] charArray = str.toCharArray();
        for (char c : charArray) {
            if(Character.isLetter(c)){
                ++letterTotal;
            }
            else if(Character.isDigit(c)){
                ++numTotal;
            }
            else{
                ++otherTotal;
            }
        }
        return letterTotal + numTotal + otherTotal;
    }
}

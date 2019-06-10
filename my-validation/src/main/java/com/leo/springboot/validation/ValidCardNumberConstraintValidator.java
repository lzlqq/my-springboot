package com.leo.springboot.validation;

import com.leo.springboot.validation.constraints.ValidCardNumber;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

/**
 * {@link ValidCardNumber} {@link ConstraintValidator}
 */
public class ValidCardNumberConstraintValidator implements ConstraintValidator<ValidCardNumber,String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        //String[] parts = StringUtils.delimitedListToStringArray(value,"-");

        String[] parts = StringUtils.split(value,"-");
        // 为什么不用String#split方法，原因在于该方法是用了正则表达式，有时候还要转义，特别麻烦
        // 其次是NPE保护不够
        // 剪切String
        // 思路：实现的时候准备多套方案（至少2套），首先是问清楚别人的需求，其次是什么样的环境，实现时候需要什么样的依赖条件
        // 一个好的程序员应该有：1.版本意识，2.API的积累，3.思维的周密
        // 有时候没有必要自己去实现，有Spring的用Spring的，没有看看JDK的，等等
        // 1.Spring -> SpringUtils#delimitedListToStringArray
        // 2.JDK -> StringTokenizer
        // 3.Apache commons-lang -> StringUtils

        //if(parts.length!=2){
        if(ArrayUtils.getLength(parts)!=2){
            return false;
        }

        String prefix =parts[0];
        String suffix =parts[1];

        // 判断一个变量是否和一个字符串相等，初学者会使用如下方式
        // "LEO".equals(prefix)
        // 但是作为架构师，应该需要灵活处理，并使用高级API
        // 此处上下文中已经决定了 prefix不会为空了，所有写成
        // prefix.equals("LEO")
        // 是没问题的
        // 总结：套路是给初学者的，专家不能固话思维，需要结合上下文分析并使用高级API
        boolean isValidPrefix = Objects.equals(prefix,"LEO");
        // 在使用二方库的时候，要稍微了解下这个二方库的主要功能
        // 在学习技术的时候一定要精通几个核心的技术，不要面面俱到，蜻蜓点水
        //  1.技术：诚实，不懂就是不懂，不要不懂装懂
        boolean isValidInteger= org.apache.commons.lang3.StringUtils.isNumeric(suffix);

        // 上面使用两个变量来判断，多用了栈，但是调试方便
        //  要使用Apache-commons 上面的工具类尽量都是用一个，否则维护麻烦
        return isValidPrefix&&isValidInteger;
    }

   public void initialize(ValidCardNumber validCardNumber) {
    }
}

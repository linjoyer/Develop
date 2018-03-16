package com.tch.common.utils;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shz on 2017/10/16.
 * Hibernate 自定义查询结果数据转换类
 * 查询返回List<Map> 格式数据，
 * Map的key按照驼峰命名规则，反向还原domain类字段名。保证查询结果和实体类查询结果字段名一致。
 */
public class MyMapResultTransformer extends AliasedTupleSubsetResultTransformer {
    public static final AliasedTupleSubsetResultTransformer MY_INSTANCE  = new MyMapResultTransformer();

    private MyMapResultTransformer() {
    }

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        Map result = new HashMap(tuple.length);

        for(int i = 0; i < tuple.length; ++i) {
            String alias = underlineToCamel2(aliases[i]);
            if (alias != null) {
                result.put(alias, tuple[i]);
            }
        }

        return result;
    }
    public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
        return false;
    }

    private Object readResolve() {
        return MY_INSTANCE ;
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串2
     *
     * @param param
     * @return
     */
    public static String underlineToCamel2(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        StringBuilder sb = new StringBuilder(param.toLowerCase());
        Matcher mc = Pattern.compile("_").matcher(param);
        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            //判断下划线是否在最后
            if(position!=sb.length()) {
                sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
            }
        }
        return sb.toString();
    }
}

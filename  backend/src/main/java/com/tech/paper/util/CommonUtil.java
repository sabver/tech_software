package com.tech.paper.util;

import com.tech.paper.crawler.ArxivDetailPipeline;
import com.tech.paper.domain.ArxivId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {

    private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);

    public static final String TOKEN_USER_ID_KEY = "token_user_id";

    public static final String TOKEN_USER_ACCOUNT_KEY = "token_user_account";

    /**
     * 获取下一个对应的arxivId
     * 这里是假设ym是不需要变化的
     * @param cur 当前id
     * @return
     */
    public static ArxivId getNextArxivId(ArxivId cur){
        ArxivId result = new ArxivId();
        String numStr = "";
        Integer num = Integer.valueOf(cur.getNumberStr());
        num++;
        //补0到5位
        if( num < 10 ){
            numStr = "0000"+num.toString();
        }else if( num < 100 ){
            numStr = "000"+num.toString();
        }else if( num < 1000 ){
            numStr = "00"+num.toString();
        }else if(num < 10000){
            numStr = "0"+num.toString();
        }else{
            numStr = num.toString();
        }
        result.setYm(cur.getYm());
        result.setNumberStr(numStr);
        return result;
    }

    /**
     * 这个版本是对应着需要跨年和跨月的做法
     * 调用这个意味着numStr是错误的，需要被重新置零的，只需要处理前面的年份
     * 1912.00001
     * @param cur
     * @return
     */
    public static ArxivId getNextArxivIdYearMonth(ArxivId cur){
        ArxivId result = new ArxivId();
        String ym = cur.getYm();
        String [] strs = ym.split("");
        String year = strs[0]+strs[1];
        String month = strs[2]+strs[3];
        Integer m = Integer.valueOf(month);
        Integer y = Integer.valueOf(year);
        log.info(year);
        log.info(month);
        log.info(y.toString());
        log.info(m.toString());
        if( m == 12 ){
            y++;
            if( y < 10 ){
                result.setYm("0"+y.toString()+"01");
            }else{
                result.setYm(y.toString()+"01");
            }

        }else{
            m++;
            //这里要补0
            if( m < 10 ){
                result.setYm(y.toString()+"0"+m.toString());
            }else{
                result.setYm(y.toString()+""+m.toString());
            }

        }
        result.setNumberStr("00001");
        return result;
    }

    public static ArxivId getNextArxivIdYearMonth(String curUrl){
        String[] strs = curUrl.split("https://arxiv.org/abs/");
        String param = strs[1];
        return getNextArxivIdYearMonth(getArxivIdByParam(param));
    }

    /**
     * 获取上一个对应的arxivId
     * @param cur 当前id
     * @return
     */
    public static ArxivId getLastArxivId(ArxivId cur){
        return null;
    }

    /**
     * 通过ArxivId构建url
     * @param cur
     * @return
     */
    public static String buildUrl(ArxivId cur){
        return "https://arxiv.org/abs/"+cur.getYm()+"."+cur.getNumberStr();
    }

    public static String buildUrl(String param){
        return buildUrl(getArxivIdByParam(param));
    }

    public static String pdfUrl(ArxivId cur){
        return "https://arxiv.org/pdf/"+cur.getYm()+"."+cur.getNumberStr();
    }

    public static String pdfUrl(String param){
        return pdfUrl(getArxivIdByParam(param));
    }

    public static ArxivId getArxivIdByParam(String param){
        log.info("getArxivIdByParam:"+param);
        String[] str = param.split("\\.");
        ArxivId result = new ArxivId();
        log.info(str[0]);
        result.setYm(str[0]);
        result.setNumberStr(str[1]);
        log.info(result.toString());
        return result;
    }

    /**
     * 如果value是空的话就返回true
     * @param value
     * @return
     */
    public static boolean checkNull(String value){
        if( value == null || value.replace(" ","").equals("") ){
            return true;
        }
        return false;
    }

}

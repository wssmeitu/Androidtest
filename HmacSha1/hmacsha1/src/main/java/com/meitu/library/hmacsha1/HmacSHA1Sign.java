package com.meitu.library.hmacsha1;

import android.text.TextUtils;
import android.util.Base64;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


/**
 * Date:2018/8/9
 * Time:下午8:35
 * author:wss
 */
public class HmacSHA1Sign {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    /**
     * 使用 HMAC-SHA1 签名方法对apiKey进行签名
     *
     * @param apiKey    被签名的字符串
     * @param apiSecret 密钥
     * @param time      时间戳
     * @return 加密后的字符串
     */
    public static String getSignResult(String apiKey, String apiSecret, long time) {

        String signApiKey = signApiKey(apiKey, time);

        String str = null;
        try {
            str = "." + signApiKey;
            byte[] bytes = str.getBytes();
            byte[] bytes1 = getByte(hmacSha1(signApiKey, apiSecret), bytes);
            return stringFormat(Base64.encodeToString(bytes1, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 对加密后的byte和后面拼接的字符串转byte后 合并
     *
     * @param data1
     * @param data2
     * @return
     */
    private static byte[] getByte(byte[] data1, byte[] data2) {

        byte[] data3 = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, data3, 0, data1.length);
        System.arraycopy(data2, 0, data3, data1.length, data2.length);
        return data3;
    }

    /**
     * 进行HmacSHA1加密
     *
     * @param signApiKey
     * @param apiSecret
     * @return
     */
    private static byte[] hmacSha1(String signApiKey, String apiSecret) {

        if (TextUtils.isEmpty(signApiKey) || TextUtils.isEmpty(apiSecret)) {
            return null;
        }

        Mac mac = null;
        try {
            mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            SecretKeySpec secret = new SecretKeySpec(apiSecret.getBytes("UTF-8"), mac.getAlgorithm());
            mac.init(secret);
            byte[] digest = mac.doFinal(signApiKey.getBytes("UTF-8"));
            return digest;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 对ApiKey 和时间戳，随机数等进行拼接
     *
     * @param ApiKey
     * @return
     */
    private static String signApiKey(String ApiKey, long time) {

        StringBuffer sb = new StringBuffer();
        sb.append("k=");
        sb.append(ApiKey);
        sb.append("&t=");
        sb.append("" + time);//时间戳
        sb.append("&r=");
        sb.append(new Random().nextInt(999999999));//随机数
        sb.append("&f=");

        return sb.toString();
    }

    /**
     * 格式化
     *
     * @param str
     * @return
     */
    private static String stringFormat(String str) {

        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}

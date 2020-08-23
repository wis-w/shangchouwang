package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2021000118602150";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCt9qrfUonbBcUgj50M+lZFUS+8DlpjUlknj5ZsfP/NMA/8qVXhdkEs5XZ2RA9uKR6BLE2yYTRv55/Hl0O7q9UvlguKTUerNjHZZmHxAQAa2tuiSixycVnWJ0whqhTU5+n3DRAzNDd5tDMi7aqaf+cSOJB8rB1ncy9Jbm27utgkiutMRVQrQ6wfTlz40l0oFLErTWnew57dsacVHWdmjaHKhpiQgtQcN9Hyl99ZXVeQ5I1w5+1dUsjgHsUkNLOjvgA5L+Vtdsjo2vtY3ZMkCayx1oAHBye4aK70UOczoOiNlArXu1dDJlGzfO09SdgR+RHCOLdVbHguCe2MDfOYtl/9AgMBAAECggEBAKlQzVpykS4VXZmlbav18wWgUR9tsNe+jHuOq0+IEch5oPW/MpEwcZrq8vOEpqgHaWM75ZtaNJk+DStLtGbyhZm9JhpDwR+Irdf0aBsTaDC7Fs3L95qjbFadBOI3EfNujS8hDcVoax4PHz49p7CcECCR4T8Wnb/UQg/sZO00m65jzmyUnA+QWmlcCH5K4OtvpVIxJH/2dIXsJjQ0dZ2tmsMzM/mSvK/QAG1c8iHNklnHI5yduLddPpQLLf5TMb4FC70OPbLktxy5W0JA5BsJNcj6pnr8LGccxNQ5VQq6W3C/tfQKWqwgijb4UqHRrD+BUTk4ZJsAUSTDQiKHozQrD0ECgYEA+nszHv148i0CivFfxFW0OLKLZ2u+WyS6TvcNaQs7s2my0+oVeExtXuy3Usm7b1mQz+qHcnrAtAw/tn8d/GqpALwLtbnXqziLNdYTg9Q5Q9PONDK2loLOjm32Yi9kKS80bFSSOoFsSGG0ofct5xfOMnTlu8SCTkNDclrhpkus+PUCgYEAscvhsu9G90PAMbMexI8LocMuTAC4+brHDeJAd+gXAR4f02Z7hmRRExKaKeqNvCrdDMZemK0i2HwVLpoJ/i7y8OtyPov3oL7EHNQTlY+bLB6TEP58MAHZK4qr3+NeqxSUAe8MUTPCV8J8L2Vlg9sKh3ptslsPFiy8/3ecB1LfBekCgYEA6f1JBjw3TdSqOW2+zsRZNVXlUFmgW7h1quqzRlvOC45EFh7oq5aJ3VE4+Eenpx9+XhxzJ4hwmEGRC0S4mGJzOlJwhfTz+ek6crJnTJFmZWxphu2REA1mNDuZHKO055xHVqOybqEUrJFJkrw7z1O3tf7p1Xx/VekUCrHHdfffwDkCgYEApIcMtq6htxLk+ZjNsgrFMs4RO5e3lLD4nqpuCgelsg0UWmYNjNGsDMA8FOnOewAXSkkPJK2i/z3jxBobgTIk8jiOob6YShH+HMdL0Czx5SPt5933Qd2T/6z1W9tlsV77j8aMWWBSSS/aB2oHRLsfxTrP5CpLjbOcubMPmd9gLnECgYAGVe03gxnmBfYLoKv5xDN9a7KAmQ36VBMzyvYSO1Z54sFB8F3dPErJ3oKEK/sbS1iU9WRKsTWbrobTEZd0U2RODYoMfnaJCxlRboeXLHN3JjadJ51YJ9v4A32775TduU3RH7VgYMYgjFGBrP7nktvg6nGASkaJFYjLGN/gzg8HBA==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiwnDz3/IIE40RkwHGdrxRTlzohcjQTwZR8YejmS/CLsYSYUvcKwT1gMu1dyrZZNRnJLJMLRkJvmDoLcZqJPy398YvpKvv0OaItVloFot9lcZdbjQrTHLlLTIkJyxi2/93ugoJZPvv72MNwLGh52V+1OPIHuMax3BHD58KpgG3tUcFT/21W7BPSb2w+a7mt1ZCQyeGjCeIKi9y5RvePI6JJnRXJpvHCz2URElZpxi+pmVl+I3nBdf/cp/tWEhj7F0sId66WvFtFj+EyyqTMHJ7vas030GwjWzyHNlZoikcaMxG7ynSREe44t9QUmJ6KlWszXi9dx5S9WfDLzLslmYaQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://4y4vih.natappfree.cc/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://4y4vih.natappfree.cc/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 日志路径
	public static String log_path = "D:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


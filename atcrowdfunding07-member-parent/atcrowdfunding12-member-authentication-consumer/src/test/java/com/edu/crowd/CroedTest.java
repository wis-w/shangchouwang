package com.edu.crowd;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.edu.croed.util.HttpUtils;


/**
 * @author wyg_edu
 * @date 2020年6月30日 下午9:01:57
 * @version v1.0
 */
public class CroedTest {
	
	@Test
	public void test() {
		// 短信接口调用的URL地址
	    String host = "https://intlsms.market.alicloudapi.com";
	    // 具体短信接口的短信地址
	    String path = "/comms/sms/groupmessaging";
	    // POST请求
	    String method = "POST";
	    // 短信接口的APPcode
	    String appcode = "847cd97706bd462cafc66ba88b366b74";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    //根据API的要求，定义相对应的Content-Type
	    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    Map<String, String> querys = new HashMap<String, String>();
	    Map<String, String> bodys = new HashMap<String, String>();
	    bodys.put("callbackUrl", "http://test.dev.esandcloud.com");
	    bodys.put("channel", "0");
	    bodys.put("mobileSet", "[8615537857756]");
	    bodys.put("templateID", "0000000");
	    bodys.put("templateParamSet", "['王亚光Test', '999999999999']");


	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
	    	System.out.println(response.toString());
	    	//获取response的body
	    	System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	
	}

}

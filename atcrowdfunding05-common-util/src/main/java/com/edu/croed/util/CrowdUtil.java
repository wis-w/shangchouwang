package com.edu.croed.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;
import com.edu.croed.constant.CrowdConstant;

/**
 * @author wyg_edu
 * @date 2020年5月7日 下午9:30:20
 * @version v1.0 工具类
 */
public class CrowdUtil {
	
	public static void main(String[] args) throws Exception {
		FileInputStream fileInputStream = new FileInputStream("333.png");
		ResultEntity<String> uploadFileToOss = uploadFileToOss("http://oss-cn-beijing.aliyuncs.com",
				"LTAI4GLAoqcGxxHxMRGXTMhP",
				"2CcmBzdZPqzdJsvvgeFTwIv0hoADz2",
				fileInputStream,
				"wyg2020",
				"http://wyg2020.oss-cn-beijing.aliyuncs.com",
				"333.png"
				);
		System.out.println(uploadFileToOss);
	}

	/**
	 * 专门负责上传文件到OSS服务器的工具方法
	 * 
	 * @param endpoint        OSS参数
	 * @param accessKeyId     OSS参数
	 * @param accessKeySecret OSS参数
	 * @param inputStream     要上传的文件的输入流
	 * @param bucketName      OSS参数
	 * @param bucketDomain    OSS参数
	 * @param originalName    要上传的文件的原始文件名
	 * @return 包含上传结果以及上传的文件在OSS上的访问路径
	 */
	public static ResultEntity<String> uploadFileToOss(String endpoint, String accessKeyId, String accessKeySecret,
			InputStream inputStream, String bucketName, String bucketDomain, String originalName) {

		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		// 生成上传文件的目录
		String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());

		// 生成上传文件在OSS服务器上保存时的文件名
		// 原始文件名：beautfulgirl.jpg
		// 生成文件名：wer234234efwer235346457dfswet346235.jpg
		// 使用UUID生成文件主体名称
		String fileMainName = UUID.randomUUID().toString().replace("-", "");

		// 从原始文件名中获取文件扩展名
		String extensionName = originalName.substring(originalName.lastIndexOf("."));

		// 使用目录、文件主体名称、文件扩展名称拼接得到对象名称
		String objectName = folderName + "/" + fileMainName + extensionName;

		try {
			// 调用OSS客户端对象的方法上传文件并获取响应结果数据
			PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, inputStream);

			// 从响应结果中获取具体响应消息
			ResponseMessage responseMessage = putObjectResult.getResponse();

			// 根据响应状态码判断请求是否成功
			if (responseMessage == null) {

				// 拼接访问刚刚上传的文件的路径
				String ossFileAccessPath = bucketDomain + "/" + objectName;

				// 当前方法返回成功
				return ResultEntity.successWithData(ossFileAccessPath);
			} else {
				// 获取响应状态码
				int statusCode = responseMessage.getStatusCode();

				// 如果请求没有成功，获取错误消息
				String errorMessage = responseMessage.getErrorResponseAsString();

				// 当前方法返回失败
				return ResultEntity.failed("当前响应状态码=" + statusCode + " 错误消息=" + errorMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();

			// 当前方法返回失败
			return ResultEntity.failed(e.getMessage());
		} finally {

			if (ossClient != null) {

				// 关闭OSSClient。
				ossClient.shutdown();
			}
		}

	}

	@Test
	public void testMessage() {
		ResultEntity<String> sendCodeByMessage = sendCodeByMessage("https://intlsms.market.alicloudapi.com",
				"/comms/sms/groupmessaging", "POST", "15537857756", "847cd97706bd462cafc66ba88b366b74", "0", "0000000");
		System.out.println(sendCodeByMessage.getData());
	}

	/**
	 * 短信发送接口 获取验证码
	 * 
	 * @param phoneNum 接受短信的手机号
	 * @param host     = "https://intlsms.market.alicloudapi.com";短信接口调用的URL地址
	 * @param path     = "/comms/sms/groupmessaging";具体短信接口的短信地址
	 * @param method   = "POST";POST请求
	 * @param appcode  用来调用第三方的appcode "847cd97706bd462cafc66ba88b366b74";
	 * @param sign     签名编号 0
	 * @param skin     模板编号 0000000
	 * @return success 返回验证码 error 异常返回错误信息
	 */
	public static ResultEntity<String> sendCodeByMessage(String host, String path, String method, String phoneNum,
			String appcode, String sign, String skin) {

		// 生成验证码
		String code = String.valueOf((int) (Math.random() * 10000));

		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		// 根据API的要求，定义相对应的Content-Type
		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		Map<String, String> querys = new HashMap<String, String>();
		Map<String, String> bodys = new HashMap<String, String>();
		bodys.put("callbackUrl", "http://test.dev.esandcloud.com");
		bodys.put("channel", sign);// 签名编号
		bodys.put("mobileSet", "[86" + phoneNum + "]");
		bodys.put("templateID", skin);// 模板编号
		bodys.put("templateParamSet", "['" + code + "', '50']");

		try {
			HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			// 获取response的body
			String body = EntityUtils.toString(response.getEntity());

			JSONObject jsonObject = (JSONObject) JSON.parse(body);

			if (jsonObject.get("code").equals("0000")) {
				return ResultEntity.successWithData(code);
			} else {
				return ResultEntity.failed((String) jsonObject.get("msg"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResultEntity.failed(e.getMessage());
		}

	}

	/**
	 * 判断请求是否ajax请求
	 * 
	 * @param request
	 * @return
	 */
	public static boolean judgeReqyestType(HttpServletRequest request) {
		// 1、获取请求消息头
		String acceptHeader = request.getHeader("Accept");
		String xRequestHeader = request.getHeader("X-Requested-With");
		// 2、判断
		return (acceptHeader != null && acceptHeader.contains("application/json")
				|| (xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest")));
	}

	/**
	 * 对字符串进行加密
	 * 
	 * @param source 铭文
	 * @return
	 */
	public static String md5(String source) {

		// 1、判断原文是否有效
		if (null == source || source.length() == 0) {
			// 2、如果不是有效字符串，则抛出异常
			throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
		}

		try {
			// 3、获取MessageDigest对象
			String algoruthm = "md5";
			MessageDigest digest = MessageDigest.getInstance(algoruthm);

			// 4、获取铭文字符串对应的数组
			byte[] input = source.getBytes();

			// 5、进行加密
			byte[] output = digest.digest(input);

			// 6、创建BigInteger
			int signum = 1; // 代表正数
			BigInteger bigInteger = new BigInteger(signum, output);

			// 7、按照16进制将其转换成字符串
			int radix = 16;
			String encoded = bigInteger.toString(radix).toUpperCase();

			// 8、返回加密串
			return encoded;

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

}

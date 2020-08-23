package com.edu.crowd.handler;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.edu.croed.util.ResultEntity;
import com.edu.crowd.api.MySQLRemotrService;
import com.edu.crowd.config.PayProperties;
import com.edu.crowd.entity.vo.OrderProjectVO;
import com.edu.crowd.entity.vo.OrderVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PayHandler {
	
	@Autowired
	private PayProperties payProperties;
	
	@Autowired
	private MySQLRemotrService mySQLRemotrService;
	
	/**
	 * 支付表单组装成VO对象
	 * @param httpSession
	 * @param orderVO
	 * @return
	 * @throws AlipayApiException 
	 */
	@ResponseBody// 防止返回的数据被mvc框架识别成视图，实际显示应该是支付宝界面
	@RequestMapping("/generate/order")
	public String generateOrder(HttpSession httpSession, OrderVO orderVO) throws AlipayApiException {
		log.info("进行远程调用支付宝接口");
		
		//1、从session域中获取orderProjectVO对象
		OrderProjectVO orderProjectVO = (OrderProjectVO) httpSession.getAttribute("orderProjectVO");
		
		// 2、组装OrderVO
		orderVO.setOrderProjectVO(orderProjectVO);
		
		// 3、生成订单号并存储在VO对象
		// 使用YYYYMMDDHHmmSS+UUID形式
		String time = new SimpleDateFormat("YYYYMMDDHHmmSS").format(new Date());
		String user = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		String orderNum = time+user;
		orderVO.setOrderNum(orderNum);
		
		// 4、计算订单总金额并存储到VO对象
		Integer orderAmount = orderProjectVO.getSupportPrice()*orderProjectVO.getReturnCount() + orderProjectVO.getFreight();
		orderVO.setOrderAmount(Double.valueOf(orderAmount));
		httpSession.setAttribute("orderVo", orderVO);// 将orderVO对象存入Session域
		// 5、远程嗲用支付宝接口
		return sendAlipay(orderNum,Double.valueOf(orderAmount),orderProjectVO.getProjectName(),orderProjectVO.getReturnContent());
	}
	
	/**	
	 * 调用支付宝接口所作的封装
	 * @param outTradNo	商户订单号，商户网站订单系统中唯一订单号，必填
	 * @param totalAmount	付款金额，必填
	 * @param subject	订单名称，必填
	 * @param body	商品描述，可空
	 * @return 返回支付宝界面
	 * @throws AlipayApiException 
	 */
	private String sendAlipay(String outTradNo,Double totalAmount,String subject,String body) throws AlipayApiException {
		log.info("进行远程调用支付宝接口2");
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(
				payProperties.getGatewayUrl(), 
				payProperties.getAppId(), 
				payProperties.getMerchantPrivateKey(), 
				"json", 
				payProperties.getCharset(), 
				payProperties.getAlipayPublicKey(), 
				payProperties.getSignType());
		
		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(payProperties.getReturnUrl());
		alipayRequest.setNotifyUrl(payProperties.getNotifyUrl());
		
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ outTradNo +"\"," 
				+ "\"total_amount\":\""+ totalAmount +"\"," 
				+ "\"subject\":\""+ subject +"\"," 
				+ "\"body\":\""+ body +"\"," 
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
		
		//请求
		return alipayClient.pageExecute(alipayRequest).getBody();
	}
	
	/**
	 * 接受支付宝的返回操作
	 * @param request
	 * @throws AlipayApiException
	 * @throws UnsupportedEncodingException
	 */
	@ResponseBody
	@RequestMapping("/return")
	public String returnUrl(HttpServletRequest request,HttpSession httpSession) throws AlipayApiException, UnsupportedEncodingException {
		log.info("接收支付宝远程调用返回信息");
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		//——请在这里编写您的程序（以下代码仅作参考）——
		//商户订单号
		String orderNum = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
	
		//支付宝交易号
		String payOrderNum = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
	
		//付款金额
		String orderAmount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
		
		// 保存到数据库
		
		// 2、从session域获取OrderVO对象
		OrderVO orderVO = (OrderVO) httpSession.getAttribute("orderVo");
		// 1、将支付宝交易号存储对象中
		orderVO.setPayOrderNum(payOrderNum);
		// 3、发送给Mysql服务
		ResultEntity<String> result = mySQLRemotrService.saveOrderRemote(orderVO);
		log.info("Order Save Result: " + result.getResult());
		return "trade_no:"+orderNum+"<br/>out_trade_no:"+payOrderNum+"<br/>total_amount:"+orderAmount;
		
	}
	
	/**
	 * 支付宝调用后台，只作为程序检验，并不会向用户展示
	 * @param request
	 * @throws UnsupportedEncodingException
	 * @throws AlipayApiException
	 */
	@RequestMapping("/notify")
	public void notifyUrl(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
		
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		
		boolean signVerified = AlipaySignature.rsaCheckV1(
				params, 
				payProperties.getAlipayPublicKey(), 
				payProperties.getCharset(), 
				payProperties.getSignType()); //调用SDK验证签名

		//——请在这里编写您的程序（以下代码仅作参考）——
		
		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
		if(signVerified) {//验证成功
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
		
			//交易状态
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
			
			log.info("支付宝交易状态：" + trade_status+"  支付宝交易号:"+trade_no+"  商户订单号" + out_trade_no);
			
			log.info("/notify模块success");
			
		}else {//验证失败
			log.info( "/notify模块 认证 fail");
			//调试用，写文本函数记录程序运行情况是否正常
			//String sWord = AlipaySignature.getSignCheckContentV1(params);
			//AlipayConfig.logResult(sWord);
		}
		
	}
}

package com.edu.crowd.handler;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.edu.croed.constant.CrowdConstant;
import com.edu.croed.util.CrowdUtil;
import com.edu.croed.util.ResultEntity;
import com.edu.croed.util.StringUtil;
import com.edu.crowd.api.MySQLRemotrService;
import com.edu.crowd.api.RedisRemoteService;
import com.edu.crowd.config.ShortMessageProperties;
import com.edu.crowd.entity.po.MemberPO;
import com.edu.crowd.entity.vo.MemberLoginVO;
import com.edu.crowd.entity.vo.MemberVo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author wyg_edu
 * @date 2020年7月2日 下午9:41:00
 * @version v1.0
 */
@Controller
@Slf4j
public class MemberHandler {

	@Autowired
	private ShortMessageProperties shortMessageProperties;

	@Autowired
	private RedisRemoteService redisRemoteService;

	@Autowired
	private MySQLRemotrService mySQLRemotrService;
	
	/**
	 * 退出登录
	 * @param session
	 * @return
	 */
	@RequestMapping("/auth/member/logout")
	public String logout(HttpSession session) {
		session.invalidate();// 删除session
		return "redirect:/";
	}
	
	/**
	 * 用户登录操作
	 */
	@RequestMapping("/auth/member/do/login")
	public String login(@RequestParam("loginacct") String loginacct, @RequestParam("userpswd") String userpswd,
			ModelMap modelMap, HttpSession session) {
		log.info("主业务模块---进行用户登录操作");
		// 1、调用mysql服务的查询交易，根据用户昵称进行数据查询
		ResultEntity<MemberPO> memberPOByLoginAcctRemote = mySQLRemotrService.getMemberPOByLoginAcctRemote(loginacct);

		if (ResultEntity.FAILED.equals(memberPOByLoginAcctRemote.getResult())) {
			log.info("主业务模块---用户查询失败");
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, "用户查询失败");
			return "member-login";
		}
		MemberPO data = memberPOByLoginAcctRemote.getData();
		if (data == null) {// 没有查询到数据
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
			return "member-login";
		}
		// 2、表单密码比较
		String userPswdFromDB = data.getUserpswd();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		boolean matches = bCryptPasswordEncoder.matches(userpswd, userPswdFromDB);// 进行密码比较 不可以进行加密后进行对比，因为盐值是随机的，无法进行加密后比较
		if(!matches) {// 密码错误
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
			return "member-login";
		}
		
		// 将结果转换成loginVO存入session域
		MemberLoginVO memberLoginVO = new MemberLoginVO(data.getId(),data.getLoginacct(),data.getEmail());
		session.setAttribute(CrowdConstant.LOGIN_MEMBER, memberLoginVO);
		// 验证通过进入主页
//		return "redirect:/auth/member/to/centor/page";// 如此重定向使用的是4000端口，因此zuul使用80端口时进行重定向则无法使用同一个cookies
		return "redirect:http://localhost:80/auth/member/to/centor/page";

	}

	/**
	 * 注册操作
	 * 
	 * @param memberVo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/auth/do/member/register")
	public String register(MemberVo memberVo, ModelMap modelMap) {

		if (StringUtil.equalsString(memberVo.getCode()) || StringUtil.equalsString(memberVo.getLoginacct())
				|| StringUtil.equalsString(memberVo.getUserpswd()) || StringUtil.equalsString(memberVo.getUsername())) {
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, "表单关键信息不得为空");
			log.info("表单用户信息检验失败");
			return "member-reg";
		}
// ************* reids操作屏蔽start*********************
//		// 获取输入的手机号
//		String phoneNum = memberVo.getPhoneNum();
//
//		// 拼接redis存储验证码key
//		String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
//
//		// 读取redis的验证码
//		ResultEntity<String> resultEntity = redisRemoteService.getRedisStringValueByKey(key);
//
//		// 判断redis查询是否有效
//		String result = resultEntity.getResult();
//
//		if (!ResultEntity.SUCCESS.equals(result)) {// 如果redis查询失败则返回注册页面进行消息提醒
//			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultEntity.getMessage());
//			return "member-reg";
//		}
//
//		String redisCode = resultEntity.getData();// 获取redis中的验证码
//
//		if (redisCode == null) {// 如果redis查询验证码不存在，则返回注册页面
//			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
//			return "member-reg";
//		}
//
//		if (!Objects.equals(redisCode, memberVo.getCode())) {// 如果redis查询验证码不存在，则返回注册页面
//			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
//			return "member-reg";
//		}
//
//		if (!Objects.deepEquals(redisCode, memberVo.getCode())) {
//			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);
//			return "member-reg";
//		}
//
//		/**
//		 * // 暂时屏蔽防止重复发送验证码 // 校验验证码是否正确，如果正确则删除key，保证验证码失效 resultEntity =
//		 * redisRemoteService.removeRedisKeyRemote(key);
//		 * 
//		 * if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
//		 * log.info("redis删除失败"); redisRemoteService.removeRedisKeyRemote(key);//
//		 * 如果删除失败则在次尝试 }
//		 */
//		************* reids操作屏蔽end*********************

		// 进行密码加密，存入数据库
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String encode = bCryptPasswordEncoder.encode(memberVo.getUserpswd());
		memberVo.setUserpswd(encode);

		// 执行保存
		// 创建空的PO对象
		MemberPO memberPO = new MemberPO();
		// 属性复制操作
		BeanUtils.copyProperties(memberVo, memberPO);
		ResultEntity<String> resultSave = mySQLRemotrService.saveRemote(memberPO);// 调用远程 操作保存
		if (ResultEntity.FAILED.equals(resultSave.getResult())) {
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, resultSave.getMessage());
			return "member-reg";
		}

		// 返回到登录页面
		// return "member-login";

		// 重定向到该页面，防止刷新后的表单重写
		return "redirect:/auth/member/to/login/page";

	}

	/**
	 * 进行短信发送，并将结果存储redis
	 * 
	 * @param phoneNum
	 * @return
	 */
	@RequestMapping("/auth/member/send/short/message.json")
	@ResponseBody
	public ResultEntity<String> sendMessage(@RequestParam("phoneNum") String phoneNum) {
		// 1、发送验证码到手机，返回验证码结果
		ResultEntity<String> sendCodeByMessage = CrowdUtil.sendCodeByMessage(shortMessageProperties.getHost(),
				shortMessageProperties.getPath(), shortMessageProperties.getMethod(), phoneNum,
				shortMessageProperties.getAppcode(), shortMessageProperties.getSign(),
				shortMessageProperties.getSkin());

		// 2、判断短信的发送结果
		if (ResultEntity.SUCCESS.equals(sendCodeByMessage.getResult())) {
			// 3、如果发送成功，则将验证码存入到redis
			String code = sendCodeByMessage.getData();

			String key = CrowdConstant.REDIS_CODE_PREFIX;// 获取redis存入的key头
			// 存入过期时间5分钟
			ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKVRemoteWithTimeOut(key + phoneNum,
					code, 50, TimeUnit.MINUTES);
			if (saveCodeResultEntity.getResult().equals(ResultEntity.SUCCESS)) {
				return ResultEntity.successWithoutData();
			} else {
				return saveCodeResultEntity;
			}
		} else {
			return sendCodeByMessage;
		}
	}
	
}

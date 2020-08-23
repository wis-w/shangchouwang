package com.edu.crowd.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.edu.croed.constant.CrowdConstant;
import com.edu.croed.util.CrowdUtil;
import com.edu.croed.util.ResultEntity;
import com.edu.crowd.api.MySQLRemotrService;
import com.edu.crowd.config.OSSProperties;
import com.edu.crowd.entity.vo.DetailProjectVO;
import com.edu.crowd.entity.vo.MemberConfirmInfoVO;
import com.edu.crowd.entity.vo.MemberLoginVO;
import com.edu.crowd.entity.vo.ProjectVO;
import com.edu.crowd.entity.vo.ReturnVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ProjectConsumerHandler {
	
	@Autowired
	private OSSProperties ossProperties;
	
	@Autowired
	private MySQLRemotrService mySQLRemotrService;
	
	/**
	 * 详情页查询
	 * @param projectId
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/get/project/detail/{projectId}")
	public String getProjectDatail(@PathVariable("projectId")Integer projectId,ModelMap modelMap) {
		ResultEntity<DetailProjectVO> detailProject = mySQLRemotrService.getDetailProjectVO(projectId);
		if(ResultEntity.SUCCESS.equals(detailProject.getResult())) {
			DetailProjectVO detailProjectVO = detailProject.getData();
			modelMap.addAttribute("detailProjectVO", detailProjectVO);
		}
		return "project-show-detail";
	}
	
	/**
	 * 进行众筹属性的保存
	 * @param modelMap
	 * @param httpSession
	 * @param confirmInfoVO
	 * @return
	 */
	@RequestMapping("/create/confirm")
	public String saveConfirm(ModelMap modelMap, HttpSession httpSession,MemberConfirmInfoVO confirmInfoVO) {
		// 从session域中读取projectVo对象
		ProjectVO projectVo = (ProjectVO) httpSession.getAttribute(CrowdConstant.ATTR_NAME_TMPLE_PROJECT);
		// 判断VO对象是否为空
		if (projectVo == null) {
			throw new RuntimeException("临时存储VO为空");
		}
		// 存放VO对象
		projectVo.setMemberConfirmInfoVO(confirmInfoVO);
		
		// 从session域中读取登录对象
		MemberLoginVO memberLoginVO = (MemberLoginVO) httpSession.getAttribute(CrowdConstant.LOGIN_MEMBER);
		
		// 调用远程的mysql保存projectVo对象
		ResultEntity<String> saveResultEntity = mySQLRemotrService.saveProjectVORemote(projectVo, memberLoginVO.getId());
		
		// 判断远程调用是否成功
		if(ResultEntity.FAILED.equals(saveResultEntity.getResult())) {
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,"saveResultEntity.getResult()");
			 return "project-confirm";
		}
		
		// 如果远程调用成功则跳转到成功页面，移出session对象
		httpSession.removeAttribute(CrowdConstant.ATTR_NAME_TMPLE_PROJECT);
		return "redirect:http://localhost/project/create/success";
	}
	
	/**
	 * 保存回报信息
	 * @param returnVO
	 * @param httpSession
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/create/save/return.json")
	public ResultEntity<String> saveReturn(ReturnVO returnVO, HttpSession httpSession){
		try {
			// 从session域中获取session对象
			ProjectVO projectVo = (ProjectVO) httpSession.getAttribute(CrowdConstant.ATTR_NAME_TMPLE_PROJECT);
			
			// 判断VO对象是否为空
			if(projectVo == null) {
				return ResultEntity.failed("临时的ProjectVO丢失"); 
			}
			
			// 获取project对象存储汇报信息的VO对象
			List<ReturnVO> returnVOList = projectVo.getReturnVOList();
			
			// 判断VO对象是否为空，位置的对象需要作此判断，避免NPE
			if (returnVOList == null || returnVOList.size() == 0) {
				returnVOList = new ArrayList<ReturnVO>();
				projectVo.setReturnVOList(returnVOList);
			}
			projectVo.getReturnVOList().add(returnVO);
			// 重新存入session域
			httpSession.setAttribute(CrowdConstant.ATTR_NAME_TMPLE_PROJECT, projectVo);
			return ResultEntity.successWithoutData();
		} catch (Exception e) {
			log.info("数据保存失败");
			return ResultEntity.failed(e.getMessage()); 
		}
	}
	
	/**
	 * 回报图片上传，不进行页面跳转
	 * formData.append("returnPicture", file);
	 * returnPicture是请求参数名 file是请求参数值
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/create/upload/return/picture.json")
	public ResultEntity<String> uploadReturnPicture(@RequestParam("returnPicture") MultipartFile picture) throws IOException{
		// 执行文件的上传 oss
		ResultEntity<String> uploadFileToOss = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(),
					ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), picture.getInputStream(),
					ossProperties.getBucketName(), ossProperties.getBucketDomain(),
					picture.getOriginalFilename());
		// 返回上传结果
		return uploadFileToOss;
		
	}
	
	/**
	 * 头图以及详情图上传并存入session域
	 * @param projectVo
	 * @param headerPicture
	 * @param detailPictureList
	 * @param httpSession
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/create/project/information")
	public String saveProjectBasicinfo(
			ProjectVO projectVo,// 接受图片之外的数据
			MultipartFile headerPicture,// 接受上传的头图
			List<MultipartFile> detailPictureList,// 接受详情图的
			HttpSession httpSession,// 将ProjectVocabulary存入到Session域
			ModelMap modelMap) throws IOException {
		// 1、完成头图的上传，
		boolean empty = headerPicture.isEmpty();
		if (!empty) {
			// 2、用户确实上传了有内容的文件
			ResultEntity<String> uploadFileToOss = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(),
					ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), headerPicture.getInputStream(),
					ossProperties.getBucketName(), ossProperties.getBucketDomain(),
					headerPicture.getOriginalFilename());
			String result = uploadFileToOss.getResult();
			// 3、判断头图是否上传成功
			if (ResultEntity.SUCCESS.equals(result)) {
				// 4、获取访问路径
				String headerPicturePath = uploadFileToOss.getData();
				projectVo.setHeaderPicturePath(headerPicturePath);
			} else {
				// 如果上传失败，则返回到表单页面显示错误消息
				modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
				return "project-launch";
			}
		}else {
			// 头图不可以为空
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, "头图不可以为空");
			return "project-launch";
		}
		// 上传详情图
		if(detailPictureList == null || detailPictureList.size()==0) {
			// 详情图不可以为空
			modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, "详情图不可以为空");
			return "project-launch";
		
		}
		projectVo.setDetailPicturePathList(new ArrayList<String>());
		for (MultipartFile detailPicture : detailPictureList) {
			empty = detailPicture.isEmpty();
			if (!empty) {
				// 执行详情图上上传
				ResultEntity<String> detailUploadEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(),
						ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(),
						detailPicture.getInputStream(), ossProperties.getBucketName(), ossProperties.getBucketDomain(),
						detailPicture.getOriginalFilename());
				if (ResultEntity.SUCCESS.equals(detailUploadEntity.getResult())) {
					String data = detailUploadEntity.getData();
					projectVo.getDetailPicturePathList().add(data);// 存放到详情图集合
				} else {
					continue;// 详情图不重要，可以忽略某张图片，由用户确认是否再次提交
				}
			}else {
				// 详情图不可以为空
				modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, "详情图单图不可以为空");
				return "project-launch";
			}
		}
		// 将projectCo存入session域
		httpSession.setAttribute(CrowdConstant.ATTR_NAME_TMPLE_PROJECT, projectVo);
		// 去下一个页面 要使用zuul进行访问  收集回报页面
		return "redirect:http://localhost/project/return/info/page";
	}

}

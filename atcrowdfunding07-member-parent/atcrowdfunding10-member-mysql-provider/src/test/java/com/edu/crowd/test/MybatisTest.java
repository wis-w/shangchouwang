package com.edu.crowd.test;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.edu.crowd.entity.po.MemberPO;
import com.edu.crowd.entity.vo.DetailProjectVO;
import com.edu.crowd.entity.vo.DetailReturnVO;
import com.edu.crowd.entity.vo.PraralProjectVo;
import com.edu.crowd.entity.vo.ProtalTypeVO;
import com.edu.crowd.mapper.MemberPOMapper;
import com.edu.crowd.mapper.ProjectPOMapper;

import lombok.extern.slf4j.Slf4j;


/**
 * @author wyg_edu
 * @date 2020年6月21日 下午9:20:53
 * @version v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MybatisTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private MemberPOMapper memberPOMapper;
	
	@Autowired
	private ProjectPOMapper projectPOMapper;
	
	@Test
	public void test4() {
		log.info("*******************start");
		DetailProjectVO selectReturnVoList = projectPOMapper.selectDetailProjectVO(24);
		log.info(selectReturnVoList.getProjectId()+"");
		log.info(selectReturnVoList.getProjectName()+"");
		log.info(selectReturnVoList.getProjectDesc()+"");
		log.info(selectReturnVoList.getFollowerCount()+"");
		log.info(selectReturnVoList.getStatus()+"");
		log.info(selectReturnVoList.getMoney()+"");
		log.info(selectReturnVoList.getSupportMoney()+"");
		log.info(selectReturnVoList.getPercentage()+"");
		log.info(selectReturnVoList.getDeployDate()+"");
		log.info(selectReturnVoList.getSupporterCount()+"");
		log.info(selectReturnVoList.getHeaderPicturePath()+"");
		selectReturnVoList.getDetailPicturePathList().forEach(System.out::println);;
		
		List<DetailReturnVO> detailReturnVOList = selectReturnVoList.getDetailReturnVOList();
		log.info("*******************");
		for (DetailReturnVO detailReturnVO : detailReturnVOList) {
			if(detailReturnVO == null) {
				continue;
			}
			log.info(detailReturnVO.getReturnId()+"");
			log.info(detailReturnVO.getSupportMoney()+"");
			log.info(detailReturnVO.getSingnalPurchase()+"");
			log.info(detailReturnVO.getPurchase()+"");
			log.info(detailReturnVO.getSupporterCount()+"");
			log.info(detailReturnVO.getFreight()+"");
			log.info(detailReturnVO.getReturnDate()+"");
			log.info(detailReturnVO.getFreight()+"");
			log.info(detailReturnVO.getContent()+"");
		}
	}
	
	
	@Test
	public void test3() {
		List<ProtalTypeVO> selectProtalTypeList = projectPOMapper.selectProtalTypeList();
		for (ProtalTypeVO protalTypeVO : selectProtalTypeList) {
			log.info(protalTypeVO.getName()+" "+protalTypeVO.getRemark());
			List<PraralProjectVo> praralProjectVo = protalTypeVO.getPraralProjectVo();
			for (PraralProjectVo PraralProjectVo : praralProjectVo) {
				if(PraralProjectVo == null)
					continue;
				log.info(PraralProjectVo+"");
			}
			log.info("***************************************");
		}
	}
	
	
	@Test
	public void testMapper() {
		BCryptPasswordEncoder accttype = new BCryptPasswordEncoder();
		String encode = accttype.encode("123123");
		MemberPO memberPO = new MemberPO(null, "jack2", encode, "jack", "test@qq.com", 1, 1, "王二小", "321321", 2);
		memberPOMapper.insert(memberPO);
		
	}
	
	@Test
	public void test() throws Exception {
		Connection connection = dataSource.getConnection();
		log.debug(connection+"");
	}

}

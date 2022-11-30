package avicit.demo.stockinfo.service;


import avicit.platform6.api.system.OssClient;
import avicit.platform6.core.exception.BusinessException;
import avicit.platform6.core.mybatis.pagehelper.PageHelper;
import avicit.platform6.core.properties.PlatformConstant;
import avicit.platform6.core.rest.msg.QueryReqBean;
import avicit.platform6.core.rest.msg.QueryRespBean;
import avicit.platform6.commons.utils.ComUtil;
import avicit.platform6.commons.utils.PojoUtil;
import avicit.platform6.commons.utils.StringUtils;
import avicit.platform6.modules.system.syslog.service.SysLogUtil;
import avicit.demo.stockinfo.dao.StockInfoDAO;
import avicit.demo.stockinfo.dto.StockInfoDTO;

import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
* @金航数码科技有限责任公司
* @作者：developer
* @邮箱：developer@avic.com
* @创建时间： 2021-07-28 11:43
* @类说明：STOCK_INFOService
* @修改记录：
*/
@Service
public class StockInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockInfoService.class);

	@Autowired
	private StockInfoDAO stockInfoDAO;

	@Autowired
	private OssClient ossClient;

	/**
	* 按条件分页查询
	* @param queryReqBean 查询条件
	* @param orgIdentity  组织id
	* @param orderBy      排序
	* @return QueryRespBean<StockInfoDTO>
	*/
	public QueryRespBean<StockInfoDTO> searchStockInfoByPage(QueryReqBean<StockInfoDTO> queryReqBean, String orgIdentity, String orderBy) {
		QueryRespBean<StockInfoDTO> queryRespBean = new QueryRespBean<>();
		try {
			PageHelper.startPage(queryReqBean.getPageParameter());
			StockInfoDTO searchParams = queryReqBean.getSearchParams();
			Page<StockInfoDTO> dataList = stockInfoDAO.searchStockInfoByPage(searchParams, orgIdentity, orderBy, queryReqBean.getKeyWord());
			queryRespBean.setResult(dataList);
			return queryRespBean;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException("查询失败");
		}
	}

	/**
	* 按条件查询
	*
	* @param queryReqBean 查询条件
	* @return List<StockInfoDTO>
	*/
	public List<StockInfoDTO> searchStockInfo(QueryReqBean<StockInfoDTO> queryReqBean) {
		try {
			return stockInfoDAO.searchStockInfo(queryReqBean.getSearchParams());
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException("查询失败");
		}
	}

	/**
	* 通过主键查询单条记录
	*
	* @param id 主键id
	* @return StockInfoDTO
	*/
	public StockInfoDTO queryStockInfoByPrimaryKey(String id) {
		try {
			StockInfoDTO stockInfoDTO = stockInfoDAO.findStockInfoById(id);
			//记录日志
			if (stockInfoDTO != null) {
				SysLogUtil.log4Query(stockInfoDTO);
			}
			return stockInfoDTO;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException("通过主键查询单条记录失败");
		}
	}

	/**
	* 新增对象
	*
	* @param stockInfoDTO 保存对象
	* @return String
	*/
	public String insertStockInfo(StockInfoDTO stockInfoDTO) {
		try {
			stockInfoDTO.setId(ComUtil.getId());
			PojoUtil.setSysProperties(stockInfoDTO, PlatformConstant.OpType.insert);
			stockInfoDAO.insertStockInfo(stockInfoDTO);
			//记录日志
			SysLogUtil.log4Insert(stockInfoDTO);
			return stockInfoDTO.getId();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException("新增对象失败");
		}
	}

	/**
	* 批量新增对象
	*
	* @param dtoList 保存对象集合
	* @return int
	*/
	public int insertStockInfoList(List<StockInfoDTO> dtoList) {
		List<StockInfoDTO> beanList = new ArrayList<>();
		for (StockInfoDTO stockInfoDTO : dtoList) {
			stockInfoDTO.setId(ComUtil.getId());
			PojoUtil.setSysProperties(stockInfoDTO, PlatformConstant.OpType.insert);
			//记录日志
			SysLogUtil.log4Insert(stockInfoDTO);
			beanList.add(stockInfoDTO);
		}
		try {
			return stockInfoDAO.insertStockInfoList(beanList);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException("批量新增对象失败");
		}
	}

	/**
	* 修改对象全部字段
	*
	* @param stockInfoDTO 修改对象
	* @return int
	*/
	public int updateStockInfo(StockInfoDTO stockInfoDTO) {
		try {
			int ret = stockInfoDAO.updateStockInfoAll(getUpdateDto(stockInfoDTO));
			if (ret == 0) {
				throw new BusinessException("数据失效，请重新更新");
			}
			return ret;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException("修改对象失败");
		}
	}

	/**
	* 修改对象部分字段
	*
	* @param stockInfoDTO 修改对象
	* @return int
	*/
	public int updateStockInfoSensitive(StockInfoDTO stockInfoDTO) {
		try {
			int count = stockInfoDAO.updateStockInfoSensitive(getUpdateDto(stockInfoDTO));
			if (count == 0) {
				throw new BusinessException("数据失效，请重新更新");
			}
			return count;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException("修改对象失败");
		}
	}

	/**
	* 内部方法，获取修改的dto对象
	*
	* @param stockInfoDTO
	* @return
	*/
	private StockInfoDTO getUpdateDto(StockInfoDTO stockInfoDTO) {
		StockInfoDTO oldDTO = findById(stockInfoDTO.getId());
		if (oldDTO == null) {
			throw new BusinessException("数据不存在");
		}
		//记录日志
		SysLogUtil.log4Update(stockInfoDTO, oldDTO);
		PojoUtil.setSysProperties(stockInfoDTO, PlatformConstant.OpType.update);
		PojoUtil.copyProperties(oldDTO, stockInfoDTO, true);
		return oldDTO;
	}

	/**
	* 批量更新对象
	*
	* @param dtoList 修改对象集合
	* @return int
	*/
	public int updateStockInfoList(List<StockInfoDTO> dtoList) {
		List<StockInfoDTO> beanList = new ArrayList<>();
		for (StockInfoDTO stockInfoDTO : dtoList) {
			StockInfoDTO oldDTO = getUpdateDto(stockInfoDTO);
			beanList.add(oldDTO);
		}
		try {
			return stockInfoDAO.updateStockInfoList(beanList);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException("批量更新对象失败");
		}
	}

	/**
	* 按主键单条删除
	*
	* @param id 主键id
	* @return int
	*/
	public int deleteStockInfoById(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new BusinessException("删除失败！传入的参数主键为null");
		}
		try {
			//记录日志
			StockInfoDTO stockInfoDTO = findById(id);
			if (stockInfoDTO == null) {
				throw new BusinessException("删除失败！对象不存在");
			}
			SysLogUtil.log4Delete(stockInfoDTO);
			//删除业务数据
			int count = stockInfoDAO.deleteStockInfoById(id);
			//删除附件
			ossClient.deleteAttachByFormId(id);
			return count;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new BusinessException("删除失败");
		}
	}

	/**
	* 批量删除数据
	*
	* @param ids 逗号分隔的id串
	* @return int
	*/
	public int deleteStockInfoByIds(String ids) {
		int result = 0;
		if(StringUtils.isNotEmpty(ids)){
			for (String id : ids.split(",")) {
				deleteStockInfoById(id);
				result++;
			}
		}
		return result;
	}

	/**
	* 日志专用，内部方法，不再记录日志
	*
	* @param id 主键id
	* @return StockInfoDTO
	*/
	private StockInfoDTO findById(String id) {
		return stockInfoDAO.findStockInfoById(id);
	}
}


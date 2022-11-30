package avicit.demo.stockinfo.rest;

import avicit.platform6.api.system.impl.SystemConstant;
import avicit.platform6.core.context.ThreadContextHelper;
import avicit.platform6.core.rest.msg.QueryReqBean;
import avicit.platform6.core.rest.msg.QueryRespBean;
import avicit.platform6.core.rest.msg.ResponseMsg;
import avicit.platform6.commons.utils.BusinessUtil;
import avicit.platform6.commons.utils.JsonHelper;
import avicit.platform6.commons.utils.StringUtils;
import avicit.demo.stockinfo.dto.StockInfoDTO;
import avicit.demo.stockinfo.service.StockInfoService;
import com.fasterxml.jackson.core.type.TypeReference;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
* @金航数码科技有限责任公司
* @作者：developer
* @邮箱：developer@avic.com
* @创建时间： 2021-07-28 11:43
* @类说明：STOCK_INFORest
* @修改记录：
*/
@RestController
@Api(tags = "stockInfo", description = "STOCK_INFO")
@RequestMapping("/api/demo/stockinfos")
public class StockInfoRest {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockInfoRest.class);

	@Autowired
	private StockInfoService stockInfoService;


	/**
	* 按条件分页查询
	* @param queryReqBean 查询条件
	* @return ResponseMsg<QueryRespBean < StockInfoDTO>>
	*/
	@PostMapping("/search-by-page/v1")
	@ApiOperation(value = "按条件分页查询")
	public ResponseMsg<QueryRespBean<StockInfoDTO>> searchByPage(@ApiParam(value = "查询条件", name = "queryReqBean") @RequestBody QueryReqBean<StockInfoDTO> queryReqBean) {
		ResponseMsg<QueryRespBean<StockInfoDTO>> responseMsg = new ResponseMsg<>();
		if (StringUtils.isNotEmpty(queryReqBean.getSidx()) && StringUtils.isNotEmpty(queryReqBean.getSord())) {
			String sordExp = BusinessUtil.getSortExpColumnName(queryReqBean.getSidx(), queryReqBean.getSord(), StockInfoDTO.class);
			if (StringUtils.isNotEmpty(sordExp)) {
				queryReqBean.setSortExp(sordExp);
			}
		}
		if (StringUtils.isNotEmpty(queryReqBean.getKeyWord())) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			StockInfoDTO searchKeyWordParam = JsonHelper.getInstance().readValue(queryReqBean.getKeyWord(), dateFormat, new TypeReference<StockInfoDTO>() {
			});
			queryReqBean.setSearchParams(searchKeyWordParam);
		}
		//获取token
		String token = ThreadContextHelper.getToken();
		QueryRespBean<StockInfoDTO> result = stockInfoService.searchStockInfoByPage(queryReqBean, ThreadContextHelper.getOrgId(), queryReqBean.getSortExp());
		responseMsg.setResponseBody(result);
		return responseMsg;
	}

	/**
	* 按条件不分页查询
	*
	* @param queryReqBean 查询条件
	* @return ResponseMsg<List < StockInfoDTO>>
	*/
	@PostMapping("/search/v1")
	@ApiOperation(value = "按条件不分页查询")
	public ResponseMsg<List<StockInfoDTO>> search(@ApiParam(value = "查询条件", name = "queryReqBean") @RequestBody QueryReqBean<StockInfoDTO> queryReqBean) {
		ResponseMsg<List<StockInfoDTO>> responseMsg = new ResponseMsg<>();
		List<StockInfoDTO> result = stockInfoService.searchStockInfo(queryReqBean);
		responseMsg.setResponseBody(result);
		return responseMsg;
	}

	/**
	* 通过主键查询单条记录
	*
	* @param id 主键id
	* @return ResponseMsg<StockInfoDTO>
	*/
	@GetMapping("/get/{id}/v1")
	@ApiOperation(value = "通过主键查询单条记录")
	public ResponseMsg<StockInfoDTO> get(@ApiParam(value = "主键id", name = "id") @PathVariable("id") String id) {
		ResponseMsg<StockInfoDTO> responseMsg = new ResponseMsg<>();
		StockInfoDTO stockInfo = stockInfoService.queryStockInfoByPrimaryKey(id);
		responseMsg.setResponseBody(stockInfo);
		return responseMsg;
	}

	/**
	* 新增对象
	*
	* @param stockInfo 保存对象
	* @return ResponseMsg<String>
	*/
	@PostMapping("/save/v1")
	@ApiOperation(value = "新增对象")
	public ResponseMsg<String> save(@ApiParam(value = "保存对象", name = "stockInfo") @RequestBody StockInfoDTO stockInfo) {
		ResponseMsg<String> responseMsg = new ResponseMsg<>();
		stockInfo.setOrgIdentity(ThreadContextHelper.getOrgId());
		if (StringUtils.isEmpty(stockInfo.getId())) {
			responseMsg.setResponseBody(stockInfoService.insertStockInfo(stockInfo));
		} else {
			responseMsg.setResponseBody(String.valueOf(stockInfoService.updateStockInfo(stockInfo)));
		}
		return responseMsg;
	}

	/**
	* 修改部分对象字段
	*
	* @param stockInfo 修改对象
	* @return ResponseMsg<Integer>
	*/
	@PutMapping("/update-sensitive/v1")
	@ApiOperation(value = "修改部分对象字段")
	public ResponseMsg<Integer> updateSensitive(@ApiParam(value = "修改对象", name = "stockInfo") @RequestBody StockInfoDTO stockInfo) {
		ResponseMsg<Integer> responseMsg = new ResponseMsg<>();
		responseMsg.setResponseBody(stockInfoService.updateStockInfoSensitive(stockInfo));
		return responseMsg;
	}

	/**
	* 修改全部对象字段
	*
	* @param stockInfo 修改对象
	* @return ResponseMsg<Integer>
	*/
	@PutMapping("/update-all/v1")
	@ApiOperation(value = "修改全部对象字段")
	public ResponseMsg<Integer> updateAll(@ApiParam(value = "修改对象", name = "stockInfo") @RequestBody StockInfoDTO stockInfo) {
		ResponseMsg<Integer> responseMsg = new ResponseMsg<>();
		responseMsg.setResponseBody(stockInfoService.updateStockInfo(stockInfo));
		return responseMsg;
	}

	/**
	* 按主键单条删除
	*
	* @param id 主键id
	* @return ResponseMsg<Integer>
	*/
	@DeleteMapping("/delete-by-id/{id}/v1")
	@ApiOperation(value = "按主键单条删除")
	public ResponseMsg<Integer> deleteById(@ApiParam(value = "主键id", name = "id") @PathVariable("id") String id) {
		ResponseMsg<Integer> responseMsg = new ResponseMsg<>();
		responseMsg.setResponseBody(stockInfoService.deleteStockInfoById(id));
		return responseMsg;
	}

	/**
	* 批量删除
	*
	* @param ids 逗号分隔的id串
	* @return ResponseMsg<Integer>
	*/
	@DeleteMapping("/delete-by-ids/{ids}/v1")
	@ApiOperation(value = "批量删除")
	public ResponseMsg<Integer> deleteByIds(@ApiParam(value = "逗号分隔的id串", name = "ids") @PathVariable("ids") String ids) {
		ResponseMsg<Integer> responseMsg = new ResponseMsg<>();
		responseMsg.setResponseBody(stockInfoService.deleteStockInfoByIds(ids));
		return responseMsg;
	}

}


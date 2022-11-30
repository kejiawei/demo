package avicit.demo.stockinfo.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import avicit.platform6.commons.utils.StringUtils;
import avicit.platform6.core.context.ThreadContextHelper;
import avicit.platform6.commons.utils.JsonHelper;
import avicit.platform6.core.properties.PlatformConstant.OpResult;
import avicit.platform6.core.rest.msg.PageParameter;
import avicit.platform6.core.rest.msg.QueryReqBean;
import avicit.platform6.core.rest.msg.QueryRespBean;

import avicit.demo.stockinfo.dto.StockInfoDTO;
import avicit.demo.stockinfo.api.StockInfoApi;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @金航数码科技有限责任公司
 * @作者：developer
 * @邮箱：developer@avic.com
 * @创建时间： 2021-07-28 11:43
 * @类说明：STOCK_INFOController
 * @修改记录： 
 */
@Controller
@Scope("prototype")
@RequestMapping("demo/stockInfo/stockInfoController")
public class StockInfoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(StockInfoController.class);

	@Autowired
	private StockInfoApi stockInfoApi;

	/**
	 * 跳转到列表页面
	 * @return ModelAndView
	 */
	@RequestMapping(value = "toStockInfoManage")
	public ModelAndView toStockInfoManage() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("avic/demo/stockinfo/StockInfoManage");
		mav.addObject("url", "platform/demo/stockInfo/stockInfoController/operation/");
		return mav;
	}


	/**
	 * 列表页面分页查询
	 * @param pageParameter 查询条件
	 * @param request 请求
	 * @return Map<String,Object>
	 */
	@RequestMapping(value = "/operation/getStockInfosByPage")
	@ResponseBody
	public Map<String, Object> togetStockInfoByPage(PageParameter pageParameter, HttpServletRequest request) {
		QueryReqBean<StockInfoDTO> queryReqBean = new QueryReqBean<StockInfoDTO>();
		queryReqBean.setPageParameter(pageParameter);
		//表单数据
		String json = ServletRequestUtils.getStringParameter(request, "param", "");
		//字段查询条件
		String keyWord = ServletRequestUtils.getStringParameter(request, "keyWord", "");
		//排序方式
		String sord = ServletRequestUtils.getStringParameter(request, "sord", "");
		//排序字段
		String sidx = ServletRequestUtils.getStringParameter(request, "sidx", "");
		if (StringUtils.isNotEmpty(keyWord)) {
			json = keyWord;
		}
		HashMap<String, Object> map = new HashMap<String, Object>();
		StockInfoDTO param = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		QueryRespBean<StockInfoDTO> result = null;
		if (json != null && !"".equals(json)) {
			param = JsonHelper.getInstance().readValue(json, dateFormat, new TypeReference<StockInfoDTO>() {
			});
			queryReqBean.setSearchParams(param);
		}
		queryReqBean.setSord(sord);
		queryReqBean.setSidx(sidx);
		queryReqBean.setKeyWord(keyWord);
		try {
			result = stockInfoApi.searchByPage(queryReqBean);
			map.put("records", result.getPageParameter().getTotalCount());
			map.put("page", result.getPageParameter().getPage());
			map.put("total", result.getPageParameter().getTotalPage());
			map.put("rows", result.getResult());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		}
		return map;
	}

	/**
	* 根据传入的的类型跳转到对应页面
	* @param type，包括三个值，分别是Add:新建；Edit：编辑；Detail：详情
	* @param id 数据的id
	* @return ModelAndView
	* @throws Exception
	*/
	@RequestMapping(value = "/operation/{type}/{id}")
	public ModelAndView toOpertionPage(@PathVariable String type, @PathVariable String id)
			throws Exception {
		ModelAndView mav = new ModelAndView();
		//编辑窗口或者详细页窗口
		if (!"Add".equals(type)) {
			StockInfoDTO dto = stockInfoApi.get(id);
			mav.addObject("stockInfoDTO", dto);
		}
		mav.setViewName("avic/demo/stockinfo/StockInfo" + type);
		return mav;
	}

	/**
	 * 保存数据
	 * @param request 请求
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/operation/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveStockInfo(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		String jsonData = ServletRequestUtils.getStringParameter(request, "data", "");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			StockInfoDTO stockInfoDTO = JsonHelper.getInstance().readValue(jsonData, dateFormat,
					new TypeReference<StockInfoDTO>() {
					});
			if (StringUtils.isEmpty(stockInfoDTO.getId())) {
				//新增
				stockInfoDTO.setOrgIdentity(ThreadContextHelper.getOrgId());
				String id = stockInfoApi.save(stockInfoDTO);
				map.put("id", id);
			} else {
				//修改
				stockInfoApi.updateSensitive(stockInfoDTO);
				map.put("id", stockInfoDTO.getId());
			}
			map.put("flag", OpResult.success);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			map.put("flag", OpResult.failure);
		}
		return map;
	}

	/**
	 * 按照id批量删除数据
	 * @param ids 逗号分隔的id串
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/operation/delete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteStockInfo(@RequestBody String ids) {
		Map<String, Object> map = new HashMap<>();
		try {
			stockInfoApi.deleteByIds(ids);
			map.put("flag", OpResult.success);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			map.put("flag", OpResult.failure);
		}
		return map;
	}
}


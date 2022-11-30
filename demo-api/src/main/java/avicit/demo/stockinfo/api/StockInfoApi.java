package avicit.demo.stockinfo.api;

import avicit.platform6.core.restclient.RestClient;
import avicit.platform6.core.restclient.RestClientUtils;
import avicit.platform6.core.rest.msg.QueryReqBean;
import avicit.platform6.core.rest.msg.QueryRespBean;
import avicit.platform6.core.rest.msg.ResponseMsg;
import avicit.demo.stockinfo.dto.StockInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;


import java.util.List;

/**
* @金航数码科技有限责任公司
* @作者：developer
* @邮箱：developer@avic.com
* @创建时间： 2021-07-28 11:43
* @类说明：STOCK_INFOApi
* @修改记录：
*/
@Component
public class StockInfoApi {

	/**
	* 服务编码
	*/
	private static final String SERVICE_CODE = "demo";
	private static final String BASE_PATH = "/api/demo/stockinfos";

	@Autowired
	private RestClient restClient;

	/**
	* 按条件分页查询
	* 123
	* @param queryReqBean 查询条件
	* @return QueryRespBean<StockInfoDTO>
	*/
	public QueryRespBean<StockInfoDTO> searchByPage(QueryReqBean<StockInfoDTO> queryReqBean) {
		String url = BASE_PATH + "/search-by-page/v1";
		ResponseMsg<QueryRespBean<StockInfoDTO>> responseMsg = restClient.doPost(SERVICE_CODE, url, queryReqBean, new ParameterizedTypeReference<ResponseMsg<QueryRespBean<StockInfoDTO>>>() {
		});
		return RestClientUtils.getResponseBody(responseMsg);
	}

	/**
	* 按条件不分页查询
	*
	* @param queryReqBean 查询条件
	* @return List <StockInfoDTO>
	*/
	public List<StockInfoDTO> search(QueryReqBean<StockInfoDTO> queryReqBean) {
		String url = BASE_PATH + "/search/v1";
		ResponseMsg<List<StockInfoDTO>> responseMsg = restClient.doPost(SERVICE_CODE, url, queryReqBean, new ParameterizedTypeReference<ResponseMsg<List<StockInfoDTO>>>() {
		});
		return RestClientUtils.getResponseBody(responseMsg);
	}

	/**
	* 通过主键查询单条记录
	*
	* @param id 主键id
	* @return StockInfoDTO
	*/
	public StockInfoDTO get(String id) {
		String url = BASE_PATH + "/get/"+ id + "/v1/" ;
		ResponseMsg<StockInfoDTO> responseMsg = restClient.doGet(SERVICE_CODE, url, new ParameterizedTypeReference<ResponseMsg<StockInfoDTO>>() {
		});
		return RestClientUtils.getResponseBody(responseMsg);
	}

	/**
	* 新增对象
	*
	* @param stockInfoDTO 保存对象
	* @return String
	*/
	public String save(StockInfoDTO stockInfoDTO) {
		String url = BASE_PATH + "/save/v1";
		ResponseMsg<String> responseMsg = restClient.doPost(SERVICE_CODE, url, stockInfoDTO, new ParameterizedTypeReference<ResponseMsg<String>>() {
		});
		return RestClientUtils.getResponseBody(responseMsg);
	}

	/**
	* 修改部分对象字段
	*
	* @param stockInfoDTO 修改对象
	* @return Integer
	*/
	public Integer updateSensitive(StockInfoDTO stockInfoDTO) {
		String url = BASE_PATH + "/update-sensitive/v1";
		ResponseMsg<Integer> responseMsg = restClient.doPut(SERVICE_CODE, url, stockInfoDTO, new ParameterizedTypeReference<ResponseMsg<Integer>>() {
		});
		return RestClientUtils.getResponseBody(responseMsg);
	}

	/**
	* 修改全部对象字段
	*
	* @param stockInfoDTO 修改对象
	* @return Integer
	*/
	public Integer updateAll(StockInfoDTO stockInfoDTO) {
		String url = BASE_PATH + "/update-all/v1";
		ResponseMsg<Integer> responseMsg = restClient.doPut(SERVICE_CODE, url, stockInfoDTO, new ParameterizedTypeReference<ResponseMsg<Integer>>() {
		});
		return RestClientUtils.getResponseBody(responseMsg);
	}

	/**
	* 按主键单条删除
	*
	* @param id 主键id
	* @return Integer
	*/
	public Integer deleteById(String id) {
		String url = BASE_PATH + "/delete-by-id/"+ id + "/v1" ;
		ResponseMsg<Integer> responseMsg = restClient.doDelete(SERVICE_CODE, url, new ParameterizedTypeReference<ResponseMsg<Integer>>() {
		});
		return RestClientUtils.getResponseBody(responseMsg);
	}

	/**
	* 批量删除
	* @param ids 逗号分隔的id串
	* @return Integer
	*/
	public Integer deleteByIds(String ids) {
		String url = BASE_PATH + "/delete-by-ids/"+ ids +"/v1";
		ResponseMsg<Integer> responseMsg = restClient.doDelete(SERVICE_CODE, url, new ParameterizedTypeReference<ResponseMsg<Integer>>() {
		});
		return RestClientUtils.getResponseBody(responseMsg);
	}
}


package avicit.demo.stockinfo.dao;

import avicit.demo.stockinfo.dto.StockInfoDTO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @金航数码科技有限责任公司
* @作者：developer
* @邮箱：developer@avic.com
* @创建时间： 2021-07-28 11:43
* @类说明：STOCK_INFODao
* @修改记录：
*/
public interface StockInfoDAO {

	/**
	* 分页查询
	* @param stockInfoDTO 查询对象
	* @param orgIdentity       组织id
	* @param orderBy           排序条件
	* @param keyWords          查询关键字
	* @return Page<StockInfoDTO>
	*/
	public Page<StockInfoDTO> searchStockInfoByPage(@Param("bean") StockInfoDTO stockInfoDTO, @Param("orgIdentity") String orgIdentity, @Param("pOrderBy") String pOrderBy, @Param("keyWords") String keyWords);

	/**
	* 不分页查询
	* @param stockInfoDTO 查询对象
	* @return List<StockInfoDTO>
	*/
	public List<StockInfoDTO> searchStockInfo(@Param("bean") StockInfoDTO stockInfoDTO);

	/**
	* 主键查询
	* @param id 主键id
	* @return StockInfoDTO
	*/
	public StockInfoDTO findStockInfoById(String id);

	/**
	* 新增
	* @param stockInfoDTO 保存对象
	* @return int
	*/
	public int insertStockInfo(StockInfoDTO stockInfoDTO);

	/**
	* 批量新增
	* @param dtoList 保存对象集合
	* @return int
	*/
	public int insertStockInfoList(@Param("dtoList") List<StockInfoDTO> dtoList);

	/**
	* 更新部分对象
	* @param stockInfoDTO 更新对象
	* @return int
	*/
	public int updateStockInfoSensitive(StockInfoDTO stockInfoDTO);

	/**
	* 更新全部对象
	* @param stockInfoDTO 更新对象
	* @return int
	*/
	public int updateStockInfoAll(StockInfoDTO stockInfoDTO);

	/**
	* 批量更新对象
	* @param dtoList 批量更新对象集合
	* @return int
	*/
	public int updateStockInfoList(@Param("dtoList") List<StockInfoDTO> dtoList);

	/**
	* 按主键删除
	* @param id 主键id
	* @return int
	*/
	public int deleteStockInfoById(String id);
}


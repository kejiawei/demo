package avicit.demo.stockinfo.dto;

import avicit.platform6.core.annotation.log.FieldRemark;
import avicit.platform6.core.annotation.log.Id;
import avicit.platform6.core.annotation.log.LogField;
import avicit.platform6.core.annotation.log.PojoRemark;
import avicit.platform6.core.domain.BeanDTO;
import avicit.platform6.core.properties.PlatformConstant;
import avicit.platform6.commons.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
* @金航数码科技有限责任公司
* @作者：developer
* @邮箱：developer@avic.com
* @创建时间： 2021-07-28 11:43
* @类说明：STOCK_INFODto
* @修改记录：
*/
@ApiModel(value = "StockInfoDTO", description = "STOCK_INFO")
@PojoRemark(table="STOCK_INFO", object="StockInfoDTO", name="STOCK_INFO")
public class StockInfoDTO extends BeanDTO{
	private static final long serialVersionUID = 1L;

	/**
	* ID
	*/
	@Id
	@LogField
	@ApiModelProperty(value = "ID", name = "id")
	@FieldRemark(column="ID", field="id", name="ID")
	private java.lang.String id;

	/**
	* CREATION_DATE起
	*/
	private java.util.Date creationDateBegin;
	
	/**
	* CREATION_DATE止
	*/
	private java.util.Date creationDateEnd;

	/**
	* LAST_UPDATE_DATE起
	*/
	private java.util.Date lastUpdateDateBegin;
	
	/**
	* LAST_UPDATE_DATE止
	*/
	private java.util.Date lastUpdateDateEnd;

	/**
	* 仓库
	*/
	@ApiModelProperty(value = "仓库", name = "name")
	@FieldRemark(column="NAME", field="name", name="仓库")
	private java.lang.String name;

	/**
	* 物料编码
	*/
	@ApiModelProperty(value = "物料编码", name = "cnCode")
	@FieldRemark(column="CN_CODE", field="cnCode", name="物料编码")
	private java.lang.String cnCode;

	/**
	* 库存数
	*/
	@ApiModelProperty(value = "库存数", name = "storageNumber")
	@FieldRemark(column="STORAGE_NUMBER", field="storageNumber", name="库存数")
	private java.math.BigDecimal storageNumber;

	/**
	* 备注
	*/
	@ApiModelProperty(value = "备注", name = "remark")
	@FieldRemark(column="REMARK", field="remark", name="备注")
	private java.lang.String remark;


	public java.lang.String getId(){
		return id;
	}

	public void setId(java.lang.String id){
		this.id = id;
	}

	public java.util.Date getCreationDateBegin(){
		return creationDateBegin;
	}

	public void setCreationDateBegin(java.util.Date creationDateBegin){
		this.creationDateBegin = creationDateBegin;
	}

	public java.util.Date getCreationDateEnd(){
		return creationDateEnd;
	}

	public void setCreationDateEnd(java.util.Date creationDateEnd){
		this.creationDateEnd = creationDateEnd;
	}

	public java.util.Date getLastUpdateDateBegin(){
		return lastUpdateDateBegin;
	}

	public void setLastUpdateDateBegin(java.util.Date lastUpdateDateBegin){
		this.lastUpdateDateBegin = lastUpdateDateBegin;
	}

	public java.util.Date getLastUpdateDateEnd(){
		return lastUpdateDateEnd;
	}

	public void setLastUpdateDateEnd(java.util.Date lastUpdateDateEnd){
		this.lastUpdateDateEnd = lastUpdateDateEnd;
	}

	public java.lang.String getName(){
		return name;
	}

	public void setName(java.lang.String name){
		this.name = name;
	}

	public java.lang.String getCnCode(){
		return cnCode;
	}

	public void setCnCode(java.lang.String cnCode){
		this.cnCode = cnCode;
	}

	public java.math.BigDecimal getStorageNumber(){
		return storageNumber;
	}

	public void setStorageNumber(java.math.BigDecimal storageNumber){
		this.storageNumber = storageNumber;
	}

	public java.lang.String getRemark(){
		return remark;
	}

	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}

	@Override
	public String getLogFormName() {
		if (StringUtils.isEmpty(super.logFormName)) {
			return "STOCK_INFO";
		}else{
			return super.logFormName;
		}
	}

	@Override
	public String getLogTitle() {
		if (StringUtils.isEmpty(super.logTitle)) {
			return "STOCK_INFO";
		}else{
			return super.logTitle;
		}
	}

	@Override
	public PlatformConstant.LogType getLogType() {
		if (super.logType == null) {
			return PlatformConstant.LogType.module_operate;
		} else {
			return super.logType;
		}
	}

}


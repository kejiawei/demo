<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="avicit.platform6.commons.utils.ViewUtil"%>
<%@taglib prefix="pt6" uri="/WEB-INF/platform6.tld"%>
<%@taglib prefix="sec" uri="/WEB-INF/shiro.tld"%>
<% 
String importlibs = "common,table,form";	
%>
<!DOCTYPE html>
<html>
<head>
<!-- ControllerPath = "platform/demo/stockInfo/stockInfoController/toStockInfoManage" -->
<title>管理</title>
<base href="<%=ViewUtil.getRequestPath(request)%>">
<jsp:include page="/avicit/platform6/h5component/common/h5uiinclude-css.jsp">
<jsp:param value="<%=importlibs%>" name="importlibs"/>
</jsp:include>
</head>
<body>
<div id="tableToolbar" class="toolbar">
	<div class="toolbar-left">
		<sec:accesscontrollist hasPermission="3" domainObject="formdialog_stockInfo_button_add" permissionDes="添加">
	  		<a id="stockInfo_insert" href="javascript:void(0)" class="btn btn-primary form-tool-btn btn-sm btn-point" role="button" title="添加"><i class="fa fa-plus"></i> 添加</a>
		</sec:accesscontrollist>
		<sec:accesscontrollist hasPermission="3" domainObject="formdialog_stockInfo_button_edit" permissionDes="编辑">
			<a id="stockInfo_modify" href="javascript:void(0)" class="btn btn-primary form-tool-btn btn-sm" role="button" title="编辑"><i class="fa fa-file-text-o"></i> 编辑</a>
		</sec:accesscontrollist>
		<sec:accesscontrollist hasPermission="3" domainObject="formdialog_stockInfo_button_delete" permissionDes="删除">
			<a id="stockInfo_del" href="javascript:void(0)" class="btn btn-primary form-tool-btn btn-sm" role="button" title="删除"><i class="fa fa-trash-o"></i> 删除</a>
		</sec:accesscontrollist>
	</div>
    <div class="toolbar-right">
	    <div class="input-group form-tool-search">
     		<input type="text" name="stockInfo_keyWord" id="stockInfo_keyWord" style="width:240px;" class="form-control input-sm" placeholder="请输入查询条件">
     		<label id="stockInfo_searchPart" class="icon icon-search form-tool-searchicon"></label>
   		</div>
   		<div class="input-group-btn form-tool-searchbtn">
   			<a id="stockInfo_searchAll" href="javascript:void(0)" class="btn btn-defaul btn-sm" role="button" >高级查询 <span class="caret"></span></a>
   		</div>
    </div>
</div>					
<table id="stockInfojqGrid"></table>
<div id="jqGridPager"></div>
</body>

<!-- 高级查询 -->
	<div id="stockInfoSearchDialog" style="overflow: auto;display: none">
		<form id="form" style="padding: 10px;">
			<table class="form_commonTable">
				<tr>
					<th width="10%">
						<label for="name">仓库:</label>
					</th>
					<td width="39%">
						<input class="form-control input-sm" type="text" name="name"  id="name" />
					</td>
					<th width="10%">
						<label for="cnCode">物料编码:</label>
					</th>
					<td width="39%">
						<input class="form-control input-sm" type="text" name="cnCode"  id="cnCode" />
					</td>
				</tr>
				<tr>
					<th>
						<label for="storageNumber">库存数:</label>
					</th>
					<td>
						<div class="input-group input-group-sm spinner" data-trigger="spinner">
							<input  class="form-control"  type="text" name="storageNumber" id="storageNumber" data-min="-99999999999999999999.99999999" data-max="99999999999999999999.99999999" data-step="1" data-precision="0">
							<span class="input-group-addon">
								<a href="javascript:;" class="spin-up" data-spin="up"><i class="glyphicon glyphicon-triangle-top"></i></a>
								<a href="javascript:;" class="spin-down" data-spin="down"><i class="glyphicon glyphicon-triangle-bottom"></i></a>
							</span>
						</div>
					</td>
					<th>
						<label for="remark">备注:</label>
					</th>
					<td>
						<input class="form-control input-sm" type="text" name="remark"  id="remark" />
					</td>
				</tr>
			</table>
		</form>
	</div>
<jsp:include page="/avicit/platform6/h5component/common/h5uiinclude-js.jsp">
	<jsp:param value="<%=importlibs%>" name="importlibs"/>
</jsp:include>
<script src="avic/demo/stockinfo/js/StockInfo.js?v=${jsversion}" type="text/javascript"></script>
<script type="text/javascript">
var stockInfo;
function formatValue(cellvalue, options, rowObject) {
		return '<a href="javascript:void(0);" onclick="stockInfo.detail(\''+rowObject.id+'\');">'+cellvalue+'</a>';
 }
function formatDateForHref(cellvalue, options, rowObject){
	var thisDate = format(cellvalue);
	return '<a href="javascript:void(0);" onclick="stockInfo.detail(\''+rowObject.id+'\');">'+thisDate+'</a>';
}
//清空日期值
function clearCommonSelectValue(element) {
	$(element).siblings("input").val("");
}
		
$(document).ready(function () {
	var dataGridColModel =  [
		{ label : 'id', name : 'id', key : true, width : 75, hidden : true},
		{ label : '仓库', name : 'name',formatter:formatValue,width: 150,align: 'left'},
		{ label : '物料编码', name : 'cnCode',width: 150,align: 'left'},
		{ label : '库存数', name : 'storageNumber',width: 150,align: 'right'},
		{ label : '备注', name : 'remark', width: 150,align: 'left'}
	];

	var searchNames = new Array();
	var searchTips = new Array();
	searchNames.push("name");
	searchTips.push("仓库");
	searchNames.push("cnCode");
	searchTips.push("物料编码");
	var searchC = searchTips.length == 2 ? '或' + searchTips[1] : "";
	$('#stockInfo_keyWord').attr('placeholder','请输入' + searchTips[0] + searchC);
	
	stockInfo= new StockInfo('stockInfojqGrid','${url}','stockInfoSearchDialog','form','stockInfo_keyWord',searchNames,dataGridColModel);
	//添加按钮绑定事件
	$('#stockInfo_insert').bind('click', function(){
		stockInfo.insert();
	});
	//编辑按钮绑定事件
	$('#stockInfo_modify').bind('click', function(){
		stockInfo.modify();
	});
	//删除按钮绑定事件
	$('#stockInfo_del').bind('click', function(){  
		stockInfo.del();
	});
	//查询按钮绑定事件
	$('#stockInfo_searchPart').bind('click', function(){
		stockInfo.searchByKeyWord();
	});
	//打开高级查询框
	$('#stockInfo_searchAll').bind('click', function(){
		stockInfo.openSearchForm(this);
	});			
});

</script>
</html>


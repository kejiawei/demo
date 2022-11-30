<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec" uri="/WEB-INF/shiro.tld"%>
<%@taglib prefix="pt6" uri="/WEB-INF/platform6.tld"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="avicit.platform6.commons.utils.ViewUtil"%>
<%
	String importlibs = "common,form,fileupload";
%>
<!DOCTYPE html>
<html>
<head>
<title>详情</title>
<base href="<%=ViewUtil.getRequestPath(request)%>">
<jsp:include page="/avicit/platform6/h5component/common/h5uiinclude-css.jsp">
<jsp:param value="<%=importlibs%>" name="importlibs"/>
</jsp:include>
</head>
<body class="easyui-layout" fit="true">
	<div data-options="region:'center',split:true,border:false">
		<form id='form'>
			<input type="hidden" name="id" value="<c:out value='${stockInfoDTO.id}'/>" />
			<input type="hidden" name="version" value="<c:out  value='${stockInfoDTO.version}'/>"/>
			<table class="form_commonTable">
				<tr>
					<th width="15%">
						<label for="name">仓库:</label></th>
					<td width="34%">
						<input class="form-control input-sm" type="text" name="name"  id="name" value="<c:out value='${stockInfoDTO.name}'/>">
   					</td>
					<th width="15%">
						<label for="cnCode">物料编码:</label></th>
					<td width="34%">
						<input class="form-control input-sm" type="text" name="cnCode"  id="cnCode" value="<c:out value='${stockInfoDTO.cnCode}'/>">
   					</td>
				</tr>
    			<tr>
					<th>
						<label for="storageNumber">库存数:</label></th>
					<td>
						<div class="input-group input-group-sm spinner" data-trigger="spinner">
							<input  class="form-control"  type="text" name="storageNumber" id="storageNumber"  data-min="-999999999999" data-max="999999999999" data-step="1" data-precision="8" value="<c:out value='${stockInfoDTO.storageNumber}'/>">
							<span class="input-group-addon">
								<a href="javascript:;" class="spin-up" data-spin="up"><i class="glyphicon glyphicon-triangle-top"></i></a>
								<a href="javascript:;" class="spin-down" data-spin="down"><i class="glyphicon glyphicon-triangle-bottom"></i></a>
							</span>
						</div>
   					</td>
					<th>
						<label for="remark">备注:</label></th>
					<td>
						<input class="form-control input-sm" type="text" name="remark"  id="remark" value="<c:out value='${stockInfoDTO.remark}'/>">
   					</td>
				</tr>
				<tr>
					<th>
						<label for="attachment">附件</label>
					</th>
					<td colspan="3">
						<div id="attachment"></div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false" style="height: 50px;">
		<div id="toolbar"
			class="datagrid-toolbar datagrid-toolbar-extend foot-formopera">
			<table class="tableForm" style="border:0;cellspacing:1;width:100%">
				<tr>
					<td width="50%" style="padding-right:4%;" align="right">
						<a href="javascript:void(0)" class="btn btn-grey form-tool-btn btn-sm" role="button" title="返回" id="stockInfo_closeForm">返回</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<jsp:include page="/avicit/platform6/h5component/common/h5uiinclude-js.jsp">
	<jsp:param value="<%=importlibs%>" name="importlibs"/>
	</jsp:include>
	<script type="text/javascript">
		function closeForm(){
			parent.stockInfo.closeDialog("detail");
		}
		$(document).ready(function (){
			//初始化附件上传组件
			$('#attachment').uploaderExt({
				formId:"${stockInfoDTO.id}",
				allowAdd: false,
				allowDelete: false
			});
			//返回按钮绑定事件
			$('#stockInfo_closeForm').bind('click', function(){
				closeForm();
			});
		});
		//form控件禁用
		setFormDisabled();
		$(document).keydown(function(event){  
			event.returnValue = false;
			return false;
		});
	</script>
</body>
</html>


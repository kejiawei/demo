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
<title>编辑</title>
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
							<input  class="form-control"  type="text" name="storageNumber" id="storageNumber" data-min="-99999999999999999999.99999999" data-max="99999999999999999999.99999999" data-step="1" data-precision="8" value="<c:out value='${stockInfoDTO.storageNumber}'/>">
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
						<a href="javascript:void(0)" class="btn btn-primary form-tool-btn typeb btn-sm" role="button" title="保存" id="stockInfo_saveForm">保存</a>
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
		//遮罩
		var maskId = null;
		//初始化时间控件
		function initDateSelect(){
			$('.date-picker').datepicker({
				beforeShow: function(selectedDate) {
					setTimeout(function () {
						$('#ui-datepicker-div').css("z-index", 99999999);
					}, 100);
				}
			});
			$('.time-picker').datetimepicker({
				oneLine:true,//单行显示时分秒
				closeText:'确定',//关闭按钮文案
				showButtonPanel:true,//是否展示功能按钮面板
				showSecond:false,//是否可以选择秒，默认否
				beforeShow: function(selectedDate) {
					if($('#'+selectedDate.id).val()==""){
							$(this).datetimepicker("setDate", new Date());
							$('#'+selectedDate.id).val('');
					}
					setTimeout(function () {
						$('#ui-datepicker-div').css("z-index", 99999999);
					}, 100);
				}
			});
			$('.date-picker').on('keydown',nullInput);
			$('.time-picker').on('keydown',nullInput);
		}
		
		function closeForm(){
			parent.stockInfo.closeDialog("edit");
		}
		function saveForm(){
			var isValidate = $("#form").validate();
	        if (!isValidate.checkForm()) {
	            isValidate.showErrors();
	            return false;
	        }
	        //验证附件密级
			var files = $('#attachment').uploaderExt('getUploadFiles');
			for(var i = 0; i < files.length; i++){
				var name = files[i].name;
				var secretLevel = files[i].secretLevel;
				//这里验证密级
			}
  		 	$('#stockInfo_saveForm').addClass('disabled').unbind("click");	
			parent.stockInfo.save($('#form'),callback);
		}
		//上传附件
		function callback(id) {
			var files = $('#attachment').uploaderExt('getUploadFiles');
			if(files == 0){
				closeForm();
			}else{
				$("#id").val(id);
				//开启遮罩
				maskId = layer.load();
				$('#attachment').uploaderExt('doUpload', id);
			}
		}
		//附件上传完后事件
		function afterUploadEvent(){
			//附件上传完毕关闭遮罩
			if(maskId != null){
				layer.close(maskId);
			}
			parent.layer.msg('保存成功！');
			closeForm();
		}
		//清空日期值
		function clearCommonSelectValue(element) {
			$(element).siblings("input").val("");
		}
		$(document).ready(function () {
			initDateSelect();
			//初始化附件上传组件
			$('#attachment').uploaderExt({
				secretLevel: 'PLATFORM_FILE_SECRET_LEVEL',
				tableName: 'STOCK_INFO',
				afterUpload: afterUploadEvent,
				formId:"${stockInfoDTO.id}"
			});
			parent.stockInfo.formValidate($('#form'));
			//保存按钮绑定事件
			$('#stockInfo_saveForm').bind('click', function(){
				saveForm();
			}); 
			//返回按钮绑定事件
			$('#stockInfo_closeForm').bind('click', function(){
				closeForm();
			});
			
		});
	</script>
</body>
</html>


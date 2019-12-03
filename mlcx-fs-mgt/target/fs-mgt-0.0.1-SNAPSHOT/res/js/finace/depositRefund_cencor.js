define([],function() {	
var depositRefundCencor = {
		appPath : getPath("app"),
		init : function() {
			//审核押金退款信息提交
			$("#cencorDepositRefund").click(function(){
				depositRefundCencor.cencorDepositRefund();
			});
			//押金退款审核页面关闭
			$("#closeDepositRefundCencor").click(function(){
				closeTab("退款审核");
			});
			$("input.butcheck").each(function(){//给所有的checkbox绑定事件
				 $(this).click(function(){
				     var l=[]; //创建空数组
					 $("input.butcheck:checked").each(function(i){l[i]=this.value});
					 //将所有的选中的值存放
					 $("#cencorConfirmItem").val(l.join(","));//将数据值联合字符串给显示对象附值
				  })
			});
		},
		cencorDepositRefund:function() {
			var form = $("form[name=depositRefundCencorForm]");
			form.ajaxSubmit({
				url : depositRefundCencor.appPath + "/depositRefund/cencorDepositRefund.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "押金退款审核操作成功", function() {
							closeTab("退款审核");
							$("#depositRefundList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "押金退款审核操作失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					var form = $("form[name=depositRefundCencorForm]");
					var itemS="确认无欠费,确认无未处理的违章,确认车辆无损坏";
					if(form.find("input[name=cencorStatus]:checked").val()==1){
						if (form.find("input[name='cencorConfirmItem']").val()=="") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择审核确认项！");
							return false;
						}
						if(form.find("input[name='cencorConfirmItem']").val()!=itemS){
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "审核通过时请勾选所有的审核确认项！");
							return false;
						}
					}
					if(form.find("textarea[name=cencorMemo]").val()==""){
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请输入审核备注！");
						return false;
					}
				}
			});
		}
}
return depositRefundCencor
})

define(
[],
function() {
var dataDictItemEdit = {
	appPath : getPath("app"),
	init : function() {
		var form = $("form[name=dataDictItemEditForm]");
		//添加提交
		$("#saveEditDataDictItem").click(function(){
			dataDictItemEdit.editDataDictItem();
		});
		//返回
		$("#closeEditDataDictItem").click(function(){
			closeTab("新增数据字典分类");
			$("#dataDictIemList").DataTable().ajax.reload(function(){});
		});
		$("#editinfo2").click(function(){
			$("#info2AddModalEdit").modal("show");
		});
		$("#info2AddWEdit").click(function(){
			var form=$("form[name=infoForm]");
			var img=form.find("input[name=info2]").val();
			if(img!=""){
				var form1=$("form[name=dataDictItemEditForm]");
				form1.find("input[name=info2]").val(img);
				$("#info2AddModalEdit").modal("hide");
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传文件！");
			}
		});
		
		//根据 父级 选择 展示出 对应的值
		form.find("select[name='parentCatCode']").change(function(){
			var parentCatCode = $('#parentCatCodeEdit option:selected').text();
			$.ajax({
				 type: "post",
	             url: dataDictItemEdit.appPath+"/dataDictItem/getModleByBrand.do",
	             data: {parentCatCode:parentCatCode},
	             success: function(res){
	            	 var dataItems = res.data;
	            	 if(res.code=="1"){
	            		 form.find("select[name='parentItemId']").html("");
	            		 var option = "";
	            		 for(var i=0;i<dataItems.length;i++){
	            			 option+="<option  value='"+dataItems[i].dataDictItemId+"'> "+dataItems[i].itemValue+"</option>";
	              		 }
	            		 form.find("select[name='parentItemId']").html(option);
	                 }else{
	                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg); 
	                 }
	             }
			});
			
		});
	
	},
	
	
	
	
	editDataDictItem:function() {
		var form = $("form[name=dataDictItemEditForm]");
		form.ajaxSubmit({
			url : dataDictItemEdit.appPath + "/dataDictItem/editDataDictItem.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("修改数据字典分类");
						$("#dataDictItemList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#dataDictItemCodeEdit").empty();
				$("#itemValueEdit").empty();
				if (form.find("select[name='dataDictCatCode']").val()=="") {
					$("#dataDictItemCodeEdit").append("<font color='red' class='formtips onError emaill'>请输入数据字典分类代码！</font>");
					return false;
				}
				if (form.find("input[name='itemValue']").val()=="") {
					$("#itemValueEdit").append("<font color='red' class='formtips onError emaill'>请输入数据字典项值！</font>");
					return false;
				}
				if(form.find("select[name='dataDictCatCode']").val()=="CAR_MODEL"){
					if (form.find("input[name='info1']").val()=="") {
						$("#info1Add").append("<font color='red' class='formtips onError emaill'>此字段不为空！</font>");
						return false;
					}
					if(form.find("input[name='info1']").val()!=""&&!/^\+?[1-9]\d*$/.test(form.find("input[name='info1']").val())){
						$("#info1Add").append("<font color='red'>必须是大于0的正整数</font>");
						return false;
					}
				}
			}
		});
	 }
	}
	return dataDictItemEdit;
})

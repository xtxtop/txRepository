define(
[],
function() {
var dataDictItemAdd = {
	appPath : getPath("app"),
	init : function() {
		var form = $("form[name=dataDictItemAddForm]");
		//添加提交
		$("#saveAddDataDictItem").click(function(){
			dataDictItemAdd.addDataDictItem();
		});
		//返回
		$("#closeAddDataDictItem").click(function(){
			closeTab("数据字典项新增");
			$("#dataDictItemList").DataTable().ajax.reload(function(){});
		});
		$("#addinfo2").click(function(){
			$("#info2AddModal").modal("show");
		});
		$("#info2AddW").click(function(){
			var form=$("form[name=infoForm]");
			var img=form.find("input[name=info2]").val();
			if(img!=""){
				var form1=$("form[name=dataDictItemAddForm]");
				form1.find("input[name=info2]").val(img);
				$("#info2AddModal").modal("hide");
			}else{
				bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传文件！");
			}
		});
		
		//根据 父级 选择 展示出 对应的值
		form.find("select[name='parentCatCode']").change(function(){
			var parentCatCode = $('#parentCatCode option:selected').text();
			$.ajax({
				 type: "post",
	             url: dataDictItemAdd.appPath+"/dataDictItem/getModleByBrand.do",
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
	addDataDictItem:function() {
		var form = $("form[name=dataDictItemAddForm]");
		form.ajaxSubmit({
			url : dataDictItemAdd.appPath + "/dataDictItem/addDataDictItem.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("数据字典项新增");
						$("#dataDictItemList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#dataDictItemCodeAdd").empty();
				$("#itemValueAdd").empty();
				$("#info1Add").empty();
				if (form.find("select[name='dataDictCatCode']").val()=="") {
					$("#dataDictItemCodeAdd").append("<font color='red' class='formtips onError emaill'>请选择数据字典分类代码！</font>");
					return false;
				}
				if (form.find("input[name='itemValue']").val()=="") {
					$("#itemValueAdd").append("<font color='red' class='formtips onError emaill'>请输入数据字典项值！</font>");
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
	return dataDictItemAdd;
})

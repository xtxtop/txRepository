define(
[],
function() {
var chargerAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveCharger").click(function(){
			chargerAdd.addCharger();
		});
		//返回
		$("#closeAddCharger").click(function(){
			closeTab("充电桩新增");
			$("#chargerList").DataTable().ajax.reload(function(){});
		});
		
		//城市改变
		$("#cityId").on("change",function(){
			var cityId = $(this).children('option:selected').val();
			$("#cityName").val($(this).children('option:selected').text());
			$.ajax({
				 type: "post",
	             url: chargerAdd.appPath+"/park/getCityPark.do",
	             data: {cityId:cityId},
	             dataType: "json",
	             success: function(data){
	            	 if(data.code="1"){
	            		 var parks = data.data;
	            		 if(parks.length == 0){
	            			 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "当前城市下无场站、请添加场站！");
	            		 }
	            		 var parkNode = $("#parkNode");
	            		 parkNode.html("");
	            		 var item = "";
	            		 $.each(parks,function(i,val){
	            			 item += "<option value="+val.parkNo+">"+val.parkName+"</option>";
	            		 });
	            		 parkNode.html(item);
	                 }
	             }
			});
		});
		
	},
	addCharger:function() {
		var form = $("form[name=chargerAddForm]");
		form.ajaxSubmit({
			url : chargerAdd.appPath + "/charger/addCharger.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("新增充电桩");
						$("#chargerList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#chargerPowerYz").empty();
				$("#chargerSnYz").empty();
				$("#cityNameYz").empty();
				$("#parkNameYz").empty();
				$("#chargerBrandNameYz").empty();
				$("#chargerModelNameYz").empty();
				$("#chargerTypeYz").empty();
				$("#isAvailableYz").empty();
				
				if ($.trim(form.find("select[name='cityId']").val())=="") {
					$("#cityNameYz").append("<font color='red' class='formtips onError emaill'>请选择城市！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='parkNo']").val())=="") {
					$("#parkNameYz").append("<font color='red' class='formtips onError emaill'>请选择场站名称！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='chargerBrandName']").val())=="") {
					$("#chargerBrandNameYz").append("<font color='red' class='formtips onError emaill'>请选择品牌！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='chargerModelName']").val())=="") {
					$("#chargerModelNameYz").append("<font color='red' class='formtips onError emaill'>请选择型号！</font>");
					return false;
				}
				
				if ($.trim(form.find("input[name='chargerPower']").val())=="") {
					$("#chargerPowerYz").append("<font color='red' class='formtips onError emaill'>请输入功率！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='chargerPower']").val())!=""&&!/^[0-9]*$/.test(form.find("input[name='chargerPower']").val())) {
					$("#chargerPowerYz").append("<font color='red' class='formtips onError emaill'>功率格式不正确,只能是数字！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='chargerSn']").val())=="") {
					$("#chargerSnYz").append("<font color='red' class='formtips onError emaill'>请输入设备串号！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='chargerSn']").val())!=""&&!/^[A-Z0-9]*$/.test(form.find("input[name='chargerSn']").val())) {
					$("#chargerSnYz").append("<font color='red' class='formtips onError emaill'>设备串号，只能是数字或是大写字母！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='chargerType']").val())=="") {
					$("#chargerTypeYz").append("<font color='red' class='formtips onError emaill'>请选择电桩类型！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='isAvailable']").val())=="") {
					$("#isAvailableYz").append("<font color='red' class='formtips onError emaill'>请选状态！</font>");
					return false;
				}
				
				
			}
		});
	 }
	}
	return chargerAdd;
})

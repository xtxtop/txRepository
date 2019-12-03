define(
[],
function() {
var parkingLockAdd = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveparkingLockAdd").click(function(){
			parkingLockAdd.addparkingLock();
		});
		//返回
		$("#closeparkingLockAdd").click(function(){
			closeTab("新增地锁");
			$("#parkingLockList").DataTable().ajax.reload(function(){});
		});
		/* 地锁 */
		$("#stationNo").change(function() {
			var stationNo = $(this).val();
			$.ajax({
                url:  parkingLockAdd.appPath+ '/parkingLock/chargingPileTree.do?stationNo='+stationNo,
                success: function (res) {
                	console.log(res)
					var cp = res;
                	var options = '<option value="">请选择</option>';
                	for(var ind in cp) {
                		options += '<option value="'+cp[ind].chargingPileNo+'">'+cp[ind].chargingPileName+"</option>";
					}
                	$("#chargingPileNo").html(options);
                }
            });
		});
	},
	addparkingLock:function() {
		var form = $("form[name=parkingLockAddForm]");
		form.ajaxSubmit({
			url : parkingLockAdd.appPath + "/parkingLock/doaddparkingLock.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
						closeTab("新增地锁");
						$("#parkingLockList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#parkingLockNameAdd").empty();
				$("#parkingLockCodeAdd").empty();
				$("#parkingLockSerialNumberAdd").empty();
				$("#parkingLockTypeAdd").empty();
				$("#parkingLockUsertypeAdd").empty();
				$("#parkingLockStatusAdd").empty();
				$("#stationNoAdd").empty();
				$("#chargingPileNoAdd").empty();
				$("#parkingLockChargingNoAdd").empty();
				$("#activeConditionAdd").empty();
				
				if ($.trim(form.find("input[name='parkingLockName']").val())=="") {
					$("#parkingLockNameAdd").append("<font color='red' class='formtips onError emaill'>请输入地锁名称！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='parkingLockCode']").val())=="") {
					$("#parkingLockCodeAdd").append("<font color='red' class='formtips onError emaill'>请输入地锁编号！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='parkingLockSerialNumber']").val())=="") {
					$("#parkingLockSerialNumberAdd").append("<font color='red' class='formtips onError emaill'>请输入地锁序列号！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='parkingLockType']:checked").val())=="") {
					$("#parkingLockTypeAdd").append("<font color='red' class='formtips onError emaill'>请选择地锁产品类型！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='parkingLockUsertype']:checked").val())=="") {
					$("#parkingLockUsertypeAdd").append("<font color='red' class='formtips onError emaill'>请选择地锁使用类型！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='parkingLockStatus']:checked").val())=="") {
					$("#parkingLockStatusAdd").append("<font color='red' class='formtips onError emaill'>请选择地锁状态！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='stationNo']").val())=="") {
					$("#stationNoAdd").append("<font color='red' class='formtips onError emaill'>请选择场站！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='chargingPileNo']").val())=="") {
					$("#chargingPileNoAdd").append("<font color='red' class='formtips onError emaill'>请选择充电桩！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='activeCondition']").val())=="") {
					$("#activeConditionAdd").append("<font color='red' class='formtips onError emaill'>请选择可用状态！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='parkingLockChargingNo']").val())=="") {
					$("#parkingLockChargingNoAdd").append("<font color='red' class='formtips onError emaill'>请选择计费方案！</font>");
					return false;
				}
			}
		});
	 }
	}
	return parkingLockAdd;
})

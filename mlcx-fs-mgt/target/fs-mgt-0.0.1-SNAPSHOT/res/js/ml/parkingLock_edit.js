define(
[],
function() {
var parkingLockEdit = {
	appPath : getPath("app"),
	init : function() {
		//添加提交
		$("#saveparkingLockEdit").click(function(){
			parkingLockEdit.editparkingLock();
		});
		//返回
		$("#closeparkingLockEdit").click(function(){
			closeTab("编辑地锁");
			$("#parkingLockList").DataTable().ajax.reload(function(){});
		});
		/* 地锁 */
		$("#stationNo").change(function() {
			var stationNo = $(this).val();
			$.ajax({
                url:  parkingLockEdit.appPath+ '/parkingLock/chargingPileTree.do?stationNo='+stationNo,
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
	editparkingLock:function() {
		var form = $("form[name=parkingLockEditForm]");
		form.ajaxSubmit({
			url : parkingLockEdit.appPath + "/parkingLock/doeditparkingLock.do",
			type : "post",
			success : function(res) {
				var msg = res.msg;
				var code = res.code;
				if (code == "1") {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑成功", function() {
						closeTab("编辑地锁");
						$("#parkingLockList").DataTable().ajax.reload(function(){});
					});
				} else {
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "编辑失败，"+msg+"！");
				}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("#parkingLockNameEdit").empty();
				$("#parkingLockCodeEdit").empty();
				$("#parkingLockSerialNumberEdit").empty();
				$("#parkingLockTypeEdit").empty();
				$("#parkingLockUsertypeEdit").empty();
				$("#parkingLockStatusEdit").empty();
				$("#stationNoEdit").empty();
				$("#chargingPileNoEdit").empty();
				$("#parkingLockChargingNoEdit").empty();
				
				if ($.trim(form.find("input[name='parkingLockName']").val())=="") {
					$("#parkingLockNameEdit").append("<font color='red' class='formtips onError emaill'>请输入地锁名称！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='parkingLockCode']").val())=="") {
					$("#parkingLockCodeEdit").append("<font color='red' class='formtips onError emaill'>请输入地锁编号！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='parkingLockSerialNumber']").val())=="") {
					$("#parkingLockSerialNumberEdit").append("<font color='red' class='formtips onError emaill'>请输入地锁序列号！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='parkingLockType']:checked").val())=="") {
					$("#parkingLockTypeEdit").append("<font color='red' class='formtips onError emaill'>请选择地锁产品类型！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='parkingLockUsertype']:checked").val())=="") {
					$("#parkingLockUsertypeEdit").append("<font color='red' class='formtips onError emaill'>请选择地锁使用类型！</font>");
					return false;
				}
				if ($.trim(form.find("input[name='parkingLockStatus']:checked").val())=="") {
					$("#parkingLockStatusEdit").append("<font color='red' class='formtips onError emaill'>请选择地锁状态！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='stationNo']").val())=="") {
					$("#stationNoEdit").append("<font color='red' class='formtips onError emaill'>请选择场站！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='chargingPileNo']").val())=="") {
					$("#chargingPileNoEdit").append("<font color='red' class='formtips onError emaill'>请选择充电桩！</font>");
					return false;
				}
				if ($.trim(form.find("select[name='parkingLockChargingNo']").val())=="") {
					$("#parkingLockChargingNoEdit").append("<font color='red' class='formtips onError emaill'>请选择计费方案！</font>");
					return false;
				}
			}
		});
	 }
	}
	return parkingLockEdit;
})

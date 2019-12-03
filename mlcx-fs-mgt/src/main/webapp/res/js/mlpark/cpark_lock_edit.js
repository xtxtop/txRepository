define([],function() {	
var cParkLockEdit = {
		appPath : getPath("app"),
		imgPath:getPath("img"),
		init : function() {
			var form = $("form[name=cParkLockEditForm]");
			 $('input').attr('autocomplete','off');//input框清楚缓存
			//停车场
			$("#parkingNoEdit").click(function(){
				$("#parkingModelEdit").modal("show");
				cParkLockEdit.pageparking();
			});
			//停车场列表查询
			$("#parkingSearch").click(function(){
				cParkLockEdit.pageparking();
	        });
			//弹出加盟商
			/*$("#parkingEdit").click(function(){
				var id=$(this).data("id");
					$("#parkingNoEdit").modal({
						 show:true,
						 backdrop:'static'
					});
					cParkLockEdit.parkingcParkLock();
				
            });*/
			//增加提交
			$("#EditcParkLock").click(function(){
				cParkLockEdit.savecParkLock();
			});
			//增加页面关闭
			$("#closecParkLockEdit").click(function(){
				closeTab("修改地锁");
			});
		},
		
		//加盟商列表
		pageparking:function(){
			var form = $("form[name=parkingSerachFormEdit]");
			var parkingName = form.find("input[name='parkingName']").val();
			var parkingBtnTpl = $("#parkingBtnTplEdit").html();
			// 预编译模板
			var template = Handlebars.compile(parkingBtnTpl);
			var table = $('#parkingListEdit').dataTable({
				searching : false,
				destroy : true,  
				"ajax" : {
					"type" : "POST",
					"url" : cParkLockEdit.appPath+"/cParkLock/pageListAuditparking.do",
					"data" : function(d) {
						return $.extend({},d,
										{"pageNo" : (d.start / d.length) + 1,
										 "pageSize" : d.length,
										 "parkingName" : parkingName,
										 "parkingType":1
										});
					},
					"dataSrc" : function(json) {
						json.recordsTotal = json.rowCount;
						json.recordsFiltered = json.rowCount;
						json.data = json.data;
						return json.data;
					},
					error : function(xhr, error, thrown) {
					}
				},
				"columns" : [ 
				{ "title":"","data": "parkingNo","width":"5px"},
				{
					"title" : "停车场编号",
					"data" : "parkingNo"
				},{
					"title" : "停车场名称",
					"data" : "parkingName"
				}],
				"dom" : "<'row'<''><'col-xs-6'f>r>"
						+ "t"
						+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
				  initComplete: function () {
						$(this).find("thead tr th:first-child").prepend('');
						$("#parkingToolssssEdit").empty().append('<button style="margin-left:300px" type="button" class="btn-new-type sysworkerMgcParkLock-batch-Editdel">选择</button><button type="button"  class="btn-new-type btn-new-type-blue sysworkerMgcParkLock-batch-Editclose">关闭</button>');
						$(".sysworkerMgcParkLock-batch-Editdel").on("click",function(){
							var ids=[];
							var parkingName="";
							var len=$('#parkingListEdit tbody input[type="checkbox"]:checked');
							if(len.length==0){
								bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择停车场！")
							}else{
								$("#parkingListEdit tbody").find("input:checked").each(function(){
									
		        					ids.push($(this).val());
		        					
		        					parkingName = $(this).next('input').val();
		        					pliesNumber = $(this).next('input').next('input').val();
		        					pliesTotalNumber = $(this).next('input').next('input').next('input').val();
		        					groundNumber = $(this).next('input').next('input').next('input').next('input').val();
		        					undergroundNumber = $(this).next('input').next('input').next('input').next('input').next('input').val();
		        				});
								if(ids.length>1){
	        						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "停车场只能选择一个！")
	        					}else{
	        						var form = $("form[name=cParkLockEditForm]");
	        	     				form.find("input[name='parkingNo']").val(ids);
	        	     				form.find("input[name='parkingName']").val(parkingName);
	        	     				form.find("input[name='pliesNumber']").attr('max',pliesNumber);
	        	     				form.find("input[name='pliesNumber']").val(1);
	        	     				$("#lockEdit").html("");
	        	     				var lock1='';
	        	     				var lock2='';
	        	     				var lock3='';
	        	     				var lock='<td><label class=" control-label"><span>*</span>停车场分布：</label></td><td>';
	        	     				if(pliesNumber!="null"&&pliesNumber!=''){
	        	     					lock1=' <input type="radio" name="spaceType"  value="3" onclick="editclickSpaceType(this)">楼层';
	        	     				}
	        	     				if(groundNumber!="null"&&groundNumber!=''){
	        	     					lock2=' <input type="radio" name="spaceType"  value="2" onclick="editclickSpaceType(this)">地面';
	        	     				}
	        	     				if(undergroundNumber!=null&&undergroundNumber!=''){
	        	     					lock3='<input type="radio" name="spaceType"  value="1" onclick="editclickSpaceType(this)">地下';
	        	     				}
	        	     				var td='<td><label class=" control-label"><span>*</span>层数：</label></td><td id="editparkingPliesNo">'
	        	     					+'<select class="form-control"  name="pliesNumberNo" ><option value="">--请选择--</option> </select></td>';
	        	     				$("#lockEdit").append(lock+lock1+lock2+lock3+'</td>'+td);
	        	     				$("#parkingModelEdit").modal("hide");
	        	     				$(".modal-backdrop").hide();
	        					}
							}
							
							$('#parkingListEdit thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#parkingListEdit tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			         $('#parkingListEdit tbody input[type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});
						});
						$(".sysworkerMgcParkLock-batch-Editclose").on("click",function(){
							$("#parkingModelEdit").modal("hide");
							$(".modal-backdrop").hide();
							$('#parkingListEdit tbody input[type="checkbox"]:checked').prop("checked",false);
						});
				  },
				"columnDefs" : [
					   {
						"targets" : [0],
						 "orderable":false,
						"render" : function(data, type, full, meta) {
							  return '<input type="checkbox" name="parkingNo" value="' + full.parkingNo + '"><input type="hidden" value="' + full.parkingName + '"><input type="hidden" value="' + full.pliesNumber + '">'+
							  '<input type="hidden" value="' + full.pliesTotalNumber + '"><input type="hidden" value="' + full.groundNumber + '"><input type="hidden" value="' + full.undergroundNumber + '">';
						}
					   }
					   ]
			});
		},
		 savecParkLock:function() {
			var form = $("form[name=cParkLockEditForm]");
			form.ajaxSubmit({
				url : cParkLockEdit.appPath + "/cParkLock/editCParkLock.do",
				type : "post",
				data :{},
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "修改成功", function() {
							closeTab("地锁修改");
							$("#cParkLockList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "修改失败！"+msg);
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					form.find("span[name='parkingLockCodeEdit']").empty();
					form.find("span[name='parkingLockNameEdit']").empty();
					form.find("span[name='parkingLockSerialNumberEdit']").empty();
					form.find("span[name='pliesNumberEdit']").empty();
					form.find("span[name='spaceNoEdit']").empty();
					form.find("span[name='parkingNoEdit']").empty();
					form.find("span[name='parkingLockTypeEdit']").empty();
					form.find("span[name='parkingLockStatusEdit']").empty();
					form.find("span[name='activeConditionEdit']").empty();
					form.find("span[name='onlineStatusEdit']").empty();
					if (form.find("input[name='parkingLockCode']").val()=="") {
						form.find("span[name='parkingLockCodeEdit']").append("<font color='red'>请输入地锁编号！</font>");
						return false;
					}
					if (form.find("input[name='parkingLockName']").val()=="") {
						form.find("span[name='parkingLockNameEdit']").append("<font color='red'>请输入地锁名称！</font>");
						return false;
					}
					if (form.find("input[name='parkingLockSerialNumber']").val()=="") {
						form.find("span[name='parkingLockSerialNumberEdit']").append("<font color='red'>请输入地锁序列号！</font>");
						return false;
					}
					if (form.find("input[name='pliesNumber']").val()!=""&&/^-?\\d+$/.test(form.find("input[name='pliesNumber']").val())) {
						form.find("span[name='pliesNumberEdit']").append("<font color='red'>楼层数应为整数！</font>");
						return false;
					}
					if (form.find("input[name='parkingNo']").val()=="") {
						form.find("span[name='parkingNoEdit']").append("<font color='red'>请选择停车场,只能选择一个！</font>");
						return false;
					}
					if (form.find("input[name='spaceNo']").val()=="") {
						form.find("span[name='spaceNoEdit']").append("<font color='red'>请输入车位号！</font>");
						return false;
					}
					if (form.find("select[name='parkingLockType']").val()=="") {
						form.find("span[name='parkingLockTypeEdit']").append("<font color='red'>请选择地锁类型！</font>");
						return false;
					}
					if (form.find("select[name='parkingLockStatus']").val()=="") {
						form.find("span[name='parkingLockStatusEdit']").append("<font color='red'>请选择升降状态！</font>");
						return false;
					}
					if (form.find("select[name='activeCondition']").val()=="") {
						form.find("span[name='activeConditionEdit']").append("<font color='red'>请选择地锁状态！</font>");
						return false;
					}
					if (form.find("select[name='onlineStatus']").val()=="") {
						form.find("span[name='onlineStatusEdit']").append("<font color='red'>选择在线状态！</font>");
						return false;
					}
				}
			});
		}
}
return cParkLockEdit
})//获取停车场层数分布
function editclickSpaceType(x){
	var form = $("form[name=cParkLockEditForm]");
	var parkingNo= form.find("input[name='parkingNo']").val();
	$.post('cParkLock/getSpaceType.do', {parkingNo:parkingNo,spaceType:x.value},
			 function(data) {
					if(data){
						$("#editparkingPliesNo").html("");
						var td='<td id="parkingPliesNo">'
     					+'<select class="form-control"  name="pliesNumberNo"><option value="">--请选择--</option> ';
						for(var i=0;i<data.length;i++){
							td+='<option value="'+data[i].parkingPliesNo+'">'+data[i].pliesNumber+'</option>';	
						}
						$("#editparkingPliesNo").append(td+'</select></td>')
					} 
			},"json");
}

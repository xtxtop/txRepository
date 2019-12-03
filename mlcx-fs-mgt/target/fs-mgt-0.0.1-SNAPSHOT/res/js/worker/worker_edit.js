define([],function() {	
			var workerEdit = {
				appPath : getPath("app"),
				init : function() {
					
					var form = $("form[name=editWorkerForm]");
					form.find("select[name='cityId']").change(function(){
						var cityId = form.find("select[name='cityId']").find("option:selected").val();
						$.ajax({
							 type: "post",
				             url: workerEdit.appPath+"/park/getDotByRegionId.do",
				             data: {cityId:cityId},
				             success: function(res){
				            	 var dataItems = res.data;
				            	 if(res.code=="1"){
				            		 form.find("select[name='regionId']").html("");
				            		 var option = "";
				            		 for(var i=0;i<dataItems.length;i++){
				            			 option+="<option  value='"+dataItems[i].dataDictItemId+"'> "+dataItems[i].itemValue+" </option>";
				              		 }
				            		 form.find("select[name='regionId']").html(option);
				                 }else{
				                	 bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + res.msg);
				                	 form.find("select[name='regionId']").html("<option value=''>请选择</option selected>");
				                 }
				             }
						});
					});
					$("#regionIdEdd").click(function(){
						
						$("#regionEddModal").modal("show");
						workerEdit.pageRegionEdd();
					});
					//修改提交
					$("#editWorker").click(function(){
						workerEdit.editWorker();
					});
					//关闭修改页
					$("#closeWorkerEdit").click(function(){
						closeTab("调度员修改");
		            });
				},
				
				pageRegionEdd:function(){
					var carMgBtnTpl = $("#regionBtnTplEdd").html();
					 var cityId=$("select[name=cityId]").val();
					// 预编译模板
					var template = Handlebars.compile(carMgBtnTpl);
					var table = $('#regionListEdd').dataTable({
						searching : false,
						destroy : true,  
						"ajax" : {
							"type" : "POST",
							"url" : workerEdit.appPath+"/worker/pageListRegion.do",
							"data" : function(d) {
								return $.extend({},d,
												{"pageNo" : (d.start / d.length) + 1,
												 "pageSize" : d.length,
												 "cityId" : cityId
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
						{ "title":"","data": "regionId","width":"5px"},
						{
							"title" : "名称",
							"data" : "regionName"
						}],
						"dom" : "<'row'<''><'col-xs-6'f>r>"
								+ "t"
								+ "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
						  initComplete: function () {
								$(this).find("thead tr th:first-child").prepend('');
								$("#regionToolssssEdd").empty().append('<button type="button"  class="btn-new-type sysworkerMgCar-batch-edddel">选择</button><button type="button"  class="btn-new-type btn-new-type-blue sysworkerMgCar-batch-eddclose">关闭</button>');
								$(".sysworkerMgCar-batch-edddel").on("click",function(){
									var ids=[];
									var wkNe=[];
									var len=$('#regionListEdd tbody input[type="checkbox"]:checked');
									if(len.length==0){
										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择片区！")
									}else{
										$("#regionListEdd tbody").find("input:checked").each(function(){
											
				        					ids.push($(this).val());
				        					
				        					wkNe.push( $(this).next('input').val());
				        					
				        				});
										
			        						var form = $("form[name=editWorkerForm]");
			        	     				form.find("input[name='regionId']").val(ids);
			        	     				form.find("input[name='regionName']").val(wkNe);
			        	     				$("#regionEddModal").modal("hide");
			        	     				$(".modal-backdrop").hide();
			        					
									}
									
									$('#regionListEdd thead input[type="checkbox"]').on("click",function(e){
				        				if(this.checked){
				        			         $('#regionListEdd tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
				        			      } else {
				        			         $('#regionListEdd tbody input[type="checkbox"]:checked').prop("checked",false);
				        			      }
				        			});
								});
								$(".sysworkerMgCar-batch-eddclose").on("click",function(){
									$("#regionEddModal").modal("hide");
									$(".modal-backdrop").hide();
									 $('#regionListEdd tbody input[type="checkbox"]:checked').prop("checked",false);
								});
						  },
						"columnDefs" : [
							   {
								"targets" : [0],
								 "orderable":false,
								"render" : function(data, type, full, meta) {
									  return '<input type="checkbox" name="regionId" value="' + full.regionId + '"><input type="hidden" value="' + full.regionName + '">';
								}
							   }
							   ]
					});
				},
				
			editWorker:function() {
				debugger;
				var form = $("form[name=editWorkerForm]");
				form.ajaxSubmit({
					url : workerEdit.appPath + "/worker/workerEdit.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "修改成功", function() {
								closeTab("调度员修改");
								$("#workerList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "修改失败！"+msg);
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						$("span[name='empNoEdit']").empty();
						$("span[name='regionIdEd']").empty();
					   	$("span[name='workerNameEdit']").empty();
					   	$("span[name='mobilePhoneEdit']").empty();
					   	$("span[name='passwordEdit']").empty();
						var form = $("form[name=editWorkerForm]");
					    var empNo=$.trim(form.find("input[name=empNo]").val());
					    var workerName=$.trim(form.find("input[name=workerName]").val());
					    var mobilePhone=$.trim(form.find("input[name=mobilePhone]").val());
					    var password=$.trim(form.find("input[name=password]").val());
					    var regionId=form.find("input[name=regionId]").val();
					    if(regionId==""){
					    	$("span[name='regionIdEd']").append("<font color='red' class='formtips onError emaill'>请选择片区！</font>");
							return false;
					    }else
					    if (empNo == "") {
					    	$("span[name='empNoEdit']").append("<font color='red' class='formtips onError emaill'>请输入工号！</font>");
							return false;
			            }else if(!/[0-9A-Za-z]+/.test(empNo)){
			            	$("span[name='empNoEdit']").append("<font color='red' class='formtips onError emaill'>工号只能输入正整数或字母,不能有空格！</font>");
							return false;
			            }
					    if (workerName == "") {
					    	$("span[name='workerNameEdit']").append("<font color='red' class='formtips onError emaill'>请输入姓名！</font>");
							return false;
			            }else if (workerName.length > 12) {
					    	$("span[name='workerNameEdit']").append("<font color='red' class='formtips onError emaill'>姓名输入长度不能超过12！</font>");
							return false;
			            }
					    if (mobilePhone == "") {
					    	$("span[name='mobilePhoneEdit']").append("<font color='red' class='formtips onError emaill'>请输入手机号！</font>");
							return false;
			            }else if(!/^(13[0-9]|15[012356789]|17[01367]|18[0-9]|14[0579])[0-9]{8}$/.test(mobilePhone)){
			            	$("span[name='mobilePhoneEdit']").append("<font color='red' class='formtips onError emaill'>手机号格式不正确！</font>");
							return false;
			            }
					    if (password == "") {
					    	$("span[name='passwordEdit']").append("<font color='red' class='formtips onError emaill'>请输入密码！</font>");
							return false;
			            }
					   
					}
				});
			}
		};
			return workerEdit;
	});

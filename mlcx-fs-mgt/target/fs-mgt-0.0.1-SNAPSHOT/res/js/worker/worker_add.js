define([],function() {	
			var workerAdd = {
				appPath : getPath("app"),
				init : function() {
					
					var form = $("form[name=addWorkerForm]");
					form.find("select[name='cityId']").change(function(){
						var cityId = form.find("select[name='cityId']").find("option:selected").val();
						$.ajax({
							 type: "post",
				             url: workerAdd.appPath+"/park/getDotByRegionId.do",
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
					$("#regionIdAdd").click(function(){
						
						$("#regionAddModal").modal("show");
						workerAdd.pageRegion();
					});
					//添加提交
					$("#addWorker").click(function(){
						workerAdd.addWorker();
					});
					//关闭增加页
					$("#closeWorker").click(function(){
						closeTab("调度员新增");
		            });
				},
				
				
				pageRegion:function(){
					var carMgBtnTpl = $("#regionBtnTplAdd").html();
					// 预编译模板
					var template = Handlebars.compile(carMgBtnTpl);
					 var cityId=$("select[name=cityId]").val();
					var table = $('#regionListAdd').dataTable({
						searching : false,
						destroy : true,  
						"ajax" : {
							"type" : "POST",
							"url" : workerAdd.appPath+"/worker/pageListRegion.do",
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
								$("#regionToolssssAdd").empty().append('<button type="button"  class="btn-new-type sysworkerMgCar-batch-adddel">选择</button><button type="button"  class="btn-new-type btn-new-type-blue  sysworkerMgCar-batch-addclose">关闭</button>');
//								$("#regionToolssssAdd").append('<button type="button"  class="btn btn-default btn-sm sysworkerMgCar-batch-addclose">关闭</button>');
								$(".sysworkerMgCar-batch-adddel").on("click",function(){
									var ids=[];
									var wkNe=[];
									var len=$('#regionListAdd tbody input[type="checkbox"]:checked');
									if(len.length==0){
										bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请选择片区！")
									}else{
										$("#regionListAdd tbody").find("input:checked").each(function(){
											
				        					ids.push($(this).val());
				        					
				        					wkNe.push( $(this).next('input').val());
				        					
				        				});
										
			        						var form = $("form[name=addWorkerForm]");
			        	     				form.find("input[name='regionId']").val(ids);
			        	     				form.find("input[name='regionName']").val(wkNe);
			        	     				$("#regionAddModal").modal("hide");
			        	     				$(".modal-backdrop").hide();
			        					
									}
									
									$('#regionListAdd thead input[type="checkbox"]').on("click",function(e){
				        				if(this.checked){
				        			         $('#regionListAdd tbody input[type="checkbox"]:not(:checked)').prop("checked",true);
				        			      } else {
				        			         $('#regionListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
				        			      }
				        			});
								});
								$(".sysworkerMgCar-batch-addclose").on("click",function(){
									$("#regionAddModal").modal("hide");
									$(".modal-backdrop").hide();
									 $('#regionListAdd tbody input[type="checkbox"]:checked').prop("checked",false);
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
			addWorker:function() {
				debugger;
				var form = $("form[name=addWorkerForm]");
				form.ajaxSubmit({
					url : workerAdd.appPath + "/worker/workerAdd.do",
					type : "post",
					success : function(res) {
						var msg = res.msg;
						var code = res.code;
						if (code == "1") {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加成功", function() {
								closeTab("调度员新增");
								$("#workerList").DataTable().ajax.reload(function(){});
							});
						} else {
							bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "添加失败！"+msg);
						}
					},
					beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
						$("span[name='cityIdDd']").empty();
						$("span[name='empNo']").empty();
					   	$("span[name='workerName']").empty();
					   	$("span[name='mobilePhone']").empty();
					   	$("span[name='password']").empty();
					   	$("span[name='regionIdDd']").empty();
						var form = $("form[name=addWorkerForm]");
					    var empNo=$.trim(form.find("input[name=empNo]").val());
					    var workerName=$.trim(form.find("input[name=workerName]").val());
					    var mobilePhone=$.trim(form.find("input[name=mobilePhone]").val());
					    var password=$.trim(form.find("input[name=password]").val());
					    var regionId=form.find("input[name=regionId]").val();
					    var cityId=form.find("select[name=cityId]").val();
					   if(cityId==""){
						   $("span[name='cityIdDd']").append("<font color='red' class='formtips onError emaill'>请选择城市！</font>");
							return false;
					   }else if(regionId==""){
						   $("span[name='regionIdDd']").append("<font color='red' class='formtips onError emaill'>请选择片区！</font>");
							return false;
					   }else if (empNo == "") {
					    	$("span[name='empNo']").append("<font color='red' class='formtips onError emaill'>请输入工号！</font>");
							return false;
			            }else if(!/[0-9A-Za-z]+/.test(empNo)){
			            	$("span[name='empNo']").append("<font color='red' class='formtips onError emaill'>工号只能输入正整数或字母,不能有空格！</font>");
							return false;
			            }
					    if (workerName == "") {
					    	$("span[name='workerName']").append("<font color='red' class='formtips onError emaill'>请输入姓名！</font>");
							return false;
			            }else if (workerName.length > 12) {
					    	$("span[name='workerName']").append("<font color='red' class='formtips onError emaill'>姓名输入长度不能超过12！</font>");
							return false;
			            }
					    if (mobilePhone == "") {
					    	$("span[name='mobilePhone']").append("<font color='red' class='formtips onError emaill'>请输入手机号！</font>");
							return false;
			            }else if(!/^(13[0-9]|15[012356789]|17[01367]|18[0-9]|14[0579])[0-9]{8}$/.test(mobilePhone)){
			            	$("span[name='mobilePhone']").append("<font color='red' class='formtips onError emaill'>手机号格式不正确！</font>");
							return false;
			            }
					    if (password == "") {
					    	$("span[name='password']").append("<font color='red' class='formtips onError emaill'>请输入密码！</font>");
							return false;
			            }
					   
					}
				});
			}
		};
			return workerAdd;
	});

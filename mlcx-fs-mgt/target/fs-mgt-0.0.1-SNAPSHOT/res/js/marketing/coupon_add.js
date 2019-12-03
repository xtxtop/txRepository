define([],function() {
			var couponAdd = {
				appPath : getPath("app"),
				imgPath : getPath("img"),
				init : function() {
					// 新增提交
					$("#addCoupon").click(function() {
						couponAdd.saveCoupon();
					});
					// 新增页面的关闭
					$("#closeAddCoupon").click(function() {
						closeTab("发放优惠券");
					});
					// 上传图片
					$("#addCouponPicUrlButton").click(function() {
						$("#couponPicUrlAddModal").modal("show");
					});
					// 新增图片回填
					$("#addCouponPicBtn").click(
						function() {
							var form = $("form[name=couponPicUrlAddForm]");
							var img = form.find("input[name=couponPicUrl1]").val();
							if (img != "") {
								var form1 = $("form[name=couponAddFrom]");
								form1.find("input[name=photoUrl]").val(img);
								form1.find("img[name=couponPicUrlImg]").attr("src",couponAdd.imgPath+ "/" + img);
								$("#couponPicUrlAddModal").modal("hide");
							} else {
								bootbox.alert("请上传图片！");
							}
						});
					// 解绑弹出层
					$("#couponModal").on("hidden.bs.modal", function() {

					});
					// 车型列表
					$("#checkAddPlanBtn").click(function() {
						couponAdd.pageListPark('plan');
						$("#couponModal").modal({
							show : true,
							backdrop : 'static'
						});
					});

					// 会员列表
					$("#checkAddMemberBtn").click(function() {
						couponAdd.pageListPark('member');
						$("#couponModal").modal({
							show : true,
							backdrop : 'static'
						});
					});
				},
				saveCoupon : function() {
					var form = $("form[name=couponAddFrom]");
					form.ajaxSubmit({
						url : couponAdd.appPath + "/coupon/addCoupon.do",
						type : "post",
						success : function(res) {
							var msg = res.msg;
							var code = res.code;
							if (code == "1") {
								bootbox.alert("优惠券发放成功", function() {
									closeTab("发放优惠券");
									$("#memberList").DataTable().ajax.reload(function() {});
								});
							} else {
								bootbox.alert(msg);
							}
						},
						beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
							$("span[name='planNoAdd']").empty();
							if ($("#planNo").val() == "") {
								$("span[name='planNoAdd']").append("<font color='red'>请选择优惠券方案！</font>");
								return false;
							}
						}
					});
				},
				pageListPark : function(dataType) {
					var tpl = $("#couponBtnTpl").html();
					// 预编译模板
					var template = Handlebars.compile(tpl);
					// 获取配置
					var config = couponAdd.getTypeConfig(dataType);
					var isSearching = false;
					if (dataType == 'member') {
						isSearching = true;
					}
					$('#couponLists').dataTable(
					{
						searching : isSearching,
						destroy : true,
						"ajax" : {
							"type" : "POST",
							"url" : config.url,
							"data" : function(d) {
								return $.extend({},
									d,{
										"pageNo" : (d.start / d.length) + 1,
										"pageSize" : d.length,
										"keyword" : d.search.value
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
						"columns" : config.columns,
						"dom" : "<'row'<''><'col-xs-6'f>r>" + "t" + "<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
						initComplete : function() {
							$(this).find("thead tr th:first-child").prepend('');
							$("#couponToolss").empty().append('<button type="button"  class="btn-new-type coupan-batch-sel">确认</button><button type="button"  class="btn-new-type btn-new-type-blue coupan-batch-close">关闭</button>');
//							$("#couponToolss").empty().append('<button type="button"  class="btn-new-type btn-new-type-blue coupan-batch-close">关闭</button>');
							$(".coupan-batch-sel").on("click",function() {
								$("#couponModal").modal("hide");
								$('#couponLists tbody input[type="checkbox"]:checked').prop("checked",false);
									//获取选中的checkbox
									 /*$.each($('input:checkbox'),function(){
							                if(this.checked){
							                	var len = $('input[type=checkbox]:checked').length;
							                	window.alert("你选了："+  len +"个，其中有："+$(this).val());
							                	if(len > 1){
							                		bootbox.alert("很抱歉、优惠券方案只能选中一种，请重新选择！");
							                	}else{
							                		$("#couponModal").modal("hide");
													$('#couponLists tbody input[type="checkbox"]:checked').prop("checked",false);
							                	}
							                    //
							                }
							           });*/
										
							});
							$(".coupan-batch-close").on("click",function() {$("#couponModal").modal("hide");});
						},
						"drawCallback" : function(settings) {
							couponAdd.couponModalCallback(dataType)
						},
						"columnDefs" : config.columnDefs
					});
				},
				getTypeConfig : function(dataType) {
					if (dataType == 'plan') {// 方案
						return {
							url : couponAdd.appPath+ '/couponPlan/pageListCouponPlanNs.do?censorStatus=1&isAvailable=1',
							columns : [ {
								"title" : "",
								"data" : "planNo"
							}, {
								"title" : "标题",
								"data" : "title"
							}, {
								"title" : "优惠类型",
								"data" : "couponType"
							}, {
								"title" : "优惠方式",
								"data" : "couponMethod"
							}, {
								"title" : "有效起始日期",
								"data" : "vailableTime1"
							}, {
								"title" : "有效结束日期",
								"data" : "vailableTime2"
							}, {
								"title" : "有效天数（天）",
								"data" : "availableDays"
							} ],
							columnDefs : [
									{
										"targets" : [ 0 ],
										"orderable" : false,
										"render" : function(data, type, full, meta) {
											return text = '<input type="radio"  name="idNo" value="'+ full.planNo+ '" nameValue="'+ full.title + '" '+' >';
//											var data = couponAdd.getSelectedData(dataType);
//											for (var i = 0; i < data.length; i++) {
//												if (data[i].id == full.planNo) {
//													text += ' checked="checked" '
//													break;
//												}
//											}
											 
										}
									},
									{
										"targets" : [ 2 ],
										"render" : function(a, b, c, d) {
											if (a == 1) {
												return "优惠券";
											} else {
												return "";
											}
										}
									},
									{
										"targets" : [ 3 ],
										"render" : function(a, b, c, d) {
											if (a == 1) {
												return "折扣";
											} else if (a == 2) {
												return "直减";
											} else {
												return "";
											}
										}
									},
									{
										"targets" : [ 4, 5 ],
										"render" : function(data, type, row,
												meta) {
											if (data) {
												var now = moment(data);
												return now.format('YYYY-MM-DD');
											} else {
												return "";
											}
										}
									} ]
						};
					} else if (dataType == 'member') {// 会员
						return {
							url : couponAdd.appPath + '/member/pageListMember.do?censorStatus=1',
							columns : [ {
								"title" : "",
								"data" : "memberNo",
								"width" : "5px"
							}, {
								"title" : "姓名",
								"data" : "memberName"
							}, {
								"title" : "手机号",
								"data" : "mobilePhone"
							}, {
								"title" : "会员类型",
								"data" : "memberType"
							}, {
								"title" : "集团名称",
								"data" : "companyName"
							}, {
								"title" : "等级",
								"data" : "levelName"
							}, {
								"title" : "状态",
								"data" : "isBlacklist"
							} ],
							columnDefs : [
									{
										"targets" : [ 0 ],
										"orderable" : false,
										"render" : function(data, type, full,
												meta) {
											var text = '<input type="checkbox"  name="idNo" value="'
													+ full.memberNo
													+ '" nameValue="'
													+ full.memberName + '" ';
											var data = couponAdd
													.getSelectedData(dataType);
											for (var i = 0; i < data.length; i++) {
												if (data[i].id == full.memberNo) {
													text += ' checked="checked" '
													break;
												}
											}
											return text + ' >';
										}
									}, {
										"targets" : [ 3 ],
										"render" : function(a, b, c, d) {
											var type;// 会员类型（1、普通会员，2、集团会员，默认1）
											if (a == 1) {
												type = "普通";
											} else {
												type = "集团";
											}
											return type;
										}
									}, {
										"targets" : [ 4 ],
										"render" : function(a, b, c, d) {
											var type;// 会员类型（1、普通会员，2、集团会员，默认1）
											if (c.memberType == 1) {
												return "";
											} else if (c.memberType == 2) {
												return a;
											} else {
												return "";
											}
										}
									}, {
										"targets" : [ 5 ],
										"render" : function(a, b, c, d) {
											if (a) {
												return a;
											} else {
												return "";
											}

										}
									}, {
										"targets" : [ 6 ],
										"orderable" : false,
										"render" : function(a, b, c, d) {
											var isBlacklist1;
											// 是否黑名单（0、非黑名单，1、黑名单，默认0）
											if (c.isBlacklist == 0) {
												isBlacklist1 = "正常"
											}
											if (c.isBlacklist == 1) {
												isBlacklist1 = "黑名单"
											}
											return isBlacklist1;
										}
									}, ]
						};
					}
				},
				getSelectedData : function(type) {
					var data = [];
					var id = "";
					var name = "";

					if (type == 'plan') {
						id = $("#planNo").val();
						name = $("#title").val();
					} else if (type == 'member') {
						id = $("#memberNo").val();
						name = $("#memberName").val();
					}
					if (id != null && id != "" && name != null && name != "") {
						var ids = id.split(",");
						var names = name.split(",");
						if (ids.length != names.length) {
							bootbox.alert("选择的数据错误");
						} else {
							for (var i = 0; i < ids.length; i++) {
								var object = new Object;
								object.id = ids[i];
								object.name = names[i];
								data.push(object);
							}
						}
					}
					return data;
				},
				showSelect : function(type, datas) {
					var idsString = "";
					var nameString = "";

					for (var i = 0; i < datas.length; i++) {
						idsString += datas[i].id + ",";
						nameString += datas[i].name + ",";
					}
					idsString = idsString.substring(0, idsString.length - 1);
					nameString = nameString.substring(0, nameString.length - 1);

					if (type == 'plan') {
						$("#planNo").val(idsString);
						$("#title").val(nameString);

						$.ajax({
									url : couponAdd.appPath+ "/couponPlan/pageViewCoupon.do",
									type : "post",
									data : {
										planNo : $("#planNo").val()
									},
									dataType : "json",
									success : function(plan) {
										if (plan != null) {
											var form = $("form[name=couponAddFrom]");
											if (plan.couponType == 1) {
												form.find("input[name=couponTypeName]").val("优惠券");
											}
											form.find("input[name=couponType]").val(plan.couponType);
											form.find("input[name=couponMethod]").val(plan.couponMethod);
											if (plan.couponMethod == 1) {
												form.find("input[name=couponMethodName]").val("折扣");
												form.find(".couponMethod-1").show();
												form.find(".couponMethod-2").hide();
												form.find("input[name=discountAmount]").val("");
												form.find("input[name=discount]").val(plan.discount)
											} else {
												form.find("input[name=couponMethodName]").val("直减");
												form.find(".couponMethod-1").hide();
												form.find(".couponMethod-2").show();
												form.find("input[name=discount]").val("");
												form.find("input[name=discountAmount]").val(plan.discountAmount)
											}
											form.find("input[name=discount]").val(plan.discount);
											form.find("input[name=discountAmount]").val(plan.discountAmount);
											if (plan.availableDays != null) {
												form.find(".availableTime-1").show();
												form.find(".availableTime-2").hide();
												form.find("input[name=vailableTime1]").val("");
												form.find("input[name=vailableTime2]").val("");
												form.find("input[name=availableDays]").val(plan.availableDays);
											} else if (plan.vailableTime1 != null) {
												form.find(".availableTime-1").hide();
												form.find(".availableTime-2").show();
												form.find("input[name=availableDays]").val("");
												form.find("input[name=vailableTime1]").val(couponAdd.getDate(plan.vailableTime1));
												form.find("input[name=vailableTime2]").val(couponAdd.getDate(plan.vailableTime2));
											}

										} else {
											bootbox.alert("数据错误！");
										}
									}
								});

					} else if (type == 'member') {
						$("#memberNo").val(idsString);
						$("#memberName").val(nameString);
					}

				},
				getRemovedArray : function(unSelectIds, oldArray) {//得到已排除未选择的项的数组
					var delIndexs = [];
					for (var i = 0; i < unSelectIds.length; i++) {
						for (var j = 0; j < oldArray.length; j++) {
							if (oldArray[j].id == unSelectIds[i]) {
								delIndexs.push(j);
								break;
							}
						}

					}
					if (delIndexs.length > 0) {
						var newArray = [];
						for (var i = 0; i < oldArray.length; i++) {
							var isAdd = true;
							for (var j = 0; j < delIndexs.length; j++) {
								if (i == delIndexs[j]) {
									isAdd = false;
									break;
								}
							}
							if (isAdd) {
								newArray.push(oldArray[i]);
							}
						}
						return newArray;
					}
					return oldArray;
				},
				getAddedArray : function(selectData, oldArray) {////得到已新增选择的项的数组
					for (var i = 0; i < selectData.length; i++) {
						var isPush = true;
						for (var j = 0; j < oldArray.length; j++) {
							if (oldArray[j].id == selectData[i].id) {
								isPush = false;
							}
						}
						if (isPush) {
							oldArray.push(selectData[i]);
						}
					}
					return oldArray;
				},
				couponModalCallback : function(type) {
					$("input[name=idNo]").click(
							function(){
								var nameVal = $(this).attr('nameValue');
								var idVal = $(this).val();
								$("#title").val(nameVal).attr('');
								$("#planNo").val(idVal);
								var data = couponAdd.getSelectedData(type);
								couponAdd.showSelect(type, data);
//								$("#couponModal").hide();
							})
//									function(a, b, c, d) {
//										debugger;
//										var selectIds = [];
//										var unSelectIds = [];
//										var selectData = [];
//										var selectNames = [];
//
//										var data = couponAdd.getSelectedData(type);
//										var len = $('#couponLists tbody input[type="checkbox"]:checked').length;
//										var isRemoveOldPlan = false;
//										if (type == 'plan' && data != null && len > 1) {
//											isRemoveOldPlan = true;
//										}
//
//										$("#couponLists tbody")
//												.find("input:checked")
//												.each(function() {
//														if (isRemoveOldPlan && $(this).val() == data[0].id) {
//																$(this).removeAttr("checked");
//															} else {
//																var object = new Object;
//																object.id = $(this).val();
//																object.name = $(this).attr('nameValue');
//																selectData.push(object);
//															}
//														});
//										$("#couponLists tbody").find(
//												"input[type=checkbox]").not("input:checked").each(function() {
//													unSelectIds.push($(this).val());
//												});
//
//										//方案单选固将之前已选中的数据放入移除的数据中
//										if (type == 'plan' && data != null && selectData != null && selectData.length > 1) {
//											for (var i = 0; i < selectData.length; i++) {
//												if (data[i].id == selectData.planNo) {
//													unSelectIds.push(data[i].id);
//												}
//											}
//										}
//										//新增
//										data = couponAdd.getAddedArray(selectData, data);
//										//移除
//										data = couponAdd.getRemovedArray(unSelectIds, data);
//										//展示
//										couponAdd.showSelect(type, data);
//									});
				},
				getDate : function(str) {
					var oDate = new Date(str), year = oDate.getFullYear(), month = oDate
							.getMonth() + 1, day = oDate.getDate(), oTime = year
							+ '-'
							+ couponAdd.getzf(month)
							+ '-'
							+ couponAdd.getzf(day);
					return oTime;
				},
				getzf : function(num) {
					if (parseInt(num) < 10) {
						num = '0' + num;
					}
					return num;
				}
			}
			return couponAdd;
		})
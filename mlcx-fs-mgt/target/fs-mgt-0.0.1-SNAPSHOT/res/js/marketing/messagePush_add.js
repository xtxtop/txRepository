define([],function() {
var messagePushAdd = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {
			//新增提交
			$("#addmessagePush").click(function(){
				messagePushAdd.savemessagePush();
			});
			//新增页面的关闭
			$("#closeAddmessagePush").click(function(){
				closeTab("新增消息推送");
			});
			
			//推送类型联动功能
			var form = $("form[name=messagePushAddFrom]");
			form.find("select[name='pushPattern']").change(function(){
				if(form.find("select[name='pushPattern']").val() == 1){
					 form.find(".memberName-div").show(); 
				}else{
					form.find(".memberName-div").hide(); 
					form.find("input[name=memberNo]").val("")
					form.find("input[name=memberName]").val("")
				}
		    });
			
			//会员列表
			$("#memberPushModalBtn").click(function(){
				messagePushAdd.pageListModel();
				$("#memberPushModal").modal({
					 show:true,
					 backdrop:'static'
				});
			});
		},
		savemessagePush:function() {
			var form = $("form[name=messagePushAddFrom]");
			form.ajaxSubmit({
				url : messagePushAdd.appPath + "/messagePush/addMessagePush.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "消息推送添加成功", function() {
							closeTab("新增消息推送");
							$("#messagePushList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "消息推送添加失败！");
					}
			},
			beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
				$("span[name='titleAdd']").empty();
				$("span[name='contentAdd']").empty();
				$("span[name='memberNameAdd']").empty();
				if (form.find("select[name='pushPattern']").val() == 1) {
					if(form.find("input[name='memberNo']").val() == null || form.find("input[name='memberNo']").val() == ""){
		            	$("span[name='memberNameAdd']").append("<font color='red'>推送的会员不能为空！</font>");
						return false;
					}
				}
				if (form.find("input[name='title']").val()=="") {
					$("span[name='titleAdd']").append("<font color='red'>请输入标题！</font>");
					return false;
				}
				if (form.find("textarea[name='content']").val()=="") {
					$("span[name='contentAdd']").append("<font color='red'>请输入消息推送内容！</font>");
					return false;
				}
			}
			});
		},pageListModel:function () {
			var tpl = $("#memberPushBtnTpl").html();
			// 预编译模板
			var template = Handlebars.compile(tpl);
			$('#memberPushLists').dataTable( {
				searching:false,
				destroy: true,
				"ajax": {
					"type": "POST",
					"url": messagePushAdd.appPath + '/member/pageListMember.do',
					"data": function ( d ) {
						return $.extend( {}, d, {
							"pageNo": (d.start/d.length)+1,
							"pageSize":d.length,
							"keyword":d.search.value
						} );
					},
					"dataSrc": function ( json ) {
						json.recordsTotal=json.rowCount;
						json.recordsFiltered=json.rowCount;
						json.data=json.data;
						return json.data;
					},
					error: function (xhr, error, thrown) {  
		            }
				},
			   "columns": [
					{ "title":"","data": "memberNo" },
					{ "title":"会员名称","data": "memberName" },
					{ "title":"性别","data": "sex" },
					{ "title":"手机号","data": "mobilePhone" },
					{ "title":"认证","data": "censorStatus" },
					{ "title":"状态","data": "isBlacklist" },
					{ "title":"押金","data": "deposit" }
				],
			   "dom": "<'row'<''><'col-xs-6'f>r>" +"t" +"<'row'<'col-xs-3'l><'col-xs-3'i><'col-xs-6'p>>",
			   initComplete: function () {
				   	$(this).find("thead tr th:first-child").prepend('');
					$("#memberPushTools").empty().append('<button type="button"  class="btn-new-type memberPush-batch-sel">确认</button><button type="button"  class="btn-new-type btn-new-type-blue memberPush-batch-close">关闭</button>');
//					$("#memberPushTools").append('');
					$(".memberPush-batch-sel").on("click",function(){
						messagePushAdd.setSelectedData();
						$("#memberPushModal").modal("hide");
						$('#memberPushLists tbody input[type="checkbox"]:checked').prop("checked",false);
					});
					$(".memberPush-batch-close").on("click",function(){
						messagePushAdd.setDataTemp(messagePushAdd.getSelectedData());//如果取消时，则需要把缓存区的数据变为与已确认的一致
						$("#memberPushModal").modal("hide");
					});
				},
				"drawCallback": function( settings ) {
					messagePushAdd.modalCallback()
			    },
				"columnDefs":[
						{
							"targets" : [0],
							"orderable":false,
							"render" : function(data, type, full, meta) {
								var nameValue = '';
								if(full.memberName != null){
									nameValue = '" nameValue="' + full.memberName +'" ';
								}
								var text = '<input type="checkbox"  name="idNo" value="' + full.memberNo + '" '+ nameValue;
								var data = messagePushAdd.getDataTemp();
								for(var i = 0; i < data.length;i ++){
									if(data[i].id == full.memberNo){
										text += ' checked="checked" '
										break;
									}
								}
								return text +' >';
							}
					},{
						"targets" : [ 2 ],
						"render" : function(a,
								b, c, d) {
							var sex1;
							if(c.sex==1){
								sex1="男";
							}else if(c.sex==0){
								sex1="女";
							}else{
								sex1="未知";
							}
							return sex1;
						}
					},{
						"targets" : [ 4 ],
						"render" : function(a,b, c, d) {
							var authenticate;
							//（0、未审核/未认证，1、已审核/已认证，2、待审核/待认证，3、未通过，默认0）
							if(a==0){
								authenticate="未认证"
							}else if(a==1){
								authenticate="已认证"
							}else if(a==2){
								authenticate="待认证"
							}else if(a==3){
								authenticate="未通过"
							}else{
								return "";
							}
							return authenticate;
						}
					},{
						"targets" : [ 5 ],
						"orderable" : false,
						"render" : function(a,b, c, d) {
							var isBlacklist1 ;
							//是否黑名单（0、非黑名单，1、黑名单，默认0）
							if(c.isBlacklist==0){
								isBlacklist1="正常"
							}
							if(c.isBlacklist==1){
								isBlacklist1="黑名单"
							}
							return isBlacklist1;
						}
					},{
						"targets" : [ 6 ],
						"orderable" : false,
						"render" : function(a,b, c, d) {
							if(c.deposit==0){
								return "无";
							}
							if(c.deposit>0){
								return "有";
							}
							return "无";
						}
				 }]
			});
		},getSelectedData:function () {//得到已经确认选择的数据
			var data = [];
			var form = $("form[name=messagePushAddFrom]");
			var id = form.find("input[name='memberNo']").val()
			var name =  form.find("input[name='memberName']").val();
			
			if(id != null && id != "" && name != null && name != ""){
				var ids = id.split(",");
				var names = name.split(",");
				for(var i = 0; i < ids.length;i ++){
					var object = new Object;
					object.id = ids[i];
					object.name = names[i];
					data.push(object);
				}
			}
			
			return data;
		},setSelectedData:function () {
			var data = messagePushAdd.getDataTemp();//获取缓存区的数据
			var form = $("form[name=messagePushAddFrom]");
			if(data != null && data.length > 0){
				var idsString = "";
				var nameString = "";
				for(var i = 0; i < data.length;i ++){
					idsString += data[i].id + ",";
					nameString += data[i].name + ",";
				}
				idsString = idsString.substring(0,idsString.length-1);
				nameString = nameString.substring(0,nameString.length-1);
				
				form.find("input[name='memberNo']").val(idsString);
				form.find("input[name='memberName']").val(nameString);
			}else{
				form.find("input[name='memberNo']").val("");
				form.find("input[name='memberName']").val("");
			}
		},getDataTemp:function () {//得到缓存区的数据
			var jsonString = $("#memberPushModal").find("input[name='dataTemp']").val();
			if(jsonString != null  && jsonString != ""){
				return JSON.parse(jsonString);
			}
			return [];
		},setDataTemp:function (data) {//设置缓存区的数据
			var input = $("#memberPushModal").find("input[name='dataTemp']");
			if(input != null){
				if(data != null && data.length > 0){
					input.val(JSON.stringify(data));
				}else{
					input.val("");//传入的data为空时清除缓存区的数据
				}
			}
		},modalCallback:function () {
			$("input[name=idNo]").click(function(a,b,c,d){
				var data = messagePushAdd.getDataTemp();//获取缓存区的数据
				if(data == null){
					data = [];
				}
				
				var newData = [];
				if($(this).is(':checked')){
					newData = data;
					var thisData = new Object;
					thisData.id = $(this).val();
					thisData.name = $(this).attr('nameValue') == null ? "" : $(this).attr('nameValue');
					newData.push(thisData)
				}else{
					if(data.length > 0){
						for(var i = 0; i < data.length;i ++){
							if(data[i].id != $(this).val()){
								newData.push(data[i])
							}
						}
					}
				}
				messagePushAdd.setDataTemp(newData);
			});
		}
	}
	return messagePushAdd;
})

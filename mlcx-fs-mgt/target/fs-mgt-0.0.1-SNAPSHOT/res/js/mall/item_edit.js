define([],function() {
var itemEdit = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {

			//初始化树	(在这加一个是修复第一次加载时保留上次选择的)				
			itemEdit.viewTree("itemSortTreeSelItemEdit",null,false,true);
			$("#itemSortTreeSelItemEdit").jstree().uncheck_all();
            $("#itemSortTreeSelItemEdit").jstree().deselect_all(true);	
			//编辑提交
			$("#editItem").click(function(){
				itemEdit.editItem();
			});
			//编辑页面的关闭
			$("#closeEditItem").click(function(){
				closeTab("商品编辑");
			});
			//上传图片
			$("#editPicUrlButton").click(function(){
				$("#itemUrlEditModal").modal("show");
			});
			//编辑图片回填
			$("#editPicBtn").click(function(){
				var form=$("form[name=picUrlEditForm]");
				var img=form.find("input[name=picUrl1]").val();
				if(img!=""){
					var form1=$("form[name=itemEditFrom]");
					form1.find("input[name=picUrl]").val(img);
					form1.find("#picUrlImg").css("background-image", "url(" + itemEdit.imgPath + '/' + img + ")");
					$("#picUrlEditModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
		},

		//分类树
		viewTree: function (domId,condition,multiple,visible) {
			var form = $("form[name=itemEditFrom]");
			var sortNo = form.find("input[name='sortNo']").val();
            $("#"+domId).jstree({
                "core": {
                    "animation": 0,
                    "check_callback": true,
                    "themes": { "stripes": true },
                    "multiple" : multiple ,
                    "data" : {
		        				"url" : itemEdit.appPath+"/mall/itemSortTree.do?type=1&selectedId="+sortNo,
		        				"cache" : false,
		        				"data" : function (node) {
		        					return node;
	        				 }
        			 }                   
                },
                "checkbox" : {
                    "keep_selected_style" : false,
                    "three_state" : false,
                    "cascade" :"undetermined",
                    "visible":visible
                 },
                "plugins": [
                    "contextmenu", "dnd", "search",
                    "state", "types", "wholerow","checkbox"
                ]
            });        	
        },
		editItem:function() {
			var form = $("form[name=itemEditFrom]");
			var sortNo = "";
			if($("#itemSortTreeSelItemEdit").jstree().get_selected().length){
				sortNo = $("#itemSortTreeSelItemEdit").jstree().get_selected()[$("#itemSortTreeSelItemEdit").jstree().get_selected().length-1];
			}
			form.find("input[name='sortNo']").val(sortNo);
			form.ajaxSubmit({
				url : itemEdit.appPath + "/mall/updateItem.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "商品编辑成功", function() {
							closeTab("商品编辑");
							$("#itemList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "商品编辑失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='itemNameEdit']").empty();
					$("span[name='sortNoEdit']").empty();
					$("span[name='picUrlEdit']").empty();
					$("span[name='picUrlAdd']").empty();
					$("span[name='pointsAdd']").empty();
					$("span[name='contentEdit']").empty();
					if (form.find("input[name='itemName']").val()=="") {
						$("span[name='itemNameEdit']").append("<font color='red'>请输入商品名称！</font>");
						return false;
					}
					if (form.find("select[name='sortNo']").val()=="") {
						$("span[name='sortNoEdit']").append("<font color='red'>请选择商品类型！</font>");
						return false;
					}
					if (form.find("input[name='picUrl']").val()=="") {
						$("span[name='picUrlEdit']").append("<font color='red'>请上传商品图片！</font>");
						return false;
					}
					if (form.find("input[name='points']").val()=="") {
						$("span[name='pointsAdd']").append("<font color='red'>请输入积分！</font>");
						return false;
					}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='points']").val())){
		            	$("span[name='pointsAdd']").append("<font color='red'>格式不正确，只能输入正整数！</font>");
						return false;
		            }
					if (form.find("input[name='num']").val()=="") {
						$("span[name='numAdd']").append("<font color='red'>请输入积分！</font>");
						return false;
					}else if(!/^[0-9]*[1-9][0-9]*$/.test(form.find("input[name='num']").val())){
		            	$("span[name='numAdd']").append("<font color='red'>格式不正确，只能输入正整数！</font>");
						return false;
		            }
					if (form.find("textarea[name='contentE']").val()=="") {
						$("span[name='contentEEdit']").append("<font color='red'>请输入商品描述！</font>");
						return false;
					}
				}
			});
		}
}
return itemEdit;
})

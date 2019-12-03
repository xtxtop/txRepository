define([],function() {
var itemAdd = {
		appPath : getPath("app"),
		imgPath : getPath("img"),
		init : function() {

			//初始化树	(在这加一个是修复第一次加载时保留上次选择的)				
			itemAdd.viewTree("itemSortTreeSelItemAdd",null,false,true);
			$("#itemSortTreeSelItemAdd").jstree().uncheck_all();
            $("#itemSortTreeSelItemAdd").jstree().deselect_all(true);	
            
			//新增提交
			$("#addItem").click(function(){
				itemAdd.saveItem();
			});
			//新增页面的关闭
			$("#closeAddItem").click(function(){
				closeTab("新增商品");
			});
			//上传图片
			$("#addPicUrlButton").click(function(){
				$("#picUrlAddModal").modal("show");
			});
			//新增图片回填
			$("#addPicBtn").click(function(){
				var form=$("form[name=picUrlAddForm]");
				var img=form.find("input[name=picUrl1]").val();
				if(img!=""){
					var form1=$("form[name=itemAddFrom]");
					form1.find("input[name=picUrl]").val(img);
					form1.find("#picUrlImg").css("background-image", "url(" + itemAdd.imgPath + '/' + img + ")");
					$("#picUrlAddModal").modal("hide");
				}else{
					bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "请上传图片！");
				}
			});
		},		
		//分类树
		viewTree: function (domId,condition,multiple,visible) {
            $("#"+domId).jstree({
                "core": {
                    "animation": 0,
                    "check_callback": true,
                    "themes": { "stripes": true },
                    "multiple" : multiple ,
                    "data" : {
		        				"url" : itemAdd.appPath+"/mall/itemSortTree.do?type=1",
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
		saveItem:function() {
			var form = $("form[name=itemAddFrom]");
			var sortNo = "";
			if($("#itemSortTreeSelItemAdd").jstree().get_selected().length){
				sortNo = $("#itemSortTreeSelItemAdd").jstree().get_selected()[$("#itemSortTreeSelItemAdd").jstree().get_selected().length-1];
			}
			form.find("input[name='sortNo']").val(sortNo);
			form.ajaxSubmit({
				url : itemAdd.appPath + "/mall/addItem.do",
				type : "post",
				success : function(res) {
					var msg = res.msg;
					var code = res.code;
					if (code == "1") {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "商品添加成功", function() {
							closeTab("新增商品");
							$("#itemList").DataTable().ajax.reload(function(){});
						});
					} else {
						bootbox.alert("<img src='res/img/tan.png' style='width: 29px;height: 29px;margin-top: -4px'>&nbsp;&nbsp;" + "商品添加失败！");
					}
				},
				beforeSubmit : function(formData, jqForm, options) {// 提交表单前数据验证
					$("span[name='itemNameAdd']").empty();
					$("span[name='sortNoAdd']").empty();
					$("span[name='picUrlAdd']").empty();
					$("span[name='pointsAdd']").empty();
					$("span[name='numAdd']").empty();
					$("span[name='contentAdd']").empty();
					if (form.find("input[name='itemName']").val()=="") {
						$("span[name='itemNameAdd']").append("<font color='red'>请输入商品名称！</font>");
						return false;
					}
					if (form.find("input[name='sortNo']").val()=="") {
						$("span[name='sortNoAdd']").append("<font color='red'>请选择商品分类！</font>");
						return false;
					}
					if (form.find("input[name='picUrl']").val()=="") {
						$("span[name='picUrlAdd']").append("<font color='red'>请上传商品图片！</font>");
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
					if (form.find("textarea[name='content']").val()=="") {
						$("span[name='contentAdd']").append("<font color='red'>请输入商品描述！</font>");
						return false;
					}
				}
		});
}
}
return itemAdd;
})

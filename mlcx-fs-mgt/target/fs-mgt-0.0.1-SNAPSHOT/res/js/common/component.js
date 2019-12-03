/**
 * 
 */

define([],function() {
	var component={
			appPath: getPath("app"),
			isFirstShow:{"item":false,"seller":false,"memberLevel":false,"brand":false,"itemCat":false,"isLoad":false},
			brand:{"pageNo":1,"pageSize":10},
			init: function(callback){
				if(!component.isFirstShow.isLoad){
					$("#appComponent").load("res/tpl/component.html",function(){
						 if(callback){
							 callback();
						 }
						 component.isFirstShow.isLoad=true;
						 $("#componentItemModal").on("show.bs.modal", function() {  
				         });
						 $("#componentItemModal").on("hidden.bs.modal", function() {  
				         });
						 $("#componentBrandModal").on("show.bs.modal", function() {  
				         });
						 $("#componentBrandModal").on("hidden.bs.modal", function() {  
				         });
						 $("#componentItemCatModal").on("show.bs.modal", function() { 
				         });
						 $("#componentItemCatModal").on("hidden.bs.modal", function() {  
				         });
						 $("#componentSellerModal").on("show.bs.modal", function() { 
				         });
						 $("#componentSellerModal").on("hidden.bs.modal", function() {  
				         });
						 $("#componentMemberLevelModal").on("show.bs.modal", function() {  
				         });
						 $("#componentMemberLevelModal").on("hidden.bs.modal", function() {  
				         });
					});
				}else{
					if(callback){
						 callback();
					 }
				}
			},
		    brandPageList: function(config,fn){	
		    	component.init(function(){
		    	$("#componentBrandModal").modal("show");
		    	if(!component.isFirstShow.brand){
		    		$("#componentBrandModal .previous").on("click",function(){
			    		if(component.brand.pageNo){
			    			component.brand.pageNo--;
			    			getData(component.brand.pageNo);
						} 
					});
					$("#componentBrandModal .next").on("click",function(){
						component.brand.pageNo++;
						getData(component.brand.pageNo);
					});
					$("#componentBrandModal .reset").on("click",function(){
						$("#componentBrandModal .brand-list").find(".selected").removeClass("selected").css("background","#fff");
					});
					$("#componentBrandModal .submit").on("click",function(){
						var result=[];
						$("#componentBrandModal .brand-list").find(".selected").each(function(){
							var brand={};
							brand.brandId=$(this).data("brandid");
							brand.brandName=$(this).data("brandname");
							brand.webSite=$(this).data("website");
							result.push(brand);							
						});
						if(!result.length){
							bootbox.alert("请选择品牌！");
							return;
						}
						if(fn){
							fn(result);
							$("#componentBrandModal").modal("hide");
						}
					});
		    	}
				getData(component.brand.pageNo);
				component.isFirstShow.brand=true;
				function getData(pageNo){
			    	$.ajax({
		        		url: component.appPath+"/brand/pageList.do", 
		        		data: {
		        			"pageNo": pageNo,
	    					"pageSize":component.brand.pageSize
		        		},
		        		success: function(data){
		        			if(data){
		        		    	var pageSize=component.brand.pageSize;
		        				var page=data.data;
		        				if((pageSize*pageNo) < data.rowCount){
		        					$("#componentBrandModal .next").show();
		        				}else{
		        					$("#componentBrandModal .next").hide();
		        				}
		        				if(pageNo==1){
		        					$("#componentBrandModal .previous").hide();
		        				}else{
		        					$("#componentBrandModal .previous").show();
		        				}
		        				var html="";
		        				for(i in page){//class="btn btn-default btn-flat"
		        					html+='<div class="col-md-3"><a '+ 
		        						  'href="#" data-brandid="'+page[i].brandId+'" data-brandname="'+page[i].brandName+'" data-website="'+page[i].website+'">'+page[i].brandName+'</a></div>';
		        					if(i==0){
		        						html='<div class="row">'+html;
		        					}
		                			if(i!=0){
		                    			if(i+1%4==0){
		                    				html=html+'</div><div class="row">';
		                    			}
		                			}
		                			if(i==page.length-1){
		                				html=html+'</div>';
		                			}
		        				}
		        				$("#componentBrandModal .brand-list").html(html);
		        				$("#componentBrandModal .brand-list").find("a")//.each(function(){
		        					.on("click",function(){
		        						if(!config.multiple){
		        							$("#componentBrandModal .brand-list").find(".selected").removeClass("selected").css("background","#fff");
		        						}
		        						if(!$(this).hasClass("selected")){
			        						$(this).addClass("selected");
		        							$(this).css("background","#ddd");
		        						}else{
			        						$(this).removeClass("selected");
		        							$(this).css("background","#fff");
		        						}
		        					});
		        				//});
		        			}
		        		}
		        	}); 					
				}
		    	});
		    },
	        itemCatTree: function(config,fn){
	        	component.init(function(){
	        	$("#componentItemCatModal").modal("show");
	        	$("#componentItemCatModal").find(".search").val("");
	        	if(!config.domId){
	        		config.domId="componentItemCatModal .itemcat-tree";
	        	}
		    	if(!component.isFirstShow.itemCat){
					$("#componentItemCatModal .reset").on("click",function(){
						$("#"+config.domId).jstree().uncheck_all();
						$("#componentItemCatModal").find(".search").val("");
						$("#"+config.domId+" a").removeClass("jstree-search");						
					});
					$("#componentItemCatModal .submit").on("click",function(){
					   var selected=$("#"+config.domId).jstree("get_selected",true);
					   var result=[];
					   for(x in selected){
						   var s=selected[x].children.length;
						   if(selected[x].children.length>0){
							   bootbox.alert("商品分类只能选最后一级，请重新选择！",function(){
								    //$("#"+config.domId).jstree().refresh();
						    		return;    
							   });
						   }else{
							   var itemCat={catId:selected[x].id,catName:selected[x].text};
							   result.push(itemCat); 
						   }
					   }
					   if(fn){
						   fn(result);
					   }
					   $("#componentItemCatModal").modal("hide");
					});
		    	}else{
		    		$("#"+config.domId).jstree().refresh();
		    		return;
		    	}
	            $("#"+config.domId).jstree({
	                "core": {
	                    "animation": 0,
	                    "check_callback": true,
	                    "themes": { "stripes": true },
	                    "multiple": config.multiple ,
	                    "expand_selected_onload":true,
	                    "data": {
			        				"url": function(node){
				        					if(config.condition){
				        					    node.catId=config.condition.catId;
				        					}
			        						return node.catId?component.appPath+"/itemCat/childTree.do" :
			        										component.appPath+"/itemCat/tree.do";
			        				},	
			        				"cache": false,
			        				"data": function (node){
			        					if(config.condition){
			        					    node.catId=config.condition.catId;
			        					}
			        					return node;
		        				     }
	        			 }
	                },
	                "checkbox": {
	                    "keep_selected_style": false,
	                    "three_state": false,
	                    "cascade":"undetermined",
	                    /*"tie_selection":false,*/
	                    "visible": config.visible
	                 },
	                "plugins": [
	                    "contextmenu", "dnd", "search",
	                    "state", "types", "wholerow","checkbox"
	                ]
	            });
	            $("#"+config.domId).bind("select_node.jstree", function(e){
	            	var selected=$(this).jstree("get_selected");
	                $.each(selected,function(){
	                	//console.log("path:", $("#"+config.domId).jstree().get_path(this,null,true));
	                	var path=$("#"+config.domId).jstree().get_path(this,null,true);
	                	$.each(path,function(index){
	                		 if(index!=path.length-1){
	                			 $("#"+config.domId).jstree().deselect_node(this);
	                		 }
	                	});
	                });
	            });
	            $("#"+config.domId).bind("ready.jstree", function(e){
	            	$(this).jstree().uncheck_all();
	            	$("#"+config.domId+" a").removeClass("jstree-search");
	            });
	            $("#"+config.domId).bind("refresh.jstree", function(e){
	            	$(this).jstree().uncheck_all();
	            	$("#"+config.domId+" a").removeClass("jstree-search");
	            });
	            var to = false;
	            $("#componentItemCatModal").find(".search").keyup(function(){
	              if(to){ 
	            	  clearTimeout(to); 
	              }
	              to = setTimeout(function(){
	                var v =  $("#componentItemCatModal").find(".search").val();
	                $("#"+config.domId).jstree(true).search(v);
	              }, 250);
	            });	
	            component.isFirstShow.itemCat=true;
	        	});
	        },
	        sellerPageList: function(config,fn){
	        	component.init(function(){
	        	$("#componentSellerModal").modal("show");
	        	if(component.isFirstShow.seller){
	        		$("#componentSellerPageList").DataTable().ajax.reload();
	        		return;
	        	}
	        	$("#componentSellerPageList").dataTable({
	        		"ajax": {
	        			"type": "POST",
	        			"url": component.appPath+"/seller/pageList.do",
	        			"data": function ( d ) {
	        				return $.extend( {}, d, {
	        					"pageNo": (d.start/d.length)+1,
	        					"pageSize":d.length,
	        					"censorStatus":1,
	        					"sellerName":d.search["value"]
	        				} );
	        			},
	        			"dataSrc": function ( json ) {
	        				json.recordsTotal=json.rowCount;
	        				json.recordsFiltered=json.rowCount;
	        				json.data=json.data;
	        				return json.data;
	        			},
	        			"error": function (xhr, error, thrown) {  
	                    }
	        		},
	        		//stateSave: true,
	        		//"ordering": false,
	        		"order": [[ 2, "desc" ]],
	        		"columns": [
	        		    { "title":"","data": "sellerId","width":"5px"},
	        		    {
	  					  "defaultContent":"",
	  					  "className":"details-control",					  
	  					  "orderable": false,
	  					  "width":"5px"
	  					},        		    
	        			{ "title":"商家名称","data": "sellerName" },
	        			{ "title":"商家类型","data": "sellerType" },
	        			{ "title":"是否个人","data": "isIndiv" }
	        		],
	        	   "dom": "<'row'<'col-xs-12'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
	        	   "initComplete": function () {
	        		   if(config.multiple){
		        		    $(this).find("thead tr th:first-child").prepend('<input type="checkbox">');
		        		    $(this).find('thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#componentSellerPageList tbody input[name="componentSeller"][type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			    	 $('#componentSellerPageList tbody input[name="componentSeller"][type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});	        			   
	        		   }
	        		},
	        		"drawCallback": function( settings ) {
	        			if(config.multiple){
	        				component.operateColumnEvent("componentSellerPageList");
	        			}else{
	       					$('#componentSellerPageList tbody input[type="checkbox"]').on("click",function(e){
			        				if(this.checked){
			        					 $('#componentSellerPageList tbody input[name="componentSeller"][type="checkbox"]:checked').prop("checked",false);
			        					 $(this).prop("checked",true);
			        				}else{
			        					 $(this).prop("checked",false);
			        				}
	     					});
	        			} 	    				
	        	    },
	        		"columnDefs": [
	        		    { "targets":[0],
	        		      //"visible": true,
	        		      "orderable":false,
	        		      "render":function (data, type, full, meta){
	        		               return '<input type="checkbox" name="componentSeller" value="' + $('<div/>').text(data).html() + '">';
	        		      }
	        		    }        			
	        		]
	        	});
				$("#componentSellerModal .submit").on("click",function(){
						var checked=$("#componentSellerPageList tbody input[name='componentSeller'][type='checkbox']:checked");
						if(!checked.length){
							bootbox.alert("请选择商家！");
							return;
						}
						var result=[];
						checked.each(function(){
					        var tr=$(this).closest("tr");
					        var row=$("#componentSellerPageList").DataTable().row(tr); 
					        result.push(row);
						});
     					if(fn){
     						fn(result);
     						$("#componentSellerModal").modal("hide");
     					}	
				});
				component.isFirstShow.seller=true;
	        	});
	        },
	        memberLevelPageList: function(config,fn){
	        	component.init(function(){
	        	$("#componentMemberLevelModal").modal("show");
	        	if(component.isFirstShow.memberLevel){
	        		$("#componentMemberLevelPageList").DataTable().ajax.reload();
	        		return;
	        	}
	        	$("#componentMemberLevelPageList").dataTable( {
	        		"retrieve": true,
	        		"ajax": {
	        			"type": "POST",
	        			"url": component.appPath+"/memberLevel/queryMemberLevel.do",
	        			"data": function ( d ) {
	        				return $.extend( {}, d, {
	        					"pageNo": (d.start/d.length)+1,
	        					"pageSize":d.length,
	        					"censorStatus":1,
	        					"levelName":d.search["value"]
	        				} );
	        			},
	        			"dataSrc": function ( json ) {
	        				json.recordsTotal=json.rowCount;
	        				json.recordsFiltered=json.rowCount;
	        				json.data=json.data;
	        				return json.data;
	        			},
	        			"error": function (xhr, error, thrown) {  
	                    }
	        		},
	        		//stateSave: true,
	        		//"ordering": false,
	        		"order": [[ 2, "desc" ]],
	        		"columns": [
	        		    { "title":"","data": "memberLevelId","width":"5px"},
	        		    {
	  					  "defaultContent":"",
	  					  "className":"details-control",					  
	  					  "orderable": false,
	  					  "width":"5px"
	  					},        		    
						{ "title":"级别名称","data": "levelName" }
	        		],
	        	   "dom": "<'row'<'col-xs-12'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
	        	   "initComplete": function () {
	        		   if(config.multiple){
		        		    $(this).find("thead tr th:first-child").prepend('<input type="checkbox">');
		        		    $(this).find('thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#componentMemberLevelPageList tbody input[name="componentMemberLevel"][type="checkbox"]:not(:checked)').prop("checked",true);
		        			      } else {
		        			    	 $('#componentMemberLevelPageList tbody input[name="componentMemberLevel"][type="checkbox"]:checked').prop("checked",false);
		        			      }
		        			});	        			   
	        		   }
	        		},
	        		"drawCallback": function( settings ) {
	        			if(config.multiple){
	        				component.operateColumnEvent("componentMemberLevelPageList");
	        			}else{
	        				$('#componentMemberLevelPageList tbody input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        					 $('#componentMemberLevelPageList tbody input[name="componentMemberLevel"][type="checkbox"]:checked').prop("checked",false);
		        					 $(this).prop("checked",true);
		        				}else{
		        					 $(this).prop("checked",false);
		        				}
	        				});
	        			}	    				
	        	    },
	        		"columnDefs": [
	        		    { "targets":[0],
	        		      //"visible": true,
	        		      "orderable":false,
	        		      "render":function (data, type, full, meta){
	        		               return '<input type="checkbox" name="componentMemberLevel" value="' + $('<div/>').text(data).html() + '">';
	        		      }
	        		    }        			
	        		]
	        	});
	        	
				$("#componentMemberLevelModal .submit").on("click",function(){
						var checked=$("#componentMemberLevelPageList tbody input[name='componentMemberLevel'][type='checkbox']:checked");
						if(!checked.length){
							bootbox.alert("请选择会员级别！");
							return;
						}	
						var result=[];
						checked.each(function(){
					        var tr=$(this).closest("tr");
					        var row=$("#componentMemberLevelPageList").DataTable().row(tr);
					        result.push(row.data());
						});							
     					if(fn){
     						fn(result);
 						    $("#componentMemberLevelModal").modal("hide");
     					}	
				});
				component.isFirstShow.memberLevel=true;
	        	});
	        },
	        itemPageList: function(config,fn){
	        	component.init(function(){
	        	$("#componentItemModal").modal("show");
	        	if(component.isFirstShow.item){
	        		$("#componentItemPageList").DataTable().ajax.reload();
	        		return;
	        	}
				$('#componentItemPageList').dataTable( {
					searching:false,
					"retrieve": true,
					//"pagingType":   "full_numbers",//分页显示方式
					//"scrollX": true,//水平滚动条
					//"paging": false,
				    "ordering": false,
				    //"info": false,
					"ajax": {
						"type": "POST",
						"url": component.appPath+"/item/itemSkuPageList.do",
						"data": function ( d ) {
							return $.extend( {}, d, {
								"pageNo": (d.start/d.length)+1,
	        					"pageSize":d.length,
	        					"itemTitle":d.search["value"],
							} );
						},
						"dataSrc": function ( json ) {
							json.recordsTotal=json.rowCount;
							json.recordsFiltered=json.rowCount;
							json.data=json.data;
							return json.data;
						},
						"error": function (xhr, error, thrown) {  
			            }
					},
					"columns": [
			            { "title":"","data": "itemId","width":"5px"},
			            { "title":"","data": "itemId","visible":false,"width":"5px"},
		            	{ "title":"商品图片","data": "defaultPicUrl" },
		            	//{ "title":"商品编号","data": "sellerItemNo" },
						{ "title":"商品名称","data": "itemTitle" },
						{ "title":"商品品牌","data": "brandName" },
						{ "title":"商品属性组","data": "propGroupName" },						
						{ "title":"市场价","data": "marketPrice" },
						{ "title":"SkuId","data": "sku.skuId","visible":false,"defaultContent":"" },
						{ "title":"SKU名称","data": "sku.skuName","defaultContent":"" },
						{ "title":"SKU价格","data": "sku.price","defaultContent":"" },
						{ "title":"库存","data": "skuStock.stock","defaultContent":"" },
						{ "title":"属性","data": "skuItemPropRel.propValue","defaultContent":"" }
						
					],
				   "rowsGroup": [1,2,3,4,5,6],
		           "dom": "<'row'<'col-xs-12'f>r>" +"t" +"<'row'<'col-xs-6'i><'col-xs-6'p>>",
				   "initComplete": function(){
					   if(config.multiple){
		        		    $(this).find("thead tr th:first-child").prepend('<input type="checkbox">');
							$('#componentItemPageList thead input[type="checkbox"]').on("click",function(e){
		        				if(this.checked){
		        			         $('#componentItemPageList tbody input[name="componentItem"][type="checkbox"]:not(:checked)').prop("checked",true);
		        			    } else {
		        			         $('#componentItemPageList tbody input[name="componentItem"][type="checkbox"]:checked').prop("checked",false);
		        			    }
		        			});						   
					   }
					},
	        		"drawCallback": function( settings ) {
	        			if(config.multiple){
	        				component.operateColumnEvent("componentItemPageList");
	        			}else{
	       					$('#componentItemPageList tbody input[name="componentItem"][type="checkbox"]').on("click",function(e){
			        				if(this.checked){
			        					 $('#componentItemPageList tbody input[name="componentItem"][type="checkbox"]:checked').prop("checked",false);
			        					 $(this).prop("checked",true);
			        				}else{
			        					 $(this).prop("checked",false);
			        				}
	     					});
	        			}
	        			/*var api = this.api();
	                    var rows = api.rows( {page:'current'} ).nodes();
	                    var last=null;
	         
	                    api.column(2, {page:'current'} ).data().each( function ( group, i ) {
	                        if ( last !== group ) {
	                            $(rows).eq( i ).before(
	                                '<tr class="group"><td colspan="5">'+group+'</td></tr>'
	                            );
	         
	                            last = group;
	                        }
	                    } );*/
	        		},
					"columnDefs": [
						{ 	"targets": 0,
						    "orderable":false,
						    "render":function(data, type, row, meta){
						             return '<input type="checkbox" name="componentItem" value="' + $('<div/>').text(data).html() + '">';
						    }
						},{
						    "targets":  1 ,
						    "searchable": false,
						    "render": function(data, type, row, meta){
				                return '<img alt="商品图片" width="40" height="40" src="http://img4.3lian.com/sucai/img6/230/29.jpg">';
				            },
						}/*,{
						    targets:  [6,7,8,9,10] ,
						    searchable: false,
						    "render": function(data, type, row, meta){
				                if()
				            },
						}*/
					]
				});
				
				$("#componentItemModal .submit").on("click",function(){
					var checked=$("#componentItemPageList tbody input[name='componentItem'][type='checkbox']:checked");
					if(!checked.length){
						bootbox.alert("请选择商品！");
						return;
					}	
					var result=[];
					checked.each(function(){
				        var tr=$(this).closest("tr");
				        var row=$("#componentItemPageList").DataTable().row(tr);
				        result.push(row.data());
					});							
 					if(fn){
 						fn(result);
						    $("#componentItemModal").modal("hide");
 					}	
				});
				component.isFirstShow.item=true;
	        	});
								
			},	        
	        operateColumnEvent: function(pageListId){	
				$('#'+pageListId+' tbody input[type="checkbox"]').on("click",function(e){
					component.updateDataTableSelectAll(pageListId);
				});
				component.updateDataTableSelectAll(pageListId);			
	        },
	        updateDataTableSelectAll:function(pageListId){
	        	   var $chkbox_all        = $('#'+pageListId+' tbody input[type="checkbox"]');
	        	   var $chkbox_checked    = $('#'+pageListId+' tbody input[type="checkbox"]:checked');
	        	   var chkbox_select_all  = $('#'+pageListId+' thead input[type="checkbox"]');
	        	   if($chkbox_checked.length === 0){
				         $('#'+pageListId+' thead input[type="checkbox"]:checked').prop("checked",false);     	     
	        	   } else if ($chkbox_checked.length === $chkbox_all.length){
				         $('#'+pageListId+' thead input[type="checkbox"]:not(:checked)').prop("checked",true);   	     
	        	   } else {
				         $('#'+pageListId+' thead input[type="checkbox"]:checked').prop("checked",false);     	     
	        	   }
	        }
	};
	return component;
})
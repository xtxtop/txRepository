<meta charset="utf-8">
<style>
	@media only screen and (max-width:992px) {
		.pull-down-menu input,
		.pull-down-menu select {
			width: 30%;
		}
		. .form-control {
			width: 45% !important;
			margin-right: 20%;
		}
		.pull-down-menu span {
			width: 20%;
		}
	}
	
	@media only screen and (max-width:768px) {
		.row .sorting_disabled {
			font-size: 1.1rem !important;
		}
	}
	
	@media only screen and (min-width:768px) and (max-width:1024px) {
		.row .sorting_disabled {
			font-size: 1.2rem !important;
		}
	}
	
	@media only screen and (min-width:1024px) and (max-width:1366px) {
		.row .sorting_disabled {
			font-size: 1.3rem !important;
		}
	}
	
	.other {
		margin: 0 !important;
		padding: 0 !important;
	}
	
	table {
		border: 1px solid rgba(127, 127, 127, 0.05);
	}
	.table-bettween-far tr td{
	height:72px;
	}
</style>
   <div class="container-fluid">
     <div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="search-input-content">
         <form class="form-horizontal" name="carPreiodSearchForm">
        <div class="seach-input-border-box">
					<!-- /.box-tools -->
					<div class="seach-input-details seach-input-details-change close-content clearfix">
                <div class="seach-input-item seach-input-item-change pull-left">
                    <span>品牌</span>
                    <select class="form-control"  name="carBrandId" id="searchCarBrandId" onchange="selectBrandValue1('searchCarBrandId')">
                    	<option value="">全部</option>
						<#if carBrandList?exists>
                            <#list carBrandList as carBrand>
                                <option value="${carBrand.carBrandId}">${carBrand.carBrandName}</option>
                            </#list>
                        </#if>
                    </select>
                </div>
            	<div class="seach-input-item seach-input-item-change pull-left">
                    <span>车型系列</span>
                    <select class="form-control" name="carSeriesId">
						<option value="">全部</option>
					</select>
                </div>
            	<div class="seach-input-item seach-input-item-change pull-left">
                     <span>车型年代</span>
                     <input type="text" class="form-control"  name="carPeriodName" placeholder="请输入年代名称">
                </div>
         </div><!-- /.box-body -->
         <div class="seacher-button-content">
						<button type="reset" class="btn-new-type"  id="carPreiodSearchafterclose">清空</button>
						<button type="button" class="btn-new-type" id="carPreiodSearchafter">确定</button>
					</div>
         </div>
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     <div class="row show-table-content">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="carPreiodTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="carPreiodList" class="table table-hover table-bettween-far" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
     
     
     

    </div><!-- /.container-fluid -->
    
     
    
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"carPreiod":"res/js/car/car_preiod_list"}});
		require(["carPreiod"], function (carPreiod){
			carPreiod.init();
		});  
	   }); 
    </script>

<meta charset="utf-8">
<style>
@media only screen and (max-width:992px) {
.pull-down-menu input, .pull-down-menu select {
    width: 30%;
}
.seach-input-item pull-left .form-control{
	width:45% !important;
	margin-right:20%;
}
.pull-down-menu span {
    width: 20%;
}
}
@media only screen and (max-width:768px) {
.row .sorting_disabled{
font-size:1.1rem !important;
}
}
@media only screen and (min-width:768px) and (max-width:1024px) {
.row .sorting_disabled{
font-size:1.2rem !important;
}
}
@media only screen and (min-width:1024px) and (max-width:1366px) {
.row .sorting_disabled{
font-size:1.3rem !important;
}
}

.other{
margin:0 !important;
padding:0 !important;
}
table{
border:1px solid rgba(127,127,127,0.05);
}
</style>
   <div class="container-fluid">
     <div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="search-input-content">
         <form class="form-horizontal" name="applyCarModelSearchForm">
        <div class="seach-input-border-box">
					
					<!-- /.box-tools -->
					<div class="seach-input-details close-content clearfix">
	                <div class="seach-input-item pull-left">
	                    <span>品牌</span>
	                    <input class="form-control" name="carBrandName" placeholder="请输入品牌"/>
	                </div>
	                <div class="seach-input-item pull-left">
	                    <span>车系</span>
	                    <input class="form-control" name="carSeriesName" placeholder="请输入车系"/>
	                </div>
	                <div class="seach-input-item pull-left">
	                    <span>车辆年代</span>
	                    <input class="form-control" name="carPeriodName" placeholder="请输入车辆年代"/>
	                </div>
                <div class="seach-input-item pull-left">
                    <span>适用类型</span>
                    <select class="form-control" name="carType">
                        <option value="" selected="selected">全部</option>
                        <option  value="1" >经济型</option>
                        <option  value="2" >商务型</option>
                        <option  value="3" >豪华型</option>
                        <option  value="4" >6至15座商务</option>
                        <option  value="5" >SUV</option>
                     </select>
                </div>
         </div><!-- /.box-body -->
         <div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="applyCarModelSearchafter">确定</button>
					</div>
         </div>
         </form>
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     <div class="row show-table-content">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="applyCarModelTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="applyCarModelList" class="table table-hover table-bettween-far" width="100%" border="1">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
   </div><!-- /.container-fluid -->
   
    
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"applyCarModel":"res/js/dailyrental/car/apply_car_model_list"}});
		require(["applyCarModel"], function (applyCarModel){
			applyCarModel.init();
		});  
	   }); 
    </script>

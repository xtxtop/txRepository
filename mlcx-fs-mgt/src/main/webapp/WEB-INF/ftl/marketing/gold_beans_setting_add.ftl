<meta charset="utf-8">
<style>
    .btn-new {
	    display: inline-block;
	    padding: 6px 12px;
	    margin-bottom: 0;
	    font-size: 14px;
	    font-weight: normal;
	    line-height: 1.42857143;
	    text-align: center;
	    white-space: nowrap;
	    vertical-align: middle;
	    -ms-touch-action: manipulation;
	    touch-action: manipulation;
	    cursor: pointer;
	    -webkit-user-select: none;
	    -moz-user-select: none;
	    -ms-user-select: none;
	    user-select: none;
	    background-image: none;
	    border: 1px solid transparent;
	    border-radius: 4px;
	}
    
    .btn-sm-new {
	    height: 30px;
		padding: 5px 10px;
	    font-size: 12px;
	    line-height: 1.5;
	    border-radius: 3px;
	}
	
	.btn-default-new {
	    color: #333;
	    background-color: #fff;
	    border-color: #ccc;
	}
	
</style>
<div class="container-fluid">
                <div class="row">
					<div class="col-md-12">
						<div class="form-group compiletitle">
							<label class="col-sm-2 control-label"><h4>金豆设置添加</h4></label>
						</div>
					</div>
				</div>	       
				<div class="row hzlist">
					<div class="col-md-12 form-horizontal">
					<form  class="form-horizontal" name="goldBeansAddFrom">
					<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;获得金豆个数：</label>
							<div class="col-sm-5">
							 <input class="form-control val" name="goldBeansNum" />
							</div>
							<div class="col-sm-5"><span name="godlBeansNumAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;获得金豆所需天数：</label>
							<div class="col-sm-5">
							 <input class="form-control val" name="days" />
							</div>
							<div class="col-sm-5"><span name="daysAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;金豆和人民币的比率值：(1金豆=xx元)</label>
							<div class="col-sm-5">
							<input class="form-control val" name="beansMoneyRatio"/>
							</div>
							<div class="col-sm-5"><span name="beansMoneyRatioAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;金豆抵扣订单金额比率值：</label>
            				<div class="col-sm-5">
            					<input class="form-control val" name="orderBeansRatio"/>
            				</div>
            				<div class="col-sm-5"><span name="orderBeansRatioAdd"></span></div>
						</div>
						<div class="form-group col-md-6">
							<label class="col-sm-6 control-label key"><span>*</span>&nbsp;&nbsp;抵扣金额封顶值：</label>
							<div class="col-sm-5">
    						<input class="form-control val" name="dedutionMaxAmount"/>
							</div>
							<div style="margin-top:7px;"><span name="dedutionMaxAmountAdd"></span></div>
						</div>
					   </form>	

					</div>	
        		</div><!-- /.row -->
                <div class="form-group">
                    <div class="col-sm-6" style="margin-bottom:20px;">
                    <!--
                    <button style="width: 95px; height: 32px; line-height: 25px; background:#fa6e30; margin-right: 50px !important" type="button" id="closeAddGoldBeans" class="btn btn-default pull-right sure btncolorb" >
                            关闭
                    </button>
                    -->
                    <button style="width: 95px; height: 32px; line-height: 25px; background:#2b94fd;" type="button" id="addGoldBeans" class="btn btn-default pull-right sure btncolora" >
                            保存
                    </button>
                    </div>
                </div>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"goldBeansAdd":"res/js/marketing/gold_beans_add"}});
		require(["goldBeansAdd"], function (goldBeansAdd){
			goldBeansAdd.init();
		});  
    });
</script>

<meta charset="utf-8">
<div class="container-fluid">
	<div class="row">
		<div class="col-md-12">
			<div class="form-group compiletitle">
				<label class="col-sm-12 control-label"><h4>芝麻分减免押金详情</h4></label>
			</div>
		</div>
	</div>	       
	<div class="row hzlist">
		<div class="col-md-10 form-horizontal">
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;芝麻分：</label>
					<div class="col-sm-7">
						<label class="control-label val">${depositZhimaReduction.zhimaScore}</lable>
				</div>
			</div>
			
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;减免金额：</label>
				<div class="col-sm-7">
				 <label class="control-label val">${depositZhimaReduction.reductionAmount}元</label>
				</div>
			</div>
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;启用状态：</label>
				<div class="col-sm-7">
					<label class="control-label val">
					<#if depositZhimaReduction.isAvailable==0>
						不可用
					<#else>
						可用
					</#if>
					</label>
				</div>
			</div>	
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;创建时间：</label>
				<div class="col-sm-7">
				<label class="control-label val">
					 ${depositZhimaReduction.createTime?string('yyyy-MM-dd HH:mm:ss')}
				</label>
				</div>
			</div>	
			<div class="form-group col-md-6">
				<label class="col-sm-4 control-label key"><span></span>&nbsp;&nbsp;更新时间：</label>
				<div class="col-sm-7">
					<label class="control-label val">
					 	${depositZhimaReduction.updateTime?string('yyyy-MM-dd HH:mm:ss')}
					</label>
				</div>
			</div>	
		</div>	
	</div><!-- /.row -->
	<div class="form-group">
		<div class="col-sm-6" style="margin-bottom:20px;">
			<button type="button" id="closeDepositZhimaReductionView" class="btn btn-default pull-right sure btncolora" style="width: 95px; height: 32px; line-height: 25px; background: #2b94fd">
	                  关闭
			</button>
		</div>	
	</div>
</div>

<script type="text/javascript">
	$(function(){
		require.config({paths: {"depositZhimaReduction":"res/js/marketing/depositZhimaReduction_list"}});
		require(["depositZhimaReduction"], function (depositZhimaReduction){
			depositZhimaReduction.init();
		});
		$(".datepicker").datepicker({
	      language: "zh-CN",
	      autoclose: true,//选中之后自动隐藏日期选择框
	      clearBtn: true,//清除按钮
	      todayBtn: 'linked',//今日按钮
	      format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
	  	});
	}); 
</script>

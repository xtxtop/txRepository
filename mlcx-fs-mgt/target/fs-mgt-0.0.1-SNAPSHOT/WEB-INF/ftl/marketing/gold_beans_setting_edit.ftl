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
<div class="container-fluid backgroundColor">
	<form name="goldBeansEditFrom">
		<input type="hidden" name="goldBeansId" value="${goldBeansSetting.goldBeansId}"/>
		<div class="row hzlist">
			<table class="tab-table table table-border table-responsive">
				<thead class="tab-thead">
					<tr>
						<th colspan="4">金豆设置编辑</th>
					</tr>
				</thead>
				<tbody class="tab-tbody">
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>获得金豆个数：</label>
						</td>
						<td>
							<input class="form-control val" name="goldBeansNum" value="${goldBeansSetting.goldBeansNum}"/>
							<span name="goldBeansNumEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>获得金豆所需天数：</label>
						</td>
						<td>
							<input class="form-control val" name="days" value="${goldBeansSetting.days}"/>
							<span name="daysEdit"></span>
						</td>
					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>金豆和人民币的比率值：(1金豆=xx元)</label>
						</td>
						<td>
							 <input class="form-control val" name="beansMoneyRatio" value="${goldBeansSetting.beansMoneyRatio}"/>
							 <span name="beansMoneyRatioEdit"></span>
						</td>
						<td>
							<label class=" control-label key"><span>*</span>金豆抵扣订单金额比率值：</label>
						</td>
						<td>
							<input class="form-control val" name="orderBeansRatio" value="${goldBeansSetting.orderBeansRatio}"/>
							<span name="orderBeansRatioEdit"></span>
						</td>

					</tr>
					<tr>
						<td>
							<label class=" control-label key"><span>*</span>抵扣金额封顶值：</label>
						<td>
							<input class="form-control val" name="dedutionMaxAmount" value="${goldBeansSetting.dedutionMaxAmount}"/>
							<span name="dedutionMaxAmountEdit"></span>
						</td>
						<td colspan="2"></td>
					</tr>
				</tbody>
				<tfoot class="tab-tfoot">
					<tr>
						<td colspan="2"><button type="button" id="editGoldBeans" class="btn-new-type-edit pull-right" style="margin-right: 10px !important">
                保存
            </button></td>
						<td colspan="2"><button type="button" id="returnGoldBeansView" class="btn-new-type-edit pull-left" style="margin-left: 10px !important">
                返回
            </button></td>
					</tr>
				</tfoot>
			</table>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function(){
	  require.config({paths: {"goldBeansEdit":"res/js/marketing/gold_beans_edit"}});
		require(["goldBeansEdit"], function (goldBeansEdit){
			goldBeansEdit.init();
		});  
    });
</script>
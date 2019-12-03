<meta charset="utf-8">
<style>
	.seach-input-item pull-left .form-control {
		width: 50% !important;
	}
	
	@media only screen and (max-width:992px) {
		.pull-down-menu input,
		.pull-down-menu select {
			width: 30%;
		}
		.seach-input-item pull-left .form-control {
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
	
	table {
		border: 1px solid rgba(127, 127, 127, 0.05);
	}
</style>
<div class="container-fluid">
	<div class="search-input-wrapper">
		<div class="clearfix search-input-header">
			<img class="pull-left" src="res/img/search.png">
		</div>
		<div class="pull-right moreButtonNew" id="">
		<!-- <div class="up-box">
            <span>更多</span>
            <i class="fa fa-angle-down click-name"></i>
        </div> -->
        </div>
		<div class="search-input-content">
			<form class="form-horizontal" name="lockOrderSearchForm">
				<div class="seach-input-border-box">
					<div class="seach-input-details close-content clearfix">
						<div class="seach-input-item pull-left">
							<span>订单编号</span>
							<input type="text" class="form-control" name="orderNo" placeholder="请输入订单编号">
						</div>
						<div class="seach-input-item pull-left">
							<span>会员名称</span>
							<input type="text" class="form-control" name="memberName" placeholder="请输入会员名称">
						</div>
						<div class="seach-input-item pull-left">
                            <span>充电站</span> 
                            <select class="form-control" name="stationNo">
                                    <option value="">--全部--</option>
                                <#list csList as cs>
                                <#if cs.isAvailable==1>
                                    <option value="${cs.stationNo}">${cs.stationName}</option>
                                </#if>
                                </#list>
                            </select>
                        </div>
						<div class="seach-input-item pull-left">
							<span>订单状态</span>
							<select class="form-control" name="orderStatus">
								<option value="">--全部--</option>
								<option value="0">进行中</option>
                                <option value="1">待支付</option>
                                <option value="2">待评价</option>
                                <option value="3">已完成</option>
							</select>
						</div>
						<div class="seach-input-item pull-left">
                            <span>支付状态</span>
                            <select class="form-control" name="payStatus">
                                <option value="">--全部--</option>
                                <option value="0">未支付</option>
                                <option value="1">已支付</option>
                            </select>
                         </div>
                         <div class="seach-input-item pull-left">
                            <span>支付方式</span>
                            <select class="form-control" name="payType">
                                <option value="">--全部--</option>
                                <option value="0">支付宝</option>
                                <option value="1">微信</option>
                            </select>
                         </div>
					</div>
					<div class="seacher-button-content">
						<button type="reset" class="btn-new-type">清空</button>
						<button type="button" class="btn-new-type" id="lockOrderSearch">确定</button>
					</div>
				</div>
			</form>
		</div>
		<!-- /.box-footer -->
	</div>
	<!-- /.box -->
	<div class="row show-table-content">
		<div class="col-xs-12">
			<!--定义操作列按钮模板-->
			<script id="lockOrderTpl" type="text/x-handlebars-template">
				{{#each func}}
				<button type="button" class="btn-new-type btn-{{this.type}}" onclick="{{this.fn}}">{{this.name}}</button> {{/each}}
			</script>
			<div class="box">
				<div class="box-body">
					<table id="lockOrderList" class="table table-hover table-bettween-far" width="100%" border="1">
					</table>
				</div>
				<!-- /.box-body -->
			</div>
			<!-- /.box -->
		</div>
	</div>
</div>
<!-- /.container-fluid -->
<div class="modal fade" id="lockOrderModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">结束地锁计费</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" name="lockOrderForm">
                <input type="hidden" name="orderNo">
                <input type="hidden" name="orderStartTime">
                <input type="hidden" name="lockBillingSchemeNo">
                <label for="inputEmail3 wen" class=" control-label" id="offlockOrderMemo"></label>
                    <div>
                        <label for="inputEmail3" class=" control-label reason"><span style="color:red;">*</span>结束原因:</label>
                    </div>
                    <div class="form-group">

                        <div class="col-sm-8">
                            <textarea class="form-control textareas" name="finishReason"></textarea>
                        </div>
                    </div>

                </form>
                <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure" id="lockOrderBtn" value="确定">
                    <button type="button" class="btn btn-default pull-right cancels" id="lockOrderCancel">取消</button>
                </div>
            </div>

        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"lockOrder": "res/js/mlorder/lockOrder_list"
			}
		});
		require(["lockOrder"], function(lockOrder) {
			lockOrder.init();
		});
		$(".datepicker").datepicker({
			language: "zh-CN",
			autoclose: true, //选中之后自动隐藏日期选择框
			clearBtn: true, //清除按钮
			todayBtn: "linked", //今日按钮
			format: "yyyy-mm-dd" //日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
		});
	});
</script>
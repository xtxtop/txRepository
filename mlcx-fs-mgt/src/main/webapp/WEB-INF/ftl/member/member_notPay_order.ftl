<meta charset="utf-8">
   <div class="container-fluid">
     <div class="row">
      <div class="col-md-12 pd10">
       <div class="box box-default">
        <div class="box-header with-border">
         <h3 class="box-title">查询</h3>
         <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
          </button>
         </div>
         <!-- /.box-tools -->
        </div>
        <!-- /.box-header -->
  
         <form class="form-horizontal" name="orderSearchForm">
        <div class="box-body">
         <div class="row pull-down-menu">
          <div class="col-md-2">
                <div class="frombg">
                    <span>订单编号</span><input type="text" class="form-control" id="orderNo" name="orderNo" placeholder="">
                </div>
          </div>
          <div class="col-md-2" style="display:none;">
            <div class="frombg">
                <span>订单状态</span><select class="form-control" name="orderStatus">
                       <option value="">全部</option>
                       <!--<option value="0">已完成</option>-->
                       <option value="1">已预约</option>
                       <option value="2">已计费</option>
                       <option value="3"  selected = "selected">已结束</option>
                       <option value="4">已取消</option>
                      </select>
            </div>
          </div>
           <div class="col-md-2" style="display:none;">
                    <div class="frombg">
                         <span>支付状态</span><select class="form-control" name="payStatus">
                           <option value="">全部</option>
                           <option value="0" selected = "selected">未支付</option>
                           <option value="1">已支付</option>
                           <!--<option value="2">未结算</option>-->
                          </select>
                    </div>
          </div>
          <div class="col-md-2" style="display:none;">
                 <div class="frombg">
                       <span>客户姓名</span><input type="text" class="form-control" id="memberName" name="memberName" placeholder="">
                  </div>
         </div>
          <div class="col-md-2" style="display:none;">
                 <div class="frombg">
                    <span>手机号码</span><input type="text" class="form-control" id="mobilePhone" name="mobilePhone" placeholder="" value="${member.mobilePhone}">
                </div>


          </div>
          <div class="col-md-2">
                <div class="frombg">
                    <span>车 牌 号</span><input type="text" class="form-control" id="carPlateNo" name="carPlateNo" placeholder="">
                </div>
          </div>
          </div><!-- /.row -->
           <div class="row pull-down-menu">
          <div class="col-md-2">
                <div class="frombg">
                   <span>时间范围</span><input class="datepicker form-control" name="startBillingTimeStart" placeholder=""/>
              </div>
         </div>
          <div class="col-md-2">
                 <div class="frombg">
                        <span>至</span><input class="datepicker form-control" name="finishTimeEnd" placeholder=""/>
                  </div>
         </div>
		 <div class="col-md-2">
		         <div class="frombg">
                        <span>集团名称</span><input type="text" class="form-control"  name="companyName" placeholder="">
                  </div>

		 </div>
		   <div class="col-md-2">
		        <div class="frombg">
                    <span>取车场站</span><input type="text" class="form-control" name="actualTakeLoacton" placeholder="">
              </div>
		  </div>
          <div class="col-md-2">
                    <div class="frombg">
                       <span>还车场站</span><input type="text" class="form-control"  name="actualTerminalParkName" placeholder="">
                    </div>
          </div>

		  </div><!-- /.row -->
         </div><!-- /.box-body -->
          <!--修改-->
            <div class="box-footer col-md-2" style='float:right'>
                <!-- <button type="submit" class="btn btn-default pull-right sure">Cancel</button> -->


                <button type="reset" class="btn btn-default pull-right btn-flat btncolorb"  style="background:#fa6e30;float:right;margin-right:20px !important">清空</button>
                <button type="button" class="btn btn-default pull-right btn-flat btncolora" id="orderSearchafter" style="background:#2b94fd;margin-right:-2px !important">确定</button>
              </div>
         </form>
         </div>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     <div class="row">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="orderTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="orderList" class="table table-bordered table-striped table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
    </div><!-- /.container-fluid -->
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"order":"res/js/member/member_order"}});
		require(["order"], function (order){
			order.init();
		});
		  $(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: "linked",//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });  
	   }); 
    </script>

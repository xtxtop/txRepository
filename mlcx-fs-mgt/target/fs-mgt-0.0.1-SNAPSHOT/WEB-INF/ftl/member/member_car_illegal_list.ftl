<meta charset="utf-8">

  <div class="container-fluid">
   <div class="row">
    <div class="col-md-12 pd10">
        <h4 class="box-title">查询</h4>
     <div class="box box-default">
         <form name="carIllegalSearchForm">
      <div class="with-border">
       <div class="box-tools pull-right">
          <button type="button" class="btn btn-box-tool more">
              更多<img src="res/img/arrow-down.png" width="15" style="margin-left: 2px;"/>
          </button>
           <div class="row pull-down-menu col-md-11" style="margin-left: 1px; background: #f1f5f8;">
           <div class="col-md-3">
                   <div class="frombg">
                       <span>车牌号</span><input type="text" class="form-control" id="carPlateNo" name="carPlateNo" placeholder="">
                   </div>
               </div>
               <div class="col-md-3">
                   <div class="frombg">
                       <span>单据号</span><input class="form-control" name="documentNo" placeholder="">
                   </div>
               </div>
               <div class="col-md-3">
                   <div class="frombg">
                       <span>用车类型</span><select class="form-control" name="useCarType">
                       <option value="">全部</option>
                       <option value="1">订单用车</option>
                       <option value="2">调度用车</option>
                   </select>
                   </div>
               </div>
               <div class="col-md-3">
                   <div class="frombg">
                       <span>处理状态</span><select class="form-control" name="processingStatus">
                       <option value="">全部</option>
                       <option value="0">未处理</option>
                       <option value="1">处理中</option>
                       <option value="2">已处理</option>
                   </select>
                   </div>
               </div>
           </div>
       </div><!-- /.box-tools -->
       </div><!-- /.box-header -->
       <div class="box-body">
           <div class="row pull-down-menu col-md-11 other searching">
         
			<div class="col-md-3" style="display:none">
                    <div class="frombg">
                        <span>用车人</span><input class="form-control" name="driverName" placeholder=""value="${member.memberName}">
                    </div>
            </div>
            <div class="col-md-3">
                <div class="frombg">
                    <span>缴费状态</span><select class="form-control" name="paymentStatus">
                                              <option value="">全部</option>
                                              <option value="0">未缴费</option>
                                              <option value="1">已缴费</option>
                                            </select>
                </div>
            </div>
			</div>
			<br/>
		<div class="row pull-down-menu">
		<div class="col-md-3">
               <div class="frombg">
                   <span>违章开始时间</span><input class="datetimepicker form-control" name="illegalTimeStart" style="background:#FFFFFF" readonly/>
               </div>
        </div>
        <div class="col-md-3">
             <div class="frombg">
                <span>违章结束时间</span><input class="datetimepicker form-control" name="illegalTimeEnd" style="background:#FFFFFF" readonly/>
            </div>
        </div>

            <script>
                $(".other").hide();
                $(document).ready(function(){
                    var state = 0; //hide
                    $(".more").click(function(){
                        if (state == 0){
                            $(".other").show();

                            $(this).html('收起<img src="res/img/arrow-up.png" width="15" style="margin-left: 2px;"/>');
                            state = 1;
                        } else {
                            $(".other").hide();
                            $(this).html('更多<img src="res/img/arrow-down.png" width="15" style="margin-left: 2px;"/>');
                            state = 0;
                        }
                    })
                })
            </script>

        <!--修改-->
        <div class="col-md-3">
            <div class="box-footer">
                 <!--<button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb"><i class="hziconfont icons-qingchu iconbtn"></i>清空</button>-->

                 <!--<button type="button" class="btn btn-default pull-right btn-flat btncolora" id="carIllegalSearch"><i class="hziconfont icons-yuanxingxuanzhong iconbtn"></i>确认</button>-->

                 <button type="reset" class="btn btn-default pull-right btn-flat flatten btncolorb"  style="background:#fa6e30">清空</button>
                      <button type="button" class="btn btn-default pull-right btn-flat flatten btncolora" id="carIllegalSearch" style="background:#2b94fd">确定</button>
            </div><!-- /.box-footer -->
        </div>
         </div><!-- /.row -->
        </div><!-- /.box-body -->
         </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
     </div><!-- /.row -->
      <div class="row">
       <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="carIllegalBtnTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
  		{{/each}}
       </script>
           <div class="row" style="width: 100%; height: 2px; background: #f1f5f8"></div>
       <div class="box">
        <div class="box-body">
         <table id="carIllegalList" class="table table-bordered table-striped table-hover" width="100%">
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->   
      </div><!-- /.col -->
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
    
    <script type="text/javascript">
    $(function(){
	  require.config({paths: {"carIllegal":"res/js/car/car_illegal_list"}});
		require(["carIllegal"], function (carIllegal){
			carIllegal.init();
		});  
	});
    $(function () {
        $(".datetimepicker").datetimepicker({
            language: "zh-CN",
             minView: "month",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
    });
</script>

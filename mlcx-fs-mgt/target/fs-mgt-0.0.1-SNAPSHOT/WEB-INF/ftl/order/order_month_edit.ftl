<meta charset="utf-8">
<div class="container-fluid" id="scollOo">
   <div class="row">
		<div class="col-md-12">
			<div class="form-group compiletitle"> <label class="col-sm-3 col-xs-4 control-label"><h4>长租订单新增</h4></label>
			</div>
		</div>
	</div>
     <form name="orderMonthEditForm" class="col-md-12">
     <input class="form-control" type="hidden" value="${orderMonth.orderNo}" name="orderNo" />
     <input type="hidden" value="" name="cars" id="carsNs">
         <div class="row hzlist">
             <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span >*</span>&nbsp;&nbsp;客户名称：</label>
                 <div class="col-sm-7">
                     <input class="form-control" value="${orderMonth.memberName}" name="memberName" />
                 </div>
                   <div style="margin-top:7px;"><span name="memberNameed"></span></div>
             </div>
             <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span >*</span>&nbsp;&nbsp;手机号码：</label>
                 <div class="col-sm-7">
                      <input class="form-control" name="mobilePhone" value="${orderMonth.mobilePhone}" />
                </div>
                 <div style="margin-top:7px;"><span name="mobilePhoneed"></span></div>
            </div>
        </div>
        <div class="row hzlist">
            <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;订单状态：</label>
                <div class="col-sm-7">
                    <select name="orderStatus" class="form-control">
                            <option value="1"  <#if orderMonth.orderStatus==1>selected</#if>  >已提车</option>
                            <option value="2" <#if orderMonth.orderStatus==2>selected</#if>  >已还车</option>
                            <option value="3"  <#if orderMonth.orderStatus==3>selected</#if> >已结束</option>
                            <option value="4"  <#if orderMonth.orderStatus==4>selected</#if> >已取消</option>
                    </select>
                </div>
            </div>
            <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;订单金额：</label>
                <div class="col-sm-7">
                     <input class="form-control" name="orderAmount" value="${orderMonth.orderAmount}"/>
                </div>
                  <div style="margin-top:7px;"><span name="orderAmounted"></span></div>
            </div>


        </div>
        <div class="row hzlist">
            <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;折扣金额：</label>
                <div class="col-sm-7">
                    <input class="form-control" name="discountAmount" value="${orderMonth.discountAmount}"/>
                </div>
                 <div style="margin-top:7px;"><span name="discountAmounted"></span></div>
            </div>
            <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;押金金额：</label>
                <div class="col-sm-7">
                    <input class="form-control" name="deposit" value="${orderMonth.deposit}"/>
                </div>
                 <div style="margin-top:7px;"><span name="deposited"></span></div>
            </div>
        </div>
        <div class="row hzlist">
            <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;保险金额：</label>
                <div class="col-sm-7">
                    <input class="form-control" name="insurance" value="${orderMonth.insurance}"/>
                </div>
                 <div style="margin-top:7px;"><span name="insuranceed"></span></div>
            </div>
            <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;应付金额：</label>
                <div class="col-sm-7">
                    <input class="form-control" name="payableAmount" value="${orderMonth.payableAmount}"/>
                </div>
                  <div style="margin-top:7px;"><span name="payableAmounted"></span></div>
            </div>

        </div>
        <div class="row hzlist">
           <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;城市：</label>
               <div class="col-sm-7">
                   <select name="cityId" class="form-control">
                       <#list cities as citie>
                           <option  value="${citie.dataDictItemId}"  <#if citie.dataDictItemId == orderMonth.cityId >selected</#if> >
                               ${citie.itemValue}
                           </option>
                       </#list>
                   </select>
               </div>
           </div>
           <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;支付方式：</label>
               <div class="col-sm-7">
                   <select name="paymentMethod" class="form-control">
                           <option  value="1"  <#if orderMonth.paymentMethod==1>selected</#if> >支付宝</option>
                           <option  value="2" <#if orderMonth.paymentMethod==2>selected</#if> >微信</option>
                           <option  value="3" <#if orderMonth.paymentMethod==3>selected</#if>>线下</option>
                           <option  value="4" <#if orderMonth.paymentMethod==4>selected</#if>>其他</option>
                   </select>
               </div>
           </div>

        </div>
        <div class="row hzlist">
            <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;支付时间：</label>
               <div class="col-sm-7">
                   <input class="datepicker form-control" name="paymentTime" value="${orderMonth.paymentTime?string('yyyy-MM-dd')}"/>
               </div>
              <br/>  <div style="margin-top:7px;"><span name="paymentTimeed"></span></div>
           </div>
           <div class="form-group col-md-6">
               <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;租车数量：</label>
               <div class="col-sm-7">
                   <input class="form-control" name="actualNumber"  value="${orderMonth.actualNumber}"/>
               </div>
                 <div style="margin-top:7px;"><span name="actualNumbered"></span></div>
           </div>
        </div>
       
        <div class="row hzlist">
                 <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;租车开始时间：</label>
                    <div class="col-sm-7">
                        <input class="datepicker form-control" name="actualStartTime" value="${orderMonth.actualStartTime?string('yyyy-MM-dd')}"/>
                    </div>
                      <div style="margin-top:7px;"><span name="actualStartTimeed"></span></div>
                </div>
                <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;租车结束时间：</label>
                    <div class="col-sm-7">
                        <input class="datepicker form-control" name="actualEndTime" value="${orderMonth.actualEndTime?string('yyyy-MM-dd')}"/>
                    </div>
                     <div style="margin-top:7px;"><span name="actualEndTimeed"></span></div>
                </div>
               


        </div><!-- /.row -->
        
        <div class="row hzlist">
            <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;取车地点：</label>
               <div class="col-sm-7">
                   <input class="form-control" name="actualTakeLoacton" value="${orderMonth.actualTakeLoacton}"/>
               </div>
              <br/>  <div style="margin-top:7px;"><span name="actualTakeLoactoned"></span></div>
           </div>
           <div class="form-group col-md-6">
               <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;还车地点：</label>
               <div class="col-sm-7">
                   <input class="form-control" name="actualTerminalParkName"  value="${orderMonth.actualTerminalParkName}"/>
               </div>
                 <div style="margin-top:7px;"><span name="actualTerminalParkNameed"></span></div>
           </div>
        </div>
        
         <div class="row hzlist">
            <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;选择车辆：</label>
                <div class="col-sm-7">
                    <input type="button"  class="btn btn-info EditorderMonthCar" value="选择车辆" >
                </div>
            </div>
            <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;支付状态：</label>
            <div class="col-sm-7"> 
                   <select name="payStatus" class="form-control">
                           <option  value="0" <#if orderMonth.payStatus==0>selected</#if>>未支付</option>
                           <option  value="1" <#if orderMonth.payStatus==1>selected</#if>>已支付</option>
                   </select>
               </div>
            </div>   
        </div>
        <div class="row hzlist">
            
           
           
           <div class="form-group col-md-6"> <label class="col-sm-3 col-xs-4 control-label"><span>*</span>&nbsp;&nbsp;已选择车辆：</label>
                <div class="col-sm-7">
                    <span id="carNmEd">
                    <#list cl as car>
                    ${car_index+1}、 ${car.carBrandName},${car.carModelName}<br/>
                    </#list>
                    
                    </span>
                </div>
                <!-- <div style="margin-top:7px;"><span name="carNmadd"></span></div> -->
            </div>
           
           
           
           

	   		

        </div>
        
    </form>
    <div class="form-group">
        <div class="col-sm-7" style="margin-bottom:20px;">
            <button type="button" id="closeOrderMonthEdit" class="btn btn-default pull-right btncolorb" >
                <i class="glyphicon glyphicon-remove"></i>关闭
            </button>
            <button type="button" id="editOrderMonth" class="btn btn-default pull-right btncolora" >
                <i class="glyphicon glyphicon-check"></i>保存
            </button>
        </div>
    </div>
</div>
<div class="modal" id="orderMonthCarEdit">
	<div class="modal-dialog" style="width:750px;">
	   	<div class="modal-content">
	       <div class="col-xs-14">
		       <!--定义操作列按钮模板-->
		       <script id="orderMonthBtnTplEdit" type="text/x-handlebars-template">
		        {{#each func}}
		        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
		        {{/each}}
		       </script>
		       <div class="box">
		       
			        <div class="box-header">
			         <!-- <h3 class="box-title">数据列</h3> -->
			        </div><!-- /.box-header -->
			        <div class="box-body">
			         <table id="orderMonthListEdit" class="table table-bordered table-striped table-hover" width="100%">
			         </table>
			        </div><!-- /.box-body -->
		   </div><!-- /.box -->
		</div><!-- /.modal-content -->
	</div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>
 
   

<script type="text/javascript">
$(function(){
	  require.config({paths: {"orderMonthEdit":"res/js/order/orderMonth_edit"}});
		require(["orderMonthEdit"], function (orderMonthEdit){
			orderMonthEdit.init();
		});  
		
		
        $(".datepicker").datetimepicker({
            language: "zh-CN",
            minView: "month",
            autoclose: true,
            clearBtn: true,//清除按钮
            todayBtn: true,
            minuteStep: 5,
            startDate: moment(new Date()).format("YYYY-MM"),
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });
});
</script>
<!--设置滚动条滚动时，时间框隐藏-->
<script type="text/javascript" src="res/js/common/dateScroll.js"></script> 

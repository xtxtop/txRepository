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
         <form class="form-horizontal" name="pricingRuleDaySearchForm">
        <div class="box-body">
         <div class="row pull-down-menu">
          <div class="col-md-3">
                <div class="frombg">
                    <span>名称</span><input type="text" class="form-control" id="ruleName" name="ruleName" placeholder="">
                </div>
          </div>
          <div class="col-md-3">
                <div class="frombg">
                    <span>标准规则</span><select class="form-control" name="isStandardBilling">
                                            <option value="">全部</option>
                                            <option value="0">否</option>
                                            <option value="1">是</option>
                                           </select>
                </div>
          </div>
          <div class="col-md-3">
                <div class="frombg">
                    <span>城市</span><select class="form-control" name="cityId">
                                      <option value="">全部</option>
                                     <#list cities as citie>
                                             <option  value="${citie.dataDictItemId}" >
                                                    ${citie.itemValue}
                                             </option>
                                     </#list>
                                     </select>
                </div>
          </div>
          <div class="col-md-3">
                 <div class="frombg">
                    <span>审核状态</span><select class="form-control" name="cencorStatus">
                                            <option value="">全部</option>
                                            <option value="0">未审核</option>
                                            <option value="1">已审核</option>
                                            <option value="2">待审核</option>
                                            <option value="3">未通过</option>
                                           </select>
                </div>
          </div>
          <div class="col-md-3">
                <div class="frombg">
                    <span>状态</span> <select class="form-control" name="isAvailable">
                                          <option value="">全部</option>
                                          <option value="1">启用</option>
                                          <option value="0">停用</option>
                                         </select>
                </div>
         </div>
          <div class="col-md-3">
                 <div class="frombg">
                     <span>有效时间</span><input class="datepicker form-control" name="availableTime1Start" placeholder=""/>
                 </div>
           </div>
           <div class="col-md-3">
                 <div class="frombg">
                     <span>至</span><input class="datepicker form-control" name="availableTime2Start" placeholder=""/>
                 </div>
          </div>
          <!--修改-->
          <div class="col-md-3">
                   <div class="box-footer">


                        <button type="reset" class="btn btn-default pull-right btn-flat btncolorb" style="background:#fa6e30">
                                                   清空
                        </button>
                         <button type="button" class="btn btn-default pull-right btn-flat btncolora" id="pricingRuleDaySearchafter" style="background:#2b94fd">
                                                     确定
                        </button>
                   </div>
           </div>
         </div>

         </div><!-- /.box-body -->
         </form>
         <!-- /.box-footer -->
       </div><!-- /.box -->
	  </div><!-- /.col -->	
     </div><!-- /.row -->
     <div class="row">
      <div class="col-xs-12">
       <!--定义操作列按钮模板-->
       <script id="pricingRuleDayTpl" type="text/x-handlebars-template">
        {{#each func}}
        <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
        {{/each}}
       </script>
       <div class="box">
        <div class="box-body">
         <table id="pricingRuleDayList" class="table table-hover table-bettween-far" width="100%" border='1'>
         </table>
        </div><!-- /.box-body -->
       </div><!-- /.box -->
      </div>
     </div>
     
     
     

    </div><!-- /.container-fluid -->
    
    
          <div class="modal fade"  id="onpricingRuleDayModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">确定启用</h4>
                </div>
                <div class="modal-body">
                  <form class="form-horizontal" name="onForm"> 
			      <input type="hidden"  name="ruleNo" id="ruleNo1Day">
			      <input type="hidden"  name="isAvailable" value="1">
			       <label for="inputEmail3" class=" control-label wen" id="onMemoPrDay"></label>
			         <div>
			            <label for="inputEmail3" class=" control-label reason">*原因:</label>
			         </div> 
			          <div class="form-group">
                            
                            <div class="col-sm-8">
                                 <textarea class="form-control"   name="availableMemo"></textarea>
                            </div>
                      </div>
			     </div>
			     
			     </form> 
			      <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure" id="pricingRuleDayOnFormBtn" value="确定" >
                    <button type="button" class="btn btn-default pull-right cancels"  id="pricingRuleDayOnCancelBtn">取消</button>
                </div>
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
     
    <div class="modal fade" id="OffpricingRuleDayModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">确定停用</h4>
                </div>
                <div class="modal-body">
                   <form class="form-horizontal" name="offFormDay"> 
				      <input type="hidden"  name="ruleNo" id="ruleNoDay2">
				      <input type="hidden"  name="isAvailable" value="0">
				      <input type="hidden"  name="carModelName" id="stoppricingRuleDayCarModelName">
				        <label for="inputEmail3 wen" class=" control-label" id="offMemoDay"></label>
				         <div>
				            <label for="inputEmail3" class=" control-label reason">*原因:</label>
				         </div> 
				          <div class="form-group">
	                            
	                            <div class="col-sm-8">
	                                 <textarea class="form-control textareas"   name="availableMemo"></textarea>
	                            </div>
	                      </div>
	                             
				   </form> 
				      <div class="modal-footer">
                    <input type="button" class="btn btn-default pull-right sure" id="pricingRuleDayOffBtn" value="确定" >
                    <button type="button" class="btn btn-default pull-right cancels"  id="pricingRuleDayOffCancel">取消</button>
                </div>    
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
    <div class="modal fade" id="customizedDateDayModal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">自定义日期计费</h4>
                </div>
                <div class="modal-body hzlist">
                   <form class="form-horizontal" name="customizedDateDayForm"> 
                   		 <input type="hidden"  name="customizedId">
				      	 <input type="hidden"  name="ruleNo">
				      	 <input type="hidden"  name="cityId">
				      	 <input type="hidden"  name="cityName">
				      	 <input type="hidden"  name="carModelName">
				      	 <input type="hidden"  name="companyId">
				         <div class="form-group col-md-12">
				         	 <label class="col-sm-4 control-label"><span>*</span>自定义日期按天计费：</label>
                             <div class="col-sm-4">
                                 <input class="form-control" name="priceOfDayCustomized">
                             </div>
                             <div>元/分钟</div>
                             <div><span name="priceOfDayCustomizedError"></span></div>
	                      </div>
	                      
	                    
	                      <div class="form-group col-md-12">
				         	 <label class="col-sm-4 control-label"><span>*</span>自定义日期：</label>
                             <div class="col-sm-4">
                                 <input class="form-control datepicker" name="customizedDateStr" readOnly="readOnly">
                             </div>
                             <div><span name="customizedDateError"></span></div>
	                      </div>
				   </form> 
				   <div class="modal-footer">
	                   <input type="button" class="btn btn-default pull-right sure" id="customizedDateDayBtn" value="确定" >
	                   <button type="button" class="btn btn-default pull-right cancels"  id="customizedDateDayCancel">取消</button>
               	   </div>    
                </div>
               
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 
    <script type="text/javascript">
    $(function(){
    require.config({paths: {"pricingRuleDay":"res/js/marketing/pricingRuleDay_list"}});
		require(["pricingRuleDay"], function (pricingRuleDay){
			pricingRuleDay.init();
		});
		$("input[name='customizedDateStr']").datepicker({
            language: "zh-CN",
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            multidate:true,
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        }); 
		$(".datepicker").datepicker({
            language: "zh-CN",
            autoclose: true,//选中之后自动隐藏日期选择框
            clearBtn: true,//清除按钮
            todayBtn: true,//今日按钮
            format: "yyyy-mm-dd"//日期格式，详见 http://bootstrap-datepicker.readthedocs.org/en/release/options.html#format
        });    
	   }); 
    </script>

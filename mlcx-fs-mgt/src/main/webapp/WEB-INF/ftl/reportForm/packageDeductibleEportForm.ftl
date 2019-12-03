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
       </div><!-- /.box-tools -->
       </div><!-- /.box-header -->
       <form name="packageDeductibleForm">
       <div class="box-body">
       
       <div class="row form-group">
		
		        
		        <label class="radio-inline">
                 <input type="radio" name="inlineRadioOptions"  value="0" onclick="reportFormNs(this.value);">全部
				</label>
                 <label class="radio-inline">
                 <input type="radio" name="inlineRadioOptions"  value="1" onclick="reportFormNs(this.value);"> 按年
				</label>
				<label class="radio-inline">
				  <input type="radio" name="inlineRadioOptions"  value="2" onclick="reportFormNs(this.value);"> 按月
				</label>
				<label class="radio-inline">
				  <input type="radio" name="inlineRadioOptions"  value="3" onclick="reportFormNs(this.value);"> 按天
				</label>
                
		
		
         </div><!-- /.row -->
       
       
		<div class="row pull-down-menu">
		<div class="yearNs" style="display: none">
		<div class="col-md-2">
		        <div class="frombg">
                    <span>起始年</span><input class="yearTime form-control" name="createTimeStart" style="background:#FFFFFF" readonly/>
                </div>
		</div>
		<div class="col-md-2">
		        <div class="frombg">
                    <span>结束年</span><input class="yearTime form-control" name="createTimeEnd" style="background:#FFFFFF" readonly/>
                </div>
		</div>
		</div>
		
		<div class="monthNs" style="display: none">
		<div class="col-md-2">
		        <div class="frombg">
                    <span>年份</span><input class="yearTime form-control" name="yearNs" style="background:#FFFFFF" readonly/>
                </div>
		</div>
		</div>
		
		<div class="dayNs" style="display: none">
		<div class="col-md-2">
		        <div class="frombg">
                    <span>月份</span><input class="monthTime form-control" name="monthNs" style="background:#FFFFFF" readonly/>
                </div>
		</div>
		</div>
		<!--修改-->
		<div class="col-md-6">
               <div class="box-footer">
                <button type="reset" class="btn btn-default pull-right btn-flat btnDefault" style="background:#fa6e30">清空</button>
                <button type="button" class="btn btn-default pull-right btn-flat btnColorA" id="packageDeductibleSearch" style="background:#2b94fd">确定</button>

               </div><!-- /.box-footer -->
        </div>
         </div><!-- /.row -->
        </div><!-- /.box-body -->

         </form>
       </div><!-- /.box -->
      </div><!-- /.col -->
     </div><!-- /.row -->
      <div class="row">
       
      
		<div class="col-sm-12 Jq_resize_box">
			
			<div class="mainbg">
			 	<div id="eportFormPackageDeductible" style="width: 90%;height:400px;margin-left:5%;margin-right:5%;"></div>
			 </div>
		</div>
		<!--2楼-->
		

	</div>
       
     </div><!-- /.row -->
    </div><!-- /.container-fluid -->
   
   
<script type="text/javascript">
	$(function() {
		require.config({
			paths: {
				"packageDeductibleEportForm": "res/js/reportForm/packageDeductibleEportForm"
			}
		});
		require(["packageDeductibleEportForm"], function(packageDeductibleEportForm) {
			packageDeductibleEportForm.init();
		});

	 
	});
</script>
<script type="text/javascript">  
$(function () {
    $(".yearTime").datetimepicker({
    	  format: 'yyyy',  
          weekStart: 1,  
          autoclose: true,  
          startView: 4,  
          minView: 4,  
          forceParse: false,  
          language: 'zh-CN'  
    });
});

$('.monthTime').datetimepicker({  
    format: 'yyyymm',  
     weekStart: 1,  
     autoclose: true,  
     startView: 3,  
     minView: 3,  
     forceParse: false,  
     language: 'zh-CN'  
}); 

</script> 


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
 <meta charset="utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <!-- Tell the browser to be responsive to screen width -->
 <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <title>行知出行管理平台 </title>
 <!-- Bootstrap 3.3.5 -->
 <link rel="stylesheet" type="text/css" href="${basePath!'' }/res/dep/bootstrap-3.3.5/css/bootstrap.min.css">
 <link rel="stylesheet" type="text/css" href="${basePath!'' }/res/css/common.css">
 <script type="text/javascript" src="${basePath!'' }/res/js/lib/jquery-1.11.3.js"></script>
</head>


<div align="right"><a href="#" title="打印" id="wareStockPriInfoBtns" target="_self" onclick="test();">打印&nbsp;&nbsp;&nbsp;&nbsp;</a></div><br/>
<span id='divPrint'>
<div class="container-fluid">
	<div class="bs-example bs-example-tabs" data-example-id="togglable-tabs">
	  <input type="hidden" value="${basePath!'' }" id="basepath"/>
	    <!-- Tab panes -->
	  <div class="tab-content">
	    <div>
           <div>
              <div>
                  <div>
                      
                             <!-- /.box-tools -->
                        </div>
                         <div align="center"><h1>库存点仓纸</h1></div>
                        <!-- /.box-header -->
                  
               </div><!-- /.col -->
            </div><!-- /.row -->   
              <div class="row">
          <table id="wareStockPrint" class="table table-bordered table-striped table-hover" width="100%" style="border-collapse: collapse;border: none;margin-left:20px; ">
			<#if flag==0>
			<tbody>
			<tr><td style="border: solid #000 1px;">产品原厂编号</td><td style="border: solid #000 1px;">产品名称</td><td style="border: solid #000 1px;">库存数量</td><td style="border: solid #000 1px;">实际库存</td></tr>
			<#list wareStockList as list>
 			<tr height="40">
	 			<td style="border: solid #000 1px;">${list.mfrCode}</td>
	 			<td style="border: solid #000 1px;">${list.itemName}</td>
	 			<td style="border: solid #000 1px;">${list.stock}</td>
	 			<td style="border: solid #000 1px;"></td>
 			</tr>
 			</#list>
 			</tbody>
 			<#else>
 				<div align="center"><h2>库存盘点中...</h2></div>
 			</#if>
			 </table>
         </div>
              
	    </div>
	   
	  </div>
	  
	</div>
</div>
<br/><br/><br/>
<br/><br/><br/>

   
	<script type="text/javascript" src="${basePath!'' }/res/js/ware/main.js"></script>
</span>
</body>

<script type="text/javascript">
   function test(){
	   	 var basepath = $("#basepath").val();
   		 $.ajax({
	    		url: basepath+"/wareStockTaking/wareStockPrint.do", 
	    		success: function(res){
	    			var code = res.code;
	    			if(code == "1"){
						document.body.innerHTML=document.getElementById('divPrint').innerHTML;
					    window.print();
					    window.opener=null;
						window.open('','_self');
						window.close();
	    			}else{
	    				bootbox.alert("出现异常，请重新操作！");
	    			}
	    		}
	    	})
   }

</script>

</html>

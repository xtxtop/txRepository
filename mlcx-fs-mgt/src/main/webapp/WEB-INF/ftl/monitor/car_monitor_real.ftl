 <meta charset="utf-8">
 <style>
.pull-down-menu{
padding:15px 0 !important;
}
.con-border{
border:1px solid #dedede;
padding-right:0px;
}

.control-btn{
width:100%;
display:inline-block;
text-align:center;
margin:0 -5%;
}

.control-btn div{
width:40%;
display:inline-block;
background:#3f9fff;
color:white;
font-size: 1rem;
height:30px;
line-height:30px;
margin:1% 0;
}



.new-seacher-content{
	overflow: hidden;
	background: #fff;
	padding: 10px 16px;
	position: absolute;
	top:10px;
	left:30px;
	z-index:10;
	box-shadow:2px 0px 5px rgba(0,0,0,0.2) 
}
.new-seacher-content input,
.new-seacher-content .carMonitorSearchButton,
.new-seacher-content select
{
	border: none;
	outline: none;
	background: none;
	float: left;
	line-height: 30px;
	height: 32px;
	box-sizing: border-box;
	border: solid 1px #dedede;
	padding-left: 14px;
	border-radius:4px;
	font-size: 14px;
}

.new-seacher-content select.type1{
	width: 100px;
}
.new-seacher-content select.type2{
	width: 100px;
	margin-left: 12px;
}
.new-seacher-content input{
	width: 120px;
	margin-left: 12px;
}
.new-seacher-content .carMonitorSearchButton{
	width: 72px;
	text-align:center;
	margin-left: 16px;
	padding: 0;
	border-radius:6px;
	background: #2A64FB;
	color: #fff;
	cursor: pointer;
}


#btnAll{
    top: 15px;
    right: 15px;
    z-index: 10;
    position: absolute;
}
#btnAll.resize-btnAll{
    right: 30px;
}
.baidu-map-button{
	z-index: 10;
	width: 130px;
	height: 80px;
	border-radius: 6px;
	overflow: hidden;
	box-sizing: border-box;
	padding-top: 14px;
	border-color: transparent;
	margin-bottom: 10px;/*将来要删除*/
	cursor:pointer;
}
.baidu-map-button.bg1{
	background: linear-gradient(45deg,#cc85fe,#85d8fe);
}
.baidu-map-button.bg2{
	background: linear-gradient(45deg,#FE6FBB,#FBA9D5);
}
.baidu-map-button.bg3{
	background: linear-gradient(45deg,#F2B293,#F7CCB7);
}
.baidu-map-button.bg4{
	background: linear-gradient(45deg,#FA7C63,#EE9E9E);
}
.baidu-map-button.active{
	border: solid 2px #2A64FB;
}
.baidu-map-button .title{
	text-align: center;
	font-size: 16px;
	color: #fff;
	margin-bottom: 0px;
}
.baidu-map-button .number{
	text-align: center;
	font-size: 28px;
	color: #fff;
}
.con-row{
font-size: 1.5rem;
    margin: auto;
    text-align: center;
     width:100%;
}
 .con-row-item{
   margin: 3%;
  } 

  .con-row-item span{
  text-align:right;
   width:20%;
    font-weight: 600;
    color: black;
  } 

   .con-row-item input{
         width:60%;
         margin-left: 8%;
         border: solid 1px #DDDDDD;
         color:#4090F7;
         border-radius:3px;
             padding: 1%;
  }  
  
   .con-row-item select{
         width:60%;
         margin-left: 8%;
         padding: 2%;
         border: solid 1px #DDDDDD;
         color:#4090F7;
         border-radius:3px;
  }  
  /**车辆信息样式*/
  .car-info{
		position: absolute;
		height: 100%!important;
		right: -260px;
		top: 57px;
		z-index:100;
		background: rgb(27,33,45);
		transition:all .5s;
		width:16%;
	}
	.car-info.show-car-info{
		right: 0px;
	}
	.head-infro{
		overflow:hidden;
		box-sizing: border-box;
		padding: 0 18px;
		background: linear-gradient(90deg,#607BF1,#6097F1);
	}
	.head-infro .left,.head-infro .right{
		color: #fff;
		line-height: 40px;
	}
	.head-infro .left{
		float: left;
		font-size: 16px;
	}	
	.head-infro .right{
		float: right;
		font-size: 12px;
	}
	.echart-content{
		border-bottom: solid 1px #252E3B;
	}
	.text-infro-content .title{
		margin:0;
		font-size: 14px;
		font-weight: normal;
		line-height: 34px;
		color: #999;
		box-sizing: border-box;
		padding-left: 16px;
		
	}
	.text-infro-content .details{
		font-size: 14px;
		color: #fff;
		text-align: center;
		line-height: 34px;
		height: 34px;
		background: #222A36;
	}
</style>
   <div class="container-fluid con-border">
     <div style="position:relative">
     	 <div class="new-seacher-content">
	        <form class="form-horizontal" name="carMonitorSerachForm">
	       		<input name="flag" type="hidden" value="${flag}">
				<select class="type1" name="cityId" id="select">
					<option value="">全部</option>
						<#if cities?exists>
							<#list cities as city>
								<option value="${city.dataDictItemId}" <#if city.dataDictItemId==cityId>selected="selected"</#if> >${city.itemValue}</option>
							</#list>
						</#if>
				</select>
				<!--<select class="type2" name="userageStatus">
					<option value="">全部</option>
					<option value="2">订单中</option>
					<option value="3">调度中</option>
				</select>-->
				<input id="inputCarPlateNo" class="" name="carPlateNo" value="${carPlateNo}" placeholder="请输入车牌号">
				<input type="button" class="carMonitorSearchButton" id="carMonitorSearch" value="搜索" />
	                 
	         </form>
		    </div>
			<div id="car-map-parent-div" style="position:relative;width:100%;transition:width .5s">
		   		<div id="car-map" class="col-md-2"></div>
			    <div id="btnAll">
		       		<div class="baidu-map-button bg1">
						<div class="title">空闲车辆</div>
						<div class="number" id="kxCarNum"></div>
					</div>
					<div class="baidu-map-button bg2">
						<div class="title">调度车辆</div>
						<div class="number" id="ddzCarNum"></div>
					</div>
					<div class="baidu-map-button bg3">
						<div class="title">订单车辆</div>
						<div class="number" id="dingdzCarNum"></div>
					</div>
					<div class="baidu-map-button bg4">
						<div class="title">已预占</div>
						<div class="number" id="yjzCarNum"></div>
					</div>
		       	</div>
		    </div>
        </div>
        <div class="car-info"></div>
   		 
   	</div>
    </div><!-- /.container-fluid -->
 <style type="text/css">
 	#car-map {
		width: 100%;
		overflow: hidden; 
		/*margin: 16px;*/
	}
	.carInfro{
		width:97%;
		margin:2% auto;
		list-style:none;
		overflow:hidden;
		padding-left:5px;
	}
	
	.carInfro li{
		margin-right:10px;
		height:25px;
		line-height:25px;
		font-size:14px !important;
	}
	/*列表样式*/
	#overlay{
		opacity:.9;
		position:absolute;
		top:25%;
		right:4%;
		width:20%;
		height:auto;
		max-height:500px;
		z-index:5000;
		background-color:white;
		border:1px solid gray;
		overflow:auto;
		display:none;
		text-align:center;
	}
	
	#overlay div{
		display:block;
		text-align:center;
		border-top:1px solid #000000;
		font-size:18px;
		line-height:20px;
		height:20px;
	}
	#overlay p{
		font-size:18px;
		font-weight:bold;
	}
	
	.carInfroFull{
		width:100%;
		margin:0 auto;
		list-style:none;
		overflow:hidden;
		padding-left:10px;
	}
	.carInfroFull h3{
		margin-right:10px;
		height:25px;
		line-height:35px;
		font-size:30px;
	}
	.carInfroFull li{
		margin-right:10px;
		height:25px;
		line-height:30px;
		font-size:25px;
		margin-bottom: 40px;
	}
	.buttonFull{
		font-size:18px;
		height:"50px";
		width:"100px";
	}
	/*全屏样式*/
	.fillScree{
		position:absolute!important;
		top:0;
		left:0;
		z-index:10000!important;
	}
</style>
<!--<script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>-->
<script type="text/javascript" src="res/dep/LuShu/LuShu_min.js"></script>
<script type="text/javascript">
	$(function(){
	  require.config({paths: {"carMonitor":"res/js/monitor/car_monitor_real"}});
		require(["carMonitor"], function (carMonitor){
			carMonitor.init();
		});  
	});  
</script>
  

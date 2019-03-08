var merchantList=[];
/**
 * 
 */
$(document).ready(function(){
	locateCenter($(".file_upload"));
	
	$(".uploadBtn").click(function(){
		$(".table_body").html();
		$(".error").text("");
		uploadCSV();
	});
	
	$(".searchBtn").click(function(){
		var activeDate=$(".activeDate").val();
		if(activeDate==""){
			activeDate=null;
		}
		showResult(activeDate);
	});
	
});

$(window).resize(function(){
	locateCenter($(".file_upload"));
});

function locateCenter(item){
	var itemHeight=item.height();
	var showInfoHeight = $(".showInfo").css("display")=="none"?0:$(".showInfo").height();
	var screenHeight=$(document).height();
	if(screenHeight>400){
		item.css("margin-top",(screenHeight-itemHeight-showInfoHeight-100)/2-40);
	}
}

function uploadCSV(){
	var formData = new FormData();
	formData.append("file",$(".file")[0].files[0]);
	var fileCheck=false;
	if($(".file")[0].files[0].type=="application/x-msexcel"){
		fileCheck=true;
	}
	if(fileCheck){
		$.ajax({ 
			url: '/uploadCSV', 
			data: formData, 
			processData: false, 
			contentType: false, 
			type: 'POST', 
			success: function(data){
				if(data.error!=undefined){
					showErrMsg(data.error);
				}else if(data.result!=undefined){
					merchantList=data.result;
					showResult(null);
					locateCenter($(".file_upload"));
				}
			},
			error : function(request, status, error) {
				showErrMsg(request.message);
			}
		});
	}else{
		showErrMsg("Please, check your file type(only CSV)");
	}
	

	
}

function showResult(inputDate){
	$(".showInfo").removeClass("hidden");
	
	var showList=[];
	if(inputDate!=null){
		inputDate=inputDate.replace(/[^0-9]/g,"");
		merchantList.forEach(function(merchant){
			if(merchant.startDate <= inputDate && merchant.endDate >= inputDate){
				showList.push(merchant);
			}
		});
	}else{
		showList=merchantList;
	}
	var str ="";
	$(".resultNo").text("Search result : "+showList.length);
	showList.forEach(function(item){
		str+=
			"<div class='tr'>"+
				"<div class='td shop'>"+item.shop+"</div>"+
				"<div class='td start_date' >"+item.startDate.substr(0,4)+"-"+item.startDate.substr(4,2)+"-"+item.startDate.substr(6,2)+"</div>"+
				"<div class='td end_date' >"+item.endDate.substr(0,4)+"-"+item.endDate.substr(4,2)+"-"+item.endDate.substr(6,2)+"</div>"+
			"</div>"
		
	});
	$(".table_body").html(str);
}

function showErrMsg(msg){
	msg=(msg=="undefind")?"please, check your file and try agin":msg;
	$(".error").text(msg);
}

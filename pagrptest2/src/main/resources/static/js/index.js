//merchantList save
var merchantList=[];

$(document).ready(function(){
	
	locateCenter($(".file_upload"));
	
	//upload Button event
	$(".uploadBtn").click(function(){
		//initialize the components
		$(".table_body").html();
		$(".error").text("");
		//start upload
		uploadCSV();
	});
	
	//Search button event
	$(".searchBtn").click(function(){
		
		var activeDate=$(".activeDate").val();
		if(activeDate==""){
			activeDate=null;
		}
		//start search
		showResult(activeDate);
	});
	
});

/**
 * adjust margin function
 * */
$(window).resize(function(){
	locateCenter($(".file_upload"));
});


/**
 * adjust margin function
 *  param item
 * 
 * */
function locateCenter(item){
	var itemHeight=item.height();
	var showInfoHeight = $(".showInfo").css("display")=="none"?0:$(".showInfo").height();
	var screenHeight=$(document).height();
	var headerHeight=$("header").height();
	if(screenHeight>400){
		item.css("margin-top",(screenHeight-itemHeight-showInfoHeight-headerHeight)/2);
	}else{
		item.css("margin-top",(screenHeight-itemHeight-showInfoHeight-headerHeight)/2+headerHeight);
	}
}

/**
 * uploadCSV
 * function 
 * check&upload
 * */
function uploadCSV(){
	//setting data
	var formData = new FormData();
	formData.append("file",$(".file")[0].files[0]);
	
	//check for file format
	var fileCheck=false;
	var file = $(".file")[0].files[0];
	if(file!=undefined){
		if(file.type=="application/vnd.ms-excel"){
			fileCheck=true;
		}
	}
	
	//upload 
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
		showErrMsg("Please, check your file(only CSV)");
	}
}


/**
 * function for Result
 * param inputDate: date for search
 * */
function showResult(inputDate){
	//control the layout 
	$(".showInfo").removeClass("hidden");
	
	//array for search results
	var showList=[];
	
	//
	if(inputDate!=null){
		inputDate=inputDate.replace(/[^0-9]/g,"");
		merchantList.forEach(function(merchant){
			
			if(merchant.startDate <= inputDate && merchant.endDate >= inputDate){
				showList.push(merchant);
			}
		});
	}else{
		 //no date selection, it should display all the shops
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

/**
 * show Error Message
 * */
function showErrMsg(msg){
	msg=(msg=="undefind")?"please, check your file and try agin":msg;
	$(".error").text(msg);
}

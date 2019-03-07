const TOP = "1";
const LEFT ="2";
const TOPLEFT="3";
/**
 * 
 */
$(document).ready(function(){
	locateCenter(TOPLEFT,$(".file_upload"));
	
	$(".uploadBtn").click(function(){
		$(".error").text("");
		uploadCSV();
	});
});

function locateCenter(type,item){
	switch(type){
		case TOP: 
		case TOPLEFT:
			var itemHeight=item.height();
			var windowHeight=$(window).height();
			item.css("margin-top",(windowHeight-itemHeight)/2);
			break;
		case LEFT: 
		case TOPLEFT:	
			var windowWidth=$(window).width();
			var itemWidth=item.width();
			item.css("margin-left",(windowWidth-itemWidth)/2);
			break;
	}
}

function uploadCSV(){
	var formData = new FormData();
	formData.append("file",$(".file")[0].files[0]);
	$.ajax({ 
		url: '/uploadCSV', 
		data: formData, 
		processData: false, 
		contentType: false, 
		type: 'POST', 
		success: function(data){
			if(data.error!=undefined){
				showErrMsg(data.error);
			}else if(data.result.size()>0){
				
			}
		},
		error : function(request, status, error) {
			showErrMsg(request.message);
		}
	});

	
}

function showResult(merchantList){
	
}

function showErrMsg(msg){
	alert(msg);
	$(".error").text(msg);
}

$(window).on("load", function(){
	getCurrentUser();
	$("#oldPrice").attr("disabled", true);
	$("#oldPrice").css("color", "white");
});

function getCurrentUser(){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getUser',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            if(data.responseJSON != undefined && data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
                getAdminFromUserId(data.responseJSON.id);
            }else {
            	alert("You cannot access this page!");
                window.location.href = "../index.html";
           	}
        }
    });
}

function getAdminFromUserId(userId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyAdminFromUserId/' + userId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
       		var admin = data.responseJSON;
       		getExaminationPrice();
        }
    });
}

function getExaminationPrice(){
	var examinationId = getUrlVars()["examinationId"];
	$.ajax({
		method: 'GET',
        url: 'http://localhost:8080/getExaminationById/' + examinationId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
		async: false,
        complete: function (data) {
        	var examination = data.responseJSON;
        	$("#oldPrice").val(examination.price);
        	saveNewPrice(examinationId);
        }
	});
}

function saveNewPrice(examinationId){
	$("#save").click(function(event){
		event.preventDefault();
		
		$.ajax({
	        method: 'PUT',
	        url: 'http://localhost:8080/updateExaminationPrice/' + examinationId + '/' + $("#newPrice").val(),
	        headers: {
	   			Authorization: 'Bearer ' + $.cookie('token')
			},
			contentType: 'application/json',
            dataType: 'json',
	        complete: function (data) {
	        	if(data.status == 200){
	        		alert("SUCCESS!");
	        		window.location.href = "pharmacyAvailableExaminations.html";
	        	}
	        }
		});
	});
}

function getUrlVars() {
    var vars = {};
    window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}


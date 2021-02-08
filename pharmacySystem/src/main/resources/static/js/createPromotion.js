$(window).on("load", function(){

	$("#datepicker").datepicker({minDate:0});
	getCurrentUser();
});

function getCurrentUser(){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getUser',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            if(data.responseJSON == undefined || data.responseJSON.type == "ROLE_PHARMACY_SYSTEM_ADMIN" ||
            	data.responseJSON.type == "ROLE_DERMATOLOGIST" || data.responseJSON.type == "ROLE_SUPPLIER" ||
            	data.responseJSON.type == "ROLE_PHARMACIST" || data.responseJSON.type == "ROLE_PATIENT"){
                
                alert("You cannot access this page!");
                window.location.href = "../index.html";
            }else if(data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
            	getAdminFromUserId(data.responseJSON.id);
           	}
        }
    });
}

function checkFields(admin){
	$("#submit").click(function(event){
		event.preventDefault();

		if($("#datepicker").val() == "")
			$("#datepicker").css("background-color", "red");
		
		if($("#text").val() == "")
			$("#text").css("background-color", "red");
		
		if($("#datepicker").val() != "" && $("#text").val() != ""){	
			$("#datepicker").css("background-color", "yellow");
			$("#text").css("background-color", "yellow");
			createPromotion(admin.pharmacyDto.id);
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
        	checkFields(admin);
        }
    });
}

function createPromotion(pharmacyId){
	
	var ourDate = $("#datepicker").val();
	var parts = ourDate.split('/');
	var sendingDate = parts[2] + "-" + parts[0] + "-" + parts[1];
		
	var data = {
		"text": $("#text").val(),
		"untilDate": sendingDate,
		"pharmacy": {
			"id": pharmacyId
		}
	}
	
	var transformedData = JSON.stringify(data);
	
	$.ajax({
        url: 'http://localhost:8080/createPromotion',
        type: 'POST',
        data: transformedData,
        contentType: 'application/json',
        dataType: 'json',
        headers: {
           Authorization: 'Bearer ' + $.cookie('token')
        },
        complete: function (data) {
            if (data.status == 201){
                alert("Success!");
                window.location.href = "index.html";
            }
            else 
                alert("Error!");
        }
    });
}


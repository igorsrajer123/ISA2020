$(window).on("load", function(){
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
            if(data.responseJSON == undefined || data.responseJSON.type != "ROLE_PHARMACY_SYSTEM_ADMIN"){
                alert("You cannot access this page!");
                window.location.href = "../index.html";
            }else {
            	checkFields();
            	submitData();
            }
        }
    });
}

function checkFields(){
	$("#save").click(function(event){
		event.preventDefault();
		
		if($("#name").val() == ""){
			$("#name").css("background-color","red");
		}
		
		if($("#address").val() == ""){
			$("#address").css("background-color","red");
		}
		
		if($("#city").val() == ""){
			$("#city").css("background-color","red");
		}
	});
}

function submitData(){
	$("#save").click(function(){
		event.preventDefault();
		
		if($("#name").val() != "" && $("#address").val() != "" && $("#city").val() != "" ){
			$("#name").css("background-color","white");
			$("#address").css("background-color","white");
			$("#city").css("background-color","white");
			createNewPharmacy();
		}
		else{
			alert("Please enter the required data!");
		}
	});
}

function createNewPharmacy(){
	var data = {
		"name": $("#name").val(),
		"address": $("#address").val(),
		"city": $("#city").val(),
		"rating": 0,
		"numberOfVotes": 0,
	}
	
	data = JSON.stringify(data);

	$.ajax({
		url: 'http://localhost:8080/addPharmacy',
        type: 'POST',
        data: data,
        contentType: 'application/json',
        dataType: 'json',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
       		if(data.status == 200){
       			alert("Success!");
       			window.location.href = "systemAdminProfile.html";	
       		}else
       			alert("ERROR!");
       	}
	});
}


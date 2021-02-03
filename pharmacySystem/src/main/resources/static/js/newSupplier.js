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
		
		if($("#email").val() == ""){
			$("#email").css("background-color","red");
		}
		
		if($("#password").val() == ""){
			$("#password").css("background-color","red");
		}
		
		if($("#firstName").val() == ""){
			$("#firstName").css("background-color","red");
		}
		
		if($("#lastName").val() == ""){
			$("#lastName").css("background-color","red");
		}
	});
}

function submitData(){
	$("#save").click(function(){
		event.preventDefault();
		
		if($("#email").val() != "" && $("#password").val() != "" && 
				$("#firstName").val() != "" && $("#lastName").val() != ""){
			$("#email").css("background-color","white");
			$("#password").css("background-color","white");
			$("#firstName").css("background-color","white");
			$("#lastName").css("background-color","white");
			createSupplier();
		}else{
			alert("Please enter the required data!");
		}
	});
}

function createSupplier(){
	var data = {
		"user": {
			"email": $("#email").val(),
			"password": $("#password").val(),
			"firstName": $("#firstName").val(),
			"lastName": $("#lastName").val(),
			"type": "ROLE_SUPPLIER"
		}
	}
	
	var transformedData = JSON.stringify(data);
	
	$.ajax({
        url: 'http://localhost:8080/addSupplier',
        type: 'POST',
        data: transformedData,
        contentType: 'application/json',
        dataType: 'json',
        complete: function (data) {
            if (data.status == 201){
                alert("Success!");
                window.location.href = "systemAdminProfile.html";
            }
            else 
                alert("Error!");
        }
    });
}

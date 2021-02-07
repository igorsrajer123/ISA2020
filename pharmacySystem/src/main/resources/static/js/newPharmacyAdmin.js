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
            	getAllPharmacies();
            }
        }
    });
}

function getAllPharmacies(){
	$.ajax({
        url: 'http://localhost:8080/getAllPharmacies',
        type: 'GET',
        headers: {
           Authorization: 'Bearer ' + $.cookie('token')
        },
        complete: function(data){
            var pharmacies = data.responseJSON;
                        
            for(var i = 0; i < pharmacies.length; i++)
            	$("#pharmacies").append("<br/><input type='checkbox' class='pharmacy-list' value='" + pharmacies[i].id + "' id='" + pharmacies[i].id + "'/> <label>" + pharmacies[i].name + "(" + pharmacies[i].city + ")" + "</label>");           		
            
           	checkFields();
            submitData();  
            checkOnlyOnePharmacy();    
        }
    });
}

function checkOnlyOnePharmacy(){
    $(".pharmacy-list").on("change", function(){
        $(this).siblings("input:checkbox").prop("checked", false);     
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
			collectCheckedPharmacy();
		}else{
			alert("Please enter the required data!");
		}
	});
}

function collectCheckedPharmacy(){
	$("input:checked").each(function(){
		var pharmacyId = $(this).closest('input').attr('id');
		
		$.ajax({
			url: 'http://localhost:8080/getPharmacy/' + pharmacyId,
	        type: 'GET',
	        async: false,
	        headers: {
	           Authorization: 'Bearer ' + $.cookie('token')
	        },
	        complete: function(data){
				createPharmacyAdmin(data.responseJSON);
			}
		});
	});
}

function createPharmacyAdmin(pharmacy){
	var data = {
		"pharmacy": pharmacy,
		"user": {
			"email": $("#email").val(),
			"password": $("#password").val(),
			"firstName": $("#firstName").val(),
			"lastName": $("#lastName").val(),
			"type": "ROLE_PHARMACY_ADMIN"
		}
	}
	
	var transformedData = JSON.stringify(data);
	
	$.ajax({
        url: 'http://localhost:8080/addPharmacyAdmin',
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
                window.location.href = "systemAdminProfile.html";
            }
            else 
                alert("Error!");
        }
    });
}
$(window).on("load", function(){
	getCurrentUser();
	redirectUser();
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
                getPharmacy(data.responseJSON.pharmacyAdministrator.id);
            }else {
            	alert("You cannot access this page!");
                window.location.href = "../index.html";
           	}
        }
    });
}

function getPharmacy(adminId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAdminsPharmacy/' + adminId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
        		$("#name").val(data.responseJSON.name);
        		$("#city").val(data.responseJSON.city);
        		$("#address").val(data.responseJSON.address);
        		$("#rating").text(data.responseJSON.rating);
        		$("#description").val(data.responseJSON.description);
        		saveData(data.responseJSON.id);
        	}else{
        		alert("Something went wrong!");
        	}
       	}
	});
}

function saveData(pharmacyId){
	$("#save").click(function(event){
        event.preventDefault();
		var data = {
        	"id": pharmacyId,
            "name": $("#name").val(),
            "city": $("#city").val(),
            "address": $("#address").val(),
            "description": $("#description").val()
        }

        var transformedData = JSON.stringify(data);
		
        $.ajax({
            method: 'POST',
            url: 'http://localhost:8080/updatePharmacy',
            data: transformedData,
            contentType: 'application/json',
            dataType: 'json',
            headers: {
                Authorization: 'Bearer ' + $.cookie('token')
              },
            complete: function (data) {
                if(data.status == 200)
                    alert("Changes successful!");
                else
                    alert("Something went wrong...");    
            }
        });
	});
}

function redirectUser(){

	$("#pharmacyMedsOptions").click(function(event){
		event.preventDefault();
		window.location.href = "pharmacyMedications.html"
	});
	
	$("#pharmacyDermatologistsOptions").click(function(event){
		event.preventDefault();
		window.location.href = "pharmacyDermatologists.html"
	});
	
	$("#pharmacyPharmacistsOptions").click(function(event){
		event.preventDefault();
	});
}

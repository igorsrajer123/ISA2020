$(window).on("load", function(){
	getCurrentUser();
	$("#datepicker").datepicker({minDate:0});
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
            	data.responseJSON.type == "ROLE_PHARMACIST" || data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
                
                alert("You cannot access this page!");
                window.location.href = "../index.html";
            }else if(data.responseJSON.type == "ROLE_PATIENT"){
            	var user = data.responseJSON;
            	getPatientFromUserId(user.id);

           	}
        }
    });
}

function getPatientFromUserId(userId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPatientByUserId/' + userId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            var patient = data.responseJSON;
            getMedicationInPharmacy(patient.id);
        }
    });
}

function getMedicationInPharmacy(patientId){
	var medicationInPharmacyId = getUrlVars()["medicationInPharmacyId"];
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getMedInPharmacyById/' + medicationInPharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            var medInPharmacy = data.responseJSON;
            makeMedicationReservation(patientId, medInPharmacy.id);
        }
    });
}

function makeMedicationReservation(patientId, medId){
	$("#submit").click(function(event){
		event.preventDefault();
		
		var ourDate = $("#datepicker").val();
		var parts = ourDate.split('/');
		var sendingDate = parts[2] + "-" + parts[0] + "-" + parts[1];
		
		var data = {
			"pickUpDate": sendingDate,
			"patient": {
				"id": patientId
			},
			"medicationFromPharmacy": {
				"id":medId
			}
		}
		
		var transformedData = JSON.stringify(data);

		$.ajax({
	        url: 'http://localhost:8080/createMedicationReservation',
	        type: 'POST',
	        data: transformedData,
	        contentType: 'application/json',
	        dataType: 'json',
	        headers: {
	   			Authorization: 'Bearer ' + $.cookie('token')
			},
	        complete: function (data) {
	            if (data.status == 200){
	               $.ajax({
				        url: 'http://localhost:8080/medicationReservationMade/' + data.responseJSON.id,
				        type: 'POST',
				        contentType: 'application/json',
				        dataType: 'json',
				        headers: {
				   			Authorization: 'Bearer ' + $.cookie('token')
						},
				        complete: function (data) {
				        	if(data.status == 200){
				        		alert("Email successfully sent!");
				        		window.location.href = "index.html";
				        	}else{
				        		alert("Email was not sent!");
				        	}
				        }
					});
	            }
	            else 
	                alert("There was an error, try again!");
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


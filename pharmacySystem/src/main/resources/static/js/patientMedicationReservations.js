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
            if(data.responseJSON == undefined || data.responseJSON.type == "ROLE_PHARMACY_SYSTEM_ADMIN" ||
            	data.responseJSON.type == "ROLE_DERMATOLOGIST" || data.responseJSON.type == "ROLE_SUPPLIER" ||
            	data.responseJSON.type == "ROLE_PHARMACIST" || data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
                
                alert("You cannot access this page!");
                window.location.href = "../index.html";
            }else if(data.responseJSON.type == "ROLE_PATIENT"){
            	getPatientFromUserId(data.responseJSON.id);
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
            getPatientActiveMedicationReservations(patient.id);
        }
    });
}

function getPatientActiveMedicationReservations(patientId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/findPatientActiveMedicationReservations/' + patientId + '/' + 'ACTIVE',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var reservations = data.responseJSON;

           	var reservationsTable = $("#table tbody");
       		reservationsTable.empty();
            
        	for(var i = 0; i < reservations.length; i++){	        	
		            reservationsTable.append("<tr id='" + reservations[i].id + "'><td>" + reservations[i].id +   
		            "</td><td>" + reservations[i].id +
		            "</td><td>" + reservations[i].id +
		            "</td><td>" + reservations[i].medicationFromPharmacy +
		            "</td><td>" + reservations[i].pickUpDate +
		            "</td><td><button id='" + reservations[i].id + reservations[i].id + "'>Cancel Reservation</button>" + 
		            "</td><td><button id='" + reservations[i].id + "' style='color: green;'>Acquire Medication</button>" + 
		            "</td></tr>");
		                
					$("#table").append(reservationsTable);
					getMedicationFromReservation(reservations[i].id);
					getMedicationPharmacyFromReservation(reservations[i].id);
					getMedicationPriceFromReservation(reservations[i].id);
					cancelReservation(patientId, reservations[i].id);
					acquireMedication(patientId, reservations[i].id);
			}
        }
    });
}

function acquireMedication(patientId, reservationId){
	$("#" + reservationId).click(function(event){
		event.preventDefault();
		
		$.ajax({
			url: 'http://localhost:8080/acquireMedication/' + patientId + '/' + reservationId,
	        type: 'PUT',
	        contentType: 'application/json',
	        dataType: 'json',
	        headers: {
	   			Authorization: 'Bearer ' + $.cookie('token')
			},
	        complete: function (data) {
	       		if(data.status == 200){
	       			alert("Success!");
	       			window.location.href = "index.html";	
	       		}else if(data.status == 404){
	       			alert("This reservation has expired!");
	       		}
	       	}
		});
	});
}

function getMedicationFromReservation(reservationId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getMedicationFromReservation/' + reservationId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	var medication = data.responseJSON;
        	 $("#" + reservationId + " td:nth-child(1)").text(medication.name);
        	 $("#" + reservationId + " td:nth-child(2)").text(medication.medicationType.name);
        }
	});
}

function getMedicationPharmacyFromReservation(reservationId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacy/' + parseInt($("#" + reservationId + " td:nth-child(3)").text()),
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	var pharmacy = data.responseJSON;
			$("#" + reservationId + " td:nth-child(3)").text(pharmacy.name);
        }
	});
}

function getMedicationPriceFromReservation(reservationId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getMedicationInPharmacyFromReservation/' + reservationId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	var med = data.responseJSON;
			$("#" + reservationId + " td:nth-child(4)").text(med.price);
        }
	});
}

function cancelReservation(patientId, reservationId){
	$("#" + reservationId + reservationId).click(function(event){
		event.preventDefault();
		
		$.ajax({
			url: 'http://localhost:8080/cancelMedicationReservation/' + patientId + '/' + reservationId,
	        type: 'PUT',
	        contentType: 'application/json',
	        dataType: 'json',
	        headers: {
	   			Authorization: 'Bearer ' + $.cookie('token')
			},
	        complete: function (data) {
	       		if(data.status == 200){
	       			alert("Success!");
	       			window.location.href = "index.html";	
	       		}else if(data.status == 404){
	       			alert("Cannot cancel reservation 24hrs before its expiration date!");
	       		}
	       	}
		});
	});
}
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
            if(data.responseJSON == undefined || data.responseJSON.type == "ROLE_PATIENT" ||
            	data.responseJSON.type == "ROLE_DERMATOLOGIST" || data.responseJSON.type == "ROLE_SUPPLIER" ||
            	data.responseJSON.type == "ROLE_PHARMACIST"){
                
                alert("You cannot access this page!");
                window.location.href = "../index.html";
            }else if(data.responseJSON.type == "ROLE_PHARMACY_SYSTEM_ADMIN"){
            	getDermatologistAbsences();
           	}else if(data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
				getPharmacyAdminFromUserId(data.responseJSON.id);
           	}
        }
    });
}

function getPharmacyAdminFromUserId(userId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyAdminFromUserId/' + userId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
           getPharmacistAbsences(data.responseJSON.pharmacyDto.id);
        }
    });
}

function getDermatologistAbsences(){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getDermatologistRequests',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	var absences = data.responseJSON;
        	
        	var absencesTable = $("#table tbody");
            absencesTable.empty();
            
            for(var i = 0; i < absences.length; i++){
            	if(absences[i].status == "PENDING"){
	            	absencesTable.append("<tr id='" + absences[i].id + "'><td>" +     
	                "</td><td>" + absences[i].from +
	                "</td><td>" + absences[i].until +
		            "</td><td>" + absences[i].status +
		            "</td><td><button id='" + absences[i].id + absences[i].id + "'>Accept</button>" + 
		            "</td><td><button id='" + absences[i].id + absences[i].id + absences[i].id +"' style='color:red;'>Decline</button>" +
	                "</td></tr>");
	                
	                $("#table").append(absencesTable);          	
	            	declineAbsenceRequest(absences[i].id);
	            	acceptAbsenceRequest(absences[i].id);
	            	getAbsenceDermatologist(absences[i].id);
	            }else{
	            	absencesTable.append("<tr id='" + absences[i].id + "'><td>" +     
	                "</td><td>" + absences[i].from +
	                "</td><td>" + absences[i].until +
		            "</td><td>" + absences[i].status +      
	                "</td></tr>");
	                
	                $("#table").append(absencesTable);
	            	getAbsenceDermatologist(absences[i].id);
	            }
            }
        }
	});
}

function getAbsenceDermatologist(absenceId){
	$.ajax({
		method: 'GET',
		url: 'http://localhost:8080/getDermatologistFromAbsenceRequest/' + absenceId,
		headers: {
	   		Authorization: 'Bearer ' + $.cookie('token')
		},
	 	complete: function (data) {

	  	var dermatologist = data.responseJSON;    
	   	$("#" + absenceId + " td:nth-child(1)").text(dermatologist.user.firstName + " " + dermatologist.user.lastName);
	  }
	});
}

function getPharmacistAbsences(pharmacyId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacistRequests/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	var absences = data.responseJSON;
        	
        	var absencesTable = $("#table tbody");
            absencesTable.empty();
            
            for(var i = 0; i < absences.length; i++){
            	if(absences[i].status == "PENDING"){
	            	absencesTable.append("<tr id='" + absences[i].id + "'><td>" +     
	                "</td><td>" + absences[i].from +
	                "</td><td>" + absences[i].until +
		            "</td><td>" + absences[i].status +
		            "</td><td><button id='" + absences[i].id + absences[i].id + "'>Accept</button>" + 
		            "</td><td><button id='" + absences[i].id + absences[i].id + absences[i].id +"' style='color:red;'>Decline</button>" +
	                "</td></tr>");
	                
	                $("#table").append(absencesTable);
	            	getAbsencePharmacist(absences[i].id);
	            	declineAbsenceRequest(absences[i].id);
	            	acceptAbsenceRequest(absences[i].id);
	            }else{
	            	absencesTable.append("<tr id='" + absences[i].id + "'><td>" +     
	                "</td><td>" + absences[i].from +
	                "</td><td>" + absences[i].until +
		            "</td><td>" + absences[i].status +      
	                "</td></tr>");
	                
	                $("#table").append(absencesTable);
	            	getAbsencePharmacist(absences[i].id);
	            }
            }
        }
	});
}

function getAbsencePharmacist(absenceId){
	$.ajax({
		method: 'GET',
		url: 'http://localhost:8080/getPharmacistFromAbsenceRequest/' + absenceId,
		headers: {
	   		Authorization: 'Bearer ' + $.cookie('token')
		},
	 	complete: function (data) {

	  	var pharmacist = data.responseJSON;      
	   	$("#" + absenceId + " td:nth-child(1)").text(pharmacist.user.firstName + " " + pharmacist.user.lastName);
	  }
	});
}

function declineAbsenceRequest(absenceId){
	$("#" + absenceId + absenceId + absenceId).click(function(event){
		event.preventDefault();

		$.ajax({
	        method: 'PUT',
	        url: 'http://localhost:8080/declineAbsence/' + absenceId,
	        headers: {
	   			Authorization: 'Bearer ' + $.cookie('token')
			},
			contentType: 'application/json',
        	dataType: 'json',
	        complete: function (data) {
	        	if(data.status == 200){
	        		alert("Success!");
	        		location.reload();
	        	}else{
	        		alert("Error!");
	        	} 
	        }
    	});
  });
}

function acceptAbsenceRequest(absenceId){
	$("#" + absenceId + absenceId).click(function(event){
		event.preventDefault();
		
		$.ajax({
	        method: 'PUT',
	        url: 'http://localhost:8080/acceptAbsence/' + absenceId,
	        headers: {
	   			Authorization: 'Bearer ' + $.cookie('token')
			},
			contentType: 'application/json',
        	dataType: 'json',
	        complete: function (data) {
	        	if(data.status == 200){
	        		alert("Success!");
	        		location.reload();
	        	}else{
	        		alert("Error!");
	        	} 
	        }
    	});
   });
}
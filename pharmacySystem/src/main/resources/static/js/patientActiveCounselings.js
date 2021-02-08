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
            }else{
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
            getPatientActiveCounselings(patient.id);
        }
    });
}

function getPatientActiveCounselings(patientId){
	console.log(patientId);
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPatientActiveCounselings/' + patientId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
       		var counselings = data.responseJSON;
        	
        	var counselingsTable = $("#table tbody");
            counselingsTable.empty();           
            
        	for(var i = 0; i < counselings.length; i++){
        		
        			counselingsTable.append("<tr id='" + counselings[i].id + "'><td>" +
        			"</td><td>" +
	                "</td><td>" + counselings[i].date +
	                "</td><td>" + counselings[i].from + "h" +
	                "</td><td>" + 
	                "</td><td><button id='" + counselings[i].id + counselings[i].id + "'>Cancel Counseling!</button>" +
	                "</td></tr>");
	                
	                $("#table").append(counselingsTable);
	      			getCounselingPharmacist(counselings[i].id);
	                cancelCounseling(counselings[i].id);
    
        	}
       	 }
	});
}

function getCounselingPharmacist(counselingId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacistFromCounseling/' + counselingId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var  pharmacist = data.responseJSON;
            $("#" + counselingId + " td:nth-child(1)").text(pharmacist.user.firstName + " " + pharmacist.user.lastName);
            
            $.ajax({
		        method: 'GET',
		        url: 'http://localhost:8080/getPharmacistPharmacy/' + pharmacist.id,
		        headers: {
		   			Authorization: 'Bearer ' + $.cookie('token')
				},
		        complete: function (data) {
		            
		            var  pharmacy = data.responseJSON;
		            $("#" + counselingId + " td:nth-child(2)").text(pharmacy.name);
		            $("#" + counselingId + " td:nth-child(5)").text(pharmacy.counselingPrice);
		        }
    		});
        }
    });
}

function cancelCounseling(counselingId){
	$("#" + counselingId + counselingId).click(function(event){
		event.preventDefault();
		
		$.ajax({
			url: 'http://localhost:8080/cancelCounseling/' + counselingId,
	        type: 'PUT',	        
	        contentType: 'application/json',
	        dataType: 'json',
	        headers: {
   				Authorization: 'Bearer ' + $.cookie('token')
			},
	        complete: function (data) {
	       		if(data.status == 200){	     
					alert("Success!");
					location.reload();
	       		}else if(data.status == 404)
	       			alert("Examination cannot be cancelled 24hrs before its start date!");
	       	}
		});
	});
}

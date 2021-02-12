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
            getPatientDoneCounselings(patient.id);
        }
    });
}

function getPatientDoneCounselings(patientId){
	console.log(patientId);
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPatientDoneCounselings/' + patientId,
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
	                "</td><td>" + counselings[i].status + 
	                "</td></tr>");
	                
	                $("#table").append(counselingsTable);
	      			getCounselingPharmacist(counselings[i].id);
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
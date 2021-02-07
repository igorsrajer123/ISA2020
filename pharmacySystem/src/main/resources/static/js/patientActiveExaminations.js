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
            getPatientActiveExaminations(patient.id);
        }
    });
}

function getExaminationsDermatologist(examinationId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getDermatologistByExaminationId/' + examinationId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var  dermatologist = data.responseJSON;
            $("#" + examinationId + " td:nth-child(1)").text(dermatologist.user.firstName + " " + dermatologist.user.lastName);
        }
    });
}

function getExaminationsPharmacies(examinationId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyByExaminationId/' + examinationId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var  pharmacy = data.responseJSON;
            $("#" + examinationId + " td:nth-child(2)").text(pharmacy.name);
        }
    });
}

function getPatientActiveExaminations(patientId){
	console.log(patientId);
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPatientActiveExaminations/' + patientId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
       		var examinations = data.responseJSON;
        	
        	var examinationsTable = $("#table tbody");
            examinationsTable.empty();           
            
        	for(var i = 0; i < examinations.length; i++){
        		if(examinations[i].duration == 0.5){
        			examinationsTable.append("<tr id='" + examinations[i].id + "'><td>" +
        			"</td><td>" +
        			"</td><td>" + examinations[i].price +    
	                "</td><td>" + examinations[i].date +
	                "</td><td>" + examinations[i].time +
	                "</td><td>" + "30min" +
	                "</td><td>" + examinations[i].status +
	                "</td><td><button id='" + examinations[i].id + examinations[i].id + "'>Schedule Examination!</button>" +
	                "</td></tr>");
	                
	                $("#table").append(examinationsTable);
	                getExaminationsDermatologist(examinations[i].id);
	                getExaminationsPharmacies(examinations[i].id);
	                cancelExamination(examinations[i].id, patientId);
        		}else{
	        		examinationsTable.append("<tr id='" + examinations[i].id + "'><td>" +
	        		"</td><td>" +
	        		"</td><td>" + examinations[i].price +    
	                "</td><td>" + examinations[i].date +
	                "</td><td>" + examinations[i].time +
	                "</td><td>" + examinations[i].duration + "h" +
	                "</td><td>" + examinations[i].status +
	                "</td><td><button id='" + examinations[i].id + examinations[i].id +"'>Cancel Examination</button>" +
	                "</td></tr>");
	                
	                $("#table").append(examinationsTable);
	                getExaminationsDermatologist(examinations[i].id);
	                getExaminationsPharmacies(examinations[i].id);
	                cancelExamination(examinations[i].id, patientId);
        		}
        	}
       	 }
	});
}

function cancelExamination(examinationId, patientId){
	$("#" + examinationId + examinationId).click(function(event){
		event.preventDefault();
		
		$.ajax({
			url: 'http://localhost:8080/cancellPatientExamination/' + patientId + '/' + examinationId,
	        type: 'DELETE',	        
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
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
            getPatientDoneExaminations(patient.id);
        }
    });
}

function getPatientDoneExaminations(patientId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPatientDoneExaminations/' + patientId,
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
	                "</td></tr>");
	                
	                $("#table").append(examinationsTable);
	                getExaminationsDermatologist(examinations[i].id);
	                getExaminationsPharmacies(examinations[i].id);
        		}else{
	        		examinationsTable.append("<tr id='" + examinations[i].id + "'><td>" +
	        		"</td><td>" +
	        		"</td><td>" + examinations[i].price +    
	                "</td><td>" + examinations[i].date +
	                "</td><td>" + examinations[i].time +
	                "</td><td>" + examinations[i].duration + "h" +
	                "</td><td>" + examinations[i].status +      
	                "</td></tr>");
	                
	                $("#table").append(examinationsTable);
	                getExaminationsDermatologist(examinations[i].id);
	                getExaminationsPharmacies(examinations[i].id);
        		}
        	}
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
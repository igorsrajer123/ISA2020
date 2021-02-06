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
            	data.responseJSON.type == "ROLE_PHARMACIST"){
                
                alert("You cannot access this page!");
                window.location.href = "../index.html";
            }else if(data.responseJSON.type == "ROLE_PATIENT"){
           // 	getPharmacyFreeExaminations();
           		getPatientFromUserId(data.responseJSON.id);
           	}else if(data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
           		getAdminFromUserId(data.responseJSON.id);       		
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
            //getDermatologistFreeExaminations(patient.id);
            getPharmacyFreeExaminations(patient.id);
        }
    });
}

function getUrlVars() {
    var vars = {};
    window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
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

function getPharmacyFreeExaminations(patientId){
	var pharmacyId = getUrlVars()["pharmacyId"];
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getExaminationsByPharmacyIdAndStatus/' + pharmacyId + '/' + 'FREE',
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
        			"</td><td>" + examinations[i].price +    
	                "</td><td>" + examinations[i].date +
	                "</td><td>" + examinations[i].time +
	                "</td><td>" + "30min" +
	                "</td><td>" + examinations[i].status +
	                "</td><td>" +
	                "</td><td><button id='" + examinations[i].id + "'>Schedule Examination!</button>" +
	                "</td></tr>");
	                
	                $("#table").append(examinationsTable);
	                getExaminationsDermatologist(examinations[i].id);
	                reserveExamination(examinations[i].id, patientId);
        		}else{
	        		examinationsTable.append("<tr id='" + examinations[i].id + "'><td>" +
	        		"</td><td>" + examinations[i].price +    
	                "</td><td>" + examinations[i].date +
	                "</td><td>" + examinations[i].time +
	                "</td><td>" + examinations[i].duration + "h" +
	                "</td><td>" + examinations[i].status +
	                "</td><td>" +
	                "</td><td><button id='" + examinations[i].id + "'>Schedule Examination!</button>" +
	                "</td></tr>");
	                
	                $("#table").append(examinationsTable);
	                getExaminationsDermatologist(examinations[i].id);
	                reserveExamination(examinations[i].id, patientId);
        		}
        	}
       	}
	});
}

function getAdminFromUserId(userId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyAdminFromUserId/' + userId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            getPharmacy(data.responseJSON.id);
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
        		var pharmacy = data.responseJSON;
        		getAllPharmacyExaminations(pharmacy.id);
        	}else{
        		alert("Something went wrong!");
        	}
       	}
	});
}
function getAllPharmacyExaminations(pharmacyId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAllPharmacyExaminations/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
        		var examinations = data.responseJSON;
        		
        	var examinationsTable = $("#table tbody");
            examinationsTable.empty();
        	for(var i = 0; i < examinations.length; i++){
        		if(examinations[i].duration == 0.5){
        			examinationsTable.append("<tr id='" + examinations[i].id + "'><td>" +
        			"</td><td>" + examinations[i].price +      
	                "</td><td>" + examinations[i].date +
	                "</td><td>" + examinations[i].time +
	                "</td><td>" + "30min" +
	                "</td><td>" + examinations[i].status +
	                "</td></tr>");
	                
	                $("#table").append(examinationsTable);
	                getExaminationsDermatologist(examinations[i].id);
        		}else{
	        		examinationsTable.append("<tr id='" + examinations[i].id + "'><td>" + 
	        		"</td><td>" + examinations[i].price +          
	                "</td><td>" + examinations[i].date +
	                "</td><td>" + examinations[i].time +
	                "</td><td>" + examinations[i].duration + "h" +
	                "</td><td>" + examinations[i].status +
	                "</td></tr>");
	                
	                $("#table").append(examinationsTable);
	                 getExaminationsDermatologist(examinations[i].id);
        		}
        	}
        	}else{
        		alert("Something went wrong!");
        	}
       	}
	});
}

function reserveExamination(examinationId, patientId){
	$("#" + examinationId).click(function(event){
		event.preventDefault();
		
		$.ajax({
			url: 'http://localhost:8080/reserveExaminationByPatient/' + examinationId + '/' + patientId,
	        type: 'PUT',	        
	        contentType: 'application/json',
	        dataType: 'json',
	        complete: function (data) {
	       		if(data.status == 200){
		       		$.ajax({
							url: 'http://localhost:8080/examinationScheduled/' + examinationId,
					        type: 'POST',	        
					        contentType: 'application/json',
					        dataType: 'json',
					        complete: function (data) {
					        	if(data.status == 200){
					   				alert("Success!");
	       							window.location.href = "allDermatologists.html";		
					        	}
					        }
					   });
	       		}else
	       			alert("ERROR!");
	       	}
		});
	});
}
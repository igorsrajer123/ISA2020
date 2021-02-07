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
            //	getDermatologistFreeExaminations();
            	getPatientFromUserId(data.responseJSON.id);
           	}
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


function getExaminationsPharmacy(examinationId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyByExaminationId/' + examinationId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var  pharmacy = data.responseJSON;
            $("#" + examinationId + examinationId +" td:nth-child(6)").text(pharmacy.name);
        }
    });
}

function getDermatologistFreeExaminations(patientId){
	var dermatologistId = getUrlVars()["dermatologistId"];
	
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getByStatusAndDermatologistId/' + 'FREE/' + dermatologistId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	var examinations = data.responseJSON;
        	
        	var examinationsTable = $("#table tbody");
            examinationsTable.empty();
            
        	for(var i = 0; i < examinations.length; i++){
        		if(examinations[i].duration == 0.5){
        			examinationsTable.append("<tr id='" + examinations[i].id + examinations[i].id +"'><td>" + examinations[i].price +    
	                "</td><td>" + examinations[i].date +
	                "</td><td>" + examinations[i].time +
	                "</td><td>" + "30min" +
	                "</td><td>" + examinations[i].status +
	                "</td><td>" +
	                "</td><td><button id='" + examinations[i].id + "'>Schedule Examination!</button>" +
	                "</td></tr>");
	                
	                $("#table").append(examinationsTable);
	                getExaminationsPharmacy(examinations[i].id);
	                reserveExamination(examinations[i].id, patientId);
        		}else{
	        		examinationsTable.append("<tr id='" + examinations[i].id + examinations[i].id +"'><td>" + examinations[i].price +    
	                "</td><td>" + examinations[i].date +
	                "</td><td>" + examinations[i].time +
	                "</td><td>" + examinations[i].duration + "h" +
	                "</td><td>" + examinations[i].status +
	                "</td><td>" +
	                "</td><td><button id='" + examinations[i].id + "'>Schedule Examination!</button>" +
	                "</td></tr>");
	                
	                $("#table").append(examinationsTable);
	                getExaminationsPharmacy(examinations[i].id);
	                reserveExamination(examinations[i].id, patientId);
        		}
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
            getDermatologistFreeExaminations(patient.id);
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
	        headers: {
   				Authorization: 'Bearer ' + $.cookie('token')
			},
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
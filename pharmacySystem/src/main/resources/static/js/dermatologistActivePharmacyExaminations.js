$(window).on("load", function(){
	
	getCurrentUser();
 	$("#datepicker").datepicker({minDate:0});
 	$("#datepicker").datepicker({ beforeShowDay: $.datepicker.noWeekends });
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
            	$("#addExamination").hide();
            	//getDermatologistExaminationsPatient();
            	getPatientFromUserId(data.responseJSON.id);
           	}else if(data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
           		$("#addExamination").show();
           		getDermatologistExaminationsAdmin();
           		createNewExamination();
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

function getDermatologistExaminationsAdmin(){
	var dermatologistId = getUrlVars()["dermatologistId"];
	var pharmacyId = getUrlVars()["pharmacyId"];
	getDermatologistWorkingHours(dermatologistId, pharmacyId);
	
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getDermatologistPharmacyExaminations/' + dermatologistId + "/" + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	var examinations = data.responseJSON;
        	
        	var examinationsTable = $("#table tbody");
            examinationsTable.empty();
            
        	for(var i = 0; i < examinations.length; i++){
        		if(examinations[i].duration == 0.5){
        			examinationsTable.append("<tr id='" + examinations[i].id + "'><td>" + examinations[i].price +    
	                "</td><td>" + examinations[i].date +
	                "</td><td>" + examinations[i].time +
	                "</td><td>" + "30min" +
	                "</td><td>" + examinations[i].status +
	                "</td></tr>");
	                
	                $("#table").append(examinationsTable);
        		}else{
	        		examinationsTable.append("<tr id='" + examinations[i].id + "'><td>" + examinations[i].price +    
	                "</td><td>" + examinations[i].date +
	                "</td><td>" + examinations[i].time +
	                "</td><td>" + examinations[i].duration + "h" +
	                "</td><td>" + examinations[i].status +
	                "</td></tr>");
	                
	                $("#table").append(examinationsTable);
               	}
        	}
        }
	});
}

function getDermatologistExaminationsPatient(patientId){
	var dermatologistId = getUrlVars()["dermatologistId"];
	var pharmacyId = getUrlVars()["pharmacyId"];
	getDermatologistWorkingHours(dermatologistId, pharmacyId);
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getDermatologistPharmacyExaminationsByStatus/' + 'FREE/' + dermatologistId + "/" + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	var examinations = data.responseJSON;
        	
        	var examinationsTable = $("#table tbody");
            examinationsTable.empty();
            
        	for(var i = 0; i < examinations.length; i++){
        		if(examinations[i].duration == 0.5){
        			examinationsTable.append("<tr><td>" + examinations[i].price +    
	                "</td><td>" + examinations[i].date +
	                "</td><td>" + examinations[i].time +
	                "</td><td>" + "30min" +
	                "</td><td>" + examinations[i].status +
	                "</td><td><button id='" + examinations[i].id + "'>Schedule Examination!</button>" +
	                "</td></tr>");
	                
	                $("#table").append(examinationsTable);
	                reserveExamination(examinations[i].id, patientId);
        		}else{
	        		examinationsTable.append("<tr><td>" + examinations[i].price +    
	                "</td><td>" + examinations[i].date +
	                "</td><td>" + examinations[i].time +
	                "</td><td>" + examinations[i].duration + "h" +
	                "</td><td>" + examinations[i].status +
	                "</td><td><button id='" + examinations[i].id + "'>Schedule Examination!</button>" +
	                "</td></tr>");
	                
	                $("#table").append(examinationsTable);
	                reserveExamination(examinations[i].id, patientId);
        		}
        	}
       	}
	});
}

function createNewExamination(){
	$("#add").click(function(event){
		event.preventDefault();
		checkFields();
	});
}

function checkFields(){	
		if($("#time").val() == "")
			$("#time").css("background-color", "red");
		
		if($("#duration").val() == "")
			$("#duration").css("background-color", "red");
			
		if($("#datepicker").val() == "")
			$("#datepicker").css("background-color", "red");
		
		if($("#price").val() == "")
			$("#price").css("background-color", "red");
		
		if($("#time").val() != "" && $("#duration").val() != "" && $("#datepicker").val() != "" && $("#price").val() != "")
			submitData();
}

function submitData(){
	var dermatologistId = getUrlVars()["dermatologistId"];
	var pharmacy = getUrlVars()["pharmacyId"];

	var ourDate = $("#datepicker").val();
	var parts = ourDate.split('/');
	var sendingDate = parts[2] + "-" + parts[0] + "-" + parts[1];
	
	var data = {
		"time": $("#time").val(),
		"duration": parseFloat($("#duration").val()),
		"date": sendingDate,
		"price": parseFloat($("#price").val()),
		"pharmacy": {
			"id": pharmacy
		},
		"dermatologist": {
			"id": dermatologistId
		},
		"status": 'FREE'		
	}
	
	var transformedData = JSON.stringify(data);
	alert(transformedData);
	$.ajax({
        url: 'http://localhost:8080/createExamination',
        type: 'POST',
        data: transformedData,
        contentType: 'application/json',
        dataType: 'json',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
	        	alert("Success!");
	        	location.reload();
        	}else if(data.status == 404){
        		alert("Dermatologist is not working in this pharmacy at the chosen time!");
        	}else if(data.status == 403){
        		alert("There is already examination for the given date and time!");
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
           	getDermatologistExaminationsPatient(patient.id);
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

function getDermatologistWorkingHours(dermatologistId, pharmacyId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getOneDermatologistActiveWorkingHours/' + dermatologistId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            var dermatologistTime = data.responseJSON;
            for(var i = 0; i < dermatologistTime.length; i++){
            	if(dermatologistTime[i].pharmacy.id == pharmacyId){
            			$("#workingTimeDerma").append(dermatologistTime[i].from + "h - " + dermatologistTime[i].to + "h");	
            	}
            }
        }
    });
}

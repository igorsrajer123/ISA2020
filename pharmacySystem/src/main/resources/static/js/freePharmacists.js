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
           	
           	var pharmacyId = getUrlVars()["pharmacyId"];
           	var time = getUrlVars()["time"];
           	var date = getUrlVars()["date"];
           	
           	getAvailablePharmacists(pharmacyId, time, date, patient.id);      	
        }
    });
}

function getAvailablePharmacists(pharmacyId, time, date, patientId){
	
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAvailablePharmacists/' + pharmacyId + '/' + time + '/' + date ,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            var pharmacists = data.responseJSON;
            
			var pharmacistsTable = $("#table tbody");
			pharmacistsTable.empty();
                    
			for(var i = 0; i < pharmacists.length; i++){
				pharmacistsTable.append("<tr><td>" + pharmacists[i].user.firstName +   
		 		"</td><td>" + pharmacists[i].user.lastName +
		    	"</td><td>" + pharmacists[i].rating +
		   		"</td><td><button id='" + pharmacists[i].id + "' style='font-weight:bold;'>Schedule Counseling!</button>" +
		    	"</td></tr>");
		    	
		    	$("#table").append(pharmacistsTable);
		    	scheduleCounseling(pharmacists[i].id, patientId);
   			}
   			sortByRating(pharmacists, patientId);
   		}
   	});
}

function scheduleCounseling(pharmacistId, patientId){
    var time = getUrlVars()["time"];
    var date = getUrlVars()["date"];

	$("#" + pharmacistId).click(function(event){
		event.preventDefault();
		
		var data = {
			"date": date,
			"from": parseInt(time),
			"to": (parseInt(time) + 1),
			"pharmacist": {
				"id": pharmacistId
			},
			"patient": {
				"id": patientId
			}
		}
		
		var transformedData = JSON.stringify(data);
		
		$.ajax({
	        method: 'POST',
	        url: 'http://localhost:8080/createCounseling',
	        headers: {
	   			Authorization: 'Bearer ' + $.cookie('token')
			},
			data: transformedData,
			contentType: 'application/json',
            dataType: 'json',
	        complete: function (data) {
	        	if(data.status == 200){
	        		 $.ajax({
				        url: 'http://localhost:8080/counselingScheduled/' + data.responseJSON.id,
				        type: 'POST',
				        contentType: 'application/json',
				        dataType: 'json',
				        headers: {
				   			Authorization: 'Bearer ' + $.cookie('token')
						},
				        complete: function (data) {
				        	if(data.status == 200){
				        		alert("Success!");
	        					window.location.href = "index.html";
				        	}else{
				        		alert("Email was not sent!");
				        	}
				        }
					});
	        	}else if(data.status == 404){
	        		alert("You already have active counseling with this pharmacist!");
	        	}
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

function sortByRating(pharmacists, patientId){
	var rising = false;
	$("#rating").click(function(){
		if(rising){
			var mojNiz = pharmacists.sort(function(a, b){
							return a.rating - b.rating;
						});
			
			var pharmacistsTable = $("#table tbody");
			pharmacistsTable.empty();
                    
			for(var i = 0; i < mojNiz.length; i++){
				pharmacistsTable.append("<tr><td>" + mojNiz[i].user.firstName +   
		 		"</td><td>" + mojNiz[i].user.lastName +
		    	"</td><td>" + mojNiz[i].rating +
		   		"</td><td><button id='" + mojNiz[i].id + "' style='font-weight:bold;'>Schedule Counseling!</button>" +
		    	"</td></tr>");
		    	
		    	$("#table").append(pharmacistsTable);
		    	scheduleCounseling(pharmacists[i].id, patientId);
   			}
			rising = false;
		}else{
			var mojNiz = pharmacists.sort(function(a, b){
							return b.rating - a.rating;
							});
							
			var pharmacistsTable = $("#table tbody");
			pharmacistsTable.empty();
                    
			for(var i = 0; i < pharmacists.length; i++){
				pharmacistsTable.append("<tr><td>" + mojNiz[i].user.firstName +   
		 		"</td><td>" + mojNiz[i].user.lastName +
		    	"</td><td>" + mojNiz[i].rating +
		   		"</td><td><button id='" + mojNiz[i].id + "' style='font-weight:bold;'>Schedule Counseling!</button>" +
		    	"</td></tr>");
		    	
		    	$("#table").append(pharmacistsTable);
		    	scheduleCounseling(mojNiz[i].id, patientId);
   			}
			rising = true;
		}			
	});
}
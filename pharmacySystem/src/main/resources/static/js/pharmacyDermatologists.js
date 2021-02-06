$(window).on("load", function(){
	getCurrentUser();
	
	for(var i = 8; i < 16; i++)
		$("#from").append($("<option>", {
			value: i,
			text: i
		}));

	
	for(var i = 9; i < 17; i++)
		$("#to").append($("<option>", {
			value: i,
			text: i
		}));
  	
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
            	getPharmacyDermatologistsPatient();
            	$("#addDermatologist").hide();
           	}else if(data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
           		$("#addDermatologist").show();
           		getAdminFromUserId(data.responseJSON.id);
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
            getAdminsPharmacy(data.responseJSON.id);
        }
    });
}

function getPharmacyDermatologistsPatient(){
	var pharmacyId = getUrlVars()["pharmacyId"];

	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyDermatologists/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var dermatologists = data.responseJSON;

            var dermatologistsTable = $("#table tbody");
            dermatologistsTable.empty();
            for(var i = 0; i < dermatologists.length; i++){
            	dermatologistsTable.append("<tr id='" + dermatologists[i].id + "'><td>" + dermatologists[i].user.firstName +    
                "</td><td>" + dermatologists[i].user.lastName +
                "</td><td>" + 
                "</td><td>" + dermatologists[i].rating +
                "</td><td><button id='" + dermatologists[i].id + dermatologists[i].id + "'>Available Examinations</button>" +
                "</td></tr>");
                
                $("#table").append(dermatologistsTable);
                getDermatologistPharmacies(dermatologists[i].id);
                buttonClickedPatient(dermatologists[i].id, pharmacyId);
            }
            searchDermatologistsPatient(dermatologists, pharmacyId);
        }
    });
}

function searchDermatologistsPatient(dermatologists, pharmacyId){
	$("#search").on("input", function(){
		if($("#search").val() != ""){
			
			var dermatologistsTable = $("#table tbody");
		    dermatologistsTable.empty();
	            
			for(var i = 0; i < dermatologists.length; i++){
				if(dermatologists[i].user.firstName.toLowerCase().includes($("#search").val().toLowerCase()) ||
					dermatologists[i].user.lastName.toLowerCase().includes($("#search").val().toLowerCase())){
					 dermatologistsTable.append("<tr id='" + dermatologists[i].id + "'><td>" +  dermatologists[i].user.firstName +
	                "</td><td>" + dermatologists[i].user.lastName +
	                "</td><td>" + 
	                "</td><td>" + dermatologists[i].rating +
	                "</td><td><button id='" + dermatologists[i].id + dermatologists[i].id + "'>Available Examinations</button>" +
	                "</td></tr>");
	                
	                $("#table").append(dermatologistsTable);
	                getDermatologistPharmacies(dermatologists[i].id);
	                buttonClickedPatient(dermatologists[i].id, pharmacyId);
				}
			}
		}else{
			getPharmacyDermatologistsPatient();
		}
	});
}

function getDermatologistPharmacies(dermatologistId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getDermatologistPharmacies/' + dermatologistId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var pharmacies = data.responseJSON;
            var all = "";
            for(var i = 0; i < pharmacies.length; i++){
            	all += pharmacies[i].name + ", ";
            }
            
            var text = all.replace(/,\s*$/, "");
            $("#" + dermatologistId + " td:nth-child(3)").text(text);
        }
    });
}

function getAdminsPharmacy(adminId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAdminsPharmacy/' + adminId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
        		getPharmacyDermatologistsAdmin(data.responseJSON.id);
        	}else{
        		alert("Something went wrong!");
        	}
       	}
	});
}

function getPharmacyDermatologistsAdmin(pharmacyId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyDermatologists/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
        		var dermatologists = data.responseJSON;
        		
				var dermatologistsTable = $("#table tbody");
            	dermatologistsTable.empty();
            	for(var i = 0; i < dermatologists.length; i++){
	            	dermatologistsTable.append("<tr id='" + dermatologists[i].id + "'><td>" + dermatologists[i].user.firstName +    
	                "</td><td>" + dermatologists[i].user.lastName +
	                "</td><td>" + 
	                "</td><td>" + dermatologists[i].rating +
	                "</td><td><button style='color:red;background-color: #F1948A;' id='" + dermatologists[i].id + dermatologists[i].id +"'>Remove</button>" +
	                "</td><td><button id='" + dermatologists[i].id + dermatologists[i].id + dermatologists[i].id + "'>Create Free Examination</button>" +
	                "</td></tr>");
	                
	                $("#table").append(dermatologistsTable);
	                getDermatologistPharmacies(dermatologists[i].id);
	                removeDermatologistFromPharmacy(dermatologists[i], pharmacyId);
	                buttonClickedAdmin(dermatologists[i].id, pharmacyId);
            }
           	getDermatologistsNotInPharmacy(pharmacyId);
            searchDermatologistsAdmin(dermatologists, pharmacyId);
        	}else{
        		alert("Something went wrong!");
        	}
       	}
	});
}

function searchDermatologistsAdmin(dermatologists, pharmacyId){
	$("#search").on("input", function(){
		if($("#search").val() != ""){
			
			var dermatologistsTable = $("#table tbody");
		    dermatologistsTable.empty();
	            
			for(var i = 0; i < dermatologists.length; i++){
				if(dermatologists[i].user.firstName.toLowerCase().includes($("#search").val().toLowerCase()) ||
					dermatologists[i].user.lastName.toLowerCase().includes($("#search").val().toLowerCase())){
					 dermatologistsTable.append("<tr id='" + dermatologists[i].id + "'><td>" +  dermatologists[i].user.firstName +
	                "</td><td>" + dermatologists[i].user.lastName +
	                "</td><td>" + 
	                "</td><td>" + dermatologists[i].rating +
	                "</td><td><button style='color:red;background-color: #F1948A;' id='" + dermatologists[i].id + dermatologists[i].id + "'>Remove</button>" +
	                "</td><td><button id='" + dermatologists[i].id + dermatologists[i].id +  + dermatologists[i].id + "'>Create Free Examination</button>" +
	                "</td></tr>");
	                
	                $("#table").append(dermatologistsTable);
	                getDermatologistPharmacies(dermatologists[i].id);
	                removeDermatologistFromPharmacy(dermatologists[i], pharmacyId);
	                buttonClickedAdmin(dermatologists[i].id, pharmacyId);
				}
			}
			getDermatologistsNotInPharmacy(pharmacyId);
		}else{
			getPharmacyDermatologistsAdmin(pharmacyId);
		}
	});
}

function getDermatologistsNotInPharmacy(pharmacyId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getDermatologistsNotInPHarmacy/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
        		var dermatologists = data.responseJSON;
 				$("#availableDermatologists").empty();
 				$("#availableDermatologists").append("<option></option>");
	            for(var i = 0; i < dermatologists.length; i++){
		        	$("#availableDermatologists").append($("<option>", {
		            	id: dermatologists[i].id,
		            	value: dermatologists[i].user.firstName,
		            	text: dermatologists[i].user.firstName + " " + dermatologists[i].user.lastName
		            }));
	            }   	
            	checkFields(pharmacyId);
        	}else{
        		alert(data.status + " " + pharmacyId);
        		
        	}
        }
	});
}

function checkFields(pharmacyId){
	$("#add").click(function(event){
		event.preventDefault();
		
		if($("#availableDermatologists").val() == "")
			$("#availableDermatologists").css("background-color", "red");
		
		if($("#from").val() == "")
			$("#from").css("background-color", "red");
			
		if($("#to").val() == "")
			$("#to").css("background-color", "red");
		
		if($("#availableDermatologists").val() != "" && $("#from").val() != "" && $("#to").val() != "")
			submitData(pharmacyId);
	});
}

function submitData(pharmacyId){
		if(parseInt($("#from").val()) >= parseInt($("#to").val()))
			alert("Please choose the correct time!");
		else{
			var dermatologistId = $("#availableDermatologists").children(":selected").attr("id");
			getOneDermatologistWorkingHours(dermatologistId, pharmacyId);
		}
}

function getOneDermatologistWorkingHours(dermatologistId, pharmacyId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getOneDermatologistActiveWorkingHours/' + dermatologistId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	var workingHours = data.responseJSON;
        	var taken = false;
        	var range1  = moment.range(new Date(0, 0, 0, $("#from").val(), 0), new Date(0, 0, 0, $("#to").val(), 0));
        	
        	for(var i = 0; i < workingHours.length; i++){
        		var range2 = moment.range(new Date(0, 0, 0, workingHours[i].from, 0), new Date(0, 0, 0, workingHours[i].to, 0));
        		if(range2.overlaps(range1)){
        			taken = true;
        		}
        	}
        	
        	if(taken){
        		alert("Dermatologist is not free for the period you chose! Please pick different working hours.");
        	}
        	
        	if(!taken){
        		addDermatologistToPharmacy(dermatologistId, pharmacyId);
        	}
        	
       	}
	});
}

function addDermatologistToPharmacy(dermatologistId, pharmacyId){
	$.ajax({
        url: 'http://localhost:8080/addDermatologistToPharmacy/' + dermatologistId + "/" + pharmacyId,
        type: 'POST',
        contentType: 'application/json',
        dataType: 'json',
        complete: function (data) {
        	addDermatologistWorkingHours(dermatologistId, pharmacyId);
        }
	});
}

function addDermatologistWorkingHours(dermatologistId, pharmacyId){
	var data = {
		"from": $("#from").val(),
		"to": $("#to").val(),
		"dermatologist": {
			"id": dermatologistId
		},
		"pharmacy": {
			"id": pharmacyId
		}
	};
	
	var transformedData = JSON.stringify(data);
	
	$.ajax({
        url: 'http://localhost:8080/addDermatologistWorkingHours',
        type: 'POST',
        data: transformedData,
        contentType: 'application/json',
        dataType: 'json',
        complete: function (data) {
        	alert("Success!");
        	window.location.href = "pharmacyDermatologists.html";
        }
	});
}

function removeDermatologistFromPharmacy(dermatologist, pharmacyId){
	$("#" + dermatologist.id + dermatologist.id).click(function(event){
		$.ajax({
	        url: 'http://localhost:8080/removeDermatologistFromPharmacy/' + dermatologist.id + "/" + pharmacyId,
	        type: 'DELETE',
	        contentType: 'application/json',
       	 dataType: 'json',
	        complete: function (data) {
	        	removeDermatologistActiveWorkingDays(dermatologist.id, pharmacyId);
	        }
		});
	});
}

function removeDermatologistActiveWorkingDays(dermatologistId, pharmacyId){
	$.ajax({
	        url: 'http://localhost:8080/removeDermatologistPharmacyWorkingHours/' + dermatologistId + "/" + pharmacyId,
	        type: 'DELETE',
	        contentType: 'application/json',
        	dataType: 'json',
	        complete: function (data) {
	        	alert("Success!");
	        	window.location.href = "pharmacyDermatologists.html";
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

function buttonClickedAdmin(id, pharmacyId){
	$("#" + id + id + id).click(function(event){
		event.preventDefault();
		window.location.href = "dermatologistActivePharmacyExaminations.html?dermatologistId=" + id + "&pharmacyId=" + pharmacyId;
	});
}

function buttonClickedPatient(id, pharmacyId){
	$("#" + id + id).click(function(event){
		event.preventDefault();
		window.location.href = "dermatologistActivePharmacyExaminations.html?dermatologistId=" + id + "&pharmacyId=" + pharmacyId;
	});
}


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
            	getPharmacyPharmacistsPatient();
            	$("#addPharmacist").hide();
            	$("#workingHours").hide();
           	}else if(data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
           		$("#addPharmacist").show();
           		$("#workingHours").show();
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

function getPharmacyPharmacistsPatient(){
	var pharmacyId = getUrlVars()["pharmacyId"];

	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyPharmacists/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var pharmacists = data.responseJSON;

            var pharmacistsTable = $("#table tbody");
            pharmacistsTable.empty();
            for(var i = 0; i < pharmacists.length; i++){
            	pharmacistsTable.append("<tr id='" + pharmacists[i].id + "'><td>" + pharmacists[i].user.firstName +    
                "</td><td>" + pharmacists[i].user.lastName +
                "</td><td>" + 
                "</td><td>" + pharmacists[i].from + "h" +
	            "</td><td>" + pharmacists[i].to + "h" +
	            "</td><td>" + pharmacists[i].rating +
                "</td></tr>");
                
                $("#table").append(pharmacistsTable);
            	getPharmacistPharmacy(pharmacists[i].id);
            }
            searchPharmacistsPatient(pharmacists);
        }
    });
}

function searchPharmacistsPatient(pharmacists){
	$("#search").on("input", function(){
		if($("#search").val() != ""){
			
			var pharmacistsTable = $("#table tbody");
		    pharmacistsTable.empty();
	            
			for(var i = 0; i < pharmacists.length; i++){
				if(pharmacists[i].user.firstName.toLowerCase().includes($("#search").val().toLowerCase()) ||
					pharmacists[i].user.lastName.toLowerCase().includes($("#search").val().toLowerCase())){
					 pharmacistsTable.append("<tr id='" + pharmacists[i].id + "'><td>" +  pharmacists[i].user.firstName +
	                "</td><td>" + pharmacists[i].user.lastName +
	                "</td><td>" + 
	                "</td><td>" + pharmacists[i].from + "h" +
	                "</td><td>" + pharmacists[i].to + "h" +
	                "</td><td>" + pharmacists[i].rating +
	                "</td></tr>");
	                
	                $("#table").append(pharmacistsTable);
	              	getPharmacistPharmacy(pharmacists[i].id);
				}
			}
		}else{
			getPharmacyPharmacistsPatient();
		}
	});
}

function getPharmacistPharmacy(pharmacistId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacistPharmacy/' + pharmacistId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var pharmacy = data.responseJSON;
            $("#" + pharmacistId + " td:nth-child(3)").text(pharmacy.name);
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
        		getPharmacyPharmacistsAdmin(data.responseJSON.id);
        	}else{
        		alert("Something went wrong!");
        	}
       	}
	});
}

function getPharmacyPharmacistsAdmin(pharmacyId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyPharmacists/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
        		var pharmacists = data.responseJSON;
        		
				var pharmacistsTable = $("#table tbody");
            	pharmacistsTable.empty();
            	
            	for(var i = 0; i < pharmacists.length; i++){
	            	pharmacistsTable.append("<tr id='" + pharmacists[i].id + "'><td>" + pharmacists[i].user.firstName +    
	                "</td><td>" + pharmacists[i].user.lastName +
	                "</td><td>" + 
	                "</td><td>" + pharmacists[i].from + "h" +
	                "</td><td>" + pharmacists[i].to + "h" +
	                "</td><td>" + pharmacists[i].rating +
	                 "</td><td><button id='" + pharmacists[i].id + pharmacists[i].id +"' style='color:red;background-color: #F1948A;'>Remove</button>" +
	                "</td></tr>");
	                
	                $("#table").append(pharmacistsTable);
	                getPharmacistPharmacy(pharmacists[i].id);
	                removePharmacistFromPharmacy(pharmacists[i], pharmacyId);
            }
            checkFields(pharmacyId);
           	searchPharmacistsAdmin(pharmacists, pharmacyId);
        	}else{
        		alert("Something went wrong!");
        	}
       	}
	});
}

function searchPharmacistsAdmin(pharmacists, pharmacyId){
	$("#search").on("input", function(){
		if($("#search").val() != ""){
			
			var pharmacistsTable = $("#table tbody");
		    pharmacistsTable.empty();
	            
			for(var i = 0; i < pharmacists.length; i++){
				if(pharmacists[i].user.firstName.toLowerCase().includes($("#search").val().toLowerCase()) ||
					pharmacists[i].user.lastName.toLowerCase().includes($("#search").val().toLowerCase())){
					 pharmacistsTable.append("<tr id='" + pharmacists[i].id + "'><td>" +  pharmacists[i].user.firstName +
	                "</td><td>" + pharmacists[i].user.lastName +
	                "</td><td>" + 
	                "</td><td>" + pharmacists[i].from + "h" +
	                "</td><td>" + pharmacists[i].to + "h" +
	                "</td><td>" + pharmacists[i].rating +
	                 "</td><td><button id='" + pharmacists[i].id + pharmacists[i].id + "' style='color:red;background-color: #F1948A;'>Remove</button>" +
	                "</td></tr>");
	                
	                $("#table").append(pharmacistsTable);
	                getPharmacistPharmacy(pharmacists[i].id);
	                removePharmacistFromPharmacy(pharmacists[i], pharmacyId);
				}
			}
			checkFields(pharmacyId);
		}else{
			getPharmacyPharmacistsAdmin(pharmacyId);
		}
	});
}

function checkFields(pharmacyId){
	$("#add").click(function(event){
		event.preventDefault();
		
		if($("#email").val() == "")
			$("#email").css("background-color", "red");
		
		if($("#password").val() == "")
			$("#password").css("background-color", "red");
			
		if($("#firstName").val() == "")
			$("#firstName").css("background-color", "red");
			
		if($("#lastName").val() == "")
			$("#lastName").css("background-color", "red");
			
		if($("#from").val() == "")
			$("#from").css("background-color", "red");
		
		if($("#to").val() == "")
			$("#to").css("background-color", "red");
		
		if($("#email").val() != "" && $("#password").val() != "" && $("#firstName").val() != "" &&
			$("#lastName").val() != "" && $("#from").val() != "" && $("#to").val() != "")
			submitData(pharmacyId);
	});
}

function submitData(pharmacyId){
	$("#email").css("background-color", "white");
	$("#password").css("background-color", "white");
	$("#firstName").css("background-color", "white");
	$("#lastName").css("background-color", "white");
	$("#from").css("background-color", "white");
	$("#to").css("background-color", "white");
	
	var data = {
		"user": {
			"email": $("#email").val(),
			"password": $("#password").val(),
			"firstName": $("#firstName").val(),
			"lastName": $("#lastName").val()
		},
		"deleted": false,
		"from": $("#from").val(),
		"to": 	$("#to").val(),
		"numberOfVotes": 0,
		"rating": 0,
		"pharmacy":{
			"id": pharmacyId
		}
	};
	
	var transformedData = JSON.stringify(data);
	
	$.ajax({
        url: 'http://localhost:8080/addPharmacist',
        type: 'POST',
        data: transformedData,
        contentType: 'application/json',
        dataType: 'json',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	alert("Success!");
        	window.location.href = "pharmacyPharmacists.html";
        }
	});
}

function removePharmacistFromPharmacy(pharmacist, pharmacyId){
	$("#" + pharmacist.id + pharmacist.id).click(function(event){
		event.preventDefault();
		$.ajax({
	        url: 'http://localhost:8080/removePharmacistFromPharmacy/' + pharmacist.id + "/" + pharmacyId,
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
	        	}else if(data.status == 404){
	        		alert("This pharmacist has active counselings and cannot be removed yet!");
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


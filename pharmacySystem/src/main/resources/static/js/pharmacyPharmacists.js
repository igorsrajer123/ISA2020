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
            	getPharmacyPharmacistsPatient();
            	$("#addPharmacist").hide();
           	}else if(data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
           		$("#addPharmacist").show();
           		getAdminsPharmacy(data.responseJSON.pharmacyAdministrator.id);
           	}
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
	                "</td><td>" + pharmacists[i].rating +
	                "</td></tr>");
	                
	                $("#table").append(dermatologistsTable);
	                getPharmacistPharmacy(pharmacists[i].id);
            }
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
	                "</td><td>" + pharmacists[i].rating +
	                "</td></tr>");
	                
	                $("#table").append(pharmacistsTable);
	                getPharmacistPharmacy(pharmacists[i].id);
				}
			}
		}else{
			getPharmacyPharmacistsAdmin(pharmacyId);
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


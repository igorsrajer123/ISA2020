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
            	getPharmacyDermatologistsPatient();
            	$("#addDermatologist").hide();
           	}else if(data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
           		$("#addDermatologist").show();
           		getAdminsPharmacy(data.responseJSON.pharmacyAdministrator.id);
           	}
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
                "</td></tr>");
                
                $("#table").append(dermatologistsTable);
                getDermatologistPharmacies(dermatologists[i].id);
            }
            searchDermatologistsPatient(dermatologists);
        }
    });
}

function searchDermatologistsPatient(dermatologists){
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
	                "</td></tr>");
	                
	                $("#table").append(dermatologistsTable);
	                getDermatologistPharmacies(dermatologists[i].id);
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
	                "</td></tr>");
	                
	                $("#table").append(dermatologistsTable);
	                getDermatologistPharmacies(dermatologists[i].id);
            }
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
	                "</td></tr>");
	                
	                $("#table").append(dermatologistsTable);
	                getDermatologistPharmacies(dermatologists[i].id);
				}
			}
		}else{
			getPharmacyDermatologistsAdmin(pharmacyId);
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

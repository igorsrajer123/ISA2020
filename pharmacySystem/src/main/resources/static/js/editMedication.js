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
            if(data.responseJSON != undefined && data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
                getMedicationData();
                getAdminPharmacy(data.responseJSON.pharmacyAdministrator);
            }else {
            	alert("You cannot access this page!");
                window.location.href = "../index.html";
           	}
        }
    });
}

function getMedicationData(){
	var medId = getUrlVars()["medId"];
	
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getMedicationById/' + medId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            if(data.status == 200){
            	var med = data.responseJSON;
            	$("#name").val(med.name);
            	$("#code").val(med.code);
            	$("#dailyIntake").val(med.dailyIntake);
            	$("#sideEffects").val(med.sideEffects);
            	$("#composition").val(med.chemicalComposition);
            	$("#price").val(med.price);
				getMedicationTypes(med);
				getAllMedications(med);
				updateMedication(med);
            }else{
            	alert("Error!");
            	window.location.href = "index.html";
            }
        }
    });
}

function getAdminPharmacy(admin){
	var medId = getUrlVars()["medId"];
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAdminsPharmacy/' + admin.id,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
        		removeMedicationFromPharmacy(data.responseJSON.id, medId);
        	}else{
        		alert("Something went wrong!");
        	}
       	}
	});
}

function removeMedicationFromPharmacy(pharmacyId, medId){
	$("#removeMedication").click(function(event){
		event.preventDefault();
		
		$.ajax({
	        method: 'POST',
	        url: 'http://localhost:8080/removeMedicationFromPharmacy/' + pharmacyId + "/" + medId,
	        headers: {
	   			Authorization: 'Bearer ' + $.cookie('token')
			},
			contentType: 'application/json',
            dataType: 'json',
	        complete: function (data) {
	        	if(data.status == 200){
	        		alert("SUCCESS!");
	        		window.location.href = "pharmacyMedications.html";
	        	}else{
	        		alert("Something went wrong!");
	        	}
	       	}
		});
	});
}

function getMedicationTypes(med){
	$.ajax({
		method: 'GET',
        url: 'http://localhost:8080/getAllMedicationTypes',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	var types = data.responseJSON;
        	
        	for(var i = 0; i < types.length; i++){
            	$("#type").append($("<option>", {
            			id: types[i].id,
	            		value: types[i].name,
	            		text: types[i].name
	            	}));         
            }	
       	$("#type option[value='" + med.medicationType.name + "'").attr("selected", true);	
       	}
	});
}

function getAllMedications(med){
	$.ajax({
		method: 'GET',
        url: 'http://localhost:8080/getAllMedications',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	var meds = data.responseJSON;
        	
        	for(var i = 0; i < meds.length; i++){
            	$("#substitution").append($("<option>", {
            			id: meds[i].id,
	            		value: meds[i].name,
	            		text: meds[i].name
	            	}));         
            }	
        $("#substitution option[value='" + med.name + "'").attr("selected", true);		
       	}
	});
}

function updateMedication(med){
	$("#save").click(function(event){
		event.preventDefault();
		
		var data = {
			"id": med.id,
			"name":	$("#name").val(),
            "code": $("#code").val(),
            "dailyIntake": $("#dailyIntake").val(),
            "sideEffects": $("#sideEffects").val(),
            "chemicalComposition": $("#composition").val(),
            "price": $("#price").val(),
            "medicationType":  {
				"id":	$("#type").children(":selected").attr("id"),
				"name":	$("#type").val()
			},
            "substitution": $("#substitution").val()
		}
		
		var transformedData = JSON.stringify(data);
		
		$.ajax({
	        method: 'PUT',
	        url: 'http://localhost:8080/updateMedication/',
	        headers: {
	   			Authorization: 'Bearer ' + $.cookie('token')
			},
			contentType: 'application/json',
            dataType: 'json',
            data: transformedData,
	        complete: function (data) {
	        	if(data.status == 200){
	        		alert("SUCCESS!");
	        		window.location.href = "pharmacyMedications.html";
	        	}else{
	        		alert("Something went wrong!");
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


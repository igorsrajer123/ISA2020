$(window).on("load", function(){
	getCurrentUser();
	$("#name").prop("disabled", true);
	$("#name").css("color", "white");
	$("#code").prop("disabled", true);
	$("#code").css("color", "white");
	$("#type").prop("disabled", true);
	$("#type").css("color", "white");
	$("#dailyIntake").prop("disabled", true);
	$("#dailyIntake").css("color", "white");
	$("#sideEffects").prop("disabled", true);
	$("#sideEffects").css("color", "white");
	$("#composition").prop("disabled", true);
	$("#composition").css("color", "white");
	$("#substitution").prop("disabled", true);
	$("#substitution").css("color", "white");
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
                getAdminFromUserId(data.responseJSON.id);
            }else {
            	alert("You cannot access this page!");
                window.location.href = "../index.html";
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
         getAdminPharmacy(data.responseJSON);
        }
    });
}

function getMedicationPriceAndAmount(pharmacy){
	var medId = getUrlVars()["medId"];
	$.ajax({
		method: 'GET',
        url: 'http://localhost:8080/findOneByPharmacyIdAndMedicationId/' + pharmacy.id + "/" + medId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
		async: false,
        complete: function (data) {
        	$("#price").val(data.responseJSON.price);
        	$("#amount").val(data.responseJSON.amount);
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
				getMedicationTypes(med);
				getAllMedications(med);
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
        		getMedicationPriceAndAmount(data.responseJSON);
        	//	updateMedication(medId, data.responseJSON);
        		updateMedicationsInPharmacies(medId, data.responseJSON);
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
	        	}else if(data.status == 404){
	        		alert("This medication is reserved by a patient!");
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

function updateMedicationsInPharmacies(medId, pharmacy){
	$("#save").click(function(event){
		event.preventDefault();
		
		var data = {
				"price": $("#price").val(),
				"amount": $("#amount").val(),
				"pharmacy": {
					"id": pharmacy.id
				},
				"medication": {
					"id": medId
				}
			}
			
		var transformedData = JSON.stringify(data);
		$.ajax({
	        method: 'PUT',
	        url: 'http://localhost:8080/updateMedicationInPharmacy',
	        headers: {
	   			Authorization: 'Bearer ' + $.cookie('token')
			},
			contentType: 'application/json',
            dataType: 'json',
            data: transformedData,
	        complete: function (data) {
	        	alert("SUCCESS!");
	        	window.location.href = "pharmacyMedications.html";
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


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
            	getPharmacyMedicationsPatient();
            	$("#addMedication").hide();
           	}else if(data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
           		$("#addMedication").show();
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

function getPharmacyMedicationsPatient(){
	var pharmacyId = getUrlVars()["pharmacyId"];
	
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyMedications/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var meds = data.responseJSON;
			
            var medsTable = $("#table tbody");
            medsTable.empty();
            
            for(var i = 0; i < meds.length; i++){
            	medsTable.append("<tr id='" + meds[i].id + "'><td>" + meds[i].name +   
                "</td><td>" + meds[i].code +
                "</td><td>" + meds[i].dailyIntake +
                "</td><td>" + meds[i].sideEffects +
                "</td><td>" + meds[i].price +
                "</td><td>" + meds[i].amount +
                "</td></tr>");
                
                $("#table").append(medsTable);
                getMedicationPriceAndAmount(pharmacyId, meds[i]);
            }
            searchMedicationsPatient(meds, pharmacyId);
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
        		getPharmacyMedicationsAdmin(data.responseJSON.id);
        		getMedicationsNotInPharmacy(data.responseJSON.id);
        	}else{
        		alert("Something went wrong!");
        	}
       	}
	});
}

function getMedicationsNotInPharmacy(pharmacyId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getMedicationsNotInPharmacy/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
        		var meds = data.responseJSON;
        		
        		var medications = data.responseJSON;
        		
            	if(medications == null){
		           
           		}else{
	            	for(var i = 0; i < medications.length; i++){
		            	$("#availableMedications").append($("<option>", {
		            		id: medications[i].id,
		            		value: medications[i].name,
		            		text: medications[i].name
		            	}));
	            	}
            	}
            //	removeDuplicateOptionTags();
            	checkFields(pharmacyId);
        	}else{
        		alert("Something went wrong!");
        	}
       	}
	});
}

function checkFields(pharmacyId){
	$("#add").click(function(event){
		event.preventDefault();
		
		if($("#availableMedications").val() == "")
			$("#availableMedications").css("background-color","red");
			
		if($("#medPrice").val() == "")
			$("#medPrice").css("background-color","red");
			
		if($("#medAmount").val() == "")
			$("#medAmount").css("background-color","red");
			
		if($("#availableMedications").val() != "" && $("#medPrice").val() != "" && $("#medAmount").val() != ""){
			var id = $("#availableMedications").find('option:selected').attr('id');
			addMedicationToPharmacy(id, pharmacyId);
		}	
	});
}

function addMedicationToPharmacy(medId, pharmacyId){
	var data = {
		"medication": {
		 	"id": medId
		 },
		"price": $("#medPrice").val(),
		"pharmacy": {
			"id": pharmacyId
		},
		"amount": $("#medAmount").val()
	}
	
	var transformedData = JSON.stringify(data);
	$.ajax({
        url: 'http://localhost:8080/addMedicationToPharmacy',
        type: 'POST',
        data: transformedData,
        async: false,
        contentType: 'application/json',
        dataType: 'json',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function(data) {
           if (data.status == 200){
            	alert("Success!");
            	window.location.href = "pharmacyMedications.html";
           }else
                alert("Something went wrong.");
        }
    });
}

function getPharmacyMedicationsAdmin(pharmacyId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyMedications/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var meds = data.responseJSON;
			
            var medsTable = $("#table tbody");
            medsTable.empty();
            
            for(var i = 0; i < meds.length; i++){
            	medsTable.append("<tr id='" + meds[i].id + "'><td>" + meds[i].name +   
                "</td><td>" + meds[i].code +
                "</td><td>" + meds[i].dailyIntake +
                "</td><td>" + meds[i].sideEffects +
                "</td><td>" + meds[i].price +
                "</td><td>" + meds[i].amount +
                "</td></tr>");
                
                $("#table").append(medsTable);
                chooseMedication(meds[i].id);
                 getMedicationPriceAndAmount(pharmacyId, meds[i]);
            }
            searchMedicationsAdmin(meds, pharmacyId);
        }
    });
}

function searchMedicationsAdmin(meds, pharmacyId){
	$("#search").on("input", function(){
		if($("#search").val() != ""){
			
			var medsTable = $("#table tbody");
		    medsTable.empty();
	            
			for(var i = 0; i < meds.length; i++){
				if(meds[i].name.toLowerCase().includes($("#search").val().toLowerCase()) ||
					meds[i].code.toLowerCase().includes($("#search").val().toLowerCase())){
					 medsTable.append("<tr><td>" +  meds[i].name +
	                "</td><td>" + meds[i].code +
	                "</td><td>" + meds[i].dailyIntake +
	                "</td><td>" + meds[i].sideEffects +
	                "</td><td>" + meds[i].price +
	                 "</td><td>" + meds[i].amount +
	                "</td></tr>");
	
	                $("#table").append(medsTable);
	                 getMedicationPriceAndAmount(pharmacyId, meds[i]);
				}
			}
		}else{
			getPharmacyMedicationsAdmin(pharmacyId);
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

function searchMedicationsPatient(meds, pharmacyId){
	$("#search").on("input", function(){
		if($("#search").val() != ""){
			
			var medsTable = $("#table tbody");
		    medsTable.empty();
	            
			for(var i = 0; i < meds.length; i++){
				if(meds[i].name.toLowerCase().includes($("#search").val().toLowerCase()) ||
					meds[i].code.toLowerCase().includes($("#search").val().toLowerCase())){
					 medsTable.append("<tr><td>" +  meds[i].name +
	                "</td><td>" + meds[i].code +
	                "</td><td>" + meds[i].dailyIntake +
	                "</td><td>" + meds[i].sideEffects +
	                "</td><td>" + meds[i].price +
	                "</td><td>" + meds[i].amount +
	                "</td></tr>");
	
	                $("#table").append(medsTable);
	                getMedicationPriceAndAmount(pharmacyId, meds[i]);
				}
			}
		}else{
			getPharmacyMedicationsPatient();
		}
	});
}

function chooseMedication(medId){
	$("#" + medId).click(function(event){
		event.preventDefault();
		window.location.href = "editMedication.html?medId=" + medId;
	});
}

function removeDuplicateOptionTags(){
	var a = new Array();
	$("#availableMedications").children("option").each(function(x){
		test = false;
		b = a[x] = $(this).val().toLowerCase();
		for (i=0;i<a.length-1;i++)
			if (b ==a[i]) 
				test =true;		
			if (test) 
				$(this).remove();
	});
}

function getMedicationPriceAndAmount(pharmacyId, medication){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/findAllMedsByPharmacyId/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	var meds = data.responseJSON;
  
	        for(var i = 0; i < meds.length; i++){
	        	if(meds[i].medication.id == medication.id){
	        		$("#" + medication.id + " td:nth-child(5)").text(meds[i].price);
	        		$("#" + medication.id + " td:nth-child(6)").text(meds[i].amount);
	        	}
	        }
       	}
	});
}


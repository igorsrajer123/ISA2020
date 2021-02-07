$(window).on("load", function(){
	getCurrentUser();
	$("#noMedFound").hide();
	$("#table").hide();
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
            	getAllMedicationsInPharmacies();
           	}
        }
    });
}

function getAllMedicationsInPharmacies(){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAllMedicationsInPharmacies',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var meds = data.responseJSON;
            searchMedications(meds);
        }
    });
}

function searchMedications(meds){
	$("#searchBtn").click(function(event){
		event.preventDefault();
		
		var medsTable = $("#table tbody");
        medsTable.empty();
            
        for(var i = 0; i < meds.length; i++){
        	if($("#search").val().toLowerCase() == meds[i].medication.name.toLowerCase()){
	            medsTable.append("<tr id='" + meds[i].id + "'><td>" + meds[i].medication.name +   
	            "</td><td>" + meds[i].medication.medicationType.name +
	            "</td><td>" + meds[i].pharmacy.name +
	            "</td><td>" + meds[i].price +
	            "</td><td><button id='" + meds[i].id + meds[i].id + "'>Make Reservation!</button>" + 
	            "</td></tr>");
	                
				$("#table").append(medsTable);
				makeReservation(meds[i].id);
	        }  
		}
		checkRowCount();	
	});
}

function checkRowCount(){
	
	var rowCount = $('#table tr').length;
	if(rowCount == 1){
		$("#table").hide();
		$("#noMedFound").show();
	}
	else{
		$("#table").show();
		$("#noMedFound").hide();
	}
}

function makeReservation(medId){
	$("#" + medId + medId).click(function(event){
		event.preventDefault();
		window.location.href = "submitMedicationReservation.html?medicationInPharmacyId=" + medId;
	});
}
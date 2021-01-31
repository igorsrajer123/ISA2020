$(window).on('load', function(){
	$("#modal").modal('show');
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
            if(data.responseJSON == undefined || data.responseJSON.type != "ROLE_PATIENT"){
                alert("You cannot access this page!");
                window.location.href = "../index.html";
            }else {
           		getPatientAllergicMedications(data.responseJSON.id);
           	}
        }
    });
}

function getPatientAllergicMedications(patientId){
	$.ajax({
        url: 'http://localhost:8080/getPatientAllergicMedications/' + patientId,
        type: 'GET',
        headers: {
           Authorization: 'Bearer ' + $.cookie('token')
        },
        complete: function(data){
            var medications = data.responseJSON;
            
            if(medications == null){
	            $("#select").append($("<option>", {
	                value: none,
	                text: "none"
            	}));
            }else{
            	for(var i = 0; i < medications.length; i++){
	            	$("#select").append($("<option>", {
	            		value: medications[i].name,
	            		text: medications[i].name
	            	}));
            	}
            }
            showAllMedications(medications, patientId);
        }
    });
}

function showAllMedications(patientAllergicMedications, patientId){
	$.ajax({
        url: 'http://localhost:8080/getAllMedications',
        type: 'GET',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function(data){
            var allMedications = data.responseJSON;
	
            var medsTable = $("#table tbody");
            medsTable.empty();

            for(var i = 0; i < allMedications.length; i++){
            	medsTable.append("<tr><td><input type='checkbox' id='" + allMedications[i].name + "'/>" +   
                "</td><td>" + allMedications[i].name +
                "</td></tr>");

                $("#table").append(medsTable);
                addPatientAllergicMedication(patientId, allMedications[i]);
            }
            
            for(var i = 0; i < allMedications.length; i++){
            	for(var j = 0; j < patientAllergicMedications.length; j++){
            		if(patientAllergicMedications[j].name == allMedications[i].name){
            			$("#" + allMedications[i].name).prop('checked', true);
            		}
            	}
            }
            
            searchMedications(allMedications, patientAllergicMedications, patientId);
        }
    });
}

function searchMedications(allMedications, patientAllergicMedications, patientId){
	$("#search").on("input", function(e){
		if($("#search").val() != ""){
			
			var medsTable = $("#table tbody");
		    medsTable.empty();
	            
			for(var i = 0; i < allMedications.length; i++){
				if(allMedications[i].name.toLowerCase().includes($("#search").val().toLowerCase())){
					 medsTable.append("<tr><td><input type='checkbox' id='" + allMedications[i].name + "'/>" +   
	                "</td><td>" + allMedications[i].name +
	                "</td></tr>");
	
	                $("#table").append(medsTable);
	                addPatientAllergicMedication(patientId, allMedications[i]);
				}
			}
			
			for(var i = 0; i < allMedications.length; i++){
            	for(var j = 0; j < patientAllergicMedications.length; j++){
            		if(patientAllergicMedications[j].name == allMedications[i].name){
            			$("#" + allMedications[i].name).prop('checked', true);
            		}
            	}
            }
		}else{
			showAllMedications(patientAllergicMedications, patientId);
		}
	});
}

function addPatientAllergicMedication(patientId, medication){
	$("#" + medication.name).change(function() {
    
    if(this.checked) {
    	var data = {
    		"id": medication.id,
    		"name": medication.name,
    		"price": medication.price
    	}
    	
    	var transformedData = JSON.stringify(data);
    	
        $.ajax({
	        url: 'http://localhost:8080/addPatientAllergicMedication/' + patientId,
	        type: 'POST',
	        data: transformedData,
        	contentType: 'application/json',
        	dataType: 'json',
	        headers: {
	           Authorization: 'Bearer ' + $.cookie('token')
	        },
	        complete: function(data){
	        	if(data.status == 200)
	        		window.location.href = "patientAllergies.html";
	        }
       });
    }
});
}
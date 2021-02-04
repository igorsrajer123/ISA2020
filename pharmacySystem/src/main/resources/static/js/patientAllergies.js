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
	            		id: medications[i].id,
	            		value: medications[i].name.toLowerCase(),
	            		text: medications[i].name
	            	}));
	            	removeDuplicateOptionTags();
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
            	medsTable.append("<tr><td><input type='checkbox' id='" + allMedications[i].id + "'/>" +   
                "</td><td>" + allMedications[i].name +
                "</td></tr>");

                $("#table").append(medsTable);
                removePatientAllergicMedication(patientId, allMedications[i]);                            
                addPatientAllergicMedication(patientId, allMedications[i]);         
            }
                        
            for(var i = 0; i < allMedications.length; i++){
            	for(var j = 0; j < patientAllergicMedications.length; j++){
            		if(patientAllergicMedications[j].name.toLowerCase() == allMedications[i].name.toLowerCase() &&
            			patientAllergicMedications[j].id == allMedications[i].id){
            				$("#" + allMedications[i].id).prop('checked', true);
            		}
            	}
            }           
            searchMedications(allMedications, patientAllergicMedications, patientId);
            removeDuplicateRows();
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
					 medsTable.append("<tr><td><input type='checkbox' id='" + allMedications[i].id + "'/>" +   
	                "</td><td>" + allMedications[i].name +
	                "</td></tr>");
	
	                $("#table").append(medsTable);
	                addPatientAllergicMedication(patientId, allMedications[i]);
	                removePatientAllergicMedication(patientId, allMedications[i]);
				}
			}
			
			for(var i = 0; i < allMedications.length; i++){
            	for(var j = 0; j < patientAllergicMedications.length; j++){
            		if(patientAllergicMedications[j].name.toLowerCase() == allMedications[i].name.toLowerCase() &&
            			patientAllergicMedications[j].id == allMedications[i].id){
            			$("#" + allMedications[i].id).prop('checked', true);
            		}
            	}
            }
		}else{
			showAllMedications(patientAllergicMedications, patientId);
		}
	});
}

function addPatientAllergicMedication(patientId, medication){
	$("#" + medication.id).change(function() {
    
    if(this.checked) {
    	var data = {
    		"id": medication.id,
    		"name": medication.name
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

function removePatientAllergicMedication(patientId, medication){
	$("#" + medication.id).change(function() {
	
	if(!this.checked) {
    	var data = {
    		"id": medication.id,
    		"name": medication.name,
    	}
    	
    	var transformedData = JSON.stringify(data);
    	
        $.ajax({
	        url: 'http://localhost:8080/removePatientAllergicMedication/' + patientId,
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

function removeDuplicateRows(){
	$tableRows = $("tr");
	$tableRows.each(function(index, element) {
  		var $element = $(element);
  		var currentRowValue = $element.find("td:nth-child(2)").text();
		
	  	for (var i = index + 1; i < $tableRows.length; i++) {
	    var $rowToCompare = $($tableRows[i]);
	    var valueToCompare = $rowToCompare.find("td:nth-child(2)").text();
	
	    if(valueToCompare === currentRowValue) {
	      var duplicateRowFourthColumnVal = $rowToCompare.find("td:nth-child(4)").text();
	      if(duplicateRowFourthColumnVal == "HIT") {
	        $element.remove();
	      }
	      else {
	        $rowToCompare.remove();
	      }
	    }
	  }
});
}

function removeDuplicateOptionTags(){
	var a = new Array();
	$("#select").children("option").each(function(x){
		test = false;
		b = a[x] = $(this).val();
		for (i=0;i<a.length-1;i++)
			if (b ==a[i]) 
				test =true;		
			if (test) 
				$(this).remove();
	});
}

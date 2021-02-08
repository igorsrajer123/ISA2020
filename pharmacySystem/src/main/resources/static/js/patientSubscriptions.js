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
            if(data.responseJSON == undefined || data.responseJSON.type != "ROLE_PATIENT"){
                alert("You cannot access this page!");
                window.location.href = "../index.html";
            }else {
           		getPatientByUserId(data.responseJSON.id);
           	}
        }
    });
}

function getPatientByUserId(userId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPatientByUserId/' + userId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {                
                getAllPharmacies(data.responseJSON.id);
        }
	});
}

function getAllPharmacies(patientId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAllPharmacies',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var allPharmacies = data.responseJSON;
			
            var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
                    
            for(var i = 0; i < allPharmacies.length; i++){
            	pharmaciesTable.append("<tr><td><input type='checkbox' id='" + allPharmacies[i].id + "'/>" +   
                "</td><td>" + allPharmacies[i].name + "(" + allPharmacies[i].city + ")" +
                "</td></tr>");	
				
                $("#table").append(pharmaciesTable);
               	getPatientSubscriptions(patientId, allPharmacies[i]);
            }   	
        }
    });
}

function getPatientSubscriptions(patientId, pharmacy){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPatientSubscriptions/' + patientId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {                
        
        	var subscriptions = data.responseJSON;
    	
        	for(var i = 0; i < subscriptions.length; i++)      		
        		if(subscriptions[i].id == pharmacy.id)
        			$("#" + pharmacy.id).prop('checked', true);   	
        	
        	subscribeToPharmacy(patientId, pharmacy.id);
        	unsubscribeFromPharmacy(patientId, pharmacy.id);
        }
	});
}

function subscribeToPharmacy(patientId, pharmacyId){
	$("#" + pharmacyId).change(function(){
		if(this.checked){
			$.ajax({
		 		url: 'http://localhost:8080/subscribeToPharmacy/' + patientId + '/' + pharmacyId,
		 		type: 'POST',
		 		contentType: 'application/json',
		  		dataType: 'json',
		    	headers: {
					Authorization: 'Bearer ' + $.cookie('token')
				},
		 		complete: function (data) {
			   		if(data.status == 200){
				 		alert("You have successfully subscribed to this pharmacy!");
				  		location.reload();
				  	}else{
						alert("Error!");
					}
				}
			});	
		}
	});
}

function unsubscribeFromPharmacy(patientId, pharmacyId){
	$("#" + pharmacyId).change(function(){
		if(!this.checked){
			$.ajax({
		 		url: 'http://localhost:8080/unsubscribeFromPharmacy/' + patientId + '/' + pharmacyId,
		 		type: 'POST',
		 		contentType: 'application/json',
		  		dataType: 'json',
		    	headers: {
					Authorization: 'Bearer ' + $.cookie('token')
				},
		 		complete: function (data) {
			   		if(data.status == 200){
				 		alert("You have successfully cancelled your subscription!");
				  		location.reload();
				  	}else{
						alert("Error!");
					}
				}
			});	
		}
	});
}

$(window).on('load', function(){
	getCurrentUser();
	
	$(".sideBar").mouseover(function(){
    	$(".sideBar").width(290);
    	$("#icon").hide();
    	$(".nav-link").show();
    	$(".lines").show();
    });
    
    $(".sideBar").mouseout(function(){
    	$(".sideBar").width(65);
    	$("#icon").show();
    	$(".nav-link").hide();
    	$(".lines").hide();
    });
    
    var pharmacyId = getUrlVars()["pharmacyId"];
    pharmacyMeds(pharmacyId);
    pharmacyDermatologists(pharmacyId);
    pharmacyPharmacists(pharmacyId);
    pharmacyExaminations(pharmacyId);
    getPharmacyPromotions(pharmacyId);
});

function getUrlVars() {
    var vars = {};
    window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}

function getCurrentUser(){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getUser',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            if(data.responseJSON != undefined && data.responseJSON.type == "ROLE_PATIENT"){
                getPharmacy(data.responseJSON.id);
            }else {
            	alert("You cannot access this page!");
                window.location.href = "../index.html";
           	}
        }
    });
}

function getPatientFromUserId(userId, pharmacyId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPatientByUserId/' + userId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            var patient = data.responseJSON;
           	getPatientSubscriptions(patient.id, pharmacyId);
        }
    });
}

function getPharmacy(userId){
	var pharmacyId = getUrlVars()["pharmacyId"];
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacy/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
        		$("#name").text(data.responseJSON.name);
        		$("#city").text(data.responseJSON.city);
        		$("#address").text(data.responseJSON.address);
        		$("#rating").text(data.responseJSON.rating);
        		getPatientFromUserId(userId, pharmacyId);
        	}else{
        		alert("Something went wrong!");
        		window.location.href = "index.html"
        	}
       	}
	});
}

function pharmacyMeds(pharmacyId){
	$("#pharmacyMeds").click(function(event){
		event.preventDefault();
		window.location.href="pharmacyMedications.html?pharmacyId=" + pharmacyId;
	});
}

function pharmacyDermatologists(pharmacyId){
	$("#pharmacyDerms").click(function(event){
		event.preventDefault();
		window.location.href="pharmacyDermatologists.html?pharmacyId=" + pharmacyId;
	});
}

function pharmacyPharmacists(pharmacyId){
	$("#pharmacyPharmacists").click(function(event){
		event.preventDefault();
		window.location.href="pharmacyPharmacists.html?pharmacyId=" + pharmacyId;
	});
}

function pharmacyExaminations(pharmacyId){
	$("#availableExaminations").click(function(event){
		event.preventDefault();
		window.location.href="pharmacyAvailableExaminations.html?pharmacyId=" + pharmacyId;
	});
}

function getPatientSubscriptions(patientId, pharmacyId){
	$("#subscribe").click(function(event){
		event.preventDefault();
		
		$.ajax({
	        method: 'GET',
	        url: 'http://localhost:8080/getPatientSubscriptions/' + patientId,
	        headers: {
	   			Authorization: 'Bearer ' + $.cookie('token')
			},
	        complete: function (data) {
	        	var subscriptions = data.responseJSON;
	        	var subscribed = false;
	        	for(var i = 0; i < subscriptions.length; i++){
	        		if(subscriptions[i].id == pharmacyId){
						subscribed = true;
	        		}
	        	}
	        	
	        	if(subscribed)
	        		alert("You are already subscribed to this pharmacy!");
	        	
	        	if(!subscribed){
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
	        }
        });
		
	});
}

function getPharmacyPromotions(pharmacyId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyPromotions/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
        		var promotions = data.responseJSON;
        		$("#promotions").empty();
        		
        		for(var i = 0; i < promotions.length; i++){
        			$("#promotions").append("<p style='color:red;'>*" + promotions[i].text + "</p><br/><br/>");
        		}
        	}else{
        		alert("Something went wrong!");
        		window.location.href = "index.html"
        	}
       	}
	});
}


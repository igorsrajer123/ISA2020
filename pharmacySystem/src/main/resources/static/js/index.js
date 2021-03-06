$(window).on('load', function(){
    getCurrentUser();
});

function welcomeMessage(user){
    if (user == undefined) {
        $("#currentUser").hide();
        $("#user").hide();
    }else {
        $("#navBar").append("<label id='currentUser'>Current user: </label><label id='user'></label>");
        $("#currentUser").show();
        $("#user").text(user.email);
    }
}

function userOptions(user){
    if(user == undefined){
    	showUnidentifiedUserOptions();
    	
    	$(".sideBar").mouseover(function(){
    		$(".sideBar").width(200);
    		$("#icon").hide();
	    	$("#allMeds").show();
	    	$("#allPharmacies").show();
    	});
    
    	$(".sideBar").mouseout(function(){
    		$(".sideBar").width(65);
    		$("#icon").show();
	    	$(".nav-item").hide();
    	});
    	
    }else if(user.type == "ROLE_PATIENT"){
    	showPatientUserOptions();
    	
    	$(".sideBar").mouseover(function(){
    		$(".sideBar").width(300);
    		$("#icon").hide();
	    	$("#allMeds").show();
	    	$("#allPharmacies").show();
	    	$("#examination").show();
	    	$("#counseling").show();
	    	$("#reservedMeds").show();
	    	$("#acquireMed").show();
	    	$("#counselingHistory").show();
	    	$("#examinationHistory").show();
	    	$("#activeExaminations").show();
	    	$("#activeCounselings").show();
	    	$("#complaint").show();
	    	$("#penalties").show();
	    	$("#myPharmacies").show();
	    	$("#myPharmacy").hide();
	    	$("#reserveMed").show();
	    	$("#createPromotion").hide();
	    	$("#createOrderList").hide();
	    	$("#viewOrders").hide();
	    	$("#holidays").hide();
	    	$("#searchDermatologists").show();
	    	$("#searchPharmacists").show();
    	});
    
    	$(".sideBar").mouseout(function(){
    		$(".sideBar").width(65);
    		$("#icon").show();
    		$(".nav-item").hide();
    	});
    }else if(user.type == "ROLE_PHARMACY_SYSTEM_ADMIN"){
    	showSystemAdminUserOptions();
    	
    	$(".sideBar").mouseover(function(){
    		$(".sideBar").width(230);
    		$("#icon").hide();
	    	$("#allMeds").show();
	    	$("#allPharmacies").show();
	    	$("#examination").hide();
	    	$("#counseling").hide();
	    	$("#reservedMeds").hide();
	    	$("#acquireMed").hide();
	    	$("#counselingHistory").hide();
	    	$("#examinationHistory").hide();
	    	$("#activeExaminations").hide();
	    	$("#activeCounselings").hide();
	    	$("#complaint").hide();
	    	$("#penalties").hide();
	    	$("#myPharmacies").hide();
	    	$("#complaints").show();
	    	$("#loyalty").show();
	    	$("#myPharmacy").hide();
	    	$("#createPromotion").hide();
	    	$("#createOrderList").hide();
	    	$("#viewOrders").hide();
	    	$("#holidays").show();
	    	$("#searchDermatologists").hide();
	    	$("#searchPharmacists").hide();
    	});
    
    	$(".sideBar").mouseout(function(){
    		$(".sideBar").width(65);
    		$("#icon").show();
    		$(".nav-item").hide();
    	});
    	
    }else if(user.type == "ROLE_PHARMACY_ADMIN"){
    	showPharmacyAdminUserOptions();
    	
    	$(".sideBar").mouseover(function(){
    		$(".sideBar").width(230);
    		$("#icon").hide();
	    	$("#allMeds").hide();
	    	$("#allPharmacies").hide();
	    	$("#examination").hide();
	    	$("#counseling").hide();
	    	$("#reservedMeds").hide();
	    	$("#acquireMed").hide();
	    	$("#counselingHistory").hide();
	    	$("#examinationHistory").hide();
	    	$("#activeExaminations").hide();
	    	$("#activeCounselings").hide();
	    	$("#complaint").hide();
	    	$("#penalties").hide();
	    	$("#myPharmacies").hide();
	    	$("#complaints").hide();
	    	$("#loyalty").hide();
	    	$("#myPharmacy").show();
	    	$("#createPromotion").show();
	    	$("#createOrderList").show();
	    	$("#viewOrders").show();
	    	$("#holidays").show();
	    	$("#searchDermatologists").show();
	    	$("#searchPharmacists").show();
    	});
    
    	$(".sideBar").mouseout(function(){
    		$(".sideBar").width(65);
    		$("#icon").show();
    		$(".nav-item").hide();
    	});
    	
    }else if(user.type == "ROLE_DERMATOLOGIST"){
    	showDermatologistUserOptions();
    	
    }else if(user.type == "ROLE_SUPPLIER"){
    	showSupplierUserOptions();
    }else if(user.type == "ROLE_PHARMACIST"){
    	showPharmacistUserOptions();
    }
}

function showUnidentifiedUserOptions(){
    $("#login").show();
    $("#register").show();
    $("#logout").hide();
    $("#patientProfile").hide();
    $("#changePassword").hide();
    $("#systemAdminProfile").hide();
    $("#pharmacyAdminProfile").hide();
    $(".sideBar").show();
}

function showPatientUserOptions(){
    $("#login").hide();
    $("#register").hide();
    $("#logout").show();
    $("#patientProfile").show();
    $("#changePassword").hide();
    $("#systemAdminProfile").hide();
    $("#pharmacyAdminProfile").hide();
    $(".sideBar").show();
}

function showSystemAdminUserOptions(){
	$("#login").hide();
    $("#register").hide();
    $("#logout").show();
    $("#patientProfile").hide();
    $("#changePassword").show();
    $("#systemAdminProfile").show();
    $("#pharmacyAdminProfile").hide();
    $(".sideBar").show();
}

function showPharmacyAdminUserOptions(){
	$("#login").hide();
    $("#register").hide();
    $("#logout").show();
    $("#patientProfile").hide();
    $("#changePassword").show();
    $("#systemAdminProfile").hide();
    $("#pharmacyAdminProfile").show();
    $(".sideBar").show();
}

function showDermatologistUserOptions(){
	$("#login").hide();
    $("#register").hide();
    $("#logout").show();
    $("#patientProfile").hide();
    $("#changePassword").show();
    $("#systemAdminProfile").hide();
    $("#pharmacyAdminProfile").hide();
    $(".sideBar").hide();
}


function showSupplierUserOptions(){
	$("#login").hide();
    $("#register").hide();
    $("#logout").show();
    $("#patientProfile").hide();
    $("#changePassword").show();
    $("#systemAdminProfile").hide();
    $("#pharmacyAdminProfile").hide();
    $(".sideBar").hide();
}

function showPharmacistUserOptions(){
	$("#login").hide();
    $("#register").hide();
    $("#logout").show();
    $("#patientProfile").hide();
    $("#changePassword").show();
    $("#systemAdminProfile").hide();
    $("#pharmacyAdminProfile").hide();
    $(".sideBar").hide();
}


function getCurrentUser(){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getUser',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            welcomeMessage(data.responseJSON);
            userOptions(data.responseJSON);
            getPatientByUserId(data.responseJSON.id);
            if(data.responseJSON != undefined && data.responseJSON.type != "ROLE_PATIENT"){
            	if(data.responseJSON.firstLogin){         	          
            		$("#modal").modal({
            			escapeClose: false,
						clickClose: false,
						showClose: false
            		});
            		
            		setTimeout(function(){
					    window.location.href = "changePassword.html";
					}, 1500);
            	}
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
               patientPenaltiesOptions(data.responseJSON);
        }
	});
}

function patientPenaltiesOptions(patient){
	if(patient.penalties >= 3){
		$("#reserveMed a").removeAttr("href");
		$("#reserveMed a").css("color", "red");
		
		$("#counseling a").removeAttr("href");
		$("#counseling a").css("color", "red");
		
		$("#examination a").removeAttr("href");
		$("#examination a").css("color", "red");
	}
}

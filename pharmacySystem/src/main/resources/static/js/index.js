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
    		$(".sideBar").width(130);
    		$("#icon").hide();
	    	$("#allMeds").show();
	    	$("#allPharmacies").show();
    	});
    
    	$(".sideBar").mouseout(function(){
    		$(".sideBar").width(65);
    		$("#icon").show();
	    	$("#allMeds").hide();
	    	$("#allPharmacies").hide();
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
    	});
    
    	$(".sideBar").mouseout(function(){
    		$(".sideBar").width(65);
    		$("#icon").show();
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
    	});
    }else if(user.type == "ROLE_PHARMACY_SYSTEM_ADMIN"){
    	showSystemAdminUserOptions();
    	
    }else if(user.type == "ROLE_PHARMACY_ADMIN"){
    	showPharmacyAdminUserOptions();
    	
    }else if(user.type == "ROLE_DERMATOLOGIST"){
    	showDermatologistUserOptions();
    	
    }else if(user.type == "ROLE_SUPPLIER"){
    	showSupplierUserOptions();
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
}

function showPatientUserOptions(){
    $("#login").hide();
    $("#register").hide();
    $("#logout").show();
    $("#patientProfile").show();
    $("#changePassword").hide();
    $("#systemAdminProfile").hide();
    $("#pharmacyAdminProfile").hide();
}

function showSystemAdminUserOptions(){
	$("#login").hide();
    $("#register").hide();
    $("#logout").show();
    $("#patientProfile").hide();
    $("#changePassword").show();
    $("#systemAdminProfile").show();
    $("#pharmacyAdminProfile").hide();
}

function showPharmacyAdminUserOptions(){
	$("#login").hide();
    $("#register").hide();
    $("#logout").show();
    $("#patientProfile").hide();
    $("#changePassword").show();
    $("#systemAdminProfile").hide();
    $("#pharmacyAdminProfile").show();
}

function showDermatologistUserOptions(){
	$("#login").hide();
    $("#register").hide();
    $("#logout").show();
    $("#patientProfile").hide();
    $("#changePassword").show();
    $("#systemAdminProfile").hide();
    $("#pharmacyAdminProfile").hide();
}


function showSupplierUserOptions(){
	$("#login").hide();
    $("#register").hide();
    $("#logout").show();
    $("#patientProfile").hide();
    $("#changePassword").show();
    $("#systemAdminProfile").hide();
    $("#pharmacyAdminProfile").hide();
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
            if(data.responseJSON != undefined && data.responseJSON.type != "ROLE_PATIENT"){
            	if(data.responseJSON.firstLogin){
            		$("#modal").modal('show');
            		setTimeout(function(){
					    window.location.href = "changePassword.html";
					}, 1500);
            	}
           	}
        }
    });
}

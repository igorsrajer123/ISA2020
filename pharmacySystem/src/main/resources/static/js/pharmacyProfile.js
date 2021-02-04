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
                getPharmacy();
            }else {
            	alert("You cannot access this page!");
                window.location.href = "../index.html";
           	}
        }
    });
}

function getPharmacy(){
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


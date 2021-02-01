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
    
    pharmacyMeds();
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
            if(data.responseJSON == undefined || data.responseJSON.type == "ROLE_PATIENT" ||
            		data.responseJSON.type == "ROLE_PHARMACY_SYSTEM_ADMIN"){
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

function pharmacyMeds(){
	$("#pharmacyMeds").click(function(event){
		event.preventDefault();
		window.location.href="pharmacyMedications.html";
	});
}

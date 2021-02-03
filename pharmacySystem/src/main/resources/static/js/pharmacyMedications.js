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
            	getPharmacyMedications();
           	}
        }
    });
}

function getPharmacyMedications(){
	var pharmacyId = getUrlVars()["pharmacyId"];
	alert(pharmacyId);
	
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
                "</td></tr>");
                
                $("#table").append(medsTable);
            }
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
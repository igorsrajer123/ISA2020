$(window).on("load", function(){
	getCurrentUser();
	$("#datepicker").datepicker({minDate:0});
	$("#datepicker").datepicker({ beforeShowDay: $.datepicker.noWeekends })
	$("#table").hide();
	$("#cancel").hide();
	$("#notify").hide();
	
	for(var i = 8; i < 17; i++)
		$("#time").append($("<option>", {
			value: i,
			text: i + ":00h"
	}));
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
            	checkFields();
            	cancelSearch();
           	}
        }
    });
}

function checkFields(){
	$("#submit").click(function(event){
		event.preventDefault();
		
		if($("#datepicker").val() == "")
			$("#datepicker").css("background-color", "red");
		
		if($("#time").val() == "")
			$("#time").css("background-color", "red");
		
		if($("#datepicker").val() != "" && $("#time").val() != ""){	
			$("#datepicker").css("background-color", "white");
			$("#time").css("background-color", "white");
			searchPharmacies();
		}
	});
}

function searchPharmacies(){
	var ourDate = $("#datepicker").val();
	var parts = ourDate.split('/');
	var sendingDate = parts[2] + "-" + parts[0] + "-" + parts[1];
	
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmaciesWithAvailablePharmacists/' + $("#time").val() + '/' + sendingDate,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            var pharmacies = data.responseJSON;
           
            if(pharmacies.length > 0){
            	$("#chooseDate").hide();
            	$("#table").show();
            	$("#cancel").show();
            	$("#notify").show();
            	
            	var pharmaciesTable = $("#table tbody");
            	pharmaciesTable.empty();
                    
            	for(var i = 0; i < pharmacies.length; i++){
	            	pharmaciesTable.append("<tr id='" + pharmacies[i].id + "'><td>" + pharmacies[i].name +   
	                "</td><td>" + pharmacies[i].address +
	                "</td><td>" + pharmacies[i].city +
	                "</td><td>" + pharmacies[i].rating +
	                 "</td><td>" + pharmacies[i].counselingPrice +
	                "</td></tr>");
				
	                $("#table").append(pharmaciesTable);
	                choosePharmacyForCounseling(pharmacies[i].id);
           		}
           		sortByCounselingPrice(pharmacies);
           		sortByRating(pharmacies);
            }else{
            	$("#chooseDate").show();
            	$("#table").hide();
            	$("#cancel").hide();
            	$("#notify").hide();
            	alert("There are no free pharmacists for your desired time!");
            }
        }
    });
}

function cancelSearch(){
	$("#cancel").click(function(event){
		event.preventDefault();
		
		$("#table").hide();
		$("#cancel").hide();
		$("#chooseDate").show();
		$("#notify").hide();
	});
}

function choosePharmacyForCounseling(pharmacyId){
	var ourDate = $("#datepicker").val();
	var parts = ourDate.split('/');
	var sendingDate = parts[2] + "-" + parts[0] + "-" + parts[1];
	
	$("#" + pharmacyId).click(function(event){
		event.preventDefault();
		window.location.href = "freePharmacists.html?pharmacyId=" + pharmacyId + "&time=" + $("#time").val() + "&date=" + sendingDate;
	});
}

function sortByRating(){

}

function sortByRating(allPharmacies){
	var rising = false;
	$("#pharmacyRating").click(function(){
		if(rising){
			var mojNiz = allPharmacies.sort(function(a, b){
							return a.rating - b.rating;
						});
			
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
                    
            for(var i = 0; i < mojNiz.length; i++){
	            pharmaciesTable.append("<tr id='" + mojNiz[i].id + "'><td>" + mojNiz[i].name +   
	            "</td><td>" + mojNiz[i].address +
	            "</td><td>" + mojNiz[i].city +
	            "</td><td>" + mojNiz[i].rating +
	            "</td><td>" + mojNiz[i].counselingPrice +
	            "</td></tr>");
				
	            $("#table").append(pharmaciesTable);
	            choosePharmacyForCounseling(mojNiz[i].id);
			}
			rising = false;
		}else{
			var mojNiz = allPharmacies.sort(function(a, b){
							return b.rating - a.rating;
							});
							
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
                    
            for(var i = 0; i < mojNiz.length; i++){
	            pharmaciesTable.append("<tr id='" + mojNiz[i].id + "'><td>" + mojNiz[i].name +   
	            "</td><td>" + mojNiz[i].address +
	            "</td><td>" + mojNiz[i].city +
	            "</td><td>" + mojNiz[i].rating +
	            "</td><td>" + mojNiz[i].counselingPrice +
	            "</td></tr>");
				
	            $("#table").append(pharmaciesTable);
	            choosePharmacyForCounseling(mojNiz[i].id);
			}
			rising = true;
		}			
	});
}

function sortByCounselingPrice(allPharmacies){
	var rising = false;
	$("#pharmacyCounselingPrice").click(function(){
		if(rising){
			var mojNiz = allPharmacies.sort(function(a, b){
							return a.counselingPrice - b.counselingPrice;
						});
			
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
                    
            for(var i = 0; i < mojNiz.length; i++){
	            pharmaciesTable.append("<tr id='" + mojNiz[i].id + "'><td>" + mojNiz[i].name +   
	            "</td><td>" + mojNiz[i].address +
	            "</td><td>" + mojNiz[i].city +
	            "</td><td>" + mojNiz[i].rating +
	            "</td><td>" + mojNiz[i].counselingPrice +
	            "</td></tr>");
				
	            $("#table").append(pharmaciesTable);
	            choosePharmacyForCounseling(mojNiz[i].id);
			}
			rising = false;
		}else{
			var mojNiz = allPharmacies.sort(function(a, b){
							return b.counselingPrice - a.counselingPrice;
							});
							
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
                    
            for(var i = 0; i < mojNiz.length; i++){
	            pharmaciesTable.append("<tr id='" + mojNiz[i].id + "'><td>" + mojNiz[i].name +   
	            "</td><td>" + mojNiz[i].address +
	            "</td><td>" + mojNiz[i].city +
	            "</td><td>" + mojNiz[i].rating +
	            "</td><td>" + mojNiz[i].counselingPrice +
	            "</td></tr>");
				
	            $("#table").append(pharmaciesTable);
	            choosePharmacyForCounseling(mojNiz[i].id);
			}
			rising = true;
		}			
	});
}


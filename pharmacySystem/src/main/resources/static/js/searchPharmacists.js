var PHARMACIES = [];
var PHARMACISTS = [];

$(window).on("load", function(){
	getCurrentUser();
	$("#noPharmaFound").hide();
	$("#table").hide();
	$("#btnFilter").hide();
	
	$("#filters").hide();
	$("#btnFilter").click(function(event){
		event.preventDefault();
		
		if($("#filters").is(":hidden"))
			$("#filters").slideDown(1000);
		else if(!$("#filters").is(":hidden"))
			$("#filters").slideUp(1000);
	});
	
	getAllPharmacies();
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
            	data.responseJSON.type == "ROLE_PHARMACIST"){
                
                alert("You cannot access this page!");
                window.location.href = "../index.html";
            }else if(data.responseJSON.type == "ROLE_PATIENT"){
            	getAllPharmacists();	
           	}else if(data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
           		getAdminFromUserId(data.responseJSON.id);
           	}
        }
    });
}

function getAllPharmacists(){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAllPharmacists',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {      
            var pharmacists = data.responseJSON;
			searchPharmacists(pharmacists);
        }
    });
}

function getAdminFromUserId(userId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyAdminFromUserId/' + userId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            getPharmacy(data.responseJSON.id);
        }
    });
}

function getPharmacy(adminId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAdminsPharmacy/' + adminId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
        		getAllPharmacistsFromPharmacy(data.responseJSON.id);
        	}else{
        		alert("No pharmacy found!");
        	}
       	}
	});
} 

function getAllPharmacistsFromPharmacy(pharmacyId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyPharmacists/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
        	if(data.status == 200){
        	var pharmacists = data.responseJSON;
        	searchPharmacists(data.responseJSON);
        	}else{
        		alert("No pharmacy found!");
        	}
       	}
	});
}

function searchPharmacists(pharmacists){
	$("#searchBtn").click(function(event){
		event.preventDefault();

		var pharmacistsTable = $("#table tbody");
        pharmacistsTable.empty();
            
        for(var i = 0; i < pharmacists.length; i++){
        	if($("#search").val().toLowerCase() == pharmacists[i].user.firstName.toLowerCase() ||
        		$("#search").val().toLowerCase() == pharmacists[i].user.lastName.toLowerCase() ||
        		$("#search").val().toLowerCase() == pharmacists[i].user.firstName.toLowerCase() + " " + pharmacists[i].user.lastName.toLowerCase()){
		            pharmacistsTable.append("<tr id='" + pharmacists[i].id + "'><td>" + pharmacists[i].user.firstName +   
		            "</td><td>" + pharmacists[i].user.lastName +
		            "</td><td>" +
		            "</td><td>" + pharmacists[i].rating +
		            "</td></tr>");
		                
					$("#table").append(pharmacistsTable);
					getPharmacistPharmacy(pharmacists[i].id);
					PHARMACISTS.push(pharmacists[i]);
	        }  
		}
		checkRowCount();
		filterPharmacists(PHARMACISTS);
	});
}

function getPharmacistPharmacy(pharmacistId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacistPharmacy/' + pharmacistId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var pharmacy = data.responseJSON;
            $("#" + pharmacistId + " td:nth-child(3)").text(pharmacy.name + "(" + pharmacy.city + ")");
        }
    });
}

function getAllPharmacies(){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAllPharmacies',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var allPharmacies = data.responseJSON;
            
            for(var i = 0; i < allPharmacies.length; i++){
            	PHARMACIES.push(allPharmacies[i].name);	
            }
            
            var myPharmacies = eliminateDuplicates(PHARMACIES);
            
            for(var j = 0; j < myPharmacies.length; j++){
				$("#pharmacies").append("<input type='checkbox' class='pharmacy-list' value='" + myPharmacies[j] + "'/> <label>" + myPharmacies[j] + "</label><br/>");	
        	}
        	
        	checkOnlyOneRating();
        	checkOnlyOnePharmacy();
        }
	});
}

function eliminateDuplicates(arr) {
  var i,
      len = arr.length,
      out = [],
      obj = {};

  for (i = 0; i < len; i++) {
    obj[arr[i]] = 0;
  }
  for (i in obj) {
    out.push(i);
  }
  return out;
}

function checkRowCount(){
	
	var rowCount = $('#table tr').length;
	if(rowCount == 1){
		$("#table").hide();
		$("#noPharmaFound").show();
		$("#btnFilter").hide();
	}
	else{
		$("#table").show();
		$("#noPharmaFound").hide();
		$("#btnFilter").show();
	}
}

function filterPharmacists(pharmacists){
	$(".rating-list").on("change", function(){
		if($(".rating-list:checked").val() == undefined && $(".pharmacy-list:checked").val() == undefined){
			generateWholeTable(pharmacists);
		}
		else if($(".rating-list:checked").val() != undefined && $(".pharmacy-list:checked").val() == undefined){	
			var pharmacistsTable = $("#table tbody");
    		pharmacistsTable.empty();
			if($(".rating-list:checked").val() == "1")
				for(var i = 0; i < pharmacists.length; i++)
					if(pharmacists[i] != undefined)
						if(pharmacists[i].rating < 2.5)
							generatePartialTable(pharmacists[i], pharmacistsTable);
			if($(".rating-list:checked").val() == "2")
				for(var i = 0; i < pharmacists.length; i++)
					if(pharmacists[i] != undefined)
						if(pharmacists[i].rating >= 2.5 && pharmacists[i].rating < 3.5)
							generatePartialTable(pharmacists[i], pharmacistsTable);        				
			if($(".rating-list:checked").val() == "3")
				for(var i = 0; i < pharmacists.length; i++)
					if(pharmacists[i] != undefined)
						if(pharmacists[i].rating >= 3.5 && pharmacists[i].rating < 4.5)
							generatePartialTable(pharmacists[i], pharmacistsTable);                 							
			if($(".rating-list:checked").val() == "4")
				for(var i = 0; i < pharmacists.length; i++)
					if(pharmacists[i].rating >= 4.5 && pharmacists[i].rating < 5.0)
						generatePartialTable(pharmacists[i], pharmacistsTable);                      							
		}
		else if($(".rating-list:checked").val() == undefined && $(".pharmacy-list:checked").val() != undefined){
			var pharmacistsTable = $("#table tbody");
            pharmacistsTable.empty();
            
			for(var i = 0; i < pharmacists.length; i++)
				if($(".pharmacy-list:checked").val() == pharmacists[i].pharmacy.name)
					generatePartialTable(pharmacists[i], pharmacistsTable);    					
		}
		else if($(".rating-list:checked").val() != undefined && $(".pharmacy-list:checked").val() != undefined){
			var pharmacistsTable = $("#table tbody");
            pharmacistsTable.empty();
            
			for(var i = 0; i < pharmacists.length; i++){
				if($(".rating-list:checked").val() == "1" && $(".pharmacy-list:checked").val() == pharmacists[i].pharmacy.name)
					if(pharmacists[i].rating < 2.5)
						generatePartialTable(pharmacists[i], pharmacistsTable);    		            				
				if($(".rating-list:checked").val() == "2" && $(".pharmacy-list:checked").val() == pharmacists[i].pharmacy.name)
					if(pharmacists[i].rating >= 2.5 && pharmacists[i].rating < 3.5)
						generatePartialTable(pharmacists[i], pharmacistsTable);    		          				
				if($(".rating-list:checked").val() == "3" && $(".pharmacy-list:checked").val() == pharmacists[i].pharmacy.name)
					if(pharmacists[i].rating >= 3.5 && pharmacists[i].rating < 4.5)
						generatePartialTable(pharmacists[i], pharmacistsTable);    		       				
				if($(".rating-list:checked").val() == "4" && $(".pharmacy-list:checked").val() == pharmacists[i].pharmacy.name)
					if(pharmacists[i].rating > 4.5)
						generatePartialTable(pharmacists[i], pharmacistsTable);    		     	
			}
		}
	});
	
	$(".pharmacy-list").on("change", function(){
		if($(".pharmacy-list:checked").val() == undefined && $(".rating-list:checked").val() == undefined){
			generateWholeTable(pharmacists);
		}
		else if($(".pharmacy-list:checked").val() != undefined && $(".rating-list:checked").val() == undefined){
			var pharmacistsTable = $("#table tbody");
            pharmacistsTable.empty();   
			for(var i = 0; i < pharmacists.length; i++)
				if($(".pharmacy-list:checked").val() == pharmacists[i].pharmacy.name)
					generatePartialTable(pharmacists[i], pharmacistsTable);         			
		}
		else if($(".pharmacy-list:checked").val() != undefined && $(".rating-list:checked").val() != undefined){
			var pharmacistsTable = $("#table tbody");
            pharmacistsTable.empty();
            
			for(var i = 0; i < pharmacists.length; i++){
				if($(".rating-list:checked").val() == "1" && $(".pharmacy-list:checked").val() == pharmacists[i].pharmacy.name)
					if(pharmacists[i].rating < 2.5)
						generatePartialTable(pharmacists[i], pharmacistsTable);                  				
				if($(".rating-list:checked").val() == "2" && $(".pharmacy-list:checked").val() == pharmacists[i].pharmacy.name)
					if(pharmacists[i].rating >= 2.5 && pharmacists[i].rating < 3.5)
						generatePartialTable(pharmacists[i], pharmacistsTable);        	        				
				if($(".rating-list:checked").val() == "3" && $(".pharmacy-list:checked").val() == pharmacists[i].pharmacy.name)
					if(pharmacists[i].rating >= 3.5 && pharmacists[i].rating < 4.5)
						generatePartialTable(pharmacists[i], pharmacistsTable);        	         				
				if($(".rating-list:checked").val() == "4" && $(".pharmacy-list:checked").val() == pharmacists[i].pharmacy.name)
					if(pharmacists[i].rating > 4.5)
						generatePartialTable(pharmacists[i], pharmacistsTable);	              				
			}
		}
		else if($(".pharmacy-list:checked").val() == undefined && $(".rating-list:checked").val() != undefined){
			var pharmacistsTable = $("#table tbody");
            pharmacistsTable.empty();
            
			if($(".rating-list:checked").val() == "1")
				for(var i = 0; i < pharmacists.length; i++)
					if(pharmacists[i].rating < 2.5)
						generatePartialTable(pharmacists[i], pharmacistsTable);	  	           							
			if($(".rating-list:checked").val() == "2")
				for(var i = 0; i < pharmacists.length; i++)
					if(pharmacists[i].rating >= 2.5 && pharmacists[i].rating < 3.5)
						generatePartialTable(pharmacists[i], pharmacistsTable);	  	      							
			if($(".rating-list:checked").val() == "3")
				for(var i = 0; i < pharmacists.length; i++)
					if(pharmacists[i].rating >= 3.5 && pharmacists[i].rating < 4.5)
						generatePartialTable(pharmacists[i], pharmacistsTable);	  	     							
			if($(".rating-list:checked").val() == "4")
				for(var i = 0; i < pharmacists.length; i++)
					if(pharmacists[i].rating >= 4.5 && pharmacists[i].rating < 5.0)
						generatePartialTable(pharmacists[i], pharmacistsTable);	  	               							
		}
	});
}

function generateWholeTable(pharmacists){
	var pharmacistsTable = $("#table tbody");
    pharmacistsTable.empty();
    
    for(var i = 0; i < pharmacists.length; i++){
		pharmacistsTable.append("<tr id='" + pharmacists[i].id + "'><td>" + pharmacists[i].user.firstName +   
		"</td><td>" + pharmacists[i].user.lastName +
		"</td><td>" +
		"</td><td>" + pharmacists[i].rating +
		"</td></tr>");
			                
		$("#table").append(pharmacistsTable);
		getPharmacistPharmacy(pharmacists[i].id);
	}
	checkRowCount();
}

function generatePartialTable(pharmacist, table){    
    table.append("<tr id='" + pharmacist.id + "'><td>" + pharmacist.user.firstName +   
	"</td><td>" + pharmacist.user.lastName +
	"</td><td>" +
	"</td><td>" + pharmacist.rating +
	"</td></tr>");
			                
	$("#table").append(table);
	getPharmacistPharmacy(pharmacist.id);
}

function checkOnlyOneRating(){
    $(".rating-list").on("change", function(){
        $(this).siblings("input:checkbox").prop("checked", false);
    });
}

function checkOnlyOnePharmacy(){
	$(".pharmacy-list").on("change", function(){
        $(this).siblings("input:checkbox").prop("checked", false);
    });
}

var PHARMACIES = [];
var DERMATOLOGISTS = [];

$(window).on("load", function(){
	getCurrentUser();
	$("#noDermFound").hide();
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
            	getAllDermatologists();	
           	}else if(data.responseJSON.type == "ROLE_PHARMACY_ADMIN"){
           		getAdminFromUserId(data.responseJSON.id);
           	}
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
        		getAllDermatologistsInPharmacy(data.responseJSON.id);
        	}else{
        		alert("No pharmacy found!");
        	}
       	}
	});
} 

function getAllDermatologistsInPharmacy(pharmacyId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getPharmacyDermatologists/' + pharmacyId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var derms = data.responseJSON;
            searchDermatologists(derms);
        }
    });
}

function getAllDermatologists(){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getAllDermatologists',
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var derms = data.responseJSON;
            searchDermatologists(derms);
        }
    });
}

function searchDermatologists(dermatologists){
	$("#searchBtn").click(function(event){
		event.preventDefault();

		var dermsTable = $("#table tbody");
        dermsTable.empty();
            
        for(var i = 0; i < dermatologists.length; i++){
        	if($("#search").val().toLowerCase() == dermatologists[i].user.firstName.toLowerCase() ||
        		$("#search").val().toLowerCase() == dermatologists[i].user.lastName.toLowerCase() ||
        		$("#search").val().toLowerCase() == dermatologists[i].user.firstName.toLowerCase() + " " + dermatologists[i].user.lastName.toLowerCase()){
		            dermsTable.append("<tr id='" + dermatologists[i].id + "'><td>" + dermatologists[i].user.firstName +   
		            "</td><td>" + dermatologists[i].user.lastName +
		            "</td><td>" +
		            "</td><td>" + dermatologists[i].rating +
		            "</td></tr>");
		                
					$("#table").append(dermsTable);
					getDermatologistPharmacies(dermatologists[i].id);
					DERMATOLOGISTS.push(dermatologists[i]);
	        }  
		}
		checkRowCount();
		filterDermatologists(DERMATOLOGISTS);	
	});
}

function checkRowCount(){
	
	var rowCount = $('#table tr').length;
	if(rowCount == 1){
		$("#table").hide();
		$("#noDermFound").show();
		$("#btnFilter").hide();
	}
	else{
		$("#table").show();
		$("#noDermFound").hide();
		$("#btnFilter").show();
	}
}

function getDermatologistPharmacies(dermatologistId){
	$.ajax({
        method: 'GET',
        url: 'http://localhost:8080/getDermatologistPharmacies/' + dermatologistId,
        headers: {
   			Authorization: 'Bearer ' + $.cookie('token')
		},
        complete: function (data) {
            
            var pharmacies = data.responseJSON;
            var all = "";
            for(var i = 0; i < pharmacies.length; i++){
            	all += pharmacies[i].name + ", ";
            }
            
            var text = all.replace(/,\s*$/, "");
            $("#" + dermatologistId + " td:nth-child(3)").text(text);
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

function generateWholeTable(dermatologists){
	var dermatologistTable = $("#table tbody");
    dermatologistTable.empty();
    
    for(var i = 0; i < dermatologists.length; i++){
		dermatologistTable.append("<tr id='" + dermatologists[i].id + "'><td>" + dermatologists[i].user.firstName +   
		"</td><td>" + dermatologists[i].user.lastName +
		"</td><td>" +
		"</td><td>" + dermatologists[i].rating +
		"</td></tr>");
			                
		$("#table").append(dermatologistTable);
		getDermatologistPharmacies(dermatologists[i].id);
	}
	checkRowCount();
}

function generatePartialTable(dermatologist, table){    
    table.append("<tr id='" + dermatologist.id + "'><td>" + dermatologist.user.firstName +   
	"</td><td>" + dermatologist.user.lastName +
	"</td><td>" +
	"</td><td>" + dermatologist.rating +
	"</td></tr>");
			                
	$("#table").append(table);
	getDermatologistPharmacies(dermatologist.id);
}

function generateHelperTable(dermatologist, table){
	
}

function filterDermatologists(dermatologists){
	$(".rating-list").on("change", function(){
		if($(".rating-list:checked").val() == undefined && $(".pharmacy-list:checked").val() == undefined){
			generateWholeTable(dermatologists);
		}
		else if($(".rating-list:checked").val() != undefined && $(".pharmacy-list:checked").val() == undefined){	
			var dermatologistsTable = $("#table tbody");
    		dermatologistsTable.empty();
			if($(".rating-list:checked").val() == "1")
				for(var i = 0; i < dermatologists.length; i++)
					if(dermatologists[i] != undefined)
						if(dermatologists[i].rating < 2.5)
							generatePartialTable(dermatologists[i], dermatologistsTable);
			if($(".rating-list:checked").val() == "2")
				for(var i = 0; i < dermatologists.length; i++)
					if(dermatologists[i] != undefined)
						if(dermatologists[i].rating >= 2.5 && dermatologists[i].rating < 3.5)
							generatePartialTable(dermatologists[i], dermatologistsTable);        				
			if($(".rating-list:checked").val() == "3")
				for(var i = 0; i < dermatologists.length; i++)
					if(dermatologists[i] != undefined)
						if(dermatologists[i].rating >= 3.5 && dermatologists[i].rating < 4.5)
							generatePartialTable(dermatologists[i], dermatologistsTable);                 							
			if($(".rating-list:checked").val() == "4")
				for(var i = 0; i < dermatologists.length; i++)
					if(dermatologists[i].rating >= 4.5 && dermatologists[i].rating < 5.0)
						generatePartialTable(dermatologists[i], dermatologistsTable);                      							
		}
		else if($(".rating-list:checked").val() == undefined && $(".pharmacy-list:checked").val() != undefined){
			var dermatologistsTable = $("#table tbody");
            dermatologistsTable.empty();
			for(var i = 0; i < dermatologists.length; i++){
				comeOn(dermatologists[i], dermatologistsTable);
			}
		}
		else if($(".rating-list:checked").val() != undefined && $(".pharmacy-list:checked").val() != undefined){
			var dermatologistsTable = $("#table tbody");
            dermatologistsTable.empty();
            
			for(var i = 0; i < dermatologists.length; i++){
				if($(".rating-list:checked").val() == "1")
					if(dermatologists[i].rating < 2.5)
						comeOn(dermatologists[i], dermatologistsTable);		            				
				if($(".rating-list:checked").val() == "2")
					if(dermatologists[i].rating >= 2.5 && dermatologists[i].rating < 3.5)
						comeOn(dermatologists[i], dermatologistsTable);	  		          				
				if($(".rating-list:checked").val() == "3")
					if(dermatologists[i].rating >= 3.5 && dermatologists[i].rating < 4.5)
						comeOn(dermatologists[i], dermatologistsTable);	       				
				if($(".rating-list:checked").val() == "4")
					if(dermatologists[i].rating > 4.5)
						comeOn(dermatologists[i], dermatologistsTable);	 		     	
			}
		}
	});
	
	$(".pharmacy-list").on("change", function(){
		if($(".pharmacy-list:checked").val() == undefined && $(".rating-list:checked").val() == undefined){
			generateWholeTable(dermatologists);
		}
		else if($(".rating-list:checked").val() == undefined && $(".pharmacy-list:checked").val() != undefined){
			var dermatologistsTable = $("#table tbody");
            dermatologistsTable.empty();
			for(var i = 0; i < dermatologists.length; i++){
				comeOn(dermatologists[i], dermatologistsTable);
			}
		}
		else if($(".rating-list:checked").val() != undefined && $(".pharmacy-list:checked").val() != undefined){
			var dermatologistsTable = $("#table tbody");
            dermatologistsTable.empty();
            
			for(var i = 0; i < dermatologists.length; i++){
				if($(".rating-list:checked").val() == "1")
					if(dermatologists[i].rating < 2.5)
						comeOn(dermatologists[i], dermatologistsTable);		            				
				if($(".rating-list:checked").val() == "2")
					if(dermatologists[i].rating >= 2.5 && dermatologists[i].rating < 3.5)
						comeOn(dermatologists[i], dermatologistsTable);	  		          				
				if($(".rating-list:checked").val() == "3")
					if(dermatologists[i].rating >= 3.5 && dermatologists[i].rating < 4.5)
						comeOn(dermatologists[i], dermatologistsTable);	       				
				if($(".rating-list:checked").val() == "4")
					if(dermatologists[i].rating > 4.5)
						comeOn(dermatologists[i], dermatologistsTable);	 		     	
			}
		}
		else if($(".rating-list:checked").val() != undefined && $(".pharmacy-list:checked").val() == undefined){	
			var dermatologistsTable = $("#table tbody");
    		dermatologistsTable.empty();
			if($(".rating-list:checked").val() == "1")
				for(var i = 0; i < dermatologists.length; i++)
					if(dermatologists[i] != undefined)
						if(dermatologists[i].rating < 2.5)
							generatePartialTable(dermatologists[i], dermatologistsTable);
			if($(".rating-list:checked").val() == "2")
				for(var i = 0; i < dermatologists.length; i++)
					if(dermatologists[i] != undefined)
						if(dermatologists[i].rating >= 2.5 && dermatologists[i].rating < 3.5)
							generatePartialTable(dermatologists[i], dermatologistsTable);        				
			if($(".rating-list:checked").val() == "3")
				for(var i = 0; i < dermatologists.length; i++)
					if(dermatologists[i] != undefined)
						if(dermatologists[i].rating >= 3.5 && dermatologists[i].rating < 4.5)
							generatePartialTable(dermatologists[i], dermatologistsTable);                 							
			if($(".rating-list:checked").val() == "4")
				for(var i = 0; i < dermatologists.length; i++)
					if(dermatologists[i].rating >= 4.5 && dermatologists[i].rating < 5.0)
						generatePartialTable(dermatologists[i], dermatologistsTable);                      							
		}
		
	});
}

function comeOn(dermatologist, table){
$.ajax({
	method: 'GET',
	url: 'http://localhost:8080/getDermatologistPharmacies/' + dermatologist.id,
	headers: {
		Authorization: 'Bearer ' + $.cookie('token')
	},
	complete: function (data) {
		var pharmacies = data.responseJSON;
		var contains = false;
		for(var i = 0; i < pharmacies.length; i++){
			if(pharmacies[i].name.includes($(".pharmacy-list:checked").val())){
				contains = true;
			}
		}
		    
		if(contains)
			generatePartialTable(dermatologist, table); 
	}
  });						
}



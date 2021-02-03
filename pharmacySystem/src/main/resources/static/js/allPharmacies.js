var CITIES = [];

$(window).on('load', function(){
	getCurrentUser();
	checkOnlyOneRating();
	
	$("#filters").hide();
	$("#btnFilter").click(function(event){
		event.preventDefault();
		
		if($("#filters").is(":hidden"))
			$("#filters").slideDown(1000);
		else if(!$("#filters").is(":hidden"))
			$("#filters").slideUp(1000);
	});
});

function blockTableClick(user){
	if(user == undefined || user.type == "ROLE_PHARMACY_SYSTEM_ADMIN")
	   $("#table tr").off("click");
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
                getAllPharmacies(data.responseJSON);
            }else {
            	alert("You cannot access this page!");
                window.location.href = "../index.html";
           	}
        }
    });
}

function getAllPharmacies(user){
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
            
            $("#cities").empty();
            $("#cities").text("Cities: ");
            $("#cities").append("<br/>");
            for(var i = 0; i < allPharmacies.length; i++){
            	pharmaciesTable.append("<tr id='" + allPharmacies[i].id + "'><td>" + allPharmacies[i].name +   
                "</td><td>" + allPharmacies[i].address +
                 "</td><td>" + allPharmacies[i].city +
                 "</td><td>" + allPharmacies[i].rating +
                "</td></tr>");
			
				CITIES.push(allPharmacies[i].city);	
                $("#table").append(pharmaciesTable);
                choosePharmacy(allPharmacies[i].id, user);
            }
            blockTableClick(user);
            var myCities = eliminateDuplicates(CITIES);
				
			for(var j = 0; j < myCities.length; j++)
				$("#cities").append("<input type='checkbox' class='city-list' value='" + myCities[j] + "'/> <label>" + myCities[j] + "</label><br/>");	
					
            sortByName(allPharmacies, user);
            sortByAddress(allPharmacies, user);
            sortByCity(allPharmacies, user);
            sortByRating(allPharmacies, user);
            searchPharmacies(allPharmacies, user);
            checkOnlyOneCity();
            filterPharmacies(allPharmacies, user);
        }
    });
}

function sortByName(allPharmacies, user){
	var rising = false;
	$("#name").click(function(){
		if(rising){
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
			var mojNiz = allPharmacies.sort((a, b) =>
							 a.name.localeCompare(b.name)
						);
			
			for(var i = 0; i < mojNiz.length; i++){
				pharmaciesTable.append("<tr id='" + mojNiz[i].id + "'><td>" + mojNiz[i].name +   
                "</td><td>" + mojNiz[i].address +
                 "</td><td>" + mojNiz[i].city +
                 "</td><td>" + mojNiz[i].rating +
                "</td></tr>");

                $("#table").append(pharmaciesTable);
                choosePharmacy(mojNiz[i].id, user);
			}
			rising = false;
			blockTableClick(user);
		}else{
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
			var mojNiz = allPharmacies.sort((a, b) =>
							b.name.localeCompare(a.name)
							);
							
			for(var i = 0; i < mojNiz.length; i++){
				pharmaciesTable.append("<tr id='" + mojNiz[i].id + "'><td>" + mojNiz[i].name +   
                "</td><td>" + mojNiz[i].address +
                 "</td><td>" + mojNiz[i].city +
                 "</td><td>" + mojNiz[i].rating +
                "</td></tr>");

                $("#table").append(pharmaciesTable);
                choosePharmacy(mojNiz[i].id, user);
			}
			rising = true;
			blockTableClick(user);
		}			
	});
}

function sortByAddress(allPharmacies, user){
	var rising = false;
	$("#address").click(function(){
		if(rising){
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
			var mojNiz = allPharmacies.sort((a, b) =>
							 a.address.localeCompare(b.address)
						);
			
			for(var i = 0; i < mojNiz.length; i++){
				pharmaciesTable.append("<tr id='" + mojNiz[i].id + "'><td>" + mojNiz[i].name +   
                "</td><td>" + mojNiz[i].address +
                 "</td><td>" + mojNiz[i].city +
                 "</td><td>" + mojNiz[i].rating +
                "</td></tr>");

                $("#table").append(pharmaciesTable);
                choosePharmacy(mojNiz[i].id, user);
			}
			rising = false;
			blockTableClick(user);
		}else{
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
			var mojNiz = allPharmacies.sort((a, b) =>
							b.address.localeCompare(a.address)
							);
							
			for(var i = 0; i < mojNiz.length; i++){
				pharmaciesTable.append("<tr id='" + mojNiz[i].id + "'><td>" + mojNiz[i].name +   
                "</td><td>" + mojNiz[i].address +
                 "</td><td>" + mojNiz[i].city +
                 "</td><td>" + mojNiz[i].rating +
                "</td></tr>");

                $("#table").append(pharmaciesTable);
                choosePharmacy(mojNiz[i].id, user);
			}
			rising = true;
			blockTableClick(user);
		}			
	});
}

function sortByCity(allPharmacies, user){
	var rising = false;
	$("#city").click(function(){
		if(rising){
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
			var mojNiz = allPharmacies.sort((a, b) =>
							 a.city.localeCompare(b.city)
						);
			
			for(var i = 0; i < mojNiz.length; i++){
				pharmaciesTable.append("<tr id='" + mojNiz[i].id + "'><td>" + mojNiz[i].name +   
                "</td><td>" + mojNiz[i].address +
                 "</td><td>" + mojNiz[i].city +
                 "</td><td>" + mojNiz[i].rating +
                "</td></tr>");

                $("#table").append(pharmaciesTable);
                choosePharmacy(mojNiz[i].id, user);
			}
			rising = false;
			blockTableClick(user);
		}else{
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
			var mojNiz = allPharmacies.sort((a, b) =>
							b.city.localeCompare(a.city)
							);
							
			for(var i = 0; i < mojNiz.length; i++){
				pharmaciesTable.append("<tr id='" + mojNiz[i].id + "'><td>" + mojNiz[i].name +   
                "</td><td>" + mojNiz[i].address +
                 "</td><td>" + mojNiz[i].city +
                 "</td><td>" + mojNiz[i].rating +
                "</td></tr>");

                $("#table").append(pharmaciesTable);
                choosePharmacy(mojNiz[i].id, user);
			}
			rising = true;
			blockTableClick(user);
		}			
	});
}

function sortByRating(allPharmacies, user){	
	
	var rising = false;
	$("#rating").click(function(){
		if(rising){
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
			var mojNiz = allPharmacies.sort(function(a, b){
							return a.rating - b.rating;
						});
			
			for(var i = 0; i < mojNiz.length; i++){
				pharmaciesTable.append("<tr id='" + mojNiz[i].id + "'><td>" + mojNiz[i].name +   
                "</td><td>" + mojNiz[i].address +
                 "</td><td>" + mojNiz[i].city +
                 "</td><td>" + mojNiz[i].rating +
                "</td></tr>");

                $("#table").append(pharmaciesTable);
                choosePharmacy(mojNiz[i].id, user);
			}
			rising = false;
			blockTableClick(user);
		}else{
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
			var mojNiz = allPharmacies.sort(function(a, b){
							return b.rating - a.rating;
							});
							
			for(var i = 0; i < mojNiz.length; i++){
				pharmaciesTable.append("<tr id='" + mojNiz[i].id + "'><td>" + mojNiz[i].name +   
                "</td><td>" + mojNiz[i].address +
                 "</td><td>" + mojNiz[i].city +
                 "</td><td>" + mojNiz[i].rating +
                "</td></tr>");

                $("#table").append(pharmaciesTable);
                choosePharmacy(mojNiz[i].id, user);
			}
			rising = true;
			blockTableClick(user);
		}			
	});
}

function searchPharmacies(allPharmacies, user){
	$("#search").on("input", function(e){
		if($("#search").val() != ""){
			
			var pharmaciesTable = $("#table tbody");
		    pharmaciesTable.empty();
	            
			for(var i = 0; i < allPharmacies.length; i++){
				if(allPharmacies[i].name.toLowerCase().includes($("#search").val().toLowerCase()) ||
					allPharmacies[i].city.toLowerCase().includes($("#search").val().toLowerCase())){
					 pharmaciesTable.append("<tr><td>" +  allPharmacies[i].name +
	                "</td><td>" + allPharmacies[i].address +
	                "</td><td>" + allPharmacies[i].city +
	                "</td><td>" + allPharmacies[i].rating +
	                "</td></tr>");
	
	                $("#table").append(pharmaciesTable);
	                choosePharmacy(allPharmacies[i].id, user);
				}
			}
			blockTableClick(user);
		}else{
			getAllPharmacies(user);
		}
	});
}

function checkOnlyOneRating(){
    $(".rating-list").on("change", function(){
        $(this).siblings("input:checkbox").prop("checked", false);
        $("#search").val("");
    });
}

function checkOnlyOneCity(){
	$(".city-list").on("change", function(){
        $(this).siblings("input:checkbox").prop("checked", false);
        $("#search").val("");
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

function filterPharmacies(allPharmacies, user){
	$(".rating-list").on("change", function(){
		if($(".rating-list:checked").val() == undefined && $(".city-list:checked").val() == undefined){
			getAllPharmacies(user);
		}
		else if($(".rating-list:checked").val() != undefined && $(".city-list:checked").val() == undefined){
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
			if($(".rating-list:checked").val() == "1")
				for(var i = 0; i < allPharmacies.length; i++)
					if(allPharmacies[i] != undefined)
						if(allPharmacies[i].rating < 2.5)
							generateTable(allPharmacies[i], pharmaciesTable, user);
			if($(".rating-list:checked").val() == "2")
				for(var i = 0; i < allPharmacies.length; i++)
					if(allPharmacies[i] != undefined)
						if(allPharmacies[i].rating >= 2.5 && allPharmacies[i].rating < 3.5)
							generateTable(allPharmacies[i], pharmaciesTable, user);                				
			if($(".rating-list:checked").val() == "3")
				for(var i = 0; i < allPharmacies.length; i++)
					if(allPharmacies[i] != undefined)
						if(allPharmacies[i].rating >= 3.5 && allPharmacies[i].rating < 4.5)
							generateTable(allPharmacies[i], pharmaciesTable, user);	                							
			if($(".rating-list:checked").val() == "4")
				for(var i = 0; i < allPharmacies.length; i++)
					if(allPharmacies[i].rating >= 4.5 && allPharmacies[i].rating < 5.0)
						generateTable(allPharmacies[i], pharmaciesTable, user);	                							
		}
		else if($(".rating-list:checked").val() != undefined && $(".city-list:checked").val() != undefined){
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
            
			for(var i = 0; i < allPharmacies.length; i++){
				if($(".rating-list:checked").val() == "1" && $(".city-list:checked").val() == allPharmacies[i].city)
					if(allPharmacies[i].rating < 2.5)
						generateTable(allPharmacies[i], pharmaciesTable, user);	                				
				if($(".rating-list:checked").val() == "2" && $(".city-list:checked").val() == allPharmacies[i].city)
					if(allPharmacies[i].rating >= 2.5 && allPharmacies[i].rating < 3.5)
						generateTable(allPharmacies[i], pharmaciesTable, user);	                				
				if($(".rating-list:checked").val() == "3" && $(".city-list:checked").val() == allPharmacies[i].city)
					if(allPharmacies[i].rating >= 3.5 && allPharmacies[i].rating < 4.5)
						generateTable(allPharmacies[i], pharmaciesTable, user);	                				
				if($(".rating-list:checked").val() == "4" && $(".city-list:checked").val() == allPharmacies[i].city)
					if(allPharmacies[i].rating > 4.5)
						generateTable(allPharmacies[i], pharmaciesTable, user);	                	
			}
		}
		else if($(".rating-list:checked").val() == undefined && $(".city-list:checked").val() != undefined){
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
            
			for(var i = 0; i < allPharmacies.length; i++)
				if($(".city-list:checked").val() == allPharmacies[i].city)
					generateTable(allPharmacies[i], pharmaciesTable, user);							
		}
	});
	
	$(".city-list").on("change", function(){
		if($(".city-list:checked").val() == undefined && $(".rating-list:checked").val() == undefined){
			getAllPharmacies(user);
		}
		else if($(".city-list:checked").val() != undefined && $(".rating-list:checked").val() == undefined){
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
            
			for(var i = 0; i < allPharmacies.length; i++)
				if($(".city-list:checked").val() == allPharmacies[i].city)
					generateTable(allPharmacies[i], pharmaciesTable, user);
				
			
		}
		else if($(".city-list:checked").val() != undefined && $(".rating-list:checked").val() != undefined){
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
            
			for(var i = 0; i < allPharmacies.length; i++){
				if($(".rating-list:checked").val() == "1" && $(".city-list:checked").val() == allPharmacies[i].city)
					if(allPharmacies[i].rating < 2.5)
						generateTable(allPharmacies[i], pharmaciesTable, user);	                				
				if($(".rating-list:checked").val() == "2" && $(".city-list:checked").val() == allPharmacies[i].city)
					if(allPharmacies[i].rating >= 2.5 && allPharmacies[i].rating < 3.5)
						generateTable(allPharmacies[i], pharmaciesTable, user);	                				
				if($(".rating-list:checked").val() == "3" && $(".city-list:checked").val() == allPharmacies[i].city)
					if(allPharmacies[i].rating >= 3.5 && allPharmacies[i].rating < 4.5)
						generateTable(allPharmacies[i], pharmaciesTable, user);	                				
				if($(".rating-list:checked").val() == "4" && $(".city-list:checked").val() == allPharmacies[i].city)
					if(allPharmacies[i].rating > 4.5)
						generateTable(allPharmacies[i], pharmaciesTable, user);	                				
			}
		}
		else if($(".city-list:checked").val() == undefined && $(".rating-list:checked").val() != undefined){
			var pharmaciesTable = $("#table tbody");
            pharmaciesTable.empty();
            
			if($(".rating-list:checked").val() == "1")
				for(var i = 0; i < allPharmacies.length; i++)
					if(allPharmacies[i].rating < 2.5)
						generateTable(allPharmacies[i], pharmaciesTable, user);	                							
			if($(".rating-list:checked").val() == "2")
				for(var i = 0; i < allPharmacies.length; i++)
					if(allPharmacies[i].rating >= 2.5 && allPharmacies[i].rating < 3.5)
						generateTable(allPharmacies[i], pharmaciesTable, user);	                							
			if($(".rating-list:checked").val() == "3")
				for(var i = 0; i < allPharmacies.length; i++)
					if(allPharmacies[i].rating >= 3.5 && allPharmacies[i].rating < 4.5)
						generateTable(allPharmacies[i], pharmaciesTable, user);	                							
			if($(".rating-list:checked").val() == "4")
				for(var i = 0; i < allPharmacies.length; i++)
					if(allPharmacies[i].rating >= 4.5 && allPharmacies[i].rating < 5.0)
						generateTable(allPharmacies[i], pharmaciesTable, user);	                							
		}
	});
}

function generateTable(pharmacy, pharmaciesTable, user){
	pharmaciesTable.append("<tr id='" + pharmacy.id + "'><td>" + pharmacy.name +   
	"</td><td>" + pharmacy.address +
	"</td><td>" + pharmacy.city +
	"</td><td>" + pharmacy.rating +
	"</td></tr>");
				
	$("#table").append(pharmaciesTable);
	blockTableClick(user);
	choosePharmacy(pharmacy.id, user);
}

function choosePharmacy(pharmacyId, user){
	$("#" + pharmacyId).click(function(event){
		if(user == undefined || user.type == "ROLE_PHARMACY_SYSTEM_ADMIN"){
		
		}
		
		if(user.type == "ROLE_PATIENT")
			window.location.href = "pharmacyProfile.html?pharmacyId=" + pharmacyId;
	});
}

$(document).ready(function(){
    $.ajax({
        type: 'post',
        url: 'changeItinerary',
        data: {"addId": "","removeId": ""},
        success: function(data){
            $("#links").find("#noOfPlaces").text(" ("+data+")");
        }
    });
});

function addToItinerary(id) {

    $.ajax({
        type: 'post',
        url: 'changeItinerary',
        data: {"addId": id,"removeId": ""},
        success: function(data){
            $("#links").find("#noOfPlaces").text(" ("+data+")");
        }
    });
}

function removeFromItinerary(id) {

    $.ajax({
        type: 'post',
        url: 'changeItinerary',
        data: {"removeId": id, "addId": ""},
        success: function(data){
            $("#links").find("#noOfPlaces").text(" ("+data+")");
        }
    });
}
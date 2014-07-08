
$(document).ready(function(){
    $.ajax({
        type: 'post',
        url: 'changeItinerary',
        data: {"addId": "","removeId": "", "startOver": ""},
        success: function(data){
            $("#links").find("#noOfPlaces").text(" ("+data+")");
        }
    });
});

function addToItinerary(id) {

    $.ajax({
        type: 'post',
        url: 'changeItinerary',
        data: {"addId": id,"removeId": "", "startOver": ""},
        success: function(data){
            $("#links").find("#noOfPlaces").text(" ("+data+")");
        }
    });
}

function removeFromItinerary(id) {

    $.ajax({
        type: 'post',
        url: 'changeItinerary',
        data: {"removeId": id, "addId": "", "startOver": ""},
        success: function(data){
            $("#links").find("#noOfPlaces").text(" ("+data+")");
        }
    });
    $("#itineraryPlace-"+id).remove();
}

function startOver() {

    $.ajax({
        type: 'post',
        url: 'changeItinerary',
        data: {"removeId": "", "addId": "", "startOver": "startOver"},
        success: function(data){
            $("#links").find("#noOfPlaces").text(" ("+data+")");
        }
    });
    window.location.href = "home.jsp";
}
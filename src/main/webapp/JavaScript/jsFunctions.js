
var geoCoder;
var markers = [];
var today  = new Date();
function initialize() {
    autoComplete();
    currentDate();
    $("#SearchBar:input").tooltip();
}

function autoComplete() {
    var autoComplete = new google.maps.places.Autocomplete((document.getElementById('address')),
        { types: ['geocode'] });
}


function geocode() {
    var address = document.getElementById('address').value;
    geoCoder = new google.maps.Geocoder();
    geoCoder.geocode({'address': address}, geocodeCallback);
}

function geocodeCallback(results, status) {
    if (!(status == google.maps.GeocoderStatus.OK)) {

        $('#address').val('').attr("placeholder", "Please enter a valid address")
            .css('box-shadow','0 0 8px red');
        return;
    } else {
        $('#address').css('box-shadow','none');
    }

}

function dateValidation(){
    var date = $("#date").val();
    var d = new Date('"' + date + '"');
    var n = d.getDay();
    if(isNaN(n)) {
        $("#date").val('').attr("placeholder", "Not valid")
            .css('box-shadow','0 0 8px red');
    } else {
        $("#date").css('box-shadow','none');
        $("#day").val(n);
    }
}

function createMarker(placeLocation,contentString,i){
    var marker = new google.maps.Marker({
        map: map,
        position: placeLocation
    });

    google.maps.event.addListener(marker, 'click', function () {
        infoWindow.setContent(contentString);
        infoWindow.open(map, this);
    });
    markers[i] = marker
}

function show(i) {
    google.maps.event.trigger(markers[i], 'click');
}

function validateForm(){
    if($("#")){

    }
}

function currentDate() {
    var dd = today.getDate();
    var mm = today.getMonth()+1;
    var yyyy = today.getFullYear();

    if(dd<10) {
        dd='0'+dd
    }

    if(mm<10) {
        mm='0'+mm
    }

    today = mm+'/'+dd+'/'+yyyy;
}



var geoCoder;
var markers = [];
var divNumber;
var isValidAddress = false;

function initialize() {
    geoCoder = new google.maps.Geocoder();
    autoComplete();
    geocode();
}


function geocode() {
    var address = document.getElementById('address').value;
    geoCoder.geocode({'address': address}, function (results, status) {
        isValidAddress = status == google.maps.GeocoderStatus.OK;
    });
}

function dateValidation(){
    var date = $("#date").val();
    var rxDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
    var dtArray = date.match(rxDatePattern);

    var d = new Date(date.replace(/\-/g,','));
    var n = d.getDay();
    return !(isNaN(n) || dtArray == null);
}


function validate() {

    if(!isValidAddress) {
        $('#address').val('').attr("placeholder", "Please enter a valid address")
                .css('box-shadow','0 0 8px red');
        return false;
    } else if(!dateValidation()){
        $("#date").val('').attr("placeholder", "mm-dd-yyyy")
            .css('box-shadow','0 0 8px red');
        return false;
    }
    $('#loadingDiv').fadeIn('fast');
    return true;
}


function autoComplete() {
    var autoComplete = new google.maps.places.Autocomplete((document.getElementById('address')),
        { types: ['geocode'] });
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

function showMoreInfo(div) {
    divNumber = div;
    $("#moreInfo").fadeIn("slow").find("#moreInfoContent"+divNumber).show();
}

function hideMoreInfo() {
    $("#moreInfo").fadeOut("slow").find("#moreInfoContent"+divNumber).hide();
}

function showDirections(div) {
    divNumber = div;
    $("#directions").fadeIn("slow").find("#directionsContent"+divNumber).show();
}

function hideDirections() {
    $("#directions").fadeOut("slow").findj("#directionsContent"+divNumber).hide();
}


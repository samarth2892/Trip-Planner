
var geoCoder;
var markers = [];
var divNumber;
var isValidAddress;

function initialize() {
    geoCoder = new google.maps.Geocoder();
    autoComplete();
}

function validate(cleanAddress) {
    if (dateValidation()) {
        var address = document.getElementById('address').value;
        geoCoder.geocode({'address': address}, function (results, status) {
            cleanAddress(status == google.maps.GeocoderStatus.OK);
        });
        alert(isValidAddress);
    } else {
         $("#date").val('').attr("placeholder", "mm-dd-yyyy")
         .css('box-shadow','0 0 8px red');
        return false;
    }
}

function cleanAddress(isValid) {
    if(isValid == false) {
        $('#address').val('').attr("placeholder", "Please enter a valid address")
            .css('box-shadow','0 0 8px red');
        isValidAddress = isValid;

    } else {
        $('#loadingDiv').fadeIn('fast');

    }
}

function autoComplete() {
    var autoComplete = new google.maps.places.Autocomplete((document.getElementById('address')),
        { types: ['geocode'] });
}

function dateValidation(){
    var date = $("#date").val();
    var rxDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
    var dtArray = date.match(rxDatePattern);

    var d = new Date('"' + date + '"');
    var n = d.getDay();
    return !(isNaN(n) || dtArray == null);
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




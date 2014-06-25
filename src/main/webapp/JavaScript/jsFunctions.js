
var geoCoder;
var center;
function autoComplete() {
    var autoComplete = new google.maps.places.Autocomplete((document.getElementById('address')),
        { types: ['geocode'] });
}


/*$(document).ready(function() {

     var pyrmont = new google.maps.LatLng(-33.8665433, 151.1956316);

     map = new google.maps.Map(document.getElementById('map'), {
     center: pyrmont,
     zoom: 15
     });
});*/

function geocode() {
    geoCoder = new google.maps.Geocoder();
    var address = document.getElementById('address').value;
    geoCoder.geocode( {'address': address}, geocodeCallback);
}

function geocodeCallback(results, status) {
    if (!(status == google.maps.GeocoderStatus.OK)) {

        $('#address').val('').attr("placeholder", "Please enter a valid address");
        return;
    }
    center = results[0].geometry.location;
}

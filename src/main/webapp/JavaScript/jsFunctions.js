
var geoCoder;
var markers = [];
var today  = new Date();
var validAddress = false;
var validDate = false;
var divNumber;

function initialize() {
    autoComplete();
}

function autoComplete() {
    var autoComplete = new google.maps.places.Autocomplete((document.getElementById('address')),
        { types: ['geocode'] });
    $("#SearchBar").find("input[type=submit]").attr("disabled", "disabled");
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
        validAddress = false;
        searchButtonStatus();
    } else {
        $('#address').css('box-shadow','none');
        validAddress = true;
        searchButtonStatus();
    }
}

function dateValidation(){
    var date = $("#date").val();
    var rxDatePattern = /^(\d{1,2})(\/|-)(\d{1,2})(\/|-)(\d{4})$/;
    var dtArray = date.match(rxDatePattern);

    var d = new Date('"' + date + '"');
    var n = d.getDay();
    if(isNaN(n) || dtArray == null) {
        $("#date").val('').attr("placeholder", "mm-dd-yyyy")
            .css('box-shadow','0 0 8px red');
        validDate = false;
        searchButtonStatus();
    } else {
        $("#date").css('box-shadow','none');
        $("#day").val(n);
        validDate = true;
        searchButtonStatus();
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

$("#address").change(searchButtonStatus());
$("#date").change(searchButtonStatus());

function searchButtonStatus(){
    if(validAddress && validDate){
        $("#SearchBar").find("input[type=submit]").removeAttr("disabled");
    } else {
        $("#SearchBar").find("input[type=submit]").attr("disabled", "disabled");
    }
}

function showMoreInfo(div) {
    divNumber = div;
    $("#moreInfo").fadeIn("slow").find("#moreInfoContent"+divNumber).show();
}

function hideMoreInfo() {
    $("#moreInfo").fadeOut("slow").find("#moreInfoContent"+divNumber).hide();
}

$("#SearchBar").find("input[type=submit]").click($("#moreInfo").fadeIn("slow"));


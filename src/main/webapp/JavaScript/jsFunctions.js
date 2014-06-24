
function autoComplete() {
    var autoComplete = new google.maps.places.Autocomplete((document.getElementById('address')),
        { types: ['geocode'] });
}
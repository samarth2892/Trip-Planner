var count = 0;


function addToItinerary(id) {

    $.ajax({
        type: 'post',
        url: 'addToItinerary',
        data: {"id": id},
        success: function(data){
            alert("Servlet "+ data);
        }
    });


}
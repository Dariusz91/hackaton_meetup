function getParticipants(callback)
{
    $.ajax({
        url: "", //TODO Add url
        cache: false,
        type: "GET",
        dataType: "json",
        data: {"eventId": eventId, "appKey": appKey},
        complete: function(){},
        success: function(data){
            if(callback != null) {
                callback(data);
            }
        },
        error: function(data, textStatus, xhr){
            if(callback != null) {
                callback(false);
            }
        }
    });
}

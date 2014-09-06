function getParticipants(callback, eventId, appKey)
{
    $.ajax({
        url: "/users",
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

function getMemberInfo(callback, userId, appKey)
{
    $.ajax({
        url: "api.meetup.com/2/member.json/",
        cache: false,
        type: "GET",
        dataType: "json",
        data: {"id": userId, "key": appKey},
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

function getPhotoFromMemberInfo(memberInfo) {
    var photo = Object.create(null);
    photo.highres_link = memberInfo.photo.highres_link;
    photo.photo_id = memberInfo.photo.photo_id;
    photo.photo_link = memberInfo.photo.photo_link;
    photo.thumb_link = memberInfo.photo.thumb_link;
    return photo;
}


var eventId = localStorage.getItem("eventId");
eventId = "201836452";
var appKey = localStorage.getItem("appKey");
appKey = "781d47243d1f565a64a4e7b354e6358";
var participants = new Array();

function processParticipants(participantsJson)
{
    participantsJson.participants.forEach(function(participantJson){
        var participant = Object.create(null);
        participant.memberId = participantJson.member_id;
        participant.name = participantJson.name;
        participants.push(participant);

    });
}

function addParticipantsToView(htmlElement)
{
    for(var i = 0; i < participants.length; ++i)
    {
        var name = participants[i].name;
        htmlElement.append(); //TODO Add content
    }
}


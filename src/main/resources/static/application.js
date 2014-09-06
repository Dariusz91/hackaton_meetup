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

function getPrizes(callback)
{
    $.ajax({
        url: "/prize/get/all",
        cache: false,
        type: "GET",
        dataType: "json",
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

function addPrize(callback, prize)
{
    $.ajax({
        url: "/prize/add",
        cache: false,
        type: "GET",
        dataType: "json",
        data: {"name": prize.name},
        complete: function(){},
        success: function(data){
            if(callback != null) {
                var prizeJson = Object.create(null);
                prizeJson.name = prize.name;
                prizeJson.id = data.id;
                callback(prizeJson);
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
var prizes = new Array();

function findParticipantIndex(participantId)
{
    return participants.findIndex(function(element, index, array){
       return element.memberId == participantId;
    });
}

function findPrizeIndex(prizeId)
{
    return prizes.findIndex(function(element, index, array){
        return element.id == prizeId;
    });
}

function processParticipants(participantsJson)
{
    $("#user_list").empty();
    participants = new Array();
    participantsJson.participants.forEach(function(participantJson){
        var participant = Object.create(null);
        participant.memberId = participantJson.member_id;
        participant.name = participantJson.name;
        participants.push(participant);
        $("#user_list").append("<li><i class='fa-li fa fa-arrow-circle-right'></i>"+ participant.name +"</li>")
    });
}

function processPrizes(prizesJson)
{
    $("#prize_list").empty();
    prizes = new Array();
    prizesJson.prizes.forEach(function(prizeJson){
        var prize = Object.create(null);
        prize.id = prizeJson.id;
        prize.name = prizeJson.name;
        prize.amount = 0;
        prizes.push(prize);
        $("#prize_list").append("<li id='"+prize.id+"'>" + prize.name + "<span class='pull-right counter'> <a href=''> <i class='fa fa-minus-square fa-2x'></i></a><span class='prize-amount'>0</span><a href=''><i class='fa fa-plus-square fa-2x'></i></a></span></li>");
    });
}

function increasePrizeAmount(prizeId)
{
    var prizeIndex = findPrizeIndex(prizeId);
    if(prizes[prizeId].amount < 99) {
        prizes[prizeId].amount += 1;
    }
    return prizes[prizeId].amount;
}

function decreasePrizeAmount(prizeId)
{
    var prizeIndex = findPrizeIndex(prizeId);
    if(prizes[prizeId].amount > 0) {
        prizes[prizeId].amount -= 1;
    }
    return prizes[prizeId].amount;
}

function processAddPrize(prizeJson)
{
    var prize = Object.create(null);
    prize.id = prizeJson.id;
    prize.name = prizeJson.name;
    prize.amount = 0;
    prizes.push(prize);
    $("#prize_list").append("<li id='"+prize.id+"'>" + prize.name + "<span class='pull-right counter'> <a href=''> <i class='fa fa-minus-square fa-2x'></i></a><span class='prize-amount'>0</span><a href=''><i class='fa fa-plus-square fa-2x'></i></a></span></li>");
}

function addParticipantsToView(htmlElement)
{
    for(var i = 0; i < participants.length; ++i)
    {
        var name = participants[i].name;
        htmlElement.append(); //TODO Add content
    }
}


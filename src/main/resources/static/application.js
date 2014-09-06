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
    for(var i = 0; i < participants.length; ++i)
    {
        if(participants[i].memberId == participantId)
        {
            return i;
        }
    }

    return -1;
}

function findPrizeIndex(prizeId)
{
    for(var i = 0; i < prizes.length; ++i)
    {
        if(prizes[i].id == prizeId)
        {
            return i;
        }
    }

    return -1;
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
        $("#prize_list").append("<li id='"+prize.id+"'>" + prize.name + "<span class='pull-right counter'> <a class='minus_square' href='#'> <i class='fa fa-minus-square fa-2x'></i></a><span class='prize-amount'>"+prize.amount+"</span><a class='plus_square' href='#'><i class='fa fa-plus-square fa-2x'></i></a></span></li>");
    });
    setAmountEvents();
}

function processPrizesFromArray()
{
    $("#prize_list").empty();

    prizes.forEach(function(prize){
        $("#prize_list").append("<li id='"+prize.id+"'>" + prize.name + "<span class='pull-right counter'> <a class='minus_square' href='#'> <i class='fa fa-minus-square fa-2x'></i></a><span class='prize-amount'>"+prize.amount+"</span><a class='plus_square' href='#'><i class='fa fa-plus-square fa-2x'></i></a></span></li>");
    });
    setAmountEvents();
}

function setAmountEvents()
{
    $(".plus_square").click(function(event){
        var el = $(this);
        var prizeId = el.parent().parent().attr("id");

        var amount = increasePrizeAmount(prizeId);

        var amountEl = $("#" + prizeId + " .prize-amount");
        amountEl.empty();
        amountEl.append(amount);
        event.preventDefault();
    });
    $(".minus_square").click(function(event){

        var el = $(this);
        var prizeId = el.parent().parent().attr("id");
        var amount = decreasePrizeAmount(prizeId);
        var amountEl = $("#" + prizeId + " .prize-amount");
        amountEl.empty();
        amountEl.append(amount);
        event.preventDefault();
    });
}

function increasePrizeAmount(prizeId)
{
    var prizeIndex = findPrizeIndex(prizeId);
    if(prizes[prizeIndex].amount < 99) {
        prizes[prizeIndex].amount += 1;
    }
    return prizes[prizeIndex].amount;
}

function decreasePrizeAmount(prizeId)
{
    var prizeIndex = findPrizeIndex(prizeId);
    if(prizes[prizeIndex].amount > 0) {
        prizes[prizeIndex].amount -= 1;
    }
    return prizes[prizeIndex].amount;
}

function processAddPrize(prizeJson)
{
    var prize = Object.create(null);
    prize.id = prizeJson.id;
    prize.name = prizeJson.name;
    prize.amount = 0;
    prizes.push(prize);
    $("#prize_list").append("<li id='"+prize.id+"'>" + prize.name + "<span class='pull-right counter'> <a class='minus_square' href='#'> <i class='fa fa-minus-square fa-2x'></i></a><span class='prize-amount'>"+prize.amount+"</span><a class='plus_square' href='#'><i class='fa fa-plus-square fa-2x'></i></a></span></li>");
    setAmountEvents();
}

function addParticipantsToView(htmlElement)
{
    for(var i = 0; i < participants.length; ++i)
    {
        var name = participants[i].name;
        htmlElement.append(); //TODO Add content
    }
}


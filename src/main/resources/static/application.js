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

function getFirstDraw(eventId)
{
    $.ajax({
        url: "/draw/start",
        cache: false,
        type: "POST",
        contentType: 'application/json',
        dataType: "json",
        processData: false,
        data: JSON.stringify({"event_id": eventId, "prizes": prizes, "participants": participants}),
        complete: function(){},
        success: function(data){

                drawId = data.id;
            console.log(drawId);
        },
        error: function(data, textStatus, xhr){

        }
    });
}

function getWinner(callback, prizeId, drawId)
{
    $.ajax({
        url: "/randomize/get_participant",
        cache: false,
        type: "GET",
        contentType: 'application/json',
        dataType: "json",
        data: {"draw_id": drawId, "prize_id": prizeId},
        complete: function(){},
        success: function(data){
            console.log(data);
            callback(data, prizeId);
        },
        error: function(data, textStatus, xhr){

        }
    });
}

function getHistory(callback)
{
    $.ajax({
        url: "/history",
        cache: false,
        type: "GET",
        contentType: 'application/json',
        dataType: "json",
        complete: function(){},
        success: function(data){
            callback(data);
        },
        error: function(data, textStatus, xhr){

        }
    });
}

function processGetHistory(data)
{
    $("#history_main_div").empty();

    jQuery.each(data, function(eventName, winnersList){
        var winnersHtml = "<div class='list-group'>";
        winnersHtml += "<a href='#' class='list-group-item active'>"+eventName+"</a>";
        for(var i = 0; i < winnersList.length; ++i)
        {
            var winner = JSON.parse(winnersList[i]);
            var winnerName = winner['participant']['name'];
            var prizeName = winner['prizeDefinition']['name'];
            winnersHtml += "<span class='list-group-item'>"+winnerName+"<span class='pull-right'>"+prizeName+"</span></span>";
        }
        $("#history_main_div").append(winnersHtml);
    });

}

function accept(callback, prizeId, drawId, memberId, accepted)
{
    console.log(accepted);
    $.ajax({
        url: "/randomize/accept",
        cache: false,
        type: "GET",
        contentType: 'application/json',
        dataType: "json",
        data: {"draw_id": drawId, "prize_id": prizeId, "participant_id":memberId, "accepted":accepted},
        complete: function(){},
        success: function(data){
        },
        error: function(data, textStatus, xhr){

        }
    });
}

var winnerId;
var currentPrizeIndex = 0;
function showWinner(data, prizeId)
{
    var memberId = data.participant_id;
    winnerId = memberId;
    console.log(winnerId);
    console.log(participants);
    var index = findParticipantIndex(memberId);
    $(".winner-name").empty();
    $(".winner-name").append(participants[index].name);
    $(".prize-name").empty();
    $(".prize-name").append(prizes[currentPrizeIndex].name);
}

function getNextPrize()
{
    if(prizes[currentPrizeIndex].amount <= 0)
    {
        for(var i = currentPrizeIndex + 1; i < prizes.length; ++i)
        {
            if(prizes[i].amount > 0)
            {
                currentPrizeIndex = i;
                return;
            }
        }

        currentPrizeIndex = -1;
    }

    return;
}

function yesClick()
{
    prizes[currentPrizeIndex].amount -= 1;
    accept(null,prizes[currentPrizeIndex].id,drawId,winnerId,true);
    var index = findParticipantIndex(winnerId);
    $("#user_table").append("<tr><td>"+participants[index].name +"</td><td>"+prizes[currentPrizeIndex].name+"</td></tr>");
    getNextPrize();
    if (currentPrizeIndex == -1) {
        $("#draw_button").attr("data-target", "#endModal");
        return;
    }
}
function noClick()
{
    accept(null,prizes[currentPrizeIndex].id,drawId,winnerId,false);
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


var eventId;// = localStorage.getItem("eventId");
//eventId = "201836452";
var appKey;// = localStorage.getItem("appKey");
//appKey = "781d47243d1f565a64a4e7b354e6358";
var participants = new Array();
var prizes = new Array();
var drawId;

function findParticipantIndex(participantId)
{
    for(var i = 0; i < participants.length; ++i)
    {
        if(participants[i].member_id == participantId)
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
        participant.member_id = participantJson.member_id;
        participant.name = participantJson.name;
        participant.id = participantJson.id;
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
    $(".plus_square").unbind("click");
    $(".plus_square").click(function(event){
        var el = $(this);
        var prizeId = el.parent().parent().attr("id");

        var amount = increasePrizeAmount(prizeId);

        var amountEl = $("#" + prizeId + " .prize-amount");
        amountEl.empty();
        amountEl.append(amount);
        event.preventDefault();
    });

    $(".minus_square").unbind("click");
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




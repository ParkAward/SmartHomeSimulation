
var key = getCookie("key");
$("#cleintID").val(key);

if ($("#cleintID").val() != "") { // 그 전에 ID를 저장해서 처음 페이지 로딩 시, 입력 칸에 저장된 ID가 표시된 상태라면,
    $("#idSaveCheck").attr("checked", true); // ID 저장하기를 체크 상태로 두기.
}

$("#idSaveCheck").change(function () { // 체크박스에 변화가 있다면,
    if ($("#idSaveCheck").is(":checked")) { // ID 저장하기 체크했을 때,
        setCookie("key", $("#cleintID").val(), 7); // 7일 동안 쿠키 보관
    } else { // ID 저장하기 체크 해제 시,
        deleteCookie("key");
    }
});

// ID 저장하기를 체크한 상태에서 ID를 입력하는 경우, 이럴 때도 쿠키 저장.
$("#cleintID").keyup(function () { // ID 입력 칸에 ID를 입력할 때,
    if ($("#idSaveCheck").is(":checked")) { // ID 저장하기를 체크한 상태라면,
        setCookie("key", $("#cleintID").val(),7); // 7일 동안 쿠키 보관
    }
});


$("#modal_trigger").leanModal({
    top: 100,
    overlay: 0.6,
    closeButton: ".modal_close"
});
//- $("#search_btn").click(function(){
//-     $("search").submit();
//- });
$("#login_form").click(function () {
    $(".social_login").hide();
    $(".user_login").show();
    return false;
});
$("#register_form").click(function () {
    $(".social_login").hide();
    $(".user_register").show();
    $(".header_title").text('Register');
    return false;
});

$(".back_btn").click(function () {
    $(".user_login").hide();
    $(".user_register").hide();
    $(".social_login").show();
    $(".header_title").text('Login');
    return false;
});




function loginSubmit() {
    if ($("#clientID").val().length > 0) {
        var clientID = $("#clientID").val();
        var topic = $("#topic").val();
        verifyLogin(clientID, topic);
    } else {
        alert("아이디를 입력하지 않았습니다.")
    }
}
function generateSubmit() {
    if ($("#newID").val().length > 0) {
        var newID = $("#newID").val();
        verifyGenerate(newID);
        // alert(newID);
    } else {
        alert("아이디를 입력하지 않았습니다.")
    }
}


function verifyGenerate(newID) {
    var newParams = {
        newID: newID
    }
    $.ajax({
        type: 'POST',
        data: JSON.stringify(newParams),
        contentType: 'application/json',
        url: '/generate',
        success: function (response) {
            // console.log(response);
            if (response.result.startsWith('S')) {
                var newID = $("#newID");
                newID.val(response.clientCode);
                newID.attr("readonly", true);
                $("#re_com").text('use the Client Code for your Arduino');
            }
            alert(response.msg);
        }
    });
}
function verifyLogin(clientID, topic) {
    var params = {
        clientID: clientID,
        topic: topic
    }
    $.ajax({
        type: 'POST',
        data: JSON.stringify(params),
        contentType: 'application/json',
        url: '/login',
        success: function (response) {
            console.log(response);


            if (response.result.startsWith('S')) {
                location.href = "/searchData/" + clientID
            }
            else
                alert(response.msg);
        }
    });
}
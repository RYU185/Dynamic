const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get("id");
console.log(id);


$(document).ready(function () {
    $.ajax({
        url: '/api/notice/all',
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
            $('tbody').empty();
            let count = 1;
            response.forEach((element) => {
                //  가이드 제목, ID, 제목만 표시       
                var $row = $(`<tr class="row">
          <td>${count++}</td>
          <td>${element.noticeTitle}</td>
        </tr>`);
                $('tbody').append($row); // 테이블에 새 행 추가

            });
        }
    })
});

$(document).ready(function () {
    $.ajax({
        url: '/api/notice/id/' + encodeURIComponent(id),
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
            $('.detail_box').empty();
            var row = `
            <div class='title'>공지 상세 내용 </div>
            <div class='description'>${response.text}</div>
            <div class="buttom">
                <div class="content">
                    <div><b>등록 날짜 :</b></div>
                    <div>${response.addDate}</div>
                </div>
                <div class="content">
                    <div><b>수정 날짜 :</b></div>
                    <div>${response.modifiedDate}</div>
                </div>
            </div>
        `;
            $('.detail_box').append(row);
        }
    });
});

$(document).on('click', 'tbody tr', function () {
    $.ajax({
        url: "/api/notice/all",
        method: "get",
        contentType: "application/json",
        success: function (response) {
            $("tbody").empty();
            let count = 1;
            response.forEach((element) => {
                // 공지사항 제목, ID, 작성일만 표시
                var $row = $(`<tr class="row">
                    <td>${count++}</td>
                    <td>${element.noticeTitle}</td>
                  </tr>`);
                $("tbody").append($row); // 테이블에 새 행 추가
                $row.on("click", () => {
                    console.log(element.noticeId + " " + element.noticeTitle);
                    window.location.href = "/notice_detail.html?id=" + element.noticeId;
                });
            });
        },
    });
});


// admin일 경우에만 추가 수정 삭제 버튼 보이도록
const userRole = JSON.parse(sessionStorage.getItem('userName'));
if (userRole === 'admin') {
    document.querySelector('#modify_notice').style.display = 'inline';
    document.querySelector('#delete_notice').style.display = 'inline';
}
const button1 = document.querySelector('#modify_notice');
const content1 = document.querySelector('.pop-up1');
const background1 = document.querySelector('.pop-up-background1');

// 버튼 클릭 시 공지사항 추가 및 공지사항 수정 팝업 보이게 처리

button1.addEventListener('click', function () {
    if (
        content1.style.display === 'none' ||
        content1.style.display === ''
    ) {
        content1.style.display = 'block'; // display: block 으로 변경
        background1.style.display = 'block';
    } else {
        content1.style.display = 'none'; // display: none 으로 다시 변경
        background1.style.display = 'block';
    }
});
document.querySelector('.x_btn1').addEventListener('click', function () {
    document.querySelector('.pop-up1').style.display = 'none';
    document.querySelector('.pop-up-background1').style.display = 'none';
});


// 공지사항 수정!!!!
$(document).on('click', "input[type='button']", function () {
    const title = document.querySelector('.modify_title');
    const text = document.querySelector('.modify_text');
    const currentDate = document.querySelector(".modify_date").textContent = new Date().toLocaleString();

    console.log(title, text, currentDate);

    var sendData = {
        "noticeId": id,
        "noticeTitle": title.innerText,
        "text": text.innerText
    }
    console.log(sendData)

    $.ajax({
        url: '/api/notice/save',
        method: 'POST',
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (response) {
            // alert('공지사항이 정상 수정되었습니다.');

            const modifyNotice = document.createElement('tr');
            modifyNotice.innerHTML = `
                        <td>${response.noticeId}</td>
                        <td>${response.noticeTitle}</td>
                    `;
            window.location.href = "/notice_detail.html?id=" + response.noticeId;
        }
    })

})

$(document).on('click', "#delete_notice", function () {
    const isConfirmed = confirm('공지사항 삭제 처리하도록 할까요 ?')

    if (isConfirmed) {
        $.ajax({
            url: 'api/notice/id/' + encodeURIComponent(id),
            method: 'delete',
            contentType: 'application/json',
            success: function () {
                window.location.href = "/main_notice.html";
            }
        })
    } else {
        alert('삭제 처리가 취소되었습니다');
    }
})



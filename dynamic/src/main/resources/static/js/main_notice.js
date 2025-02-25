
// admin일 경우에만 추가 수정 삭제 버튼 보이도록
const userRole = JSON.parse(sessionStorage.getItem('userName'));
if (userRole === 'admin') {
    document.querySelector('#modify_notice').style.display = 'inline';
    document.querySelector('#add_notice').style.display = 'inline';
    document.querySelector('#delete_notice').style.display = 'inline';
}

const button2 = document.querySelector('#add_notice');
const content2 = document.querySelector('.pop-up2');
const background2 = document.querySelector('.pop-up-background2');

const button1 = document.querySelector('#modify_notice');
const content1 = document.querySelector('.pop-up1');
const background1 = document.querySelector('.pop-up-background1');

// 버튼 클릭 시 display 속성 변경
button2.addEventListener('click', function () {
    if (
        content2.style.display === 'none' ||
        content2.style.display === ''
    ) {
        content2.style.display = 'block'; // display: block 으로 변경
        background2.style.display = 'block';
    } else {
        content2.style.display = 'none'; // display: none 으로 다시 변경
        background2.style.display = 'block';
    }
});

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

function submit_go() {
    let title = document.querySelector("input[id='search_notice']");
    var sendData = title.value; // title 값만 사용

    $.ajax({
        url: '/api/notice/title/' + encodeURIComponent(sendData), // URL에 검색어(title) 추가
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
            if (response.length > 0) {
                $('tbody').empty(); // 기존 테이블 내용 비우기

                response.forEach((element) => {
                    // 공지사항 제목, ID, 작성일만 표시
                    var row = `<tr>
                      <td>${element.noticeId}</td>
                      <td>${element.noticeTitle}</td>
                      <td>${element.addDate}</td>
                    </tr>`;
                    $('tbody').append(row); // 테이블에 새 행 추가
                });
            } else {
                alert('검색 결과가 없습니다.');

            }
        },
        error: function () {
            alert('검색 오류가 발생했습니다.');
        },
    });
}
const come_back = document.querySelector('.main_title h1');
come_back.addEventListener('click', function () {
    window.location.href = 'main_notice.html';
});

document.querySelector(".x_btn1").addEventListener('click', function () {
    document.querySelector('.pop-up1').style.display = 'none';
    document.querySelector('.pop-up-background1').style.display = 'none';
    document.querySelector('.pop-up2').style.display = 'none';
    document.querySelector('.pop-up-background2').style.display = 'none';
});

document.querySelector(".x_btn2").addEventListener('click', function () {
    document.querySelector('.pop-up2').style.display = 'none';
    document.querySelector('.pop-up-background2').style.display = 'none';
});

document.querySelector("input[type='button']").addEventListener('click', function () {
    const title = document.querySelector('.write');
    const text = document.querySelector('.text_content');
    const currentDate = document.querySelector(".add_date").textContent = new Date().toLocaleString();

    console.log(title, text, currentDate);

    var sendData = {
        "noticeId": 0,
        "noticeTitle": title.innerText,
        "text": text.innerText
    }
    console.log(sendData);
    if (title && text) {
        $.ajax({
            url: '/api/notice/save',
            method: "POST",
            data: JSON.stringify(sendData),
            contentType: 'application/json',
            success: function (response) {
                alert("공지사항이 정상 등록되었습니다.");

                // 서버에서 반환한 공지사항 데이터를 기반으로 새 행 추가
                const newNotice = document.createElement('tr');
                newNotice.innerHTML = `
                        <td>${response.noticeId}</td>
                        <td>${response.noticeTitle}</td>
                        <td>${currentDate}</td>
                    `;
                window.location.href = "/main_notice.html";
            }
        });
    }

});

$(document).ready(function () {
    $.ajax({
        url: '/api/notice/all',
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
            $('tbody').empty();
            response.forEach((element) => {
                // 공지사항 제목, ID, 작성일만 표시       
                var row = `<tr>
                  <td>${element.noticeId}</td>
                  <td>${element.noticeTitle}</td>
                  <td>${element.addDate}</td>
                </tr>`;
                $('tbody').append(row); // 테이블에 새 행 추가
            });
        }

    })
})
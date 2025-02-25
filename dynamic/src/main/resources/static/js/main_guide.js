
// admin일 경우에만 추가/ 삭제 버튼 보이도록 처리
const userRole = JSON.parse(sessionStorage.getItem('userName'));
if (userRole === 'admin') {
    document.querySelector('#add_notice').style.display = 'inline';
    document.querySelector('#delete_notice').style.display = 'inline';
}

// 버튼을 클릭하면 가이드 추가 팝업 보이게 처리
const button = document.querySelector('#add_notice');
const content = document.querySelector('.pop-up1');
const background = document.querySelector('.pop-up-background1');

button.addEventListener('click', function () {
    if (
        content.style.display === 'none' ||
        content.style.display === ''
    ) {
        content.style.display = 'block'; // display: block 으로 변경
        background.style.display = 'block';
    }
});
// title을 통한 검색
function submit_go() {
    let title = document.querySelector("input[id='search_guide']");
    var sendData = title.value; // title 값만 사용

    $.ajax({
        url: '/api/guide/title/' + encodeURIComponent(sendData), // URL에 검색어(title) 추가
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
            if (response.length > 0) {
                $('tbody').empty(); // 기존 테이블 내용 비우기

                response.forEach((element) => {
                    // 공지사항 제목, ID, 작성일만 표시
                    var row = `<tr>
                      <td>${element.id}</td>
                      <td>${element.title}</td>
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
// h1 가이드를 클릭하면 원 페이지로 복귀
const come_back = document.querySelector('.main_title h1');
come_back.addEventListener('click', function () {
    window.location.href = 'main_notice.html';
});

// 가이드 작성 X 버튼 누르면 팝업 창 꺼지기
document.querySelector(".x_btn").addEventListener('click', function () {
    document.querySelector('.pop-up1').style.display = 'none';
    document.querySelector('.pop-up-background1').style.display = 'none';
});

// 가이드 추가 후 다시 메인 페이지로 돌아오기
document.querySelector("input[type='button']").addEventListener('click', function () {
    const title = document.querySelector('.write');
    const text = document.querySelector('.text_content');
    const currentDate = document.querySelector(".add_date").textContent = new Date().toLocaleString();

    console.log(title, text, currentDate);

    var sendData = {
        "id": 0,
        "title": title.innerText,
        "text": text.innerText
    }
    console.log(sendData);
    if (title && text) {
        $.ajax({
            url: '/api/guide/save',
            method: "POST",
            data: JSON.stringify(sendData),
            contentType: 'application/json',
            success: function (response) {
                alert("공지사항이 정상 등록되었습니다.");

                // 서버에서 반환한 공지사항 데이터를 기반으로 새 행 추가
                const newNotice = document.createElement('tr');
                newNotice.innerHTML = `
                        <td>${response.id}</td>
                        <td>${response.title}</td>
                        <td>${currentDate}</td>
                    `;
                window.location.href = "/main_guide.html";
            }
        });
    }
});
// 
$(document).ready(function () {
    $.ajax({
        url: '/api/guide/all',
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
            $('tbody').empty();
            response.forEach((element) => {
                // 공지사항 제목, ID, 작성일만 표시       
                var row = `<tr>
                  <td>${element.id}</td>
                  <td>${element.title}</td>
                  <td>${element.addDate}</td>
                </tr>`;
                $('tbody').append(row); // 테이블에 새 행 추가
            });
        }

    })
});
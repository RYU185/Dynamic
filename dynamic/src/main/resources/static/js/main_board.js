const userRole = JSON.parse(sessionStorage.getItem('userName'));
if (userRole != undefined) {
  document.querySelector('#add_board').style.display = 'inline';
}

// 버튼을 클릭하면 가이드 추가 팝업 보이게 처리
const button = document.querySelector('#add_board');
const content = document.querySelector('.pop-up1');
const background = document.querySelector('.pop-up-background1');

button.addEventListener('click', function () {
  if (content.style.display === 'none' || content.style.display === '') {
    content.style.display = 'block'; // display: block 으로 변경
    background.style.display = 'block';
  }
});

// title을 통한 검색
function submit_go() {
  let title = document.querySelector("input[id='search_board']");
  var sendData = title.value; // title 값만 사용

  $.ajax({
    url: '/api/board/title/' + encodeURIComponent(sendData), // URL에 검색어(title) 추가
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      if (response.length > 0) {
        $('tbody').empty(); // 기존 테이블 내용 비우기

        response.forEach((element) => {
          // ID, 본문, 작성일자, 관리자 답변 여부
          var row = `<tr>
                    <td>${element.id}</td>
                    <td>${element.title}</td>
                    <td>${element.addDate}</td>
                     <td>${element.answer}</td>
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

// h1를 클릭하면 원 페이지로 복귀
const come_back = document.querySelector('.main_title h1');
come_back.addEventListener('click', function () {
  window.location.href = 'main_board.html';
});

//  작성 X 버튼 누르면 팝업 창 꺼지기
document.querySelector('.x_btn').addEventListener('click', function () {
  document.querySelector('.pop-up1').style.display = 'none';
  document.querySelector('.pop-up-background1').style.display = 'none';
});

// 추가 후 다시 메인 페이지로 돌아오기
document
  .querySelector("input[type='button']")
  .addEventListener('click', function () {
    const text = document.querySelector('.text_content');
    const currentDate = (document.querySelector('.add_date').textContent =
      new Date().toLocaleString());

    if (!text) {
      alert('내용을 입력해 주세요.');
      return;
    }

    console.log(text, currentDate);

    var sendData = {
      id: 0,
      title: text.innerText,
    };
    console.log(sendData);
    if (sendData.title) {
      $.ajax({
        url: '/api/board/save',
        method: 'POST',
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (response) {
          alert('게시판이 정상 등록되었습니다.');

          // 서버에서 반환한 게시판 데이터를 기반으로 새 행 추가
          const newNotice = document.createElement('tr');
          newNotice.innerHTML = `
                      <td>${response.id}</td>
                      <td>${response.title}</td>
                      <td>${currentDate}</td>
                      <td>${response.answer}</td>
                  `;
          window.location.href = '/main_board.html';
        },
      });
    }
  });

// db랑 front랑 연결하는 코드
$(document).ready(function () {
  $.ajax({
    url: '/api/board/all',
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      console.log(response);
      $('tbody').empty();
      let count = 1;
      response.forEach((element) => {
        if (element.isActive === true) {
          //  가이드 제목, ID, 작성일만 표시
          var $row = $(`<tr class="row">
                <td>${count++}</td>
                <td>${element.title}</td>
                <td>${element.addDate}</td>
                 <td>${element.answer}</td>
              </tr>`);
          $('tbody').append($row); // 테이블에 새 행 추가
          $row.on('click', () => {
            console.log(element.id + ' ' + element.title);
            window.location.href = '/board_detail.html?id=' + element.id;
          });
        }
      });
    },
  });
});

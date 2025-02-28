const userRole = JSON.parse(sessionStorage.getItem('userName'));

// db랑 front랑 연결하는 코드
$(document).ready(function () {
  $.ajax({
    url: '/api/employee/all',
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      AllData(response)
    },
  });
});

const button1 = document.querySelector("#add_employee");
const content1 = document.querySelector(".pop-up1");
const background1 = document.querySelector(".pop-up-background1");

button1.addEventListener('click', function () {
  if (
    content1.style.display === 'none' ||
    content1.style.display === ''
  ) {
    content1.style.display = 'block'; // display: block 으로 변경
    background1.style.display = 'block';
  } else {
    content1.style.display = 'none'; // display: none 으로 다시 변경
    background1.style.display = 'none';
  }
});


document.querySelectorAll('.x_btn').forEach((btn) => {
  btn.addEventListener('click', function () {
    document.querySelector('.pop-up1').style.display = 'none';
    document.querySelector('.pop-up-background1').style.display = 'none';
    document.querySelector('.pop-up2').style.display = 'none';
    document.querySelector('.pop-up-background2').style.display = 'none';
  });
})

const button3 = document.querySelector(".delete_button");

function AllData(response) {
  console.log(response);
  // $('tbody').empty();
  response.forEach((element) => {
    if (element.isActive === true) {
      var $row = $(`<tr class="row">
          <td>${element.name}</td>
          <td>${element.birthday}</td>
          <td>${element.hireDate}</td>
           <td>${element.hourlyRate}</td>
         <td >
              <img src="img/writing.png" alt="수정 버튼" class="modify_button" />
            </td>
            <td >
              <img src="img/쓰레기통 새로운거.png" alt="삭제 버튼" class="delete_button" />
            </td>
        </tr>`);
      $('tbody').append($row); // 테이블에 새 행 추가
      // 연결하는 동시에 직원 수정 버튼 클릭 될 수있도록 처리
      const button2 = document.querySelectorAll(".modify_button");
      const content2 = document.querySelector(".pop-up2");
      const background2 = document.querySelector(".pop-up-background2");
      button2.forEach((button) => {
        button.addEventListener('click', function () {
          if (
            content2.style.display === 'none' ||
            content2.style.display === ''
          ) {
            content2.style.display = 'block'; // display: block 으로 변경
            background2.style.display = 'block';
          } else {
            content2.style.display = 'none'; // display: none 으로 다시 변경
            background2.style.display = 'none';
          }
        });
      })
    }
  });
}

// title을 통한 검색
function submit_go() {
  let name = document.querySelector("input[id='search_employee']");
  var sendData = name.value; // title 값만 사용
  $.ajax({
    url: '/api/employee/name/' + encodeURIComponent(sendData), // URL에 검색어(title) 추가
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      AllData(response);
    },
    error: function () {
      alert('검색 오류가 발생했습니다.');
    },
  });
}
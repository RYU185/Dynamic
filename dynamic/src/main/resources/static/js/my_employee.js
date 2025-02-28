const userRole = JSON.parse(sessionStorage.getItem('userName'));

// db랑 front랑 연결하는 코드
$(document).ready(function () {
  $.ajax({
    url: '/api/employee/all',
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
        }
      });
    },
  });
});

const button1 = document.querySelector("#add_employee");
const content1 = document.querySelector(".pop-up1");
const background1 = document.querySelector(".pop-up-background1");



const button3 = document.querySelector(".delete_button");


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

const button2 = document.querySelector(".modify_button");
const content2 = document.querySelector(".pop-up2");
const background2 = document.querySelector(".pop-up-background2");

button2.addEventListener('click', function () {
  console.log(button2, content2, background2)
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

document.querySelector('.x_btn').addEventListener('click', function () {
  document.querySelector('.pop-up1').style.display = 'none';
  document.querySelector('.pop-up-background1').style.display = 'none';
  document.querySelector('.pop-up2').style.display = 'none';
  document.querySelector('.pop-up-background2').style.display = 'none';
});

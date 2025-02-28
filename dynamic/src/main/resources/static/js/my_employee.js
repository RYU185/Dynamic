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


function AllData(response) {
  console.log(response);
  $('tbody').empty(); // 기존 테이블 내용 비우기
  response.forEach((element) => {
    if (element.isActive === true && element.freeTemplate === false) {
      var $row = $(`<tr class="row" id="${element.id}">
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
      // $row.on("click", () => {
      //   // console.log(element.id + " " + element.name);
      //   window.location.href = "/my_employee.html?id=" + element.id;
      // });

      // 나의 직원 급여명세서 작성 페이지로 이동
      const write = document.querySelector('.write');
      $(write).on("click", () => {
        console.log(element.id + " " + element.name);
        window.location.href = "/my_employee_payrolltemplate.html?id=" + element.id;
      });
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
  // $(document).on('click', 'tr', function () {
  //   const id = document.querySelector('tr');
  //   console.log(id.id);
  // })


  // 해당 직원 삭제처리
  $(document).on('click', '.delete_button', function () {
    const id = document.querySelector('.delete_button');
    const parent = id.closest('tr');
    console.log(parent.id);
    const isConfirmed = confirm('해당 직원을 삭제 처리하도록 할까요 ?');
    if (isConfirmed) {
      $.ajax({
        url: 'api/employee/delete/' + encodeURIComponent(parent.id),
        method: 'POST',
        contentType: 'application/json',
        success: function () {
          window.location.href = '/my_employee.html';
        },
      });
    } else {
      alert('삭제 처리가 취소되었습니다');
    }
  });


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

  document.querySelector('.add').addEventListener('click', function () {
    const name = document.querySelector('#name');
    const birthday = document.querySelector('#birthday');
    const position = document.querySelector('#position');
    const department = document.querySelector('#department');
    const hiredate = document.querySelector('#hiredate');
    const phone = document.querySelector('#phone');
    const hourly_rate = document.querySelector('#hourly_rate');

    console.log(name, birthday, position, department, hiredate, phone, hourly_rate);

    var sendData = {
      "id": 0,
      "name": name.innerText,
      "department": department.innerText,
      "position": position.innerText,
      "hourlyRate": hourly_rate.innerText,
      "birthday": birthday.innerText,
      "hireDate": hiredate.innerText,
      "phoneNumber": phone.innerText
    }
    console.log(sendData);
    if (name &&
      birthday &&
      position &&
      department &&
      hiredate &&
      hourly_rate &&
      phone) {
      $.ajax({
        url: '/api/employee/save',
        method: 'POST',
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (response) {
          alert("직원이 정상 등록되었습니다.");
          var $row = $(`<tr class="row">
          <td>${response.name}</td>
          <td>${response.birthday}</td>
          <td>${response.hireDate}</td>
           <td>${response.hourlyRate}</td>
         <td >
              <img src="img/writing.png" alt="수정 버튼" class="modify_button" />
            </td>
            <td >
              <img src="img/쓰레기통 새로운거.png" alt="삭제 버튼" class="delete_button" />
            </td>
        </tr>`);
          $('tbody').append($row);

          window.location.href = '/my_employee.html';
        }
      });
    }
  });
}

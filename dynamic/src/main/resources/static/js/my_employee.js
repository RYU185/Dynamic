const userRole = JSON.parse(sessionStorage.getItem('userName'));

$(document).on('input', '.amount', function () {
  let value = $(this)
    .val()
    .replace(/[^0-9]/g, ''); // 숫자 외의 문자 제거
  if (value) {
    // 3자리마다 ',' 추가
    value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  }
  $(this).val(value); // 포맷팅된 값을 input에 설정
});

document.querySelectorAll('.date').forEach(function (inputField) {
  inputField.addEventListener('input', function (e) {
    let value = e.target.value.replace(/[^0-9]/g, ''); // 숫자만 남기기
    if (value.length <= 4) {
      e.target.value = value; // 연도만 입력되었을 경우
    } else if (value.length <= 6) {
      e.target.value = value.slice(0, 4) + '-' + value.slice(4); // 연도-월
    } else {
      e.target.value =
        value.slice(0, 4) + '-' + value.slice(4, 6) + '-' + value.slice(6, 8); // 연도-월-일
    }
  });
});

document.querySelectorAll('.phone').forEach(function (inputField) {
  inputField.addEventListener('input', function (e) {
    let value = e.target.value.replace(/\D/g, ''); // 숫자만 남기기

    // 10자리 전화번호 형식 000-0000-0000으로 제한
    if (value.length <= 3) {
      e.target.value = value; // 3자리까지만 입력
    } else if (value.length <= 7) {
      e.target.value = value.slice(0, 3) + '-' + value.slice(3); // 3자리-4자리
    } else {
      e.target.value =
        value.slice(0, 3) + '-' + value.slice(3, 7) + '-' + value.slice(7, 11); // 3자리-4자리-4자리
    }
  });
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
      formatSpecificCells();
    },
    error: function () {
      $('tbody').empty();
    },
  });
}

// db랑 front랑 연결하는 코드
$(document).ready(function () {
  $.ajax({
    url: '/api/employee/all',
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      AllData(response);
      formatSpecificCells();
    },

  });
});

const button1 = document.querySelector('#add_employee');
const content1 = document.querySelector('.pop-up1');
const background1 = document.querySelector('.pop-up-background1');

button1.addEventListener('click', function () {
  if (content1.style.display === 'none' || content1.style.display === '') {
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
});

function AllData(response) {
  console.log(response);
  $('tbody').empty(); // 기존 테이블 내용 비우기
  response.forEach((element) => {
    if (element.isActive === true && element.freeTemplate === false) {
      var $row = $(`<tr class="row" id="${element.id}">
          <td >${element.name}</td>
          <td class="birthday">${element.birthday}</td>
          <td class="hiredate">${element.hireDate}</td>
           <td class="hourly_rate">${element.hourlyRate}</td>
         <td >
              <img src="img/writing.png" alt="수정 버튼" class="modify_button" />
            </td>
            <td >
              <img src="img/쓰레기통 새로운거.png" alt="삭제 버튼" class="delete_button" />
            </td>
        </tr>`);
      $('tbody').append($row);
      // 나의 직원 급여명세서 작성 페이지로 이동

      document.querySelector('.write').addEventListener('click', () => {
        window.location.href = '/my_employee_payrolltemplate_information.html';
      });
      // 연결하는 동시에 직원 수정 버튼 클릭 될 수있도록 처리
      const content2 = document.querySelector('.pop-up2');
      const background2 = document.querySelector('.pop-up-background2');
      const modify_button = $row.find('.modify_button');
      modify_button.on('click', function (e) {
        e.target;
        if (
          content2.style.display === 'none' ||
          content2.style.display === ''
        ) {
          content2.style.display = 'block'; // display: block 으로 변경
          background2.style.display = 'block';
          document.querySelector('#employee_id').value = element.id;
          document.querySelector('#name1').value = element.name;
          document.querySelector('#position1').value = element.position;
          document.querySelector('#department1').value = element.department;
          document.querySelector('#phone1').value = element.phoneNumber;
          document.querySelector('#hourly_rate1').value =
            element.hourlyRate.toLocaleString();
        } else {
          content2.style.display = 'none'; // display: none 으로 다시 변경
          background2.style.display = 'none';
        }
      });
      // 해당 직원 삭제처리
      //  중첩의 문제를 해결하기 위해 하나의 row가 생성될때마다 event 달기
      const delete_button = $row.find('.delete_button');
      delete_button.on('click', function (e) {
        const button = e.target;
        const parent = button.closest('tr');
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
    }
  });
  $(document).on('click', '.modify', function (e) {
    e.target;

    const name = document.querySelector('#name1').value.trim();
    const position = document.querySelector('#position1').value.trim();
    const department = document.querySelector('#department1').value.trim();
    const phone = document.querySelector('#phone1').value.trim();
    const hourly_rate =
      document.querySelector('#hourly_rate1').value.trim().replace(/,/g, '') ||
      0;
    const birthday = document.querySelector('.birthday').value;
    const hiredate = document.querySelector('.hiredate').value;
    const employee_id = document.querySelector('#employee_id').value;

    var sendData = {
      id: employee_id,
      name: name,
      department: department,
      position: position,
      hourlyRate: hourly_rate,
      birthday: birthday,
      hireDate: hiredate,
      phoneNumber: phone,
    };
    console.log(sendData);

    $.ajax({
      url: '/api/employee/update',
      method: 'PUT',
      data: JSON.stringify(sendData),
      contentType: 'application/json',
      success: function (response) {
        console.log(response);
        alert('직원이 정상 수정되었습니다.');
        window.location.href = 'my_employee.html';
      },
    });
  });
  document.querySelector('.add').addEventListener('click', function () {
    const name = document.querySelector('#name').value.trim();
    const birthday = document.querySelector('#birthday').value;
    const position = document.querySelector('#position').value.trim();
    const department = document.querySelector('#department').value.trim();
    const hiredate = document.querySelector('#hiredate').value;
    const phone = document.querySelector('#phone').value;
    const hourly_rate =
      document.querySelector('#hourly_rate').value.trim().replace(/,/g, '') ||
      0;

    console.log(
      name,
      birthday,
      position,
      department,
      hiredate,
      phone,
      hourly_rate
    );

    var sendData = {
      id: 0,
      name: name,
      department: department,
      position: position,
      hourlyRate: hourly_rate.replace(/,/g, ''),
      birthday: birthday,
      hireDate: hiredate,
      phoneNumber: phone,
    };
    console.log(sendData);
    if (
      name &&
      birthday &&
      position &&
      department &&
      hiredate &&
      hourly_rate &&
      phone
    ) {
      $.ajax({
        url: '/api/employee/save',
        method: 'POST',
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (response) {
          alert('직원이 정상 등록되었습니다.');
          var $row = $(`<tr  class="row" id="${response.id}">
          <td >${response.name}</td>
          <td class="birthday">${response.birthday}</td>
          <td class="hire_date">${response.hireDate}</td>
           <td class="hourly_rate">${response.hourlyRate}</td>
         <td >
              <img src="img/writing.png" alt="수정 버튼" class="modify_button" />
            </td>
            <td >
              <img src="img/쓰레기통 새로운거.png" alt="삭제 버튼" class="delete_button" />
            </td>
        </tr>`);
          $('tbody').append($row);
          window.location.href = '/my_employee.html';
        },
      });
    }
  });
}



function formatSpecificCells() {
  // 'price' 클래스를 가진 td 요소만 선택
  const priceCells = document.querySelectorAll('.hourly_rate');

  priceCells.forEach(cell => {
    const price = parseInt(cell.textContent, 10); // 텍스트를 숫자로 변환

    if (!isNaN(price)) {
      // 천 단위 구분 기호 추가
      cell.textContent = price.toLocaleString();
    }
  });
}



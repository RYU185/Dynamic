const userRole = JSON.parse(sessionStorage.getItem('userName'));

$(document).ready(function () {
  $.ajax({
    url: 'api/employee/free-template-count',
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      console.log(response);
      if (userRole != 'admin') {
        if (response >= 5) {
          alert('이용 가능한 무료 횟수가 다 소진되었습니다');
          window.location.href = '/after_login.html';
        }
      }
    },
  });
});

$(document).on('input', '#hourly_rate', function () {
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

document.querySelectorAll('#phone').forEach(function (inputField) {
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

// 정보 입력을 추가 하지 않으면 안보이도록 설정
$(document).on('click', '.show_calendar', function () {
  // contenteditable로 설정된 값 가져오기
  const name = document.querySelector('#employee_name').value.trim();
  const birthday = document.querySelector('#birthday').value.trim();
  const position = document.querySelector('#position').value.trim();
  const department = document.querySelector('#department').value.trim();
  const hiredate = document.querySelector('#hiredate').value.trim();
  const phone = document.querySelector('#phone').value.trim();
  const start_date = document.querySelector('#start_date').value.trim();
  const last_date = document.querySelector('#last_date').value.trim();
  const hourly_rate = document.querySelector('#hourly_rate').value.trim();
  const payment_date = document.querySelector('#payment_date').value.trim();
  const calendar = document.querySelector('.down'); // 달력 요소

  // 모든 필드가 입력되었는지 확인
  if (
    name &&
    birthday &&
    position &&
    department &&
    hiredate &&
    phone &&
    start_date &&
    last_date &&
    hourly_rate &&
    payment_date
  ) {
    // 모든 필드가 입력되었으면 달력 보이기
    if (calendar.style.display !== 'block') {
      calendar.style.display = 'block'; // 달력 보이기
    }
  } else {
    alert('모든 정보를 입력해야 달력을 생성할 수 있습니다.');
    calendar.style.display = 'none'; // 필드가 다 입력되지 않으면 달력 숨기기
  }
});

// 입력된 값을 세션에 저장하고 급여명세서 양식 페이지로 이동
$(document).on('click', '.apply', function () {
  const name = document.querySelector('#employee_name').value.trim();
  const birthday = document.querySelector('#birthday').value.trim();
  const position = document.querySelector('#position').value.trim();
  const department = document.querySelector('#department').value.trim();
  const hiredate = document.querySelector('#hiredate').value.trim();
  const phone = document.querySelector('#phone').value.trim();
  const start_date = document.querySelector('#start_date').value.trim();
  const last_date = document.querySelector('#last_date').value.trim();
  const hourly_rate = document.querySelector('#hourly_rate').value.trim();
  const payment_date = document.querySelector('#payment_date').value.trim();
  const company_name = document.querySelector('#company_name').value.trim();

  var sendData = {
    payrollTemplateDTO: {
      id: 0,
      startPayrollPeriod: start_date,
      lastPayrollPeriod: last_date,
      paymentDate: payment_date,
    },
    employeeDTO: {
      id: 0,
      name: name,
      department: department,
      position: position,
      hourlyRate: hourly_rate.replace(/,/g, ''),
      birthday: birthday,
      hireDate: hiredate,
      phoneNumber: phone,
      companyName: company_name,
    },
  };
  console.log(sendData);
  sessionStorage.setItem('userData', JSON.stringify(sendData));
  window.location.href = '/payrolltemplate.html';
});

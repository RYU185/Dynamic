const userRole = JSON.parse(sessionStorage.getItem('userName'));

$(document).ready(function () {
  $.ajax({
    url: 'api/employee/free-template-count',
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      console.log(response);
      if (userCopy != 'admin') {
        if (response > 5) {
          alert('이용 가능한 무료 횟수가 다 소진되었습니다');
          return;
        }
        window.location.href = '/after_login.html';
      }
    },
  });
});

// 정보 입력을 추가 하지 않으면 안보이도록 설정
$(document).on('click', '.show_calendar', function () {
  // contenteditable로 설정된 값 가져오기
  const name = document.querySelector('#employee_name').innerText.trim();
  const birthday = document.querySelector('#birthday').innerText.trim();
  const position = document.querySelector('#position').innerText.trim();
  const department = document.querySelector('#department').innerText.trim();
  const hiredate = document.querySelector('#hiredate').innerText.trim();
  const phone = document.querySelector('#phone').innerText.trim();
  const start_date = document.querySelector('#start_date').innerText.trim();
  const last_date = document.querySelector('#last_date').innerText.trim();
  const hourly_rate = document.querySelector('#hourly_rate').innerText.trim();
  const payment_date = document.querySelector('#payment_date').innerText.trim();
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
  const name = document.querySelector('#employee_name');
  const birthday = document.querySelector('#birthday');
  const position = document.querySelector('#position');
  const department = document.querySelector('#department');
  const hiredate = document.querySelector('#hiredate');
  const phone = document.querySelector('#phone');
  const start_date = document.querySelector('#start_date');
  const last_date = document.querySelector('#last_date');
  const hourly_rate = document.querySelector('#hourly_rate');
  const payment_date = document.querySelector('#payment_date');

  var sendData = {
    payrollTemplateDTO: {
      id: 0,
      startPayrollPeriod: start_date.innerText,
      lastPayrollPeriod: last_date.innerText,
      paymentDate: payment_date.innerText,
    },
    employeeDTO: {
      id: 0,
      name: name.innerText,
      department: department.innerText,
      position: position.innerText,
      hourlyRate: hourly_rate.innerText,
      birthday: birthday.innerText,
      hireDate: hiredate.innerText,
      phoneNumber: phone.innerText,
    },
  };
  console.log(sendData);
  sessionStorage.setItem('userData', JSON.stringify(sendData));
  window.location.href = '/payrolltemplate.html';
});

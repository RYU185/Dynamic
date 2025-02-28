const userRole = JSON.parse(sessionStorage.getItem('userName'));
const userData = sessionStorage.getItem('userData');
const data = JSON.parse(userData);
// 데이터를 페이지에 반영
const employee = data.employeeDTO;
const payroll = data.payrollTemplateDTO;
const limit_name = document.querySelector('#limit_name');
const limit_num = document.querySelector('#limit_num');
// db랑 front랑 연결하는 코드
$(document).ready(function () {
  $.ajax({
    url: 'api/employee/free-template-count',
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      if (userRole != 'admin') {
        if (
          limit_name.style.display != 'inline' ||
          limit_num.style.display != 'block'
        ) {
          limit_name.style.display = 'inline';
          limit_num.style.display = 'block';
        }
      } else {
        limit_name.style.display = 'none';
        limit_num.style.display = 'none';
      }
      let count = response;
      $('#limit_num').text(5 - count + '/5');
    },
  });
  $('#employee_name').text(employee.name);
  $('#birthday').text(employee.birthday);
  $('#position').text(employee.position);
  $('#department').text(employee.department);
  $('#hiredate').text(employee.hireDate);
  $('#phone').text(employee.phoneNumber);
  $('#start_date').text(payroll.startPayrollPeriod);
  $('#last_date').text(payroll.lastPayrollPeriod);
  $('#company_name').text(employee.companyName);
  $('#payment_date').text(payroll.paymentDate);
});
$(document).on('input', 'input[type="text"]', function () {
  let value = $(this)
    .val()
    .replace(/[^0-9]/g, ''); // 숫자 외의 문자 제거
  if (value) {
    // 3자리마다 ',' 추가
    value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  }
  $(this).val(value); // 포맷팅된 값을 input에 설정
});

$(document).on('click', '#send', function () {
  console.log(limit_num.innerText);
  if (userRole != 'admin') {
    if (limit_num.innerText === '0/5') {
      alert('이용 가능한 무료 횟수가 다 소진되었습니다');
      return;
    }
  }
  var sendData = {
    employeeDTO: {
      id: 0,
      name: employee.name,
      department: employee.department,
      position: employee.position,
      hourlyRate: employee.hourlyRate,
      birthday: employee.birthday,
      hireDate: employee.hireDate,
      phoneNumber: employee.phoneNumber,
    },
    payrollTemplateDTO: {
      id: 0,
      startPayrollPeriod: payroll.startPayrollPeriod,
      lastPayrollPeriod: payroll.lastPayrollPeriod,
      paymentDate: payroll.paymentDate,
    },
  };

  $.ajax({
    url: '/api/employee/use/free-payrolltemplate',
    method: 'POST',
    data: JSON.stringify(sendData),
    contentType: 'application/json',
    success: function (response) {
      window.location.href = '/payrolltemplate.html';
    },
  });
});
// if (count > 0) {
//   count--; // 카운트 감소
//   sessionStorage.setItem('count', count); // 변경된 count 값을 세션 스토리지에 저장
//   $('p').text(count + '/5'); // p 태그 안의 텍스트 변경
//   window.location.href = '/payrolltemplate.html';
// } else {
//   alert('이용 가능한 무료 횟수가 다 소진되었습니다');
//   return;
// }

const userRole = JSON.parse(sessionStorage.getItem('userName'));
const employeeData = sessionStorage.getItem('employeeData');
const payrolltemplateData = sessionStorage.getItem('payrolltemplateData');
const employee = JSON.parse(employeeData);
const payrolltemplate = JSON.parse(payrolltemplateData);

$(document).ready(function () {
  $('#employee_name').text(employee.name);
  $('#birthday').text(employee.birthday);
  $('#position').text(employee.position);
  $('#department').text(employee.department);
  $('#hiredate').text(employee.hireDate);
  $('#phone').text(employee.phoneNumber);
  $('#start_date').text(payrolltemplate.startPayrollPeriod);
  $('#last_date').text(payrolltemplate.lastPayrollPeriod);
  $('#company_name').text(employee.companyName);
  $('#payment_date').text(payrolltemplate.paymentDate);
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
  var sendData = {
    id: 0,
    startPayrollPeriod: payrolltemplate.startPayrollPeriod,
    lastPayrollPeriod: payrolltemplate.lastPayrollPeriod,
    paymentDate: payrolltemplate.paymentDate,
    employeeId: employee.id,
  };

  $.ajax({
    url: '/api/payrolltemplate/save',
    method: 'POST',
    data: JSON.stringify(sendData),
    contentType: 'application/json',
    success: function (response) {
      console.log(response);
      window.location.href = '/my_employee_payrolltemplate_information.html';
    },
  });
});
// 모든 input 필드 초기화
$('#reset').on('click', function () {
  $('input[type="text"]').val('');
});

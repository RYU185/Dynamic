const userRole = JSON.parse(sessionStorage.getItem('userName'));

// db랑 front랑 연결하는 코드
$(document).ready(function () {
  const userData = sessionStorage.getItem('userData');
  if (userData) {
    const data = JSON.parse(userData);

    // 데이터를 페이지에 반영
    const employee = data.employeeDTO;
    const payroll = data.payrollTemplateDTO;

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
  }
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

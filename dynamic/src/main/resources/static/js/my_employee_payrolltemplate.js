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
// 총 급여액 계산!!
$('#reset').on('click', function () {
  $('input[type="text"]').val('');
});

document
  .querySelectorAll(
    '#salary, #bonus, #meal, #transfer, #night, #holiday, #other_allowance'
  )
  .forEach((element) => {
    element.addEventListener('input', function () {
      // 각 입력값을 숫자로 변환 (빈 문자열을 처리하려면 0으로 기본값을 설정)
      const salary =
        parseFloat(document.querySelector('#salary').value.replace(/,/g, '')) ||
        0;
      const bonus =
        parseFloat(document.querySelector('#bonus').value.replace(/,/g, '')) ||
        0;
      const meal =
        parseFloat(document.querySelector('#meal').value.replace(/,/g, '')) ||
        0;
      const transfer =
        parseFloat(
          document.querySelector('#transfer').value.replace(/,/g, '')
        ) || 0;
      const night =
        parseFloat(document.querySelector('#night').value.replace(/,/g, '')) ||
        0;
      const holiday =
        parseFloat(
          document.querySelector('#holiday').value.replace(/,/g, '')
        ) || 0;
      const other_allowance =
        parseFloat(
          document.querySelector('#other_allowance').value.replace(/,/g, '')
        ) || 0;

      // 총합 계산
      const total =
        salary + bonus + meal + transfer + night + holiday + other_allowance;
      console.log(total);
      // 계산된 총합을 total_amount 요소에 표시
      document.querySelector('#total_amount').value = total.toLocaleString();
    });
  });

//총 공제액
document
  .querySelectorAll(
    '#health_insurance, #national_pension, #employeement_insurance, #income_tax, #local_tax, #freelancer_income, #freelancer_local,#other'
  )
  .forEach((element) => {
    element.addEventListener('input', function () {
      // 각 입력값을 숫자로 변환 (빈 문자열을 처리하려면 0으로 기본값을 설정)
      const health_insurance =
        parseFloat(
          document.querySelector('#health_insurance').value.replace(/,/g, '')
        ) || 0;
      const national_pension =
        parseFloat(
          document.querySelector('#national_pension').value.replace(/,/g, '')
        ) || 0;
      const employeement_insurance =
        parseFloat(
          document
            .querySelector('#employeement_insurance')
            .value.replace(/,/g, '')
        ) || 0;
      const income_tax =
        parseFloat(
          document.querySelector('#income_tax').value.replace(/,/g, '')
        ) || 0;
      const local_tax =
        parseFloat(
          document.querySelector('#local_tax').value.replace(/,/g, '')
        ) || 0;
      const freelancer_income =
        parseFloat(
          document.querySelector('#freelancer_income').value.replace(/,/g, '')
        ) || 0;
      const freelancer_local =
        parseFloat(
          document.querySelector('#freelancer_local').value.replace(/,/g, '')
        ) || 0;

      const other =
        parseFloat(document.querySelector('#other').value.replace(/,/g, '')) ||
        0;

      // 총합 계산
      const total =
        health_insurance +
        national_pension +
        employeement_insurance +
        income_tax +
        local_tax +
        freelancer_income +
        freelancer_local +
        other;

      // 계산된 총합을 total_amount 요소에 표시
      console.log(total);
      document.querySelector('#total_insurance').value = total.toLocaleString();
    });
  });

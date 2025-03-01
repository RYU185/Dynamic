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
// 모든 input 필드 초기화
$('#reset').on('click', function () {
  $('input[type="text"]').val('');
});

// $(document).ready(function () {
//   // 총 급여 계산
//   function calculateTotalAmount() {
//     const salary = parseFloat($('#salary').val()) || 0;
//     const bonus = parseFloat($('#bonus').val()) || 0;
//     const meal = parseFloat($('#meal').val()) || 0;
//     const transfer = parseFloat($('#transfer').val()) || 0;
//     const night = parseFloat($('#night').val()) || 0;
//     const holiday = parseFloat($('#holiday').val()) || 0;
//     const other_allowance = parseFloat($('#other_allowance').val()) || 0;

//     const totalAmount =
//       salary + bonus + meal + transfer + night + holiday + other_allowance;
//     $('#total_amount').val(totalAmount); // 총 급여액 업데이트
//     calculateFinalPayment(); // 최종 지급액 계산
//   }

//   // 총 공제액 계산
//   function calculateTotalInsurance() {
//     const health_insurance = parseFloat($('#health_insurance').val()) || 0;
//     const national_pension = parseFloat($('#national_pension').val()) || 0;
//     const employeement_insurance =
//       parseFloat($('#employeement_insurance').val()) || 0;
//     const income_tax = parseFloat($('#income_tax').val()) || 0;
//     const local_tax = parseFloat($('#local_tax').val()) || 0;
//     const freelancer_income = parseFloat($('#freelancer_income').val()) || 0;
//     const freelancer_local = parseFloat($('#freelancer_local').val()) || 0;
//     const other = parseFloat($('#other').val()) || 0;

//     const totalInsurance =
//       health_insurance +
//       national_pension +
//       employeement_insurance +
//       income_tax +
//       local_tax +
//       freelancer_income +
//       freelancer_local +
//       other;
//     $('#total_insurance').val(totalInsurance); // 총 공제액 업데이트
//     calculateFinalPayment(); // 최종 지급액 계산
//   }

//   // 최종 지급액 계산
//   function calculateFinalPayment() {
//     const total_amount = parseFloat($('#total_amount').val()) || 0;
//     const total_insurance = parseFloat($('#total_insurance').val()) || 0;

//     const finalPayment = total_amount - total_insurance;
//     $('#final_payment').val(finalPayment); // 최종 지급액 업데이트
//   }

//   // 입력 필드 변화에 따른 계산
//   $('#salary, #bonus, #meal, #transfer, #night, #holiday, #other_allowance').on(
//     'input',
//     function () {
//       calculateTotalAmount(); // 총 급여 계산
//     }
//   );

//   $(
//     '#health_insurance, #national_pension, #employeement_insurance, #income_tax, #local_tax, #freelancer_income, #freelancer_local, #other'
//   ).on('input', function () {
//     calculateTotalInsurance(); // 총 공제액 계산
//   });
// });

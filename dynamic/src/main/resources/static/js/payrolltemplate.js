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
  const total_amount = document.querySelector('#total_amount').value.replace(/,/g, "");
  const final_payment = document.querySelector('#final_payment').value.replace(/,/g, "");
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
      totalAmount: total_amount,
      finalPayment: final_payment
    },
  };

  $.ajax({
    url: '/api/employee/use/free-payrolltemplate',
    method: 'POST',
    data: JSON.stringify(sendData),
    contentType: 'application/json',
    success: function (response) {
      alert('발급하신 급여명세서는 나의 급여명세서 목록에서 조회 가능합니다')
      window.location.href = '/payrolltemplate.html';
    },
  });
});
// 모든 input 필드 초기화
$('#reset').on('click', function () {
  $('input[type="text"]').val('');
});

document
  .querySelectorAll(
    "#salary, #bonus, #meal, #transfer, #night, #holiday, #other_allowance"
  )
  .forEach((element) => {
    element.addEventListener("change", function () {
      // 각 입력값을 숫자로 변환 (빈 문자열을 처리하려면 0으로 기본값을 설정)
      const salary =
        parseFloat(document.querySelector("#salary").value.replace(/,/g, "")) ||
        0;
      const bonus =
        parseFloat(document.querySelector("#bonus").value.replace(/,/g, "")) ||
        0;
      const meal =
        parseFloat(document.querySelector("#meal").value.replace(/,/g, "")) ||
        0;
      const transfer =
        parseFloat(
          document.querySelector("#transfer").value.replace(/,/g, "")
        ) || 0;
      const night =
        parseFloat(document.querySelector("#night").value.replace(/,/g, "")) ||
        0;
      const holiday =
        parseFloat(
          document.querySelector("#holiday").value.replace(/,/g, "")
        ) || 0;
      const other_allowance =
        parseFloat(
          document.querySelector("#other_allowance").value.replace(/,/g, "")
        ) || 0;

      // 총합 계산
      const total =
        salary + bonus + meal + transfer + night + holiday + other_allowance;
      console.log(total);
      // 계산된 총합을 total_amount 요소에 표시
      document.querySelector("#total_amount").value = total.toLocaleString();
      document
        .querySelector("#final_payment")
        .dispatchEvent(new Event("change"));
    });
  });

//총 공제액
document
  .querySelectorAll(
    "#health_insurance, #national_pension, #employeement_insurance, #income_tax, #local_tax, #freelancer_income, #freelancer_local,#other"
  )
  .forEach((element) => {
    element.addEventListener("change", function () {
      // 각 입력값을 숫자로 변환 (빈 문자열을 처리하려면 0으로 기본값을 설정)
      const health_insurance =
        parseFloat(
          document.querySelector("#health_insurance").value.replace(/,/g, "")
        ) || 0;
      const national_pension =
        parseFloat(
          document.querySelector("#national_pension").value.replace(/,/g, "")
        ) || 0;
      const employeement_insurance =
        parseFloat(
          document
            .querySelector("#employeement_insurance")
            .value.replace(/,/g, "")
        ) || 0;
      const income_tax =
        parseFloat(
          document.querySelector("#income_tax").value.replace(/,/g, "")
        ) || 0;
      const local_tax =
        parseFloat(
          document.querySelector("#local_tax").value.replace(/,/g, "")
        ) || 0;
      const freelancer_income =
        parseFloat(
          document.querySelector("#freelancer_income").value.replace(/,/g, "")
        ) || 0;
      const freelancer_local =
        parseFloat(
          document.querySelector("#freelancer_local").value.replace(/,/g, "")
        ) || 0;

      const other =
        parseFloat(document.querySelector("#other").value.replace(/,/g, "")) ||
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
      document.querySelector("#total_insurance").value = total.toLocaleString();
      document
        .querySelector("#final_payment")
        .dispatchEvent(new Event("change"));
    });
  });

function calculateFinalPayment() {
  const total_amount =
    parseFloat(
      document.querySelector("#total_amount").value.replace(/,/g, "")
    ) || 0;
  const total_insurance =
    parseFloat(
      document.querySelector("#total_insurance").value.replace(/,/g, "")
    ) || 0;
  const total = total_amount - total_insurance;
  document.querySelector("#final_payment").value = total.toLocaleString();
}

document
  .querySelector("#final_payment")
  .addEventListener("change", calculateFinalPayment);

const userRole = JSON.parse(sessionStorage.getItem("userName"));

$(document).ready(function () {
  $.ajax({
    url: "/api/employee/all",
    method: "get",
    contentType: "application/json",
    success: function (response) {
      AllData(response);
    },
  });
});

function AllData(response) {
  console.log(response);
  $("tbody").empty(); // 기존 테이블 내용 비우기
  response.forEach((element) => {
    if (element.isActive === true && element.freeTemplate === false) {
      var $row = $(`<tr class="row" id="${element.id}">
          <td >${element.name}</td>
          <td >${element.birthday}</td>
        </tr>`);
      $("tbody").append($row);
      $row.on("click", () => {
        var employeeData = {
          id: element.id,
          name: element.name,
          department: element.department,
          position: element.position,
          hourlyRate: element.hourlyRate,
          birthday: element.birthday,
          hireDate: element.hireDate,
          phoneNumber: element.phoneNumber,
          companyName: element.companyName,
        };
        console.log(employeeData);
        sessionStorage.setItem("employeeData", JSON.stringify(employeeData));
        window.location.href =
          "/my_employee_payrolltemplate_information.html?id=" + element.id;
      });
    }
  });
}

document.querySelectorAll(".date").forEach(function (inputField) {
  inputField.addEventListener("input", function (e) {
    let value = e.target.value.replace(/[^0-9]/g, ""); // 숫자만 남기기
    if (value.length <= 4) {
      e.target.value = value; // 연도만 입력되었을 경우
    } else if (value.length <= 6) {
      e.target.value = value.slice(0, 4) + "-" + value.slice(4); // 연도-월
    } else {
      e.target.value =
        value.slice(0, 4) + "-" + value.slice(4, 6) + "-" + value.slice(6, 8); // 연도-월-일
    }
  });
});

$(document).on("click", ".show_calendar", function () {
  // contenteditable로 설정된 값 가져오기
  const start_date = document.querySelector("#start_date").value.trim();
  const last_date = document.querySelector("#last_date").value.trim();
  const payment_date = document.querySelector("#payment_date").value.trim();
  const calendar = document.querySelector(".down"); // 달력 요소

  // 모든 필드가 입력되었는지 확인
  if (start_date && last_date && payment_date) {
    // 모든 필드가 입력되었으면 달력 보이기
    if (!calendar.classList.contains("active")) {
      calendar.classList.add("active");
    } else {
      calendar.classList.remove("active");
    }
  } else {
    alert("모든 정보를 입력해야 달력을 생성할 수 있습니다.");
  }
});

// 입력된 값을 세션에 저장하고 급여명세서 양식 페이지로 이동

const employeeData = sessionStorage.getItem("employeeData");
const data = JSON.parse(employeeData);
// 데이터를 페이지에 반영
$(document).on("click", ".apply", function () {
  const start_date = document.querySelector("#start_date").value.trim();
  const last_date = document.querySelector("#last_date").value.trim();
  const payment_date = document.querySelector("#payment_date").value.trim();
  var payrolltemplateData = {
    id: 0,
    startPayrollPeriod: start_date,
    lastPayrollPeriod: last_date,
    paymentDate: payment_date,
    employeeId: data.id,
  };
  console.log(payrolltemplateData);
  sessionStorage.setItem(
    "payrolltemplateData",
    JSON.stringify(payrolltemplateData)
  );
  window.location.href = "/my_employee_payrolltemplate.html";
});

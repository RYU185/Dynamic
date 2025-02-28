const userRole = JSON.parse(sessionStorage.getItem('userName'));

$(document).ready(function () {
  $.ajax({
    url: '/api/employee/all',
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      AllData(response);
    },
  });
});

function AllData(response) {
  console.log(response);
  $('tbody').empty(); // 기존 테이블 내용 비우기
  response.forEach((element) => {
    if (element.isActive === true && element.freeTemplate === false) {
      var $row = $(`<tr class="row" id="${element.id}">
          <td >${element.name}</td>
          <td >${element.birthday}</td>
        </tr>`);
      $('tbody').append($row);
    }
  });
}

$(document).on('click', '.show_calendar', function () {
  // contenteditable로 설정된 값 가져오기
  const start_date = document.querySelector('#start_date').innerText.trim();
  const last_date = document.querySelector('#last_date').innerText.trim();
  const payment_date = document.querySelector('#payment_date').innerText.trim();
  const calendar = document.querySelector('.down'); // 달력 요소

  // 모든 필드가 입력되었는지 확인
  if (start_date && last_date && payment_date) {
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

$(document).ready(function () {
  $.ajax({
    url: '/api/payrolltemplate/all',
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      $('tbody').empty();
      response.forEach((element) => {
        //  가이드 제목, ID, 제목만 표시
        var $row = $(`<tr class="row" id ="${element.id}">
          <td>${element.employeeName}</td>
          <td>${element.position}</td>
          <td class="amount">${element.totalAmount}</td>
          <td class="amount">${element.finalPayment}</td>
          <td>${element.paymentDate}</td>
        </tr>`);
        $('tbody').append($row); // 테이블에 새 행 추가
      });
      formatSpecificCells();
    },
  });
});

$(document).on('change', '#options', function () {
  const month = document.querySelector('#options').value;

  console.log(month);
  if (month > 0) {
    $.ajax({
      url: '/api/payrolltemplate/payment_date_month/' + month,
      method: 'get',
      contentType: 'application/json',
      success: function (response) {
        if (response.length > 0) {
          $('tbody').empty();
          response.forEach((element) => {
            //  가이드 제목, ID, 제목만 표시
            var $row = $(`<tr class="row" id ="${element.id}">
              <td>${element.employeeName}</td>
              <td>${element.position}</td>
              <td class="amount">${element.totalAmount}</td>
              <td class="amount">${element.finalPayment}</td>
              <td>${element.paymentDate}</td>
            </tr>`);
            $('tbody').append($row); // 테이블에 새 행 추가
          });
        }
        formatSpecificCells();
      },
      error: function () {
        $('tbody').empty();
      },
    });
  } else {
    $.ajax({
      url: '/api/payrolltemplate/all',
      method: 'get',
      contentType: 'application/json',
      success: function (response) {
        $('tbody').empty();
        response.forEach((element) => {
          //  가이드 제목, ID, 제목만 표시
          var $row = $(`<tr class="row" id ="${element.id}">
              <td>${element.employeeName}</td>
              <td>${element.position}</td>
              <td class="amount">${element.totalAmount}</td>
              <td class="amount">${element.finalPayment}</td>
              <td>${element.paymentDate}</td>
            </tr>`);
          $('tbody').append($row); // 테이블에 새 행 추가
        });
        formatSpecificCells();
      },
    });
  }
});

function submit_go() {
  let name = document.querySelector("input[id='search_employee']");
  var sendData = name.value; // title 값만 사용
  $.ajax({
    url: '/api/payrolltemplate/name/' + encodeURIComponent(sendData), // URL에 검색어(title) 추가
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      $('tbody').empty();
      response.forEach((element) => {
        //  가이드 제목, ID, 제목만 표시
        var $row = $(`<tr class="row" id ="${element.id}">
          <td>${element.employeeName}</td>
          <td>${element.position}</td>
          <td class="amount">${element.totalAmount}</td>
          <td class="amount">${element.finalPayment}</td>
          <td>${element.paymentDate}</td>
        </tr>`);
        $('tbody').append($row); // 테이블에 새 행 추가
      });
      formatSpecificCells();
    },
    error: function () {
      alert('검색 오류가 발생했습니다.');
    },
  });
}

function formatSpecificCells() {
  // 'price' 클래스를 가진 td 요소만 선택
  const priceCells = document.querySelectorAll('.amount');

  priceCells.forEach((cell) => {
    const price = parseInt(cell.textContent, 10); // 텍스트를 숫자로 변환

    if (!isNaN(price)) {
      // 천 단위 구분 기호 추가
      cell.textContent = price.toLocaleString();
    }
  });
}

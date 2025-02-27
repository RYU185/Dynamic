const userRole = JSON.parse(sessionStorage.getItem('userName'));

// db랑 front랑 연결하는 코드
$(document).ready(function () {
  $.ajax({
    url: '/api/employee/all',
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      console.log(response);
      $('tbody').empty();
      let count = 1;
      response.forEach((element) => {
        if (element.isActive === true) {
          //  가이드 제목, ID, 작성일만 표시
          var $row = $(`<tr class="row">
                <td>${element.name}</td>
                <td>${element.birthday}</td>
                <td>${element.hireDate}</td>
                 <td>${element.hourlyRate}</td>
               <td class="modify_button">
                    <img src="img/writing.png" alt="수정 버튼" />
                  </td>
                  <td class="delete_button">
                    <img src="img/쓰레기통 새로운거.png" alt="삭제 버튼" />
                  </td>
              </tr>`);
          $('tbody').append($row); // 테이블에 새 행 추가
        }
      });
    },
  });
});

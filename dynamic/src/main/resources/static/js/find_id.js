$(document).on('click', '#duplicate', function () {
  const email = document.querySelector('#email').value.trim();

  $.ajax({
    url: '/api/user/find-user/email/' + email,
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      $('#id_box').empty();
      response.forEach((element) => {
        var $row = $(`<p class= "left">나의 아이디</p>
     <input type="text" class="myid middle" id="myid" readonly  value="${element}" />
               <p class="right" id="copy">복사</p>     
            `);
        $('#id_box').append($row);
      });

      console.log(response);

      document.querySelector('#duplicate').innerText = '인증 완료';
      // document.querySelector('#myid').value = response;
    },
    error: function () {
      alert('해당 이메일로 존재하는 아이디가 없습니다');
      return;
    },
  });
});

// 복사 기능 추가
$(document).on('click', '#copy', function () {
  const myidValue = $(this).siblings('#myid').val();
  navigator.clipboard
    .writeText(myidValue)
    .then(() => alert('아이디가 복사되었습니다!'))
    .catch(() => alert('복사에 실패했습니다.'));
});

$(document).on('click', '#home', function () {
  window.location.href = '/login.html';
});

$('#reset').on('click', function () {
  $('input[type="text"]').val('');
});

const userRole = JSON.parse(sessionStorage.getItem('userName'));

// db랑 front랑 연결하는 코드
$(document).ready(function () {
  getUserInformation();
});
$('#reset').on('click', function () {
  getUserInformation();
});

function getUserInformation() {
  $.ajax({
    url: '/api/user/id/' + userRole,
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      document.querySelector('#name').value = response.realName;
      document.querySelector('#gender').value = response.gender;
      document.querySelector('#email').value = response.email;
      document.querySelector('#id').value = response.userName;
      document.querySelector('#phone').value = response.phoneNumber;
    },
  });
}

$(document).on('click', '#save', function () {
  const realName = document.querySelector('#name').value.trim();
  const email = document.querySelector('#email').value.trim();
  const phone = document.querySelector('#phone').value.trim();

  var sendData = {
    realName: realName,
    email: email,
    phoneNumber: phone,
  };
  if (realName && email && phone) {
    $.ajax({
      url: '/api/user/user-data',
      method: 'POST',
      data: JSON.stringify(sendData),
      contentType: 'application/json',
      success: function (response) {
        window.location.href = '/edit_profile.html';
      },
    });
  } else {
    alert('빈칸으로 제출이 불가능합니다');
  }
});

document.querySelector('#phone').addEventListener('input', function (e) {
  let value = e.target.value.replace(/\D/g, ''); // 숫자만 남기기

  // 10자리 전화번호 형식 000-0000-0000으로 제한
  if (value.length <= 3) {
    e.target.value = value; // 3자리까지만 입력
  } else if (value.length <= 7) {
    e.target.value = value.slice(0, 3) + '-' + value.slice(3); // 3자리-4자리
  } else {
    e.target.value =
      value.slice(0, 3) + '-' + value.slice(3, 7) + '-' + value.slice(7, 11); // 3자리-4자리-4자리
  }
});

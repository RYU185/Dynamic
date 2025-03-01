const existBusinessOperator = sessionStorage.getItem('existBusinessOperator');
const data = JSON.parse(existBusinessOperator); // 기존 사업자 여부 true 가져오기
const regist = document.querySelector('#regist');
let duplicate = false;

$(document).on('click', '.right', function () {
  const userName = document.querySelector('#id');
  $.ajax({
    url: ' /api/user/check-id/' + userName.value,
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      if (response === true) {
        alert('이미 등록된 아이디입니다. 다른 아이디를 이용해주세요');
        duplicate = false;
        return;
      } else {
        alert('사용 가능한 아이디입니다!');
        duplicate = true;
      }
      if (duplicate) {
        regist.disable = false;
      } else {
        regist.disable = true;
      }
    },
  });
});

$(document).on('click', '#regist', function () {
  const realName = document.querySelector('#name');
  const email = document.querySelector('#email');
  const userName = document.querySelector('#id');
  const password = document.querySelector('#pwd');
  const phoneNumber = document.querySelector('#phone');
  const businessNumber = document.querySelector('#businessNumber');
  const companyName = document.querySelector('#companyName');
  const businessType = document.querySelector('#businessName');
  const gender = document.querySelector("input[name='gender']:checked");

  var sendData = {
    userName: userName.value,
    companyName: companyName.value,
    realName: realName.value,
    password: password.value,
    gender: gender.value,
    email: email.value,
    phoneNumber: phoneNumber.value,
    businessNumber: businessNumber.value,
    businessType: businessType.value,
    existBusinessOperator: data.value,
  };
  console.log(sendData);

  const check = document.querySelector('#agree');
  if (check.checked) {
    $.ajax({
      url: '/api/user/register',
      method: 'POST',
      data: JSON.stringify(sendData),
      contentType: 'application/json',
      success: function (response) {
        alert('회원가입이 완료되었습니다. 로그인하여 이용 바랍니다');
        window.location.href = '/login.html';
      },
    });
  } else {
    alert('약관 동의 후 회원가입이 가능합니다');
    return;
  }
});

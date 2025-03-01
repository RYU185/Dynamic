const existBusinessOperator = sessionStorage.getItem('existBusinessOperator');
const data = JSON.parse(existBusinessOperator); // 기존 사업자 여부 true 가져오기
const regist = document.querySelector('#regist');
const userName = document.querySelector('#id');
let duplicate = null;
console.log(data);
userName.addEventListener('input', function () {
  duplicate = null;
});

$(document).on('click', '#duplicate', function () {
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
    },
  });
});

$(document).on('click', '#regist', function () {
  const realName = document.querySelector('#name').value.trim();
  const email = document.querySelector('#email').value.trim();
  const userName = document.querySelector('#id').value.trim();
  const password = document.querySelector('#pwd').value.trim();
  const phoneNumber = document.querySelector('#phone').value.trim();
  const businessNumber = document.querySelector('#businessNumber').value.trim();
  const companyName = document.querySelector('#companyName').value.trim();
  const businessType = document.querySelector('#businessName').value.trim();
  const gender = document.querySelector("input[name='gender']:checked");
  if (!realName) {
    alert('이름을 작성해주세요');
    return;
  }
  if (!gender) {
    alert('성별을 선택해주세요');
    return;
  }
  if (!email) {
    alert('이메일을 작성해주세요');
    return;
  }
  if (!userName) {
    alert('아이디를 작성해주세요');
    return;
  }
  if (!password) {
    alert('비밀번호를 작성해주세요');
    return;
  }
  if (!phoneNumber) {
    alert('전화번호를 작성해주세요');
    return;
  }
  if (!businessNumber) {
    alert('사업자번호를 작성해주세요');
    return;
  }
  if (!companyName) {
    alert('회사명를 작성해주세요');
    return;
  }
  if (!businessType) {
    alert('업종명를 작성해주세요');
    return;
  }

  if (duplicate === null) {
    alert('아이디 중복 확인을 먼저 해주세요.');
    return; // 버튼이 비활성화되었을 때는 진행하지 않음
  }

  var sendData = {
    userName: userName,
    companyName: companyName,
    realName: realName,
    password: password,
    gender: gender.value,
    email: email,
    phoneNumber: phoneNumber,
    businessNumber: businessNumber,
    businessType: businessType,
    existBusinessOperator: data,
  };
  console.log(sendData);

  const check = document.querySelector('#agree');
  if (check.checked && duplicate === true) {
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

$('#cancel').on('click', function () {
  $('input[type="text"]').val('');
  $('input[type="radio"]').prop('checked', false); // 라디오 버튼 초기화
  $('input[type="checkbox"]').prop('checked', false); // 체크박스 초기화
});

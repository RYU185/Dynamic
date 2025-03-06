const existBusinessOperator = sessionStorage.getItem('existBusinessOperator');
const data = JSON.parse(existBusinessOperator); // 기존 사업자 여부 true 가져오기
const regist = document.querySelector('#regist');
const userName = document.querySelector('#id');
let duplicate = null;

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
document.querySelector('#phone').addEventListener('input', function (e) {
  let value = e.target.value.replace(/\D/g, ''); // 숫자만 남기기

  // 000-0000-0000으로 제한
  if (value.length <= 3) {
    e.target.value = value; // 3자리까지만 입력
  } else if (value.length <= 7) {
    e.target.value = value.slice(0, 3) + '-' + value.slice(3); // 3자리-4자리
  } else {
    e.target.value =
      value.slice(0, 3) + '-' + value.slice(3, 7) + '-' + value.slice(7, 11); // 3자리-4자리-4자리
  }
});
$(document).on('click', '#add', function () {
  const infoBox = document.querySelector(".informtion_box");

  if (infoBox.style.height === '460px') {
    infoBox.style.height = '';  // 기본 값으로 되돌리기
  } else {
    infoBox.style.height = '460px';  // 460px로 설정
  }
})

$(document).on('click', '#regist', function () {
  const realName = document.querySelector('#name').value.trim();
  const email = document.querySelector('#email').value.trim();
  const userName = document.querySelector('#id').value.trim();
  const password = document.querySelector('#pwd').value.trim();
  const phoneNumber = document.querySelector('#phone').value.trim();
  const gender = document.querySelector("input[name='gender']:checked");
  const plan = document.querySelector("input[name='business']:checked");
  const other_plan = document.querySelector('#else').value.trim();

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

  if (!plan && !other_plan) {
    alert('예정 업종을 선택 혹은 작성해주세요');
    return;
  }

  if (duplicate === null) {
    alert('아이디 중복 확인을 먼저 해주세요.');
    return; // 버튼이 비활성화되었을 때는 진행하지 않음
  }

  var sendData = {
    userName: userName,
    companyName: '',
    realName: realName,
    password: password,
    gender: gender.value,
    email: email,
    phoneNumber: phoneNumber,
    businessNumber: '',
    businessType: '',
    existBusinessOperator: false,
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

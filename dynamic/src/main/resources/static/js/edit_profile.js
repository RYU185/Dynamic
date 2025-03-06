const userRole = JSON.parse(sessionStorage.getItem('userName'));
const existBusinessOperator = sessionStorage.getItem('existBusinessOperator');
console.log(existBusinessOperator);

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
      console.log(response);
      document.querySelector('#login_name').value = response.realName;
      document.querySelector('#gender').value = response.gender;
      document.querySelector('#email').value = response.email;
      document.querySelector('#id').value = response.userName;
      document.querySelector('#phone').value = response.phoneNumber;
      document.querySelector('#read_businessNumber').value = response.businessNumber;
      document.querySelector('#modify_company_name').value = response.companyName;
      document.querySelector('#modify_businessName').value = response.businessType;
    },
  });
}

$(document).on('click', '#save', function () {
  const realName = document.querySelector('#login_name').value.trim();
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
        alert('프로필 정보가 정상적으로 수정되었습니다');
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

const button2 = document.querySelector('#password');
const content3 = document.querySelector('.pop-up3');
const background = document.querySelector('.pop-up-background1');

button2.addEventListener('click', function () {
  if (content3.style.display === 'none' || content3.style.display === '') {
    content3.style.display = 'block'; // display: block 으로 변경
    background.style.display = 'block';
  }
});

document.querySelector('.x_btn1').addEventListener('click', function () {
  document.querySelector('.pop-up1').style.display = 'none';
  document.querySelector('.pop-up-background1').style.display = 'none';
});
document.querySelector('.x_btn2').addEventListener('click', function () {
  document.querySelector('.pop-up2').style.display = 'none';
  document.querySelector('.pop-up-background1').style.display = 'none';
});
document.querySelector('.x_btn3').addEventListener('click', function () {
  document.querySelector('.pop-up3').style.display = 'none';
  document.querySelector('.pop-up-background1').style.display = 'none';
});

$(document).on('click', '#modify_pwd', function () {
  const pwd = document.querySelector('#pwd');
  const new_password = document.querySelector('#new_password');
  const one_more = document.querySelector('#one_more');

  console.log(pwd, new_password, one_more);

  var sendData = {
    userId: userRole,
    currentPassword: pwd.value,
    newPassword: new_password.value,
    confirmNewPassword: one_more.value,
  };
  console.log(sendData);
  if (new_password.value !== one_more.value) {
    alert('입력하신 비밀번호가 일치하지 않습니다 ');
    one_more.focus();
    return;
  }

  $.ajax({
    url: '/api/user/modify-pw',
    method: 'POST',
    data: JSON.stringify(sendData),
    contentType: 'application/json',
    success: function (response) {
      alert('비밀번호를 정상적으로 수정하였습니다');
      window.location.href = '/edit_profile.html';
    },
    error: function () {
      alert('현재 비밀번호를 확인하여 올바르게 작성하세요');
      pwd.focus();
      return;
    },
  });
});

const button1 = document.querySelector('#add_button');
const content1 = document.querySelector('.pop-up1');
const content2 = document.querySelector('.pop-up2');

$(document).on('click', '#add_button', function () {
  const isexist = existBusinessOperator === 'true';
  console.log(isexist);
  if (!isexist) {
    if (content2.style.display === 'none' || content2.style.display === '') {
      content2.style.display = 'block'; // display: block 으로 변경
      background.style.display = 'block';
    }
  } else {
    if (content1.style.display === 'none' || content3.style.display === '') {
      content1.style.display = 'block'; // display: block 으로 변경
      background.style.display = 'block';

    }
  }
});


$(document).on('click', '#modify', function () {
  const businessType = document.querySelector('#modify_businessName');
  const companyName = document.querySelector('#modify_company_name');

  console.log(businessType, companyName);

  var sendData = {
    "userName ": userRole,
    "businessType": businessType.value,
    "companyName": companyName.value,
  };
  console.log(sendData);

  $.ajax({
    url: '/api/user/user-business-number',
    method: 'POST',
    data: JSON.stringify(sendData),
    contentType: 'application/json',
    success: function (response) {
      alert('사업자 정보가 정상적으로 수정되었습니다');
      window.location.href = '/edit_profile.html';
    }
  });
});
document
  .querySelector('#add_businessNumber')
  .addEventListener('input', function (e) {
    let value = e.target.value.replace(/\D/g, ''); // 숫자만 남기기

    // 000-00-00000으로 제한
    if (value.length <= 3) {
      e.target.value = value; // 3자리까지만 입력
    } else if (value.length <= 5) {
      e.target.value = value.slice(0, 3) + '-' + value.slice(3); // 3자리-2자리
    } else {
      e.target.value =
        value.slice(0, 3) + '-' + value.slice(3, 5) + '-' + value.slice(5, 10); // 3자리-2자리-5자리
    }
  });


$(document).on('click', '#add', function () {
  const businessType = document.querySelector('#add_businessName');
  const companyName = document.querySelector('#add_company_name');
  const businessNumber = document.querySelector('#add_businessNumber');

  // console.log(businessType.value, companyName.value, businessNumber.value);

  var sendData = {
    "userName ": userRole,
    "businessType": businessType.value,
    "companyName": companyName.value,
    "businessNumber": businessNumber.value
  };
  console.log(sendData);

  $.ajax({
    url: '/api/user/user-business-number',
    method: 'POST',
    data: JSON.stringify(sendData),
    contentType: 'application/json',
    success: function (response) {
      alert('사업자 정보가 정상적으로 등록되었습니다');
      window.location.href = '/edit_profile.html';
      newExistBusinessOperator = existBusinessOperator ? existBusinessOperator : {};
      newExistBusinessOperator.someField = 'new value';
      sessionStorage.setItem("existBusinessOperator", response.existBusinessOperator);
    }
  });
});

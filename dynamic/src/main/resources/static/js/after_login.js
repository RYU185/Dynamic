const userCopy = JSON.parse(sessionStorage.getItem('userName'));

document.querySelector('.log_out').addEventListener('click', function () {
  sessionStorage.removeItem('userName');
  window.location.href = '/index.html';
});

window.onload = function () {
  if (!userCopy) {
    alert('로그인 후 이용 가능합니다.');
    window.location.href = '/login.html';
  } else {
    $('.name').text(userCopy);
  }
};

// 버튼과 콘텐츠 요소 가져오기
document.querySelector('.button').addEventListener('click', function () {
  $.ajax({
    url: 'api/employee/free-template-count',
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      console.log(response);
      if (userCopy != 'admin') {
        if (response > 5) {
          alert('이용 가능한 무료 횟수가 다 소진되었습니다');
          return;
        }
        window.location.href = '/free_payrolltemplate_information.html';
      } else {
        window.location.href = '/free_payrolltemplate_information.html';
      }
    },
  });
});

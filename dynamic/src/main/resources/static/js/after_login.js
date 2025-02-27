const userCopy = JSON.parse(sessionStorage.getItem("userName"));



document.querySelector('.log_out').addEventListener('click', function () {
    sessionStorage.removeItem("userName");
    window.location.href = '/index.html';
})


window.onload = function () {
    if (!userCopy) {
        alert('로그인 후 이용 가능합니다.');
        window.location.href = '/login.html';
    }
    else {
        $('.name').text(userCopy);
    }
};



// 버튼과 콘텐츠 요소 가져오기
document.querySelector('.button').addEventListener('click', function () {

    window.location.href = '/free_payrolltemplate_information.html'
})

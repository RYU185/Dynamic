const userRole = JSON.parse(sessionStorage.getItem('userName'));



window.onload = function () {
    if (userRole) {
        window.location.href = '/after_login.html';
    }
}


document.querySelector('.button').addEventListener('click', function () {

    alert('로그인 후 이용이 가능합니다.');
})



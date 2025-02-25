function submit_go() {
    let id = document.querySelector("input[name='id']");
    let pw = document.querySelector("input[name='pw']");

    // 값이 제대로 가져와졌는지 확인
    console.log('아이디: ', id.value);
    console.log('비밀번호: ', pw.value);

    if (!id.value) {
        alert('아이디는 필수 입력사항입니다');
        id.focus();
        return;
    }
    if (!pw.value) {
        alert('비밀번호는 필수 입력사항입니다');
        pw.focus();
        return;
    }

    var sendData = {
        userName: id.value,
        password: pw.value,
    };
    $.ajax({
        url: '/api/user/login',
        method: 'POST',
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (response) {
            sessionStorage.setItem(
                'userName',
                JSON.stringify(sendData.userName)
            );
            const userCopy = JSON.parse(sessionStorage.getItem('userName'));
            $('.name').text(userCopy);
            $('.hello').text('환영합니다');
            window.location.href = '/after_login.html';
        },
    });
}
const find_id = document.querySelector('.find_id');
const register = document.querySelector('.register');
find_id.addEventListener('click', function () {
    window.location.href = 'find_id.html';
});
register.addEventListener('click', function () {
    window.location.href = 'joinMembership.html';
});
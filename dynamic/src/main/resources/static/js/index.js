const userRole = JSON.parse(sessionStorage.getItem('userName'));

// 버튼과 콘텐츠 요소 가져오기
const button = document.querySelector('.button');
const content = document.querySelector('.pop-up');
const opacity = document.querySelector('.opacity');



// 버튼 클릭 시 display 속성 변경
button.addEventListener('click', function () {
    if (content.style.display === 'none' || content.style.display === '') {
        content.style.display = 'block'; // display: block 으로 변경
        opacity.style.display = "block";
    } else {
        content.style.display = 'none'; // display: none 으로 다시 변경
    }
});

opacity.addEventListener('click', function () {
    if (!content.contains(event.target) && event.target !== button) {
        content.style.display = 'none';
        opacity.style.display = "none";
    }
});

window.onload = function () {
    if (userRole) {
        window.location.href = '/after_login.html';
    }
}


function validateInput(input) {
    const inputs = document.getElementsByClassName('element');
    for (let i = 0; i < inputs.length; i++) {
        inputs[i].value = inputs[i].value.replace(/[^0-9]/g, '');  // 숫자 외의 문자는 모두 삭제
        // 3자리마다 쉼표 넣기
        let formattedValue = inputs[i].value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');

        // 쉼표가 포함된 값으로 입력 필드 업데이트
        inputs[i].value = formattedValue;
    }
}
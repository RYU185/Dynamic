const userCopy = JSON.parse(sessionStorage.getItem("userName"));
$(".name").text(userCopy);

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

document.querySelector('#reset').addEventListener('click', function () {

    const item = document.querySelectorAll('.template input');
    item.forEach(function (input) {
        input.value = '';
    });
});

document.querySelector('.log_out').addEventListener('click', function () {
    sessionStorage.removeItem("userName");
    window.location.href = '/index.html';
})
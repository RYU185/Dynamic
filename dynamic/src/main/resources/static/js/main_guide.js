
// admin일 경우에만 추가/ 삭제 버튼 보이도록 처리
const userRole = JSON.parse(sessionStorage.getItem('userName'));
if (userRole === 'admin') {
    document.querySelector('#add_notice').style.display = 'inline';
    document.querySelector('#delete_notice').style.display = 'inline';
}


const button = document.querySelector('#add_notice');
const content = document.querySelector('.pop-up1');
const background = document.querySelector('.pop-up-background1');

button.addEventListener('click', function () {
    if (
        content.style.display === 'none' ||
        content.style.display === ''
    ) {
        content.style.display = 'block'; // display: block 으로 변경
        background.style.display = 'block';
    }
});

function submit_go() {
    let title = document.querySelector("input[id='search_guide']");
    var sendData = title.value; // title 값만 사용
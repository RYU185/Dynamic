
// admin일 경우에만 추가/ 삭제 버튼 보이도록 처리
const userRole = JSON.parse(sessionStorage.getItem('userName'));
if (userRole === 'admin') {
    document.querySelector('#add_formation_data').style.display = 'inline';
    document.querySelector('#delete_formation_data').style.display = 'inline';
}


// 버튼을 클릭하면 가이드 추가 팝업 보이게 처리
const button = document.querySelector('#add_formation_data');
const content = document.querySelector('.pop-up1');
const background = document.querySelector('.pop-up-background1');
const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get("id");
console.log(id);

$(document).ready(function () {
    $.ajax({
        url: '/api/guide/all',
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
            $('tbody').empty();
            let count = 1;
            response.forEach((element) => {
                //  가이드 제목, ID, 제목만 표시       
                var $row = $(`<tr class="row">
          <td>${count++}</td>
          <td>${element.title}</td>
        </tr>`);
                $row.on("click", () => {
                    console.log(element.id + " " + element.title);
                    window.location.href = "/guide_detail.html?id=" + element.id;
                });
                $('tbody').append($row); // 테이블에 새 행 추가
            });
        }
    })
});

$(document).ready(function () {
    $.ajax({
        url: '/api/guide/id/' + encodeURIComponent(id),
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
            $('.detail_box').empty();
            var row = `
            <div class='title'>가이드 상세 내용 </div>
            <div class='description'>${response.text}</div>
            <div class="buttom">
                <div class="content">
                    <div><b>등록 날짜 :</b></div>
                    <div>${response.addDate}</div>
                </div>
            </div>
        `;
            $('.detail_box').append(row);
        }
    });
});



// admin일 경우에만 삭제 버튼 보이도록
const userRole = JSON.parse(sessionStorage.getItem('userName'));
if (userRole === 'admin') {
    document.querySelector('#delete_guide').style.display = 'inline';
}

$(document).on('click', "#delete_guide", function () {
    const isConfirmed = confirm('가이드 삭제 처리하도록 할까요 ?')

    if (isConfirmed) {
        $.ajax({
            url: 'api/guide/id/' + encodeURIComponent(id),
            method: 'delete',
            contentType: 'application/json',
            success: function () {
                window.location.href = "/main_guide.html";
            }
        })
    } else {
        alert('삭제 처리가 취소되었습니다');
    }
})


document.querySelector(".home").addEventListener('click', function () {
    window.location.href = 'main_guide.html';
});

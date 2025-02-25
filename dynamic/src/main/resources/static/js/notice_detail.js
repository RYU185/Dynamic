const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get("id");
console.log(id);


$(document).ready(function () {
    $.ajax({
        url: '/api/notice/all',
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
            $('tbody').empty();
            let count = 1;
            response.forEach((element) => {
                //  가이드 제목, ID, 제목만 표시       
                var $row = $(`<tr class="row">
          <td>${count++}</td>
          <td>${element.noticeTitle}</td>
        </tr>`);
                $('tbody').append($row); // 테이블에 새 행 추가

            });
        }
    })
});

$(document).ready(function () {
    $.ajax({
        url: '/api/notice/id/' + encodeURIComponent(id),
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
            $('.detail_box').empty();
            var row = `
            <div class='title'>공지 상세 내용 </div>
            <div class='description'>${response.text}</div>
            <div class="buttom">
                <div class="content">
                    <div><b>등록 날짜 :</b></div>
                    <div>${response.addDate}</div>
                </div>
                <div class="content">
                    <div><b>수정 날짜 :</b></div>
                    <div>${response.modifiedDate}</div>
                </div>
            </div>
        `;
            $('.detail_box').append(row);
        }
    });
});


// $row.on("click", () => {
//     console.log(element.id + " " + element.title);
//     window.location.href = "/guide_detail.html?id=" + element.id;
// });
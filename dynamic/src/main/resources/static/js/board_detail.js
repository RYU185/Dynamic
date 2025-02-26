const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');
console.log(id);

$(document).ready(function () {
  $.ajax({
    url: '/api/board/all',
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
        $('tbody').append($row); // 테이블에 새 행 추가
      });
    },
  });
});
// 페이지 이전할때 보여지는 페이지
// 해당 본문에 맞는 댓글도 같이 함께 보이기
$(document).ready(function () {
  $.ajax({
    url: '/api/board/id/' + encodeURIComponent(id),
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      $('.detail_box').empty();
      var row = `
          <div class='title'>본문 상세 내용 </div>
          <div class="date">${response.addDate}</div>
          <div class='description'>${response.title}</div>  
           <div class='comment_list'>
           <div class="list"></div>  
           </div>           
      `;
      $('.detail_box').append(row);
      console.log(row);

      $.ajax({
        url: '/api/comment/board-id/' + encodeURIComponent(id),
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
          response.forEach((element) => {
            var row = `
                <div class="comment_box">
                  <div class="name">${element.userName}</div>
                  <div class="push_date">${element.addDate}</div>
                  <div class="comment">${element.text}</div>
              </div>`;
            $('.list').append(row);
            console.log(row);
          });
          var row1 = `
          <div class="write_box">
                <div class="write">
                  <div class="comment" contenteditable="true" class="write"></div>
                  <img src="/img/up.png" alt="전송하기" class="press" />
                </div>
              </div>`;
          $('.comment_list').append(row1);
        },
      });
    },
  });
});

// 제목 클릭 시 해당 내용으로 본문 상세 내용 변경
$(document).on('click', 'tbody tr', function () {
  $.ajax({
    url: '/api/board/all',
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      $('tbody').empty();
      let count = 1;
      response.forEach((element) => {
        // 공지사항 제목, ID, 작성일만 표시
        var $row = $(`<tr class="row">
                            <td>${count++}</td>
                            <td>${element.title}</td>
                          </tr>`);
        $('tbody').append($row); // 테이블에 새 행 추가
        $row.on('click', () => {
          console.log(element.id + ' ' + element.title);
          window.location.href = '/board_detail.html?id=' + element.id;
        });
      });
    },
  });
});


const userRole = JSON.parse(sessionStorage.getItem('userName'));
$(document).on('click', '.press', function () {
  const text = document.querySelector('.write');
  if (userRole === null || userRole == undefined) {
    alert('로그인 후 이용 가능합니다');
    return;
  }
  // 댓글 내용이 비어있는 경우 처리
  if (!text || text.innerText.trim() === "") {
    alert("댓글 내용을 입력해 주세요.");
    return;
  }

  var sendData = {
    "id": 0,
    "boardId": id,
    "text": text.innerText
  }
  console.log(sendData)

  $.ajax({
    url: '/api/comment/save',
    method: 'POST',
    data: JSON.stringify(sendData),
    contentType: 'application/json',
    success: function (response) {
      const newComment = document.createElement('div')
      newComment.classList.add('comment_box');
      newComment.innerHTML = `
      <div class="name">${response.userName}</div>
      <div class="push_date">${response.addDate}</div>
      <div class="comment">${response.text}</div>
    `;
      window.location.href = "/board_detail.html?id=" + id;

    }
  })
});
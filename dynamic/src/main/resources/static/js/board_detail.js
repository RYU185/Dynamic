const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('id');
console.log(id);
const modify_board = document.querySelector('#modify_board');
const delete_board = document.querySelector('#delete_board');
const userRole = JSON.parse(sessionStorage.getItem('userName'));


$(document).ready(function () {
  $.ajax({
    url: '/api/board/all',
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      $('tbody').empty();
      let count = 1;
      response.forEach((element) => {
        if (element.isActive === true) {
          //  가이드 제목, ID, 제목만 표시
          var $row = $(`<tr class="row">
        <td>${count++}</td>
        <td>${element.title}</td>
      </tr>`);
          $('tbody').append($row); // 테이블에 새 행 추가
        }
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
      document.querySelector('.add_date').innerHTML = response.addDate;
      document.querySelector('.modified_date').innerHTML = response.modifyDate;
      document.querySelector('.description').innerHTML = response.title;

      document.querySelector('.modify_text').innerText = response.title;
      const writeUser = response.userName;
      console.log(writeUser);
      console.log(userRole);
      if (userRole === writeUser) {
        if (
          modify_board.style.display != 'block' &&
          delete_board.style.display != 'block'
        ) {
          modify_board.style.display = 'block';
          delete_board.style.display = 'block';
        }
      }

      $.ajax({
        url: '/api/comment/board-id/' + encodeURIComponent(id),
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
          $('.list').empty();

          response.forEach((element) => {
            if (element.isActive === true) {
              var $row = $(`
                <div class="comment_box" id="${element.id}">
                <div class="name">${element.userName}</div>
                 <div class="push_date">${element.addDate}</div>
                  <div class="comment">${element.text}</div>
                   <div  class="delete_comment"><img src="img/쓰레기통 새로운거.png" alt="삭제버튼"></div>
                 </div>
                   `);
              if (userRole === element.userName) {
                $row.find('.delete_comment').css('display', 'block');
              }
              const delete_comment = $row.find('.delete_comment');
              delete_comment.on('click', function (e) {
                const button = e.target;
                const parent = button.closest('.comment_box');
                console.log(parent.id);
                const isConfirmed = confirm('해당 댓글을 삭제 처리하도록 할까요 ?');
                if (isConfirmed) {
                  $.ajax({
                    url: '/api/comment/delete/' + parent.id,
                    method: 'POST',
                    contentType: 'application/json',
                    success: function () {
                      window.location.href = "board_detail.html?id=" + encodeURIComponent(id);
                    },
                  });
                }
              });
              $('.list').append($row);
            }
          });
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
        if (element.isActive === true) {
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
        }
        $(document).on('click', '.modify', function (e) {
          e.target;

          const name = document.querySelector('#name1').value.trim();
          const position = document.querySelector('#position1').value.trim();
          const department = document.querySelector('#department1').value.trim();
          const phone = document.querySelector('#phone1').value.trim();
          const hourly_rate =
            document.querySelector('#hourly_rate1').value.trim().replace(/,/g, '') ||
            0;
          const birthday = document.querySelector('.birthday').value;
          const hiredate = document.querySelector('.hiredate').value;
          const employee_id = document.querySelector('#employee_id').value;

          var sendData = {
            id: employee_id,
            name: name,
            department: department,
            position: position,
            hourlyRate: hourly_rate,
            birthday: birthday,
            hireDate: hiredate,
            phoneNumber: phone,
          };
          console.log(sendData);

          $.ajax({
            url: '/api/employee/update',
            method: 'PUT',
            data: JSON.stringify(sendData),
            contentType: 'application/json',
            success: function (response) {
              console.log(response);
              alert('직원이 정상 수정되었습니다.');
              window.location.href = 'my_employee.html';
            },
          });
        });


      });
    },
  });
});

$(document).on('click', '.press', function () {
  const text = document.querySelector('.write_comment');
  text.focus();
  if (userRole === null || userRole == undefined) {
    alert('로그인 후 이용 가능합니다');
    return;
  }
  // 댓글 내용이 비어있는 경우 처리
  if (!text || text.innerText.trim() === '') {
    alert('댓글 내용을 입력해 주세요.');
    return;
  }

  var sendData = {
    id: 0,
    boardId: id,
    text: text.innerText,
  };
  // console.log(sendData)

  $.ajax({
    url: '/api/comment/save',
    method: 'POST',
    data: JSON.stringify(sendData),
    contentType: 'application/json',
    success: function (response) {
      const newComment = document.createElement('div');
      newComment.classList.add('comment_box');
      newComment.innerHTML = `
      <div class="name">${response.userName}</div>
      <div class="push_date">${response.addDate}</div>
      <div class="comment">${response.text}</div>
        <div  class="delete_comment" ><img src="img/쓰레기통 새로운거.png" alt="삭제버튼"></div>
      `;


      window.location.href = '/board_detail.html?id=' + id;
    },
  });
});
// 버튼 클릭 시 , 수정 팝업 보이게 처리
const content1 = document.querySelector('.pop-up1');
const background1 = document.querySelector('.pop-up-background1');
modify_board.addEventListener('click', function () {
  if (content1.style.display === 'none' || content1.style.display === '') {
    content1.style.display = 'block'; // display: block 으로 변경
    background1.style.display = 'block';
  } else {
    content1.style.display = 'none'; // display: none 으로 다시 변경
    background1.style.display = 'block';
  }
});
document.querySelector('.x_btn1').addEventListener('click', function () {
  document.querySelector('.pop-up1').style.display = 'none';
  document.querySelector('.pop-up-background1').style.display = 'none';
});

// 나의 게시글 수정!!!!
$(document).on('click', "input[type='button']", function () {
  const text = document.querySelector('.modify_text');
  const currentDate = (document.querySelector('.modify_date').textContent =
    new Date().toLocaleString());

  console.log(text, currentDate);

  var sendData = {
    id: id,
    title: text.innerHTML,
  };
  console.log(sendData);

  $.ajax({
    url: '/api/board/update',
    method: 'PUT',
    data: JSON.stringify(sendData),
    contentType: 'application/json',
    success: function (response) {
      console.log(response);
      alert('게시글이 정상 수정되었습니다.');

      document.querySelector('.description').innerHTML = response.title;

      window.location.href = '/board_detail.html?id=' + response.id;
    },
  });
});

$(document).on('click', '#delete_board', function () {
  const isConfirmed = confirm('해당 게시물을 삭제 처리하도록 할까요 ?');

  if (isConfirmed) {
    $.ajax({
      url: 'api/board/delete/' + encodeURIComponent(id),
      method: 'POST',
      contentType: 'application/json',
      success: function () {
        window.location.href = '/main_board.html';
      },
    });
  } else {
    alert('삭제 처리가 취소되었습니다');
  }
});

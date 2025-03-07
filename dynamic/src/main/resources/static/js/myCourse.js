$(document).ready(function () {
  let courses = [];

  function fetchUserCourses() {
    $.ajax({
      url: '/api/user-product/all',
      method: 'GET',
      contentType: 'application/json',
      success: function (data) {
        console.log('서버 응답 데이터:', data);

        console.log('담긴 강의: ', courses);
        renderCourses(data);
      },
      error: function (xhr, status, error) {
        console.error('데이터 불러오기 실패:', error);
      },
    });
  }

  $('.modal .close', 'pop-up-background2').on('click', function () {
    $('.modal').css('display', 'none');
    $('.pop-up-background2').css('display', 'none');
  });

  fetchUserCourses();
});
function renderCourses(data) {
  courses = data.filter((item) => item.product.type === 'course');
  const cardWrap = $('.cardwrap');
  cardWrap.empty();

  courses.forEach((course) => {
    const title = course.product.title;
    const description = course.product.description;
    const productId = course.product.id;

    const courseCard = `
                <div class="card" data-product-id="${productId}">
                      <div class="pic">
                      <a href="#"><img src="./img/courseThumnail1.png" /></a>
                      </div>
                      <p>강의 제목: ${title}</p>
                      <br />
                      <p>강의 설명:${description}</p>
                </div>
                `;
    cardWrap.append(courseCard);
  });
  viewCourseDetail();
}

function viewCourseDetail() {
  $('.card').on('click', function () {
    const productId = $(this).data('product-id');
    if (!productId) return;

    $.ajax({
      url: `/api/course/id/${productId}`,
      method: 'get',
      contentType: 'application/json',
      success: function (course) {
        $('.modal .title p').text(course.title);
        $('.modal .description p').text(course.description);
        $('.modal').css('display', 'block');
        $('.pop-up-background2').css('display', 'block');
      },
      error: function (xhr, status, error) {
        console.error('강의 정보 불러오기 실패:', error);
      },
    });
  });
}

// title을 통한 검색 기능
function submit_go() {
  let title = document.querySelector("input[id='search_mycourse']");
  var sendData = title.value;

  if (sendData === '') {
    alert('검색어를 입력해주세요');
    return;
  }

  $.ajax({
    url: '/api/user-product/product-name/' + encodeURIComponent(sendData),
    method: 'get',
    contentType: 'application/json',
    success: function (response) {
      renderCourses(response);
    },
    error: function (xhr, status, error) {
      console.error('검색 요청 실패:', error);
    },
  });
}

document.querySelector('.home').addEventListener('click', function () {
  window.location.href = 'main_notice.html';
});

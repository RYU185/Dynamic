document.addEventListener('DOMContentLoaded', function () {
  const courseSearchInput = document.getElementById('courseSearch');
  const subscSearchInput = document.getElementById('subscSearch');
  const courseList = document.querySelector('.courseList .course');
  const subscList = document.querySelector('.subscList .subsc');

  let purchaseData = [];

  $(document).ready(function () {
    loadingPurchaseHistory();
  });

  function loadingPurchaseHistory() {
    $.ajax({
      url: `/api/purchase-history/all`,
      method: 'get',
      contentType: 'application/json',
      success: function (data) {
        purchaseData = data;
        renderPurchaseHistory(purchaseData);
        console.log(data);
      },
      error: function () {
        alert('구매내역을 불러오는 데 실패했습니다');
      },
    });
  }

  function renderPurchaseHistory(data) {
    console.log('🔹 renderPurchaseHistory 실행됨!', data);

    courseList.innerHTML = '';
    subscList.innerHTML = '';

    data.forEach((item) => {
      const product = item.product;
      if (product.isActive) {
        if (product.type === 'course') {
          console.log('강의추가: ', product.title);
          courseList.innerHTML += `
                        <article>
                            <div class="thumbnail">
                                <img
                                    src="./img/courseThumnail1.png"
                                    alt="1번째 동영상"
                                />
                            </div>
                            <h2>${product.title}</h2>
                            <p>구매날짜:${item.purchaseDate}</p>
                            <p>구매금액:${product.price.toLocaleString()}원</p>
                        </article>    
                    `;
        } else if (product.type === 'payrollsubscription') {
          console.log('구독권 추가: ', product.title);
          subscList.innerHTML += `
                        <article>
                            <div class="thumbnail">
                            <img src="./img/구독권 이미지.png" alt="구독권 이미지" />
                            </div>
                            <h2>${product.title}</h2>
                            <p>시작일: ${formatDate(product.startDate)}</p>
                            <p>종료일: ${formatDate(product.expireDate)}</p>
                        </article>
                    `;
        }
      }
    });
    console.log('화면 업데이트 완료');
  }
});

// 나의 강의 구매내역 검색
function submit_go_course() {
  let title = document.querySelector("input[id='courseSearch']");
  var sendData = title.value;

  $.ajax({
    url: '/api/purchase-history/product-name/' + encodeURIComponent(sendData),
    method: 'GET',
    contentType: 'application/json',
    success: function (response) {
      $('.course').empty();
      response.forEach((element) => {
        const product = element.product;
        if (product.category.name === '강의') {
          var $row = $(`
                   <article>
                            <div class="thumbnail">
                                <img
                                    src="./img/courseThumnail1.png"
                                    alt="1번째 동영상"
                                />
                            </div>
                    <h2>${product.title}</h2>
                    <p>구매날짜: ${formatDate(element.purchaseDate)}</p>
                    <p>구매금액: ${product.price.toLocaleString()}원</p>
                  </article>
            `);
          $('.course').append($row);
        }
      });
    },
    error: function () {
      $('.course').empty();
      alert('검색 결과가 없습니다.');
    },
  });
}
// $(document).on('click', '#courseSearch', function () {
//   submit_go_course();
// });

// 나의 구매기록 구독권 조회
function submit_go_subsc() {
  let title = document.querySelector("input[id='subscSearch']");
  var sendData = title.value;

  $.ajax({
    url: '/api/purchase-history/product-name/' + encodeURIComponent(sendData),
    method: 'GET',
    contentType: 'application/json',
    success: function (response) {
      $('.subsc').empty();
      response.forEach((element) => {
        const product = element.product;
        if (product.category.name === '정기 구독권') {
          var $row = $(`
                   <article>
                            <div class="thumbnail">
                            <img src="./img/구독권 이미지.png" alt="구독권 이미지" />
                            </div>
                      <h2>${product.title}</h2>
                      <p>시작일: ${formatDate(product.startDate)}</p>
                      <p>종료일: ${formatDate(product.expireDate)}</p>
                  </article>
            `);
          $('.subsc').append($row);
        }
      });
    },
    error: function () {
      $('.subsc').empty();
      alert('검색 결과가 없습니다.');
    },
  });
}
document.querySelector('.home').addEventListener('click', function () {
  window.location.href = 'myPurchaseHistory.html';
});

// 날짜 포맷 함수 (YYYY-MM-DD 형식)
function formatDate(dateStr) {
  if (!dateStr) return '날짜 없음';
  const date = new Date(dateStr);
  return date.toISOString().split('T')[0];
}

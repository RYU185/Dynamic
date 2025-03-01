let cartItem = [];
let userName = null;
let selectedProductId = null;

$(document).ready(function () {
  userName = JSON.parse(sessionStorage.getItem("userName"));
  console.log("로그인된 사용자:", userName);

  if (!userName) {
    alert("로그인이 필요합니다");
    return;
  }

  loadingCart(userName);

  // 장바구니
  function loadingCart(userName) {
    $.ajax({
      url: "api/cart/username/" + userName,
      method: "GET",
      contentType: "application/json",
      success: function (cartResponse) {
        cartItem = cartResponse;
        console.log("장바구니 데이터 업데이트됨:", cartItem);
        loadProductList();
      },
      error: function () {
        alert("장바구니 정보를 불러오는 데 실패했습니다.");
      },
    });
  }

  // 모든 제품 목록 불러오기
  function loadProductList() {
    $.ajax({
      url: "/api/product/all",
      method: "GET",
      contentType: "application/json",
      success: function (response) {
        $("#courseContainer").empty();
        $("#subscContainer").empty();

        response.forEach((product) => {
          var title = product.title;
          var price = product.price;
          var addDate = product.addDate || "날짜 없음";
          var productId = product.id;
          var category = product.category?.name;
          var description = product.description || "";

          var $article = $(`
                        <article>
                            <div class="thumbnail">
                                <img src="./img/courseThumnail1.png" alt="${title} 썸네일" />
                            </div>
                            <h2>${title}</h2>
                            <br>
                            <div>
                                <span>가격: </span><span class="price">${price}</span><span>원</span>
                            </div>
                            <p>${addDate}</p>
                            <p>${description}</p>
                            <p>별점: ★★★★☆</p>
                            <button class="btnInCart" data-id="${productId}">장바구니</button>
                            <button class="btnPurchase" data-id="${productId}">구매</button>
                        </article>
                    `);

          if (category === "강의") {
            $("#courseContainer").append($article);
          } else {
            $("#subscContainer").append($article);
          }
        });
      },
      error: function () {
        alert("제품 목록을 불러오는 데 실패했습니다.");
      },
    });
  }
});

//  장바구니 추가
$(document).on("click", ".btnInCart", function () {
  var productId = $(this).data("id");

  console.log(productId);

  // 중복 확인
  let exist = false;
  for (let i = 0; i < cartItem.length; i++) {
    if (cartItem[i].productId === productId) {
      console.log("이미 장바구니에 있는 제품:", productId);
      exist = true;
      break;
    }
  }

  if (exist) {
    alert("모든 상품은 중복 구매가 불가능합니다");
    return;
  }

  var cartItemData = {
    productId: productId,
    username: userName,
    cartId: 0,
  };

// 장바구니 추가
$(document).on("click", ".btnInCart", function () {
  var productId = $(this).data("id");

  console.log(productId);

  // 1. 선택한 상품 정보 가져오기 (비동기)
  $.ajax({
    url: `/api/product/${productId}`,
    method: "GET",
    contentType: "application/json",
    success: function (selectedProduct) {
      if (!selectedProduct) {
        alert("상품 정보를 불러오는 데 실패했습니다.");
        return;
      }

      var category = selectedProduct.category.name;
      var startDate = selectedProduct.startDate;
      var expireDate = selectedProduct.expireDate;

      var hasLecture = false;
      var hasActiveSubscription = false;
      var exist = false;

      var today = new Date();

      // 2. 장바구니 항목 검사 (반복문 사용)
      for (var i = 0; i < cartItem.length; i++) {
        var item = cartItem[i];

        // 동일한 상품이 이미 있는 경우
        if (item.productId === productId) {
          exist = true;
          break;
        }

        // 강의 중복 검사
        if (category === "강의" && item.category === "강의") {
          hasLecture = true;
        }

        // 구독권 중복 검사 (유효 기간 확인)
        if (category === "구독권" && item.category === "구독권") {
          var subStart = new Date(item.startDate);
          var subExpire = new Date(item.expireDate);
          if (today >= subStart && today <= subExpire) {
            hasActiveSubscription = true;
          }
        }
      }

      // 3. 중복 검사 결과에 따른 처리
      if (exist) {
        alert("해당 제품은 이미 장바구니에 있습니다.");
        return;
      }

      if (hasLecture) {
        alert("강의는 하나만 장바구니에 담을 수 있습니다.");
        return;
      }

      if (hasActiveSubscription) {
        alert("현재 유효한 구독권이 있어 새로운 구독권을 추가할 수 없습니다.");
        return;
      }

      // 4. 장바구니 추가 요청 (비동기)
      var cartItemData = {
        productId: productId,
        username: userName,
        cartId: 0,
      };

      $.ajax({
        url: "/api/cart/save",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(cartItemData),
        success: function () {
          alert("제품이 정상적으로 장바구니에 담겼습니다.");
          loadingCart(userName); // 장바구니 데이터 다시 불러오기
        },
        error: function () {
          alert("장바구니 추가 중 오류가 발생했습니다.");
        },
      });
    },
    error: function () {
      alert("상품 정보를 불러오는 데 실패했습니다.");
    },
  });
});



// 검색창

function submit_go() {
  let titleInput = document.querySelector("input[id='search_product']");
  let sendData = titleInput.value

  if (!sendData) {
    alert("검색어를 입력하세요!");
    return; 
  }

  $.ajax({
    url: "/api/product/title/" + encodeURIComponent(sendData),
    method: "GET",
    contentType: "application/json",
    success: function (response) {
      let courseContainer = document.getElementById("courseContainer");
      let subscContainer = document.getElementById("subscContainer");

      courseContainer.innerHTML = "";
      subscContainer.innerHTML = "";

      if (response.length > 0) {
        response.forEach((product) => {
          let article = document.createElement("article");
          article.innerHTML = `
            <div class="thumbnail">
              <img src="./img/courseThumnail1.png" alt="1번째 동영상" />
            </div>
            <h2>${product.title}</h2>
            <br>
            <div>
              <span>가격: </span><span class="price">${product.price}</span><span>원</span>
            </div>
            <p>${product.addDate ? product.addDate : "날짜 없음"}</p>
            <p>별점: </p>
            <button class="btnInCart" data-id="${product.id}">장바구니</button>
            <button class="btnPurchase" data-id="${product.id}">구매</button>
          `;

          // 강의 또는 구독권에 따라 분류
          if (product.category === "강의") {
            courseContainer.appendChild(article);
          } else {
            subscContainer.appendChild(article);
          }
        });
      } else {
        alert("검색 결과가 없습니다.");
      }
    },
    error: function () {
      alert("검색 오류가 발생하였습니다.");
    }
  });
}

document.addEventListener("DOMContentLoaded", function () {
  let searchButton = document.querySelector(".searchBar button");

  if (searchButton) {
    searchButton.addEventListener("click", submit_go);
  }
});

const come_back = document.querySelector(".wrapCourse h1");
come_back.addEventListener("click", function(){
  window.location.href = "product.html"
});

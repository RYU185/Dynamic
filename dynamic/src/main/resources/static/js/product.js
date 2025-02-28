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
  $.ajax({
    url: "/api/cart/save",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(cartItemData),
    success: function () {
      alert("제품이 정상적으로 장바구니에 담겼습니다.");
      loadingCart(userName);
    },
    error: function () {
      alert("장바구니 추가 중 오류가 발생했습니다.");
    },
  });
});

// show / hide 사용 : div를 숨김처리할 수 있다.

$(document).on("click", ".btnPurchase", function () {
  selectedProductId = $(this).data("id");
  console.log("선택된 제품 ID:", selectedProductId);
  $(".purchaseConfirm").show();
});

$(document).on("click", "#yes", function () {
  console.log("구매 요청 사용자:", userName);

  if (!selectedProductId) {
    alert("구매할 제품을 선택하세요.");
    return;
  }

  var sendData = [
    {
      username: userName,
      productId: selectedProductId,
    },
  ];

  $.ajax({
    url: "/api/purchase-history/save/purchase-history-and-user-product",
    method: "POST",
    data: JSON.stringify(sendData),
    contentType: "application/json",
    success: function (response) {
      if (response) {
        alert("해당 제품이 구매되었습니다.");
      } else {
        alert("오류가 발생하여 해당 제품을 구매하지 못했습니다.");
      }
      $(".purchaseConfirm").hide();
    },
    error: function (xhr, status, error) {
      console.error("서버 응답 오류:", xhr.responseText);
      alert("이미 해당제품을 구매하였습니다.");
    },
  });
});

$(document).on("click", "#no", function () {
  $(".purchaseConfirm").hide();
});

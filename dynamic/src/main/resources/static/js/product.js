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

// 장바구니 추가
$(document).on("click", ".btnInCart", function () {
  var productId = $(this).data("id");
  console.log("장바구니 추가 요청, 제품 ID:", productId);

  $.ajax({
    url: "/api/product/id/" + encodeURIComponent(productId),
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

      for (var i = 0; i < cartItem.length; i++) {
        var item = cartItem[i];

        if (item.productId === productId) {
          exist = true;
          break;
        }

        // 강의 중복 검사
        if (category === "강의" && item.category === "강의") {
          hasLecture = true;
        }

        // 구독권 중복 검사
        if (category === "구독권" && item.category === "구독권") {
          var subStart = new Date(item.startDate);
          var subExpire = new Date(item.expireDate);

          var selectedStart = new Date(startDate);
          var selectedExpire = new Date(expireDate);

          if (
            (today >= subStart && today <= subExpire) ||
            (today >= selectedStart && today <= selectedExpire)
          ) {
            hasActiveSubscription = true;
          }
        }
      }

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
          loadingCart(userName);
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

// 마우스 오버 시 detail 출력
$(document).ready(function () {
  const productDetail = $(".product_detail");
  const productTitle = productDetail.find(".product_title");
  const productDate = productDetail.find(".upload_date");
  const productDescription = productDetail.find(".description");
  const productPrice = productDetail.find(".price");

  $(document).on("mouseenter", ".course article, .subsc article", function (event) {
    const productId = $(this).find(".btnPurchase").data("id");
    console.log("마우스 오버 - 제품 ID:", productId);

    if (!productId) {
      return;
    }

    $.ajax({
      url: `/api/product/id/${productId}`,
      method: "GET",
      dataType: "json",
      success: function (data) {
        console.log(data);

        productTitle.text(data.title);
        productDescription.text(data.description || "설명 없음");
        productPrice.text(`${data.price}원`);

        if (data.category === "강의") {
          productDate.text(data.addDate || "날짜 없음");
        } else if (data.category === "구독권") {
          productDate.text(`시작: ${data.startDate || "없음"} / 종료: ${data.expireDate || "없음"}`);
        } else {
          productDate.text("날짜 정보 없음");
        }

        productDetail.css("display", "block");
      },
      error: function () {
        console.error("상품 정보를 불러오지 못했습니다.");
      },
    });
  });

  $(document).on("mouseleave", ".course article, .subsc article", function () {
    productDetail.css("display", "none");
  });
});

let cartItem = [];
let userName = null;
let selectedProductId = null;
userName = JSON.parse(sessionStorage.getItem("userName"));

// 중복 체크용 유저네임을 통한 장바구니 가지고 오기
$(document).ready(function () {
  console.log("로그인된 사용자:", userName);

  loadingCart(userName);

  function loadingCart(userName) {
    $.ajax({
      url: "api/cart/username/" + userName,
      method: "GET",
      contentType: "application/json",
      success: function (cartResponse) {
        cartItem = cartResponse;
        console.log("장바구니 데이터 업데이트됨:", cartItem);
        //전체 제품을 가지고와서 중복 여부 비교
        loadProductList();
      },
      error: function () {
        alert("장바구니 정보를 불러오는 데 실패했습니다.");
      },
    });
  }
  // 전체 제품 가지고 오기
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


// 검색

function submit_go() {
  let titleInput = document.querySelector("input[id='search_product']");
  let sendData = titleInput.value;

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
  let searchIcon = document.getElementById("search_icon");

  if (searchIcon) {
    searchIcon.addEventListener("click", submit_go);
  }
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

        productDetail.css("display", "block"); // none이었던 css block으로
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


$(document).ready(function () {
  const userRole = JSON.parse(sessionStorage.getItem("userName"));

  if (userRole === "admin") {
    document.querySelector(".adminOnly").style.display = "block";
    $(".product-checkbox").css("display", "inline-block");
  }

  console.log("로그인 계정:", userName)

  $(".add").on("click", function () {
    $(".product_add").css("display", "block");
  });

  $(".closeBtn").on("click", function () {
    $(".product_add").css("display", "none");
  });

  //h1 누르면 원상태로 복귀
  const come_back = document.querySelector('.wrapCourse h1');
  come_back.addEventListener('click', function () {
    window.location.href = 'product.html';
  })

  //장바구니 추가 버튼

  $(".submit_add").on("click", function () {
    console.log("추가 버튼 클릭");
    const newId = document.querySelector(".new_id").value;
    const newTitle = document.querySelector(".new_title").value;
    const newDescription = document.querySelector(".new_description").value;
    const newAddDate = document.querySelector(".new_add_date").value;
    const newPrice = document.querySelector(".new_price").value;
    const fileData = document.getElementById("fileUpload").files[0];

    if (!newTitle || !newDescription || !newAddDate || !newPrice || !fileData) {
      alert("모든 필드를 채워주세요")
      return;
    }

    const productData = {
      id: newId,
      type: "course",
      title: newTitle,
      price: newPrice,
      category: { name: "강의" },
      isActive: true,
      courseDetails: {
        description: newDescription,
        addDate: newAddDate
      }
    };

    $.ajax({
      url: "/api/product/save",
      method: "POST",
      data: JSON.stringify(productData),
      contentType: "application/json",
      success: function (response) {
        alert("강의가 성공적으로 추가되었습니다.");
        $(".product_add").css("display", "none");

        `<h2>${response.title}</h2>
            <p>가격: ${response.price}원</p>
            <p>설명: ${response.courseDetails.description}</p>
            <p>업로드 날짜: ${response.courseDetails.addDate}</p>
          `;
        document.getElementById("courseContainer").appendChild(newProduct);
        window.location.href = "/product.html";
      },
      error: function () {
        alert("강의 추가 중 오류 발생")
      }
    })
  });

  let selectedProductId;
  // 바로구매 버튼 클릭시 유저제품/구매내역
  $(document).on("click", ".btnPurchase", function () {
    selectedProductId = $(this).data("id");
    console.log("즉시 구매 요청, 제품 ID:", selectedProductId);

    // 기존 구매 내역 조회
    $.ajax({
      url: `/api/purchase-history/username/${userName}`,
      method: "get",
      contentType: "application/json",
      success: function (purchaseHistory) {
        console.log("구매내역: ", purchaseHistory);

        let isPurchased = false;
        let isActiveSubscription = false;
        let selectedProduct = null;

        // 현재 선택한 제품 가져오기
        $.ajax({
          url: `/api/product/id/${selectedProductId}`,
          method: "GET",
          contentType: "application/json",
          success: function (product) {
            if (!product) {
              alert("상품 정보를 불러올 수 없습니다.");
              return;
            }

            selectedProduct = product;
            let category = product.category.name;

            // 구매내역 조회하고 중복확인
            purchaseHistory.forEach((item) => {
              if (item.productId === selectedProductId) {
                isPurchased = true;
              }

              // 조건2.
              if (category === "구독권" || item.product.category.name === "구독권") {
                let today = new Date();
                let expire = new Date(item.expireDate);

                if (today <= expire) {
                  isActiveSubscription = true;
                }
              }
            });

            if (category === "강의" && isPurchased) {
              alert("이미 구매한 강의입니다");
              return;
            }

            if (category === "구독권" && isActiveSubscription) {
              alert("현재 활성화된 구독권이 있어 구매할 수 없습니다.");
              return;
            }

            // 구매 확정 창 띄우기
            $(".purchaseConfirm").css("display", "block");
          }
        });
      },
      error: function () {
        alert("구매 내역을 불러올 수 없습니다.");
      }
    });

    // 아니오/x 누르면 끄기
    $("#no").on("click", function () {
      $(".purchaseConfirm").css("display", "none");
    });

    $("#yes").off("click").on("click", function () {
      var purchaseData = {
        productId: selectedProductId,
        username: userName
      }

      $.ajax({
        url: `/api/purchase-history/save/instant-buy/${selectedProductId}`,
        method: "post",
        contentType: "application/json",
        data: JSON.stringify(purchaseData),
        success: function (response) {
          alert("구매가 완료되었습니다");

          console.log("구매성공", response);

          $(".purchaseConfirm").css("display", "none");
        },
        error: function () {
          alert("바로구매 처리 중 에러가 발생하였습니다");
          console.error();
        },
      });
    });

    $(".purchaseConfirm .close").off("click").on("click", function () {
      $(".purchaseConfirm").off("click").css("display", "none");
    });
  });

  $(document).ready(function () {
    // 전체 선택 체크박스
    $("#selectAll").on("change", function () {
      $(".product-checkbox").prop("checked", $(this).prop("checked"));
    });

    $(document).on("change", ".product-checkbox", function () {
      $("#selectAll").prop("checked", $(".product-checkbox:checked").length === $(".product-checkbox").length);
    });

    $(".delete").on("click", function () {
      const selectedProducts = $(".product-checkbox:checked");

      if (selectedProducts.length === 0) {
        alert("삭제할 제품을 선택해 주세요");
        return;
      }

      if (!confirm("선택한 제품을 삭제하시겠습니까?")) {
        return;
      }

      const productIds = selectedProducts.map(function () {
        return $(this).data("id");
      }).get();

      deleteProducts(productIds);
    });

    // 삭제 요청
    function deleteProducts(productIds) {
      productIds.forEach((id) => {
        $.ajax({
          url: `/api/product/delete/${id}`,
          method: "POST",
          contentType: "application/json",
          success: function () {
            $(`.product-checkbox[data-id="${id}"]`).closest("article").remove();
          },
          error: function () {
            alert(`제품 ID ${id} 삭제 중 오류 발생`);
          }
        });
      });
    }
  });
});
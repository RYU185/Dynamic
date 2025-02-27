$(document).ready(function () {
    $.ajax({
        url: "/api/product/all",
        method: "get",
        contentType: "application/json",
        success: function (response) {
        $("#courseContainer").empty();
        $("#subscContainer").empty();

        response.forEach((product) => {
            console.log(product);
            console.log(product.title);

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

                    <button class="btnInCart" data-id="${productId}" data-title="${title}" data-price="${price}" data-category="${category}">장바구니</button>
                    <button class="btnPurchase" data-id="${productId}">구매</button>
                </article>
        `);

            if (category === "강의") {
            $("#courseContainer").append($article);
            } else {
            $("#subscContainer").append($article);
            }
        });


        $(".btnInCart").on("click", function () {
            var productId = $(this).data("id");
            var userName = JSON.parse(sessionStorage.getItem("userName"));

            if (!userName) {
              alert("로그인이 필요합니다.");
              return;
            }

            var cartItem = {
              productId: productId,
              userName: userName,
              cartId: 0
            };

        $.ajax({
          url: "/api/cart/save",
          type: "POST",
          contentType: "application/json",
          data: JSON.stringify(cartItem),
          success: function () {
            alert("제품이 정상적으로 장바구니에 담겼습니다.");
          },
          error: function () {
            alert("장바구니 추가 중 오류가 발생했습니다.");
          },
        });
        });
        },
    });
    });

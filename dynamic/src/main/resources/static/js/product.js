$(document).ready(function () {
    var userName = JSON.parse(sessionStorage.getItem("userName"));

    if (!userName) {
        alert("로그인이 필요합니다");
        return;
    }

    let cartItem = [];

    // 모든 장바구니 데이터
    function loadingCart(userName, callback) {
        $.ajax({
            url: "api/cart/username/" + userName,
            method: "GET",
            contentType: "application/json",
            success: function (cartResponse) {
                cartItem = cartResponse;
                console.log("🔹 장바구니 데이터 로드 완료:", cartItem); 
                if (callback) callback(); 
            },
            error: function () {
                alert("장바구니 정보를 불러오는 데 실패했습니다.");
            }
        });
    }

    // 중복 확인
    function checkExistProduct(productId) {
        let exist = false;

        for (let i = 0; i < cartItem.length; i++) {
            if ((cartItem[i].productId) === productId) { 
                console.log("⚠️ 중복 제품 발견:", productId);
                exist = true;
                break;
            }
        }
        return exist;
    }

    loadingCart(userName, function () {
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

                // 장바구니 추가 버튼
                $(document).on("click", ".btnInCart", function () {
                    var productId = $(this).data("id");

                    console.log(productId); 

                    if (checkExistProduct(productId)) {
                        alert("모든 상품은 중복 구매가 불가능합니다");
                        return;
                    }

                    var cartItemData = {
                        productId: productId,
                        userName: userName,
                        cartId: 0
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
                        }
                    });
                });
            },
            error: function () {
                alert("제품 목록을 불러오는 데 실패했습니다.");
            }
        });
    });
});

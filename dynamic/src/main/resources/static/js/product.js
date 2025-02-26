
// 내가 눌러야 할 버튼

$(document).ready(function () {
        $(".btnInCart").on("click", function () {
            const article = $(this).closest("article");

            const productId = $(this).attr("id");
            const productName = article.find("h2").text();
            const productPrice = article.find("p:contains('가격')").text().replace(/\D/g, ""); // p 태그에서 숫자만 추출하는 코드
            // 양쪽 문자열 다 떼고 공백제거


        let product = {
            id: productId,
            name: productName,
            price: parseInt(productPrice,10),
            // 10진수로 변환해달라는 뜻
            quantity:1
        };


        let cart = JSON.parse(localStorage.getItem("cart")) || [];

        let existProduct = cart.find(item => item.id === productId);
            if (existProduct) {
                existProduct.quantity += 1;
            } else {
                cart.push(product);
            }

            localStorage.setItem("cart",JSON.stringify(cart));

            $.ajax({
                type: "POST",
                url: "/cart/save", // Spring Boot에서 받을 엔드포인트
                contentType: "application/json",
                data: JSON.stringify(product),
                success: function (response) {
                    if(!response || !response.cartItem){
                        alert("장바구니가 올바르게 반환되지 않았습니다");
                        return;
                    }

                    $(".cart-item-list").empty();
                    

                    response.cartItem.empty((element) => {
                      let cartItem = `
                        <div class="cart-item">
                            <div class="check">
                                <input type="checkbox">
                            </div>
                            <div class="pic">
                                <img src="/img/default.png" alt="상품 이미지"> <!-- 기본 이미지 사용 가능 -->
                            </div>
                            <div class="description">
                                <h1>${item.name}</h1>
                                <p>${item.price.toLocaleString()}원</p>
                                <p>수량: ${item.quantity}</p>
                            </div>
                            <div class="counter">
                                <button class="btn minus" data-id="${
                                    item.id
                                }">-</button>
                                <span class="count">${item.quantity}</span>
                                <button class="btn plus" data-id="${
                                    item.id
                                }">+</button>
                            </div>
                            <div class="close">
                                <img src="./img/X.png" class="delete-item" data-id="${
                                    item.id
                                }">
                            </div>
                        </div>`;
                      $(".cart-item-list").append(cartItem); // 장바구니 목록에 추가
                    });

                alert(`${productName}이(가) 장바구니에 추가되었습니다.`);
                },
            });


            alert(`${productName}이(가) 장바구니에 추가되었습니다.`);
    })
});

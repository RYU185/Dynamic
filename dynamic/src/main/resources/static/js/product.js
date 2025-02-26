
// 내가 눌러야 할 버튼

$(document).ready(function () {
        $(".btnInCart").on("click", function () {
            const article = $(this).closest("article");

            const productId = $(this).attr("id");
            const productName = article.find("h2").text();
            const productPrice = article.find("p:contains('가격')").text().replace(/\D/g, ""); // p 태그에서 숫자만 추출하는 코드
            const productImg = article.find(".thumbnail img").attr("src")
            // 양쪽 문자열 다 떼고 공백제거


        let product = {
            id: productId,
            name: productName,
            price: parseInt(productPrice,10),
            // 10진수로 변환해달라는 뜻
            quantity:1,
            image: productImg
        };

        //장바구니 가져오기
        let cart = JSON.parse(localStorage.getItem("cart")) || [];


        // 중복여부 확인
        let existProduct = cart.find(item => item.id === productId);
            if (existProduct) {
                existProduct.quantity += 1;
            } else {
                cart.push(product);
            }

            localStorage.setItem("cart",JSON.stringify(cart));

            $.ajax({
                type: "POST",
                url: "/cart/save", 
                contentType: "application/json",
                data: JSON.stringify(product),
                success: function (response) {
                  if (!response || !response.cartItem) {
                    alert("장바구니가 올바르게 반환되지 않았습니다");
                    return;
                  }

                  $(".cart-item-list").empty();

                  response.cartItems.forEach((item) => {
                    let cartItem = `
                        <div class="cart-item">
                            <div class="check">
                                <input type="checkbox">
                            </div>
                            <div class="pic">
                                <img src="${item.image}" alt="상품 이미지">
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

                localStorage.setItem(
                    "cart",
                    JSON.stringify(response.cartItems)
                );

                  // 장바구니에 들어가는 총 갯수
                let totalQuantity = response.cartItems.reduce(
                    (sum, item) => sum + item.quantity,
                    0
                );

                  // 장바구니에 들어가는 제품 총액
                let totalPrice = response.cartItems.reduce(
                    (sum, item) => sum + item.price * item.quantity,
                    0
                );
                
                $("#cartCount").text(totalQuantity); //장바구니 갯수 화면에 업데이트
                $("#totalPrice").text(totalPrice.toLocaleString() + "원"); // 장바구니 총액 화면에 업데이트

                alert(`${productName}이(가) 장바구니에 추가되었습니다.`);
                },

                error:function(){
                    alert("장바구니 추가 중 오류가 발생하였습니다")
                }
            });
            alert(`${productName}이(가) 장바구니에 추가되었습니다.`);
    })
});

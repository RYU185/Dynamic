

$(document).ready(function () {
    $(".btnInCart").on("click", function () {
        const article = $(this).closest("article");

        const productId = $(this).attr("id");
        const productName = article.find("h2").text();
        const productPrice = article.find(".price").text()
        const productImg = article.find(".thumbnail img").attr("src")
        const userName = JSON.parse(sessionStorage.getItem('userName'))
        // 양쪽 문자열 다 떼고 공백제거


        const product = {
            cartId: 1,
            userName: userName,
            productId: productId
        };

        console.log("전송할 상품 데이터:", product);

        $.ajax({
            type: "POST",
            url: "/api/cart/save",
            contentType: "application/json",
            data: JSON.stringify(product),
            success: function (response) {

                // if (!response || !response.cartItem) {

                //     alert("장바구니가 올바르게 반환되지 않았습니다");
                //     return;
                // }

                localStorage.setItem("cart", JSON.stringify(response.cartItems));

                //     $(".cart-item-list").empty();

                //     response.cartItems.forEach((item) => {
                //         let cartItem = `<li>${item.name}</li>`;
                //         $(".cart-item-list").append(cartItem); // 장바구니 목록에 추가
                //     });

                //     localStorage.setItem(
                //         "cart",
                //         JSON.stringify(response.cartItems)
                //     );

                //     // 장바구니에 들어가는 총 갯수    
                //     let totalQuantity = response.cartItems.reduce(
                //         (sum, item) => sum + item.quantity,
                //         0
                //     );

                //     // 장바구니에 들어가는 제품 총액
                //     let totalPrice = response.cartItems.reduce(
                //         (sum, item) => sum + item.price * item.quantity,
                //         0
                //     );

                //     $("#cartCount").text(totalQuantity); //장바구니 갯수 화면에 업데이트
                //     $("#totalPrice").text(totalPrice.toLocaleString() + "원"); // 장바구니 총액 화면에 업데이트

                //     alert(`${productName}이(가) 장바구니에 추가되었습니다.`);
                // },

                // error: function () {
                //     alert("장바구니 추가 중 오류가 발생하였습니다")
            }
        });
        alert(`${productName}이(가) 장바구니에 추가되었습니다.`);

    })
});


$(document).ready(function(){
    $.ajax({
        url: '/api/product/all',
        method:'get',
        contentType:'application/json',
        success: function (response){
            $('article').empty();
            response.forEach((element) => {
                var $article = $(`
                    <article>
                        <h2>제품카테고리: ${element.category}</h2>
                        <div>
                            <span>가격: </span><span class="price">${
                                element.price
                            }</span><span>원</span>
                        </div>
                        <div>
                            <span>상태: </span><span class="status">${
                                element.isActive ? "판매 중" : "판매 종료"
                            }</span>
                        </div>
                        <div class="btn">
                            <button class="btnInCart" data-id="${
                                element.id
                            }">장바구니</button>
                            <button class="btnPurchase">구매</button>
                        </div>
                    </article>
            `);

            $('article').append($article);
            $article.on('click',() => {
                console.log(element.category + ' '+ element.title);
                window.location.href = '/'
            })
        })
    }})
})
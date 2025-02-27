
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
                $()

                alert(`${productName}이(가) 장바구니에 추가되었습니다.`);
            }
        });
    });


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
        var price = product.price.toLocaleString();
        var addDate = product.addDate || "날짜 없음"; // addDate가 없으면 기본값
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
    });
});
    
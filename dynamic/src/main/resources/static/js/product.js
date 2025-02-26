
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

            alert(`${productName}이(가) 장바구니에 추가되었습니다.`);
    })
});

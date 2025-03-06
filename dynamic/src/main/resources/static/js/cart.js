
userName = JSON.parse(sessionStorage.getItem("userName"));
var sendCart = [];
console.log("장바구니 데이터:", sendCart);
$(document).ready(function () {
    $.ajax({
        url: '/api/cart/all',
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
            $("#courseCartList").empty();
            $("#subscCartList").empty();
            response.forEach(element => {
                if (element.isActive === true) {
                    console.log(element);
                    var $row = $(`
                                     <div class="cart-item" id=${element.cartId}>               
                                            <div class="pic">
                                                 <img src="./img/courseThumnail1.png" alt="${element.product.title}">
                                             </div>
                                             <div class="description">
                                                <h1>${element.product.title}</h1>
                                                <p>가격: ${element.product.price}원</p>
                                            </div>
                                            <img src="/img/X.png" alt="취소 버튼" class="x_btn"/>
                                        </div>
                                        `);
                    var $row1 = $(`
                                    <div class="cart-item" id=${element.cartId}>               
                                         <div class="pic">
                                              <img src="./img/구독권 이미지.png" alt="${element.product.title}">
                                               </div>
                                                <div class="description">
                                                <h1>${element.product.title}</h1>
                                                 <p>가격: ${element.product.price}원</p>
                                                </div>
                                                <img src="/img/X.png" alt="취소 버튼" class="x_btn"/>
                                               </div>
                                               `);
                    if (element.product.category.name === "강의") {
                        $("#courseCartList").append($row);
                    } else {
                        $("#subscCartList").append($row1);
                    }
                    const delete_course = $row.find('.x_btn');
                    delete_course.on('click', function (e) {
                        const button = e.target;
                        const parent = button.closest('.cart-item');
                        console.log(parent.id);
                        $.ajax({
                            url: 'api/cart/delete/' + parent.id,
                            method: 'DELETE',
                            contentType: 'application/json',
                            success: function () {
                                window.location.href = '/cart.html';
                            }
                        });
                    });
                    const delete_subscription = $row1.find('.x_btn');
                    delete_subscription.on('click', function (e) {
                        const button = e.target;
                        const parent = button.closest('.cart-item');
                        console.log(parent.id);
                        $.ajax({
                            url: 'api/cart/delete/' + parent.id,
                            method: 'DELETE',
                            contentType: 'application/json',
                            success: function () {
                                window.location.href = '/cart.html';
                            }
                        });
                    });


                }
                let item = {
                    cartId: element.cartId,
                    user: userName,
                    product: element.product,
                };
                sendCart.push(item);


                // 3. 장바구니 데이터 전송
                if (sendCart.length === 0) {
                    alert("장바구니가 비어 있습니다.");
                    return;
                }

            });

        }
    });
});

$(".btn-purchase").on("click", function () {
    const month = sessionStorage.getItem('month');
    const payrollsubscription = JSON.parse(sessionStorage.getItem('payrollsubscription'));
    if (("#subscCartList").length !== 0) {
        var sendData = {
            "type": "payrollsubscription",
            "id": payrollsubscription.id
        }
        console.log(sendData);
        $.ajax({
            url: "/api/product/update/payrollsubscription/" + month,
            method: "PUT",
            data: JSON.stringify(sendData),
            contentType: 'application/json',
            success: function () {
                sessionStorage.setItem("expireDate", sendData.expireDate);
            }
        })
    }
    $.ajax({
        url: "/api/purchase-history/save/purchase-history-and-user-product",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(sendCart),
        success: function () {
            alert("결제가 완료되었습니다.");
            window.location.href = '/cart.html';

        },
    })
})

const userRole = JSON.parse(sessionStorage.getItem('userName'));
const expireDate = sessionStorage.getItem('expireDate');
if (userRole === 'admin') {
    document.querySelector('#add_course').style.display = 'inline';
}
// db랑 front랑 연결하는 코드
$(document).ready(function () {
    $.ajax({
        url: '/api/product/all',
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
            $('#course_box').empty();
            response.forEach((element) => {

                if (element.category === "강의") {
                    var $row = $(`<div class="box1" id="${element.id}">
                        <div class="img"><img src="img/courseThumnail1.png" alt="이미지"></div>
                        <div class="title">제목 : ${element.title}</div>
                        <div class="amount">가격 : ${element.price}</div>
                       <button class="cart">장바구니</button>
  <button class="buy">구매</button>
                      </div>`);
                    $('#course_box').append($row); // 테이블에 새 행 추가

                    const cart = $row.find('.cart');
                    cart.on('click', function (e) {
                        const button = e.target;
                        const parent = button.closest('.box1');
                        console.log(parent.id);

                        var sendData = {
                            "cartId": 0,
                            "userName": userRole,
                            "productId": parent.id
                        }
                        $.ajax({
                            url: 'api/cart/product-id/' + parent.id,
                            method: 'get',
                            contentType: 'application/json',
                            success: function (response) {
                                console.log(response);
                                response.forEach((element) => {
                                    alert('이미 장바구니에 담긴 혹은 구매한 제품입니다')
                                    return;
                                })
                            },
                            error: function () {
                                $.ajax({
                                    url: 'api/cart/save',
                                    method: 'POST',
                                    data: JSON.stringify(sendData),
                                    contentType: 'application/json',
                                    success: function () {
                                        alert('장바구니에 담겼습니다')
                                        window.location.href = '/product!.html';
                                    },
                                })
                            }
                        })
                    })
                }
            });
        }
    });
})

document.querySelectorAll('.cart1').forEach(function (cart) {
    cart.addEventListener('click', function (e) {
        expireDate
        const currentDate = new Date().toLocaleString();
        if (expireDate > currentDate) {
            alert("아직 만료일이 지나지 않은 구독권이 있습니다");
            return;
        }
        const button = e.target;
        const parent = button.closest('.box2');
        console.log(parent.id);
        const div = button.closest('div');
        const title = div.querySelector('.title');
        const price = div.querySelector('.price');
        console.log(title.innerText, price.innerText);
        let count = 1;
        var sendData = {
            "type": "payrollsubscription",
            "id": "S" + count++,
            "price": price.innerText,
            "category": "정기 구독권",
            "title": title.innerText
        }
        $.ajax({
            url: 'api/cart/product-category/' + sendData.category,
            method: 'get',
            contentType: 'application/json',
            success: function (response) {
                console.log(response);
                response.forEach((element) => {
                    alert('이미 장바구니에 담긴 제품입니다')
                    return;
                })
            },
            error: function () {
                $.ajax({
                    url: 'api/product/save/payrollsubscription/' + parent.id,
                    method: 'POST',
                    data: JSON.stringify(sendData),
                    contentType: 'application/json',
                    success: function (response) {
                        sessionStorage.setItem("payrollsubscription", JSON.stringify(sendData));
                        sessionStorage.setItem("month", parent.id);

                    }
                })
                var sendCart = {
                    "cartId": 0,
                    "userName": userRole,
                    "productId": sendData.id
                }
                console.log(sendCart);
                $.ajax({
                    url: 'api/cart/save',
                    method: 'POST',
                    data: JSON.stringify(sendCart),
                    contentType: 'application/json',
                    success: function () {
                        alert('장바구니에 담겼습니다')
                        window.location.href = '/product!.html';
                    },
                })
            }
        })
    })
})

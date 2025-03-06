const userRole = JSON.parse(sessionStorage.getItem('userName'));
const expireDate = sessionStorage.getItem('expireDate');
const existBusinessOperator = sessionStorage.getItem('existBusinessOperator');
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
                if (element.category === '강의') {
                    var $row = $(`<div class="box1" id="${element.id}">
                        <div class="img"><img src="img/courseThumnail1.png" alt="이미지"></div>
                      <div class ="title_wrap">제목 : </div><span class="title">${element.title}</span>
                      <div class "price_wrap">가격 : </div><span class="price"> ${element.price}</span>
                       <button class="cart">장바구니</button>
                      </div>`);
                    $('#course_box').append($row); // 테이블에 새 행 추가
                    const cart = $row.find('.cart');
                    cart.on('click', function (e) {
                        const button = e.target;
                        const parent = button.closest('.box1');
                        const div = button.closest('div');
                        const title = div.querySelector('.title');
                        const price = div.querySelector('.price');
                        console.log(parent.id);

                        var sendData = {
                            type: 'course',
                            id: parent.id,
                            price: price.innerText,
                            category: '강의',
                            title: title.innerText,
                        };

                        var sendData = {
                            cartId: 0,
                            userName: userRole,
                            product: sendData,
                        };
                        $.ajax({
                            url: 'api/cart/product-id/' + parent.id,
                            method: 'get',
                            contentType: 'application/json',
                            success: function (response) {
                                console.log(response);
                                response.forEach((element) => {
                                    alert('이미 장바구니에 담긴 혹은 구매한 제품입니다');
                                    return;
                                });
                            },
                            error: function () {
                                $.ajax({
                                    url: 'api/cart/save',
                                    method: 'POST',
                                    data: JSON.stringify(sendData),
                                    contentType: 'application/json',
                                    success: function () {
                                        alert('장바구니에 담겼습니다');
                                        window.location.href = '/product!.html';
                                    },
                                });
                            },
                        });
                    });
                }
            });
        },
    });
});

document.querySelectorAll('.cart1').forEach(function (cart) {
    cart.addEventListener('click', function (e) {
        if (existBusinessOperator === 'false') {
            alert('사업자 등록 후 구매 가능합니다.');
            return;
        }
        expireDate;
        const currentDate = new Date().toLocaleString();
        if (expireDate > currentDate) {
            alert('아직 만료일이 지나지 않은 구독권이 있습니다');
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
            type: 'payrollsubscription',
            id: 'S' + count++,
            price: price.innerText,
            category: '정기 구독권',
            title: title.innerText,
        };
        $.ajax({
            url: 'api/cart/product-category/' + sendData.category,
            method: 'get',
            contentType: 'application/json',
            success: function (response) {
                console.log(response);
                response.forEach((element) => {
                    alert('이미 장바구니에 담긴 제품입니다');
                    return;
                });
            },
            error: function () {
                $.ajax({
                    url: 'api/product/save/payrollsubscription/' + parent.id,
                    method: 'POST',
                    data: JSON.stringify(sendData),
                    contentType: 'application/json',
                    success: function (response) {
                        sessionStorage.setItem(
                            'payrollsubscription',
                            JSON.stringify(sendData)
                        );
                        sessionStorage.setItem('month', parent.id);
                    },
                });
                var sendCart = {
                    cartId: 0,
                    userName: userRole,
                    product: sendData,
                };
                console.log(sendCart);
                $.ajax({
                    url: 'api/cart/save',
                    method: 'POST',
                    data: JSON.stringify(sendCart),
                    contentType: 'application/json',
                    success: function () {
                        alert('장바구니에 담겼습니다');
                        window.location.href = '/product!.html';
                    },
                });
            },
        });
    });
});

const add_button = document.querySelector("#add_course");
const pop_up = document.querySelector(".pop-up");
const background = document.querySelector("#pop-up-background");
if (userRole === 'admin') {
    add_button.style.display = 'inline';
}
add_button.addEventListener('click', function () {
    if (pop_up.style.display === 'none' || pop_up.style.display === '') {
        pop_up.style.display = 'block'; // display: block 으로 변경
        background.style.display = 'block';
    } else {
        pop_up.style.display = 'none'; // display: none 으로 다시 변경
        background.style.display = 'block';
    }
})
document.querySelector('.x_btn').addEventListener('click', function () {
    pop_up.style.display = 'none';
    background.style.display = 'none';
});
$(document).on('input', '#price', function () {
    let value = $(this)
        .val()
        .replace(/[^0-9]/g, ''); // 숫자 외의 문자 제거
    if (value) {
        // 3자리마다 ',' 추가
        value = value.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    }
    $(this).val(value); // 포맷팅된 값을 input에 설정
});

document
    .querySelector("input[type='button']")
    .addEventListener('click', function () {

        const title = document.querySelector('#title').value.trim();
        const price = document.querySelector('#price').value.trim().replace(/,/g, '') ||
            0;
        const currentDate = (document.querySelector('#add_date').value =
            new Date().toLocaleString());
        const description = document.querySelector('#description').value.trim();

        console.log(title, price, description);
        let count = 7;
        var sendData = {
            "type": "course",
            "id": "C" + count++,
            "price": price,
            "category": "강의",
            "title": title,
            "description": description
        }
        if (
            price &&
            title &&
            description
        ) {
            $.ajax({
                url: '/api/product/save',
                method: 'POST',
                data: JSON.stringify(sendData),
                contentType: 'application/json',
                success: function (response) {
                    alert('제품이 정상 등록되었습니다.');
                    var $row = $(`<div class="box1" id="${response.id}">
                        <div class="img"><img src="img/courseThumnail1.png" alt="이미지"></div>
                        <div class ="title_wrap">제목 : </div><div class="title">${response.title}</div>
                        <div class ="price_wrap">가격 : </div><div class="price">${response.price}</div>
                       <button class="cart">장바구니</button>
                      </div>`);
                    $('#course_box').append($row);
                    window.location.href = '/product.html';
                },
            });
        }
    })


function submit_go() {
    let name = document.querySelector("input[id='search_product']");
    var sendData = name.value; // title 값만 사용
    $.ajax({
        url: '/api/product/title/' + encodeURIComponent(sendData), // URL에 검색어(title) 추가
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
            $('#course_box').empty();
            response.forEach((element) => {
                if (element.category === '강의') {
                    var $row = $(`<div class="box1" id="${element.id}">
                        <div class="img"><img src="img/courseThumnail1.png" alt="이미지"></div>
                      <div class ="title_wrap">제목 : </div><span class="title">${element.title}</span>
                      <div class "price_wrap">가격 : </div><span class="price"> ${element.price}</span>
                       <button class="cart">장바구니</button>
                      </div>`);
                    $('#course_box').append($row);
                }
            })
        },
        error: function () {
            $('#course_box').empty();
        },
    });
}

$(document).on('click', '#home', function () {
    window.location.href = "/product.html";
})

// function formatSpecificCells() {
//     // 'price' 클래스를 가진 td 요소만 선택
//     const priceCells = document.querySelectorAll('.hourly_rate');

//     priceCells.forEach(cell => {
//         const price = parseInt(cell.textContent, 10); // 텍스트를 숫자로 변환

//         if (!isNaN(price)) {
//             // 천 단위 구분 기호 추가
//             cell.textContent = price.toLocaleString();
//         }
//     });
// }



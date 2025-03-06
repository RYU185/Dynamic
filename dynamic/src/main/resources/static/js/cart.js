
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
                                     <div class="cart-item"">               
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
                    if (element.product.category.name === "강의") {
                        $("#courseCartList").append($row);
                    } else {
                        $("#subscCartList").append($row);
                    }
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





// let cartItem = [];
// // 전체 장바구니
// $(document).ready(function () {
//     var userName = JSON.parse(sessionStorage.getItem("userName"));

//     //
//     let productData = {};


//     $.ajax({
//         url: "api/product/all",
//         method: "get",
//         contentType: "application/json",
//         success: function (productResponse) {
//             productResponse.forEach(product => {
//                 productData[product.id] = {
//                     title: product.title,
//                     price: product.price,
//                     category: product.category

//                 }
//                 console.log(product.category);
//             });
//             loadingCart(userName);
//         },
//         error: function () {
//             alert("제품목록을 불러오는 데 실패했습니다.")
//         }
//     });

//     // 유저의 장바구니에 가져오기

//     function loadingCart(userName) {
//         $.ajax({
//             url: "api/cart/username/" + userName,
//             method: "get",
//             contentType: "application/json",
//             success: function (cartResponse) {
//                 $.ajax({
//                     url: "api/cart/is-active",
//                     method: "get",
//                     contentType: "application/json",
//                     success: function (response) {
//                         response.forEach((element) => {
//                             if (element.isActive === "true")
//                                 cartItem = cartResponse;
//                                 onCart();
//                         })
//                     }
//                 })

//             },
//             error: function () {
//                 alert("장바구니 정보를 불러오는데 실패했습니다");
//             }
//         });
//     }



//     function updateCartSummary() {
//         let totalCount = cartItem.length;
//         let totalPrice = 0;


//         for (let i = 0; i < cartItem.length; i++) {
//             let product = productData[cartItem[i].productId];
//             if (product) {
//                 totalPrice += product.price;
//             }
//         }

//         $(".calc-all p").text(`총${totalCount}개`);
//         $(".calc-all h1").text(`${totalPrice}원`);
//     }

//     // 최종으로 장바구니를 화면으로 가져옴
//     function onCart() {
//         $("#courseCartList").empty();
//         $("#subscCartList").empty();

//         // 장바구니 목록 반복
//         cartItem.forEach(cartItem => {
//             var productOnList = productData[cartItem.productId];

//             console.log(productOnList);

//             if (productOnList) {
//                 var $cartItem = $(`
//                     <div class="cart-item">
//                         <div class="pic">
//                             <img src="./img/courseThumnail1.png" alt="${productOnList.title}">
//                         </div>
//                         <div class="description">
//                             <h1>${productOnList.title}</h1>
//                             <p>가격: ${productOnList.price}원</p>
//                         </div>
//                     </div>
//                     `);

//                 if (productOnList.category === "강의") {
//                     $("#courseCartList").append($cartItem);
//                 } else {
//                     $("#subscCartList").append($cartItem);
//                 }
//             }
//         });

//         updateCartSummary();
//     }


//     $(document).ready(function () {
//         $(".btn-purchase").on("click", function () {
//             // const startdate = sessionStorage.getItem('startdate');
//             // const expiredate = sessionStorage.getItem('expiredate');
//             const month = sessionStorage.getItem('month');
//             const payrollsubscription = JSON.parse(sessionStorage.getItem('payrollsubscription'));
//             if (("#subscCartList").length != 0) {
//                 var sendData = {
//                     "type": "payrollsubscription",
//                     "id": payrollsubscription.id
//                 }
//                 console.log(sendData);
//                 $.ajax({
//                     url: "/api/cart/update/payrollsubscription/" + month,
//                     method: "PUT",
//                     data: JSON.stringify(sendData),
//                     contentType: 'application/json',
//                     success: function () {
//                         lender();
//                     }
//                 });
//             }
//             lender();
//         });
//     });
// })

// function lender() {
//     console.log("cartItem:", JSON.stringify(cartItem, null, 2));

//     if (cartItem.length === 0) {
//         alert("장바구니가 비어있습니다.");
//         return;
//     }

//     if (!confirm("결제를 진행하시겠습니까?")) return;

//     console.log("서버로 전송할 데이터:", JSON.stringify(cartItem, null, 2));

//     $.ajax({
//         url: "/api/purchase-history/save/purchase-history-and-user-product",
//         method: "POST",
//         contentType: "application/json",
//         data: JSON.stringify(cartItem),
//         success: function () {
//             alert("결제가 완료되었습니다.");
//             deleteCartFromServer();
//             window.location.href = "/mypage.html";
//         },

//         error: function (xhr) {
//             console.error("결제 처리 중 오류 발생:", xhr.responseText);

//             let errorMessage = "결제 처리 중 오류가 발생했습니다.";

//             try {
//                 let response = JSON.parse(xhr.responseText);
//                 if (response["Invalid Request"]) {
//                     errorMessage = response["Invalid Request"];
//                 }

//             } catch (e) {
//                 console.error("JSON 파싱 오류:", e);
//             }

//             alert(errorMessage);
//         },
//     });
// }


// function deleteCartFromServer() {
//     if (cartItem.length === 0) return;

//     cartItem.forEach(item => {
//         $.ajax({
//             url: "/api/cart/delete/" + item.cartId,
//             method: "POST"
//         });
//     });

//     cartItem = [];
//     onCart();
// }
let cartItem = [];
// 전체 장바구니
$(document).ready(function (){
    var userName = JSON.parse(sessionStorage.getItem("userName"));

    if(!userName){
        alert("로그인이 필요합니다");
        return;
    }

    //
    let productData = {};


    $.ajax({
        url:"api/product/all",
        method:"get",
        contentType:"application/json",
        success: function(productResponse){
            productResponse.forEach(product => {
                productData[product.id] = {
                    title: product.title,
                    price: product.price,
                    category: product.category
                    
                }
                console.log(product.category);
            });
            loadingCart(userName);
        },
        error:function(){
            alert("제품목록을 불러오는 데 실패했습니다.")
        }
    });

    // 유저의 장바구니에 가져오기

    function loadingCart(userName){
        $.ajax({
                url:"api/cart/username/"+ userName,
                method:"get",
                contentType:"application/json",
                success: function(cartResponse){

                    cartItem = cartResponse;
                    onCart();
                },
                error:function(){
                    alert("장바구니 정보를 불러오는데 실패했습니다");
                }
        });
    }


    
    function updateCartSummary(){
        let totalCount = cartItem.length;
        let totalPrice = 0;


    for (let i=0 ; i < cartItem.length; i++){
        let product = productData[cartItem[i].productId];
        if(product){
            totalPrice+= product.price;
        }
    }

    $(".calc-all p").text(`총${totalCount}개`);
    $(".calc-all h1").text(`${totalPrice}원`);
}

    // 최종으로 장바구니를 화면으로 가져옴
    function onCart(){
        $("#courseCartList").empty();
        $("#subscCartList").empty();

        // 장바구니 목록 반복
            cartItem.forEach(cartItem => {
            var productOnList = productData[cartItem.productId];

            console.log(productOnList);

            if(productOnList){
                var $cartItem = $(`
                    <div class="cart-item">
                        <div class="pic">
                            <img src="./img/courseThumnail1.png" alt="${productOnList.title}">
                        </div>
                        <div class="description">
                            <h1>${productOnList.title}</h1>
                            <p>가격: ${productOnList.price}원</p>
                        </div>
                    </div>
                    `);
                    
                    if (productOnList.category === "강의") {
                        $("#courseCartList").append($cartItem);
                    } else {
                        $("#subscCartList").append($cartItem);
                }
            }
        });

        updateCartSummary();
    }

    function deleteCartFromServer() {
        if (cartItem.length === 0) return;
    
        cartItem.forEach(item => {
            $.ajax({
                url: "/api/cart/delete/" + item.cartId,
                method: "POST"
            });
        });
    
        cartItem = [];
        onCart();
    }


$(document).ready(function(){
    $(".btn-purchase").on("click", function() {

        console.log("cartItem:", JSON.stringify(cartItem, null, 2));

        if(cartItem.length === 0){
            alert("장바구니가 비어있습니다.");
            return;
        }
    
        if (!confirm("결제를 진행하시겠습니까?")) return;

        console.log("서버로 전송할 데이터:", JSON.stringify(cartItem, null, 2));


        $.ajax({
            url:`/api/purchase-history/username/` + userName,
            method:"GET",
            contentType:"application/json",
            success: function(purchaseHistory){
                console.log(purchaseHistory);

                let alreadyPurchased =[];

                for(let i = 0; i< cartItem.length; i++){
                    let cart = cartItem[i];

                    for (let j = 0; j<purchaseHistory.length; j++){
                        let purchase = purchaseHistory[j];

                        if(cart.productId === purchase.productId){
                            alreadyPurchased.push(cart.productId);
                        }
                    }
                }

                if (alreadyPurchased.length > 0) {
                    let productNames = alreadyPurchased.join(", ");
                    alert(`이미 구매한 제품이 있습니다: ${productNames}`);
                    return; 
                }
                
                processPurchase();
            }
        });
    });

    function processPurchase(){
        $.ajax({
                url:"/api/purchase-history/save/purchase-history-and-user-product",
                method:"POST",
                contentType:"application/json",
                data: JSON.stringify(cartItem),
                success: function(){
                    alert("결제가 완료되었습니다.");
                    deleteCartFromServer();
                    window.location.href = '/mypage.html';
                },       
            
                error:function(){
                    alert("결제 처리 중 오류가 발생했습니다");
                }
            })
        }
    });
});

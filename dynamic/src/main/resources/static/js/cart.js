document.querySelectorAll('.checkbox-all').forEach(allCheckbox => {
    allCheckbox.addEventListener('click', function () {
        const section = this.closest('.checkbox-wrap'); // 현재 섹션 찾기
        const checkboxes = section.querySelectorAll('.checkbox-item');

        checkboxes.forEach(checkbox => {
            checkbox.checked = this.checked;
        });
    });
});

// 개별 체크박스 상태 변경 시 전체 선택 체크박스 상태 업데이트
document.querySelectorAll('.checkbox-item').forEach(checkbox => {
    checkbox.addEventListener('click', function () {
        const section = this.closest('.checkbox-wrap');
        const allCheckbox = section.querySelector('.checkbox-all');
        const allCheckboxes = section.querySelectorAll('.checkbox-item');
        const checkedBoxes = section.querySelectorAll('.checkbox-item:checked');

        allCheckbox.checked = allCheckboxes.length === checkedBoxes.length;
    });
});

document.addEventListener('click',function(event){
    if(event.target.closest('.close img')){
        const cartItem = event.target.closest('.cart-item');
        if(cartItem){
            cartItem.remove();
        }
    }
});

//db front 연결//ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ
$.ajax({
    url:"/api/cart/all",
    method:"GET",
    contentType:"application/json",
    success: function(response){
        if(!response || !response.cartItem){
            $(".cart-item-list").html("<p>장바구니가 비어 있습니다.</p>");
            return;
        }

        $(".cart-item-list").empty();

        response.cartItem.forEach((item)=>{
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
                </div>`;
            $(".cart-item-list").append(cartItem); 
        });

        let totalQuantity = response.cartItems.reduce(
          (sum, item) => sum + item.quantity,
          0
        );
        let totalPrice = response.cartItems.reduce(
          (sum, item) => sum + item.price * item.quantity,
          0
        );

        $("#cartCount").text(totalQuantity);
        $("#totalPrice").text(totalPrice.toLocaleString() + "원");
    },
    error:function(){
        alert('장바구니를 불러오지 못했습니다')
    }
});


$(document).ready(function () {
    loadCartItems(); // 장바구니 데이터 불러오기

    function loadCartItems() {
        $.ajax({
            url: '/api/cart/all',
            method: 'GET',
            contentType: 'application/json',
            success: function (response) {
                $('.cart-item-list').empty(); 

                response.forEach((item) => {
                    var formattedPrice = item.price.toLocaleString();
                    var totalPrice = (item.price * item.quantity).toLocaleString();

                    var $row = $(`
                        <div class="cart-item" data-id="${item.id}">
                            <div class="check">
                                <input type="checkbox" class="checkbox-item">
                            </div>
                            <div class="pic">
                                <img src="${item.imageUrl}" alt="제품 이미지">
                            </div>
                            <div class="description">
                                <h1>${item.title}</h1>
                                <p class="item-price" data-price="${item.price}">${totalPrice}원</p>
                            </div>
                            <div class="counter">
                                <button class=
                                "btn minus">-</button>
                                <span class="count">${item.quantity}</span>
                                <button class="btn plus">+</button>
                            </div>
                            <div class="close">
                                <img src="./img/X.png" class="delete-item" data-id="${item.id}">
                            </div>
                        </div>
                    `);
                    $('.cart-item-list').append($row);
                });

                updateCartSummary();
            }
        });
    }

    $(document).on('click', '#checkbox-all', function () {
        console.log("전체 선택 클릭됨", this.checked);
        $('.checkbox-item').prop('checked', this.checked);
    });

    // 개별 체크박스 변경하면 off되는 전체선택
    $(document).on('click', '.checkbox-item', function () {
        const allCheckboxes = $('.checkbox-item').length;
        const checkedBoxes = $('.checkbox-item:checked').length;
        
        console.log(`체크박스 상태: 전체(${allCheckboxes}) | 선택됨(${checkedBoxes})`); // 디버깅 로그
        $('#checkbox-all').prop('checked', allCheckboxes === checkedBoxes);
    });

    // 수량 변경
    $(document).on('click', '.btn', function () {
        const isPlus = $(this).hasClass('plus');
        const cartItem = $(this).closest('.cart-item');
        const countElement = cartItem.find('.count');
        let quantity = parseInt(countElement.text(), 10);

        if (isPlus) {
            quantity += 1;
        } else {
            if (quantity > 1) quantity -= 1;
        }

        countElement.text(quantity);

        // 가격 
        const itemPrice = cartItem.find('.item-price');
        const pricePerUnit = parseInt(itemPrice.data('price'), 10);
        itemPrice.text(`${(pricePerUnit * quantity).toLocaleString()}원`);

        updateCartSummary(); // 총 가격
    });

    // 장바구니 총 가격과 개수
    function updateCartSummary() {
        let totalQuantity = 0;
        let totalPrice = 0;

        $('.cart-item').each(function () {
            const quantity = parseInt($(this).find('.count').text(), 10);
            const priceText = $(this).find('.item-price').text().replace(/[^0-9]/g, "");
            const price = parseInt(priceText, 10);

            totalQuantity += quantity;
            totalPrice += price;
        });

        $('.total-quantity').text(totalQuantity);
        $('.total-price').text(totalPrice.toLocaleString());
    }


    $(document).on('click', '.delete-item', function () {
        const cartItem = $(this).closest('.cart-item');
        cartItem.remove();
        updateCartSummary();
    });
    // 장바구니 삭제
    $(document).on('click', '.delete', function () {
        $('.checkbox-item:checked').each(function () {
            $(this).closest('.cart-item').remove();
        });
        updateCartSummary();
    });
});
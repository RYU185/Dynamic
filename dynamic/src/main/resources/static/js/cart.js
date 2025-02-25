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

$(document).ready(function () {
    $.ajax({
        url: '/api/cart/all', // 백엔드 API 엔드포인트
        method: 'GET',
        contentType: 'application/json',
        success: function (response) {
            $('.cart-item-list').empty(); // 기존 목록 초기화

            response.forEach((item) => {
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
                            <p class="item-price" data-price="${item.price}">${item.price * item.quantity}원</p>
                        </div>
                        <div class="counter">
                            <button class="btn minus" data-id="${item.id}">-</button>
                            <span class="count">${item.quantity}</span>
                            <button class="btn plus" data-id="${item.id}">+</button>
                        </div>
                        <div class="close">
                            <img src="./img/X.png" class="delete-item" data-id="${item.id}">
                        </div>
                    </div>
                `);
                $('.cart-item-list').append($row); // 목록에 추가
            });

            updateCartSummary(); // 총 가격 및 개수 업데이트
        }
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

        countElement.text(quantity); // 수량 업데이트

        // 가격 업데이트
        const itemPrice = cartItem.find('.item-price');
        const pricePerUnit = parseInt(itemPrice.data('price'), 10);
        itemPrice.text(`${pricePerUnit * quantity}원`);

        updateCartSummary(); // 총 가격 및 제품 개수 업데이트
    });

    // 장바구니 총 가격 및 개수 업데이트
    function updateCartSummary() {
        let totalQuantity = 0;
        let totalPrice = 0;

        $('.cart-item').each(function () {
            const quantity = parseInt($(this).find('.count').text(), 10);
            const price = parseInt($(this).find('.item-price').text(), 10);

            totalQuantity += quantity;
            totalPrice += price;
        });

        $('.calc-all p').text(`총 ${totalQuantity}개`);
        $('.calc-all h1').text(`${totalPrice}원`);
    }
});
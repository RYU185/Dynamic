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
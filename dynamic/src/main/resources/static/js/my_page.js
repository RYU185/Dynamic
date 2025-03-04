const existBusinessOperator = sessionStorage.getItem('existBusinessOperator');
console.log(existBusinessOperator);
$(document).on('click', '#employee_box', function () {

    const isexist = existBusinessOperator === 'true';
    if (!isexist) {
        alert('사업자가 없을 경우 별도로 직원 등록 및 조회가 불가능합니다');
        return;
    } else {
        window.location.href = '/my_employee.html';
    }
})
const existBusinessOperator = sessionStorage.getItem('existBusinessOperator');
console.log(existBusinessOperator);

const expireDate = sessionStorage.getItem('expireDate');
const currentDate = new Date().toLocaleString();
console.log(expireDate);
$(document).on('click', '#employee_box', function () {
  const isexist = existBusinessOperator === 'true';
  if (!isexist) {
    alert(
      '사업자가 없을 경우 별도로 직원 등록 및 조회가 불가능합니다.사업자 등록 후 정기 구독권 구매 후 이용 바랍니다.'
    );
    return;
  }
  if (!expireDate) {
    alert('정기 구독권 구매 후 이용 가능합니다.');
    return;
  } else {
    if (expireDate < currentDate) {
      alert('정기 구독권 만료일이 지났습니다. 구매 후 이용 바랍니다.');
      return;
    } else {
      window.location.href = '/my_employee.html';
    }
  }
});

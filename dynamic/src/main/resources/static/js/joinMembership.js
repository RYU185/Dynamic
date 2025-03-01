let selectedOption = null; // 선택된 값 추적

$('#true').on('click', function () {
  selectedOption = 'true'; // 기존사업자 선택
  $('#true').addClass('selected'); // 선택된 상태로 스타일 변경
  $('#false').removeClass('selected'); // 다른 선택 해제
});

// 창업예정자 클릭 시
$('#false').on('click', function () {
  selectedOption = 'false'; // 창업예정자 선택
  $('#false').addClass('selected'); // 선택된 상태로 스타일 변경
  $('#true').removeClass('selected'); // 다른 선택 해제
});

// NEXT 버튼 클릭 시
$('.next_box').on('click', function () {
  var existBusinessOperator = selectedOption;
  console.log(existBusinessOperator);
  sessionStorage.setItem(
    'existBusinessOperator',
    JSON.stringify(existBusinessOperator)
  );

  if (selectedOption === 'true') {
    window.location.href = '/businessoperator_true.html'; // 기존사업자 선택 시 해당 페이지로 이동
  } else if (selectedOption === 'false') {
    window.location.href = '/businessoperator_false.html'; // 창업예정자 선택 시 해당 페이지로 이동
  } else {
    alert('옵션을 선택해주세요.'); // 선택되지 않았을 경우 경고
  }
});

$('#home').on('click', function () {
  window.location.href = '/index.html';
});

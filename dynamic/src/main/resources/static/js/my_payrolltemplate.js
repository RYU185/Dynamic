const total_amount = sessionStorage.getItem("total_amount");

console.log()
$(document).ready(function () {

    $.ajax({
        url: '/api/payrolltemplate/all',
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
            $('tbody').empty();
            response.forEach((element) => {
                //  가이드 제목, ID, 제목만 표시       
                var $row = $(`<tr class="row" id ="${element.id}">
          <td>${element.employeeName}</td>
          <td>${element.position}</td>
          <td>${total_amount}</td>
          <td>${element.position}</td>
          <td>${element.paymentDate}</td>

        </tr>`);
                $('tbody').append($row); // 테이블에 새 행 추가

            });

        }
    })
}
)
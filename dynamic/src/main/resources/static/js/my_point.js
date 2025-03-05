$(document).ready(function () {

    $.ajax({
        url: 'api/point/all',
        method: 'get',
        contentType: 'application/json',
        success: function (response) {
            addRowToTop(response);
            formatSpecificCells();
        }
    })
})

function addRowToTop(response) {
    let count = 0;
    response.forEach((element) => {
        count = count + element.amount;
        const table = document.querySelector('table');
        const tbody = table.querySelector('tbody');

        // 새로운 tr 생성
        const newRow = document.createElement('tr');
        const cell1 = document.createElement('td');
        const cell2 = document.createElement('td');
        const cell3 = document.createElement('td');
        const cell4 = document.createElement('td');

        cell1.textContent = '적립';
        cell2.textContent = element.amount;
        cell3.textContent = element.addDate;
        cell4.textContent = count;

        cell2.classList.add('amount');
        cell4.classList.add('amount');

        newRow.appendChild(cell1);
        newRow.appendChild(cell2);
        newRow.appendChild(cell3);
        newRow.appendChild(cell4);



        // 새로운 행을 tbody의 맨 앞에 추가
        tbody.insertBefore(newRow, tbody.firstChild);
    })
}

function formatSpecificCells() {
    // 'price' 클래스를 가진 td 요소만 선택
    const priceCells = document.querySelectorAll('.amount');

    priceCells.forEach(cell => {
        const price = parseInt(cell.textContent, 10); // 텍스트를 숫자로 변환

        if (!isNaN(price)) {
            // 천 단위 구분 기호 추가
            cell.textContent = price.toLocaleString();
        }
    });
}



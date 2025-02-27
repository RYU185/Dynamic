const userRole = JSON.parse(sessionStorage.getItem('userName'));
const calendar = document.querySelector('.down');





$(document).on('click', '.show_calendar', function () {

    const name = document.querySelector('#name')
    const birthday = document.querySelector('#birthday')
    const position = document.querySelector('#position')
    const department = document.querySelector('#department')
    const hiredate = document.querySelector('#hiredate')
    const phone = document.querySelector('#phone')
    const start_date = document.querySelector('#start_date')
    const last_date = document.querySelector('#last_date')
    const hourly_rate = document.querySelector('#hourly_rate')
    const payment_date = document.querySelector('#payment_date')

    if (start_date && last_date && hourly_rate && payment_date && birthday && position && department && hiredate && phone && name) {
        if (calendar.style.display != 'block') {
            calendar.style.display = 'block';
        } else {
            alert('정보 입력이 완료어야 달력 생성이 가능합니다')
            return;
        }
    }
});


$(document).on('click', '.apply', function () {

    const name = document.querySelector('#name')
    const birthday = document.querySelector('#birthday')
    const position = document.querySelector('#position')
    const department = document.querySelector('#department')
    const hiredate = document.querySelector('#hiredate')
    const phone = document.querySelector('#phone')
    const start_date = document.querySelector('#start_date')
    const last_date = document.querySelector('#last_date')
    const hourly_rate = document.querySelector('#hourly_rate')
    const payment_date = document.querySelector('#payment_date')

    var sendData = {
        "payrollTemplateDTO": {
            "id": 0,
            "startPayrollPeriod": start_date.innerText,
            "lastPayrollPeriod": last_date.innerText,
            "paymentDate": payment_date.innerText
        },
        "employeeDTO": {
            "id": 0,
            "name": name.innerText,
            "department": department.innerText,
            "position": position.innerText,
            "hourlyRate": hourly_rate.innerText,
            "birthday": birthday.innerText,
            "hireDate": hiredate.innerText,
            "phoneNumber": phone.innerText
        }
    }
    console.log(sendData);
    $.ajax({
        url: '/api/employee/use/free-payrolltemplate',
        method: "POST",
        data: JSON.stringify(sendData),
        contentType: 'application/json',
        success: function (response) {
            sessionStorage.setItem('userData', JSON.stringify(sendData));
            window.location.href = '/payrolltemplate.html'
        }
    })
});
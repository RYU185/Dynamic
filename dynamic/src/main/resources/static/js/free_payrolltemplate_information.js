const userRole = JSON.parse(sessionStorage.getItem('userName'));

$(document).on('click', '.info', function () {
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
        {
        "payrollTemplateDTO" : {
            "id" : 0,
                "startPayrollPeriod": start_date.innerText,
                    "lastPayrollPeriod": last_date.innerText,
                        "paymentDate": payment_date.innerText
        },
        "employeeDTO" : {
            "id" : 0,
                "name": name.innerText,
                    "department": department.innerText,
                        "position": position.innerText,
                            "hourlyRate" : hourly_rate.innerText,
                                "birthday" : birthday.innerText,
                                    "hireDate": hiredate.innerText,
                                        "phoneNumber": phone.innerText
        }
    }
}


});
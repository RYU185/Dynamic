const userRole = JSON.parse(sessionStorage.getItem("userName"));

document.addEventListener("DOMContentLoaded", function () {
  fetch("header.html")
    .then((response) => response.text())
    .then((data) => {
      document.querySelector("header").innerHTML = data;

      // 로그인여부 확인
      if (userRole) {
        document.querySelectorAll(".header_upper li").forEach((li) => {
          li.classList.toggle("hidden");
        });
        $(".name").text(userRole);
      }

      // 로그아웃 버튼 이벤트
      document.querySelector(".log_out").addEventListener("click", function () {
        sessionStorage.removeItem("userName");
        window.location.href = "/index.html";
      });

      // 버튼과 콘텐츠 요소 가져오기
      document.querySelector(".button").addEventListener("click", function () {
        if (userRole) {
          $.ajax({
            url: "api/employee/free-template-count",
            method: "get",
            contentType: "application/json",
            success: function (response) {
              console.log(response);
              if (userRole != "admin") {
                if (response >= 5) {
                  alert("이용 가능한 무료 횟수가 다 소진되었습니다");
                  return;
                } else {
                  window.location.href =
                    "/free_payrolltemplate_information.html";
                }
              } else {
                window.location.href = "/free_payrolltemplate_information.html";
              }
            },
          });
        } else {
          alert("로그인 후 이용이 가능합니다.");
        }
      });

      console.log("DOMContentLoaded 완료");
    });
});

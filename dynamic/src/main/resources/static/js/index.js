const userRole = JSON.parse(sessionStorage.getItem("userName"));

window.onload = function () {
  if (userRole) {
    window.location.href = "/index.html";
  }
};

document.querySelector(".button").addEventListener("click", function () {
  alert("로그인 후 이용이 가능합니다.");
  return;
});

$(document).on("click", ".nav", function () {
  alert("로그인 후 이용이 가능합니다.");
  window.location.href = "/index.html";
  window.location.href = "/header.html";
});

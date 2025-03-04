$(document).ready(function () {
  let courses = [];

  function fetchUserCourses() {
    $.ajax({
      url: "/api/user-product/all",
      method: "GET",
      contentType: "application/json",
      success: function (data) {
        console.log("서버 응답 데이터:", data);
        courses = data.filter((item) => item.product.type === "course");
        console.log("담긴 강의: ", courses);
        renderCourses();
      },
      error: function (xhr, status, error) {
        console.error("데이터 불러오기 실패:", error);
      },
    });
  }

  function renderCourses() {
    const cardWrap = $(".cardwrap");
    cardWrap.empty();

    courses.forEach((course) => {
      const title = course.product.title;
      const description = course.product.description;

      const courseCard = `
            <div class="card">
                  <div class="pic">
                  <a href="#"><img src="./img/courseThumnail1.png" /></a>
                  </div>
                  <p>강의 제목:</p>
                  <br />
                  <p>${title}</p>
                  <p>강의 설명:${description}</p>
            </div>
            `;
      cardWrap.append(courseCard);
    });
  }
  fetchUserCourses();

  // card클릭시 모달 창 열기
  


// title을 통한 검색 기능
function submit_go(){
      let title = document.querySelector("input[id='search_mycourse']");
      var sendData = title.value;

      if(sendData === ""){
            alert("검색어를 입력해주세요");
            return;
      }

      $.ajax({
            url:"/api/user-product/product-name/" + encodeURIComponent(sendData),
            method:"get",
            contentType: 'application/json',
            success: function(response){
                  const cardWrap = $(".cardwrap");
                  cardWrap.empty();
                  if(response.length > 0){
                        response.forEach((course)=>{
                              const courseCard = `
                              <div class="card">
                                    <div class="pic">
                                    <a href="#"><img src="${course.product.thumbnail}" alt="강의 썸네일" /></a>
                                    </div>
                                    <p>강의 제목:</p>
                                    <p>${course.product.title}</p>
                                    <p>강의 설명: ${course.product.description}</p>
                              </div>
                              `;
                              cardWrap.append(courseCard);
                        });
                  }else{
                        cardWrap.append("<p>검색 결과가 없습니다</p>");
                  }
            },
            error: function (xhr, status, error) {
            console.error("검색 요청 실패:", error);
            }
      })
}


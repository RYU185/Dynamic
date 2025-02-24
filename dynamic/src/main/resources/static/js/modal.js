
document.addEventListener('DOMContentLoaded', function(){
  // 변수 선언 - modal창 닫기
  const closeModal = document.getElementById("modal");
  
  // 변수 선언 - modal창 열기
  const openModal = document.querySelector(
    ".main_box tr:not(:first-of-type) td:nth-of-type(2)");

  // 함수 - modal창 닫기 
  function modalClose(){
    closeModal.style.display = "none";
    console.log("Modal close");
  }

  // 함수 - modal창 열기
  function modalOpen(){
    closeModal.style.display = "block";
    console.log("Modal Open");
  }

  openModal.addEventListener('click', function(event){
    if(event.target === openModal){
      modalOpen();
    }
  })

  closeModal.addEventListener("click", function(event2){
    if(event2.target === closeModal){
      modalClose();
    }
  })
})


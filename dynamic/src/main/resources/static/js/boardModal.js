
const opacity = document.querySelector('.opacity');
const overlay = document.querySelector('.overlay')
const cells = document.querySelectorAll("table tr:not(:first-child) td:nth-child(2)");
const content1 = document.querySelector('.pop-up1');
const content2 = document.querySelector('.pop-up2');

// 바깥 클릭시 display 속성 바꾸기
content2.addEventListener('click', function(){
  if (content2.style.display === 'none'){
      content2.style.display = 'block';
      opacity.style.display = 'block';
      overlay.style.display = 'block';
  }
  else {
    content2.style.display = 'none';
  }
});

cells.forEach(cell => {
  cell.addEventListener("click", function() {
      content2.style.display = "block";
      opacity.style.display = "block";
      overlay.style.display = 'block';
  });
});

overlay.addEventListener('click', function(){
    content2.style.display = 'none';
    opacity.style.display = 'none'
    overlay.style.display = 'none';
})
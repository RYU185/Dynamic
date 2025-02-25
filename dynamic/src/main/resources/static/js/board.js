
const opacity = document.querySelector('.opacity');
const overlay = document.querySelector('.overlay');
const cells = document.querySelectorAll("table tr:not(:first-child) td:nth-child(2)");
const content1 = document.querySelector('.pop-up1');
const content2 = document.querySelector('.pop-up2');
const addNewPost = document.querySelector('.btn img[src="./img/add.png"]');

function openModalNewPost(){
  content1.style.display = "block";
  opacity.style.display = "block";
  overlay.style.display = "block";
}

function openModalDetail() {
  content2.style.display = "block";
  opacity.style.display = "block";
  overlay.style.display = "block";
}

function closeModal() {
  content1.style.display = "none"
  content2.style.display = "none";
  opacity.style.display = "none";
  overlay.style.display = "none";
}

addNewPost.addEventListener('click', openModalNewPost);

cells.forEach(cell => {
  cell.addEventListener("click", openModalDetail);
});

opacity.addEventListener('click', closeModal);

//  내가 누를 요소(html에서 가져올 것) + 기능(이걸 누르면 css에서 어떻게 변하는지)



const opacity = document.querySelector('.opacity');
const overlay = document.querySelector('.overlay')
const cells = document.querySelectorAll("table tr:not(:first-child) td:nth-child(2)");
const content1 = document.querySelector('.pop-up1');
const content2 = document.querySelector('.pop-up2');

function openModal() {
  content2.style.display = "block";
  opacity.style.display = "block";
  overlay.style.display = "block";
}

function closeModal() {
  content2.style.display = "none";
  opacity.style.display = "none";
  overlay.style.display = "none";
}

cells.forEach(cell => {
  cell.addEventListener("click", openModal);
});

opacity.addEventListener('click', closeModal);
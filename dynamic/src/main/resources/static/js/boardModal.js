
const opacity = document.querySelector('.opacity');
const content1 = document.querySelector('.pop-up1');
const content2 = document.querySelector(
  '.main_box tr:not(:first-of-type) td:nth-of-type(2)');

content2.addEventListener('click', function(event){
  if (content2.style.display === 'none'){
      content2.style.display = 'block';
      opacity.style.display = 'block';
  }
  else {
    content2.style.display = 'none';
  }
});

opacity.addEventListener('click', function(event){
  if(!content2.contains(event.target)){
    content2.style.display = 'none';
    opacity.style.display = 'none'
  }
})
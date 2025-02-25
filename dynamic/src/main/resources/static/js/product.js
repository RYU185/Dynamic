
const deleteBtn = document.querySelector('.delete');
const addBtn = document.querySelector('.add');

const userRole = JSON.parse(sessionStorage.getItem('userName'));
if (userRole === 'admin'){
    deleteBtn.style.
}

document.querySelector('.searchBtn').addEventListener('click', function(){
    const courseTitle = document.querySelector(#)


    document.querySelector()
})


$(document).ready(function(){
    $.ajax({
        url:'api/product/all',
        method:'GET',
        contentType: 'application/json',
        success: function(response){
            $('tbody').empty();

            response.forEach(element => {
                var content = '
                <tr></tr>';
                
            });
        }
    })
})
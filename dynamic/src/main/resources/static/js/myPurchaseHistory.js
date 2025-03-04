$(document).ready(function(){
    let products = [];

    function fetchPurchaseHistory(){
        $.ajax({
            url:`/api/purchase-History/all`,
            method:"get",
            contentType:"application/json",
            success: function(data){
                console.log("서버 응답 데이터: ", data);
            }
        })
    }
})
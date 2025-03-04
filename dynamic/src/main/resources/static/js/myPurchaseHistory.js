document.addEventListener("DOMContentLoaded", function(){
    const searchInput = document.querySelector(".searchBar input");
    const courseList = document.querySelector(".courseList .course");
    const subscList = document.querySelector(".subscList .subsc");

    let purchaseData = [];

    function loadingPurchaseHistory(){
        $.ajax({
            url:`/api/purchase-history/all`,
            method:"get",
            contentType:"application/json",
            success: function(data){
                purchaseData = data;
                renderPurchaseHistory(purchaseData);
                console.log(data);
            },
            error:function(){
                alert("구매내역을 불러오는 데 실패했습니다")
            }
        });
    };
    
    function renderPurchaseHistory(data){
        console.log("🔹 renderPurchaseHistory 실행됨!", data);

        const courseList = document.querySelector(".courseList .course");
        const subscList = document.querySelector(".subscList .subsc");

        courseList.innerHTML = "";
        subscList.innerHTML = "";

        data.forEach((item) => {
            const product = item.product;
            if(product.isActive){
            if(product.type === "course"){
                console.log("강의추가: ",product.title);
                const courseHTML =`
                        <article>
                            <div class="thumbnail">
                                <img
                                    src="./img/courseThumnail1.png"
                                    alt="1번째 동영상"
                                />
                            </div>
                            <h2>${product.title}</h2>
                            <p>구매날짜:${formatDate(item.addDate)}</p>
                            <p>구매금액:${product.price.toLocaleString()}원</p>
                        </article>
                    
                    `;
                    courseList.innerHTML += courseHTML;
                }else if (product.type === "payrollsubscription"){
                    console.log("구독권 추가: ",product.title)
                    subscList.innerHTML += `
                        <article>
                            <div class="thumbnail">
                            <img src="./img/default-subscription.png" alt="구독권 이미지" />
                            </div>
                            <h2>${product.title}</h2>
                            <p>시작일: ${formatDate(product.startDate)}</p>
                            <p>종료일: ${formatDate(product.expireDate)}</p>
                        </article>
                    `;
                }
            }
        });
        console.log("화면 업데이트 완료")
    }

    searchInput.addEventListener("input", function(){
        const query = searchInput.value;

        if(query===""){
            renderPurchaseHistory(purchaseData);
            return;
        }

        const filteredData = purchaseData.filter((item) => 
            item.isActive && item.title.toLowerCase().includes(query)
        );

        if(filteredData.length === 0 ){
            alert("검색 결과가 없습니다");
        }

        renderPurchaseHistory(filteredData);
    });

    loadingPurchaseHistory();

    // 날짜 포맷 함수 (YYYY-MM-DD 형식)
    function formatDate(dateStr) {
        if (!dateStr) return "날짜 없음";
        const date = new Date(dateStr);
        return date.toISOString().split("T")[0];
    }
});


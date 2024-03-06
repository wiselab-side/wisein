        function validCheck() {
            let result = true;
             if(document.querySelector("#subject").value == ''){
                commonPopup.alertPopup('제목을 입력하세요✍');
                document.querySelector("#subject").focus();
                result = false;
             }else if(editor.getMarkdown() == ''){
                commonPopup.alertPopup('내용을 입력하세요✍')
                editor.focus();
                result = false;
             }
             return result;
        };

        function reg(){
            let category = document.querySelector("#category").value;
            let subject = document.querySelector("#subject").value;
            let content = editor.getHTML();
            let data = {category: category, subject: subject, content: content, brdNum: brdNum};

            if(validCheck()){
                fetch('/tipBoard',{
                    method: 'POST',
                    cache : 'no-cache',
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify(data)
                })
                .then(response => response.text())
                .catch(error => console.error('Error:', error))
                .then(response => window.location.href = "/tipList")
            }
        };

        function udp(){
            let num = document.location.search.replace(/[^0-9]/g,"");
            let category = document.querySelector("#category").value;
            let subject = document.querySelector("#subject").value;
            let content = editor.getHTML();
            let data = {num: num, category: category, subject: subject, content: content};

            if(validCheck()){
                  fetch('/updTip',{
                        method: 'POST',
                        cache : 'no-cache',
                        headers: {"Content-Type": "application/json"},
                        body:JSON.stringify(data)
                  })
                  .then(response => response.text())
                  .catch(error => console.error('Error:', error))
                  .then(response => window.location.href = "/tipDetail?num="+num)
            }
        };

        async function cancel(){
            if(await commonPopup.confirmPopup('진짜 취소하실꺼에여?🥺', commonPopup.callback)){
                window.history.back()
            }
        };

        /*
         * 작성자 : 오연경
         * TIP 게시판 카테고리 선택
         * param : event
         * return : URL 이동
         * 날짜 : 2024-02-20
         * */
        // 카테고리 선택
        function selectSearchType(){
            let selectedSearchType = document.querySelector("#selectOption").value;

            // 현재 URL 가져오기
            let currentUrl = window.location.href;

            // searchType 파라미터가 있는지 확인 (indexOf : 값이 있으면 인덱스값, 없으면 -1 반환)
            let searchTypeParamTF = currentUrl.indexOf('searchType=');

            // "전체"가 선택된 경우 모든 카테고리 조회
            if (selectedSearchType == "전체") {
                window.location.href = currentUrl.replace(/searchType=[^&]+/, 'searchType=' + 'all');
                return;
            }

            // 이미 카테고리 선택된 경우(if)
            let newUrl;
            if (searchTypeParamTF !== -1) {
                // 이미 category 파라미터(&를 제외^한 전체[] 문자)가 있으면 해당 파라미터 대체
                newUrl = currentUrl.replace(/searchType=[^&]+/, 'searchType=' + selectedSearchType);
                newUrl = newUrl.replace(/currentPageNo=[^&]+/, 'currentPageNo=1');
            } else {
                // category 파라미터가 없으면 추가
                newUrl = currentUrl + (currentUrl.indexOf('?') !== -1 ? '&' : '?') + 'searchType=' + selectedSearchType;
            }

            // 새로운 URL로 이동
            window.location.href = newUrl;
        };

        /*
         * 작성자 : 오연경
         * TIP 게시판 목록 정렬
         * param : event
         * return : URL 이동
         * 날짜 : 2024-02-20
         * */
        function sort(event){
            let sorted = event.target.closest('.board-cell').getAttribute('value');
            let order;

            // 현재 URL 가져오기
            let currentUrl = window.location.href;

            // 파라미터가 있는지 확인 (indexOf : 값이 있으면 인덱스값, 없으면 -1 반환)
            let sortParamTF = currentUrl.indexOf('sort=');
            let orderParamTF = currentUrl.indexOf('order=');

            // currentUrl 에서 newUrl 로 변경
            let newUrl;
            if (sortParamTF !== -1) {
                if(currentUrl.search(sorted) !== -1) {
                    if(currentUrl.search('desc') !== -1) {
                        order = 'asc';
                    } else {
                        order = 'desc';
                    }
                    newUrl = currentUrl.replace(/order=[^&]+/, 'order=' + order);
                } else if(currentUrl.search(sorted) === -1) {
                    // 이미 category 파라미터(&를 제외^한 전체[] 문자)가 있으면 해당 파라미터 대체
                    newUrl = currentUrl.replace(/sort=[^&]+/, 'sort=' + sorted);
                    newUrl = newUrl.replace(/order=[^&]+/, 'order=desc');
                }
            } else if (sortParamTF === -1) {
                // 파라미터가 없으면 추가
                newUrl = currentUrl + (currentUrl.indexOf('?') !== -1 ? '&' : '?') + 'sort=' + sorted + '&order=desc';
            }

            // 새로운 URL로 이동
            window.location.href = newUrl;
        };
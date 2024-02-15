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

        // 카테고리 선택
        function selectSearchType(){
            let selectedSearchType = document.querySelector("#selectOption");

            // 현재 URL 가져오기
            let currentUrl = window.location.href;

            // searchType 파라미터가 있는지 확인 (indexOf : 값이 있으면 인덱스값, 없으면 -1 반환)
            let searchTypeParamTF = currentUrl.indexOf('searchType=');

            // "전체"가 선택된 경우 모든 카테고리 조회
            if (selectedSearchType.text() == "전체") {

//                if (searchTypeParamTF !== -1) {
//                    // 이미 category 파라미터가 있으면 해당 파라미터 제거
//                    currentUrl = currentUrl.substring(0, searchTypeParamTF - 1);
//                }
//                window.location.href = "/tipList";
                window.location.href = currentUrl.replace(/searchType=[^&]+/, 'searchType=' + 'all');
                return;
            }

            // 이미 카테고리 선택된 경우(if)
            let newUrl;
            if (searchTypeParamTF !== -1) {
                // 이미 category 파라미터(&를 제외^한 전체[] 문자)가 있으면 해당 파라미터 대체
                newUrl = currentUrl.replace(/searchType=[^&]+/, 'searchType=' + selectedSearchType.value);
                newUrl = newUrl.replace(/currentPageNo=[^&]+/, 'currentPageNo=1');
            } else {
                // category 파라미터가 없으면 추가
                newUrl = currentUrl + (currentUrl.indexOf('?') !== -1 ? '&' : '?') + 'searchType=' + selectedSearchType.value;
            }

            // 새로운 URL로 이동
            window.location.href = newUrl;
        };

        function sort(event){
            let sorted = event.target.closest('.board-cell').getAttribute('value');

            // 현재 URL 가져오기
            let currentUrl = window.location.href;

            // 파라미터가 있는지 확인 (indexOf : 값이 있으면 인덱스값, 없으면 -1 반환)
            let ParamTF = currentUrl.indexOf('sort=');

            // 이미 content 로 sort 된 경우 전체 조회
            if (sorted == "${sorted}") {
                window.location.href = "/tipList";
                return;
            }

            // 카테고리 선택된 경우
            let newUrl;
            if (ParamTF !== -1) {
                // 이미 category 파라미터(&를 제외^한 전체[] 문자)가 있으면 해당 파라미터 대체
                newUrl = currentUrl.replace(/sort=[^&]+/, 'sort=' + sorted);
            } else {
                // category 파라미터가 없으면 추가
                newUrl = currentUrl + (currentUrl.indexOf('?') !== -1 ? '&' : '?') + 'sort=' + sorted;
            }

            // 새로운 URL로 이동
            window.location.href = newUrl;
        };
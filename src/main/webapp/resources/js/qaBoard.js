
        function regQa(writer, category){

            if(writer != ""){
                document.querySelector("#content").value = editor.getHTML();
                var num = document.querySelector("#num").value;
                var parentNum = document.querySelector("#parentNum").value;
                //var category = "";
                var subject = document.querySelector("#subject").value;
                var content = document.querySelector("#content").value;

                if(subject == ""){
                    commonPopup.alertPopup('제목을 입력하세요.');
                    return false;
                }

                if(content == '<p><br></p>'){
                    commonPopup.alertPopup('본문을 입력하세요.');
                    return false;
                }

                if(category != ""){
                    document.querySelector("#category").value = category;
                    document.querySelector("#updGubun").value = "Y";
                }
                if(num == ""){document.querySelector("#num").value = 0;}
                if(parentNum == ""){document.querySelector("#parentNum").value = 0;}

                var form = document.getElementById("qaBoardForm");
                form.action = "/qaBoard";
                form.method = "POST";
                form.submit();
            } else if(writer == ""){
               commonPopup.alertPopup("로그인 후 이용가능합니다.");
            }
        }

        function update(){
            document.querySelector("#content").value = editor.getHTML();

            var form = document.getElementById("qaBoardForm");
            form.action = "/qaUpdatePro";
            form.method = "POST";
            form.submit();
        }

        async function cancel(){
            if(await commonPopup.confirmPopup('진짜 취소하실꺼에여?🥺', commonPopup.callback)){
                console.log('뒤로가기되찌롱');
                window.history.back()
            }
        }

        /*
         * 작성자 : 오연경
         * QA 게시판 카테고리 선택
         * param : searchType
         * return : URL 이동
         * 날짜 : 2024-02-20
         * */
        // 카테고리 선택
        function selectSearchType(){
            let selectedSearchType = document.querySelector("#selectOption").value;

            // 현재 URL 가져오기
            let currentUrl = window.location.href;

            // 파라미터가 있는지 확인 (indexOf : 값이 있으면 인덱스값, 없으면 -1 반환)
            let searchTypeParamTF = currentUrl.indexOf('searchType=');

            // "카테고리"가 선택된 경우 모든 카테고리 조회
            if (selectedSearchType == "all") {
                currentUrl = currentUrl.replace(/searchType=[^&]+/, 'searchType=all');
                window.location.href = currentUrl;
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
         * QA 게시판 목록 정렬
         * param : event
         * return : URL 이동
         * 날짜 : 2024-02-20
         * */
        function sort(event){
            // 현재 URL 가져오기
            let currentUrl = window.location.href;

            let sorted = event.target.closest('.board-cell').getAttribute('value');
            let order;

            // 파라미터가 있는지 확인 (indexOf : 값이 있으면 인덱스값, 없으면 -1 반환)
            let sortParamTF = currentUrl.indexOf('sort=');
            let orderParamTF = currentUrl.indexOf('order=');

            // currentUrl 에서 newUrl 로 변경
            let newUrl;
            if(sortParamTF !== -1) {
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
            } else if(sortParamTF === -1) {
                // 파라미터가 없으면 추가
                newUrl = currentUrl + (currentUrl.indexOf('?') !== -1 ? '&' : '?') + 'sort=' + sorted + '&order=desc';
            }

            // 새로운 URL로 이동
            window.location.href = newUrl;
        };
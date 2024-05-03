//모드개발

//비동기
//async 키워드를 사용하여 Promise를 반환하는 비동기함수를 선언
async function get1(bno){ //async -> 비동기 처리함수 명시..
    //await 키워드를 사용하여 Promise를 반환하는 비동기 작업을 기다림
    // axios.get은 HTTP GET 요청을 보내고 이 요청의 결과를 Promise로 반환
    //이 프로미스가 해결 될 때까지 (http 응답이 도착할 때까지) 코드 실행이 일시 중지됨
    const result = await axios.get(`/replies/list/${bno}`)//await -> 비동기 호출

    //console.log(result) //
    //함수는 HTTP 응답 본문을 반환함
    //async 함수이므로 , 이반환 값은 Promise로 감싸져서 반환됨
    return result.data;
}
//goLast는 마지막댓글 확인하기 위해서
 //acending 오름차순

                        //파라미터 요청시 이런값을 사용한다.
                        //key  value / key , value
async  function getList({bno, page, size, goLast}){
    const result =await axios.get(`/replies/list/${bno}`,{params: {page, size}})
    if (goLast){/*<-불린이다*/
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))
        // 자기가 자신을 호출해서 반환함
        return getList({bno:bno, page:lastPage, size:size})
    }
    return result.data;
}

async function addReply(replyObj){
    const response = await axios.post(`/replies/`,replyObj) //rno
    return response.data
}

//p.588

async function getReply(rno){
    const response = await axios.get(`/replies/${rno}`)
    console.log(response)  //
    return response.data
}

async function modifyReply(replyObj){
    //수정된 정보를 전달해준다.
    const  response = await axios.put(`/replies/${replyObj.rno}`, replyObj)
    //보내줄 데이터를 디티오에 집어넣는다 TValue 형태로 데이터값을 넣어주면 된다
    return response.data
}
//p.588 end

//p.593
async  function removeReply(rno){
    const  response = await  axios.delete(`/replies/${rno}`)
    return response.data
}
//p.593 end
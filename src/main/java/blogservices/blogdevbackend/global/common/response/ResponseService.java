package blogservices.blogdevbackend.global.common.response;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {
    // 단일건 결과 메소드
    public <T> SingleResponse<T> getSingResponse(T data) {
        SingleResponse<T> response = new SingleResponse<>();
        response.setData(data);
        setSuccessResponse(response);
        return response;
    }

    // 복수건 결과 메서드
    public <T> ListResponse<T> getListResponse(List<T> list) {
        ListResponse<T> response = new ListResponse<>();
        response.setData(list);
        setSuccessResponse(response);
        return response;
    }

    // 성공 결과 처리
    public CommonResponse getSuccessResponse() {
        CommonResponse response = new CommonResponse();
        setSuccessResponse(response);
        return response;
    }

    // 실패 결과 처리
    public CommonResponse getFailResponse() {
        CommonResponse response = new CommonResponse();
        setFailResponse(response);
        return response;
    }

    // API 요청 성공 시 응답 모델 성공 데이터 세팅
    private void setSuccessResponse(CommonResponse response) {
        response.setSuccess(true);
        response.setCode(CommonStatus.SUCCESS.getCode());
        response.setMessage(CommonStatus.SUCCESS.getMessage());
    }

    // API 요청 실패 시 응답 모델 실패 데이터 세팅
    private void setFailResponse(CommonResponse response) {
        response.setSuccess(false);
        response.setCode(CommonStatus.FAIL.getCode());
        response.setMessage(CommonStatus.FAIL.getMessage());
    }


}

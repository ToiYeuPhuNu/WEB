package project.WebGioiThieuSanPham.exception;


import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import project.WebGioiThieuSanPham.dto.clothesDto.response.ApiBaseResponse;



@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MasterException.class)
    public ResponseEntity<ApiBaseResponse> masterException(MasterException ex, WebRequest request) {
        return new ResponseEntity<>(
                new ApiBaseResponse(ex.exceptionCode.toString(), ex.toString()),
                ex.getExceptionCode()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiBaseResponse globalExceptionHandler(Exception ex, WebRequest request) {
        StringBuilder sb =new StringBuilder();
        for (StackTraceElement e:ex.getStackTrace()) {
            sb.append(e.toString());
        }
        return new ApiBaseResponse(ex.getMessage(), sb.toString());
    }
}

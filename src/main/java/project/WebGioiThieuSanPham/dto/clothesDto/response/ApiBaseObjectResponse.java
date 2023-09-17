package project.WebGioiThieuSanPham.dto.clothesDto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
@With
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiBaseObjectResponse<T> extends ApiBaseResponse {
    protected T data;

    public ApiBaseObjectResponse(String code, String message, T data) {
        super(code, message);
        this.data = data;

    }
}

package project.WebGioiThieuSanPham.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.WebGioiThieuSanPham.enums.TypeSort;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiListBaseRequest{
    protected String orderBy = "serial";
    protected TypeSort orderDirection = TypeSort.ASC;
    protected Integer size = 10;
    protected Integer page = 0;
}

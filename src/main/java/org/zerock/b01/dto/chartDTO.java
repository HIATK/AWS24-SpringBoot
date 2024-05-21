package org.zerock.b01.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class chartDTO {
    private  Long id;

    private String item;
    private  int cell;
}

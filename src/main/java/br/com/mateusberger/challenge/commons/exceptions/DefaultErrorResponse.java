package br.com.mateusberger.challenge.commons.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DefaultErrorResponse {

    private Integer code;

    private String message;

}

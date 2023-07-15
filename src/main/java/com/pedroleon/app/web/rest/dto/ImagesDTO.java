package com.pedroleon.app.web.rest.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagesDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String xs;
    private String sm;
    private String md;
    private String lg;
}

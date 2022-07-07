package com.rashome.rashome.po;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RasberryPiMapping {
    private Long id;

    private Long rasberryPiID;

    private String rasberryPiName;

    private Date createTime;

    private Date updateTime;

    private Long rasberryPiModel;
}
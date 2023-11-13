package com.iot.rashome.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@TableName("device_t")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeviceVo {
    @TableId(type=IdType.AUTO)
    private Long deviceId;
}

package cn.andy.code;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageCode {

    private BufferedImage image;

    private String code;

    private LocalDateTime expireTime;

    public ImageCode(BufferedImage image,String code,int expireIn){
        this.image=image;
        this.code=code;
        this.expireTime=LocalDateTime.now().plusSeconds(expireIn);
    }
}

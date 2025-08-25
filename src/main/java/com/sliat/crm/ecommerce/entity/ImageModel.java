package com.sliat.crm.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "image_model")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fileName;
    private String fileType;
    @Column(length = 50000000)
    private byte[] byteImage;

    public ImageModel(String fileName, String fileType, byte[] byteImage) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.byteImage = byteImage;
    }
}

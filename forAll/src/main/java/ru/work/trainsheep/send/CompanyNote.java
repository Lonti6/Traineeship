package ru.work.trainsheep.send;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

import javax.swing.text.html.ImageView;

@Data
@AllArgsConstructor
public class CompanyNote {
    ImageView companyImage;
    String header;
    String content;

    public CompanyNote(String header, String content, ImageView companyImage) {
        this.header = header;
        this.content = content;
        this.companyImage = companyImage;
    }


    @Override
    public String toString() {
        return "Note{\n" +
                ", \n    header='" + header + '\'' +
                ", \n    content='" + content + '\'' +
                "\n}\n";
    }
}

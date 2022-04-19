package ru.work.trainsheep.send;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class CompanyNote {

    private long id;
    private String header;
    private String content;
    private String companyImage;
    private boolean favorite;
}

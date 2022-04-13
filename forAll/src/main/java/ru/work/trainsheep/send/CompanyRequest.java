package ru.work.trainsheep.send;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {
    int page = 1;
    int countNotesOnPage = 10;
}

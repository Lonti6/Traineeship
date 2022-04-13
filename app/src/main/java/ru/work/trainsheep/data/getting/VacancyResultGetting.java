package ru.work.trainsheep.data.getting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.work.trainsheep.send.VacancyResult;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyResultGetting {
    String status;
    VacancyResult result;
}
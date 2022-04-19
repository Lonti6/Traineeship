package ru.work.trainsheep.data.getting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.work.trainsheep.send.VacancyNote;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateVacancyGetting {
    String status;
    VacancyNote vacancy;
}

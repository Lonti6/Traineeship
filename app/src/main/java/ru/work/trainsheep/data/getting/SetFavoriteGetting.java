package ru.work.trainsheep.data.getting;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.work.trainsheep.send.VacancyNote;

@Data
@NoArgsConstructor
public class SetFavoriteGetting {
    String status;
    VacancyNote vacancy;
}

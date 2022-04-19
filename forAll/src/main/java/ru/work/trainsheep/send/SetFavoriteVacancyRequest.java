package ru.work.trainsheep.send;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetFavoriteVacancyRequest {

    long id;
    boolean favorite = true;

}

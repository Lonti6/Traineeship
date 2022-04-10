package ru.work.trainsheep.data.getting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.work.trainsheep.send.AdvertResult;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertResultGetting {
    String status;
    AdvertResult result;
}


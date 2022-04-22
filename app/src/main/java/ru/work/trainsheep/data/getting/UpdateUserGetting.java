package ru.work.trainsheep.data.getting;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.work.trainsheep.send.UserData;

@Data
@NoArgsConstructor
public class UpdateUserGetting {
    String status;
    UserData user;
}

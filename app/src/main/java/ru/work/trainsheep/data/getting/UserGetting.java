package ru.work.trainsheep.data.getting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.work.trainsheep.data.UserInfo;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGetting {
    String status;
    UserInfo user;
}

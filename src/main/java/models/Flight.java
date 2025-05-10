package models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Flight {
    String macb;
    String gadi;
    String gaden;
    int dodai;
    Time giodi;
    Time gioden;
    int chiphi;

    @Override
    public String toString(){
        return "ChuyenBay {" +
                "macb='" + macb + '\'' +
                ", gadi='" + gadi + '\'' +
                ", gaden='" + gaden + '\'' +
                ", dodai=" + dodai +
                ", giodi=" + (giodi != null ? giodi.toLocalTime() : null) +
                ", gioden=" + (gioden != null ? gioden.toLocalTime() : null) +
                ", chiphi=" + chiphi +
                '}';
    }

}

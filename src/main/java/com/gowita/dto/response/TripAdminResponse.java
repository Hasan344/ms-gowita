package com.gowita.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gowita.constant.Status;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TripAdminResponse {
    Long id;
    Status status;
    String fullName;
    String phoneNumber;
    String rejectMessage;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MMM-yyyy")
    LocalDateTime date;

}

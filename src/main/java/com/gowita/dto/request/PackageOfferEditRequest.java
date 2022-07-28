package com.gowita.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PackageOfferEditRequest {
    @NotNull(message = "Price must be entered")
    BigDecimal price;
    @NotBlank(message = "From where must be selected")
    String fromWhere;
    @NotNull(message = "Pickup date must be entered")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate pickUpDate;
}

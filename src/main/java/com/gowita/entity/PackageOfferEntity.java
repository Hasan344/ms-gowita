package com.gowita.entity;

import com.gowita.constant.PackageOfferStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@Table(name = "package_offer")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PackageOfferEntity extends BaseEntity {
    @ManyToOne
    PackageEntity pack;
    @ManyToOne
    TripEntity trip;
    BigDecimal price;
    String fromWhere;
    LocalDate pickUpDate;
    @Enumerated(EnumType.STRING)
    PackageOfferStatus status;
}

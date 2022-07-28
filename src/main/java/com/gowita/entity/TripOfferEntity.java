package com.gowita.entity;

import com.gowita.constant.TripOfferStatus;
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
@Table(name = "trip_offer")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TripOfferEntity extends BaseEntity {

    @Enumerated(EnumType.STRING)
    TripOfferStatus status;
    @ManyToOne
    PackageEntity pack;
    @ManyToOne
    TripEntity trip;
}

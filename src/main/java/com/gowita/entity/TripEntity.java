package com.gowita.entity;

import com.gowita.constant.Status;
import com.gowita.constant.TripType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trip")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TripEntity extends BaseEntity {

    Double width;
    Double length;
    Double height;
    Double weight;
    LocalDateTime departureDate;
    LocalDateTime arrivalDate;
    LocalDate pickUpDate;
    String rejectMessage;
    boolean hasNotification;
    @Enumerated(EnumType.STRING)
    TripType tripType;
    @Enumerated(EnumType.STRING)
    Status status;
    @ManyToOne
    UserEntity user;
    @ManyToOne
    PackageTypeEntity packageType;
    @ManyToOne
    CityEntity fromWhere;
    @ManyToOne
    CityEntity toWhere;
    @OneToMany(mappedBy = "pack")
    List<PackageOfferEntity> packageOffers;
    @OneToMany(mappedBy = "trip")
    List<TripOfferEntity> tripOffers;
}

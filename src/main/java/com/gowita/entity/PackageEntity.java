package com.gowita.entity;

import com.gowita.constant.Status;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
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
@Table(name = "package")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PackageEntity extends BaseEntity {

    Double width;
    Double length;
    Double height;
    Double weight;
    String rejectMessage;
    LocalDate conductDate;
    LocalDate deliveryDate;
    boolean hasNotification;
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pack")
    List<FilePathEntity> filePaths;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pack")
    List<PackageOfferEntity> packageOffers;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trip")
    List<TripOfferEntity> tripOffers;
}

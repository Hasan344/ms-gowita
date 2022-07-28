package com.gowita.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "city")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    @ManyToOne
    CountryEntity country;

    @OneToMany(mappedBy = "fromWhere")
    List<PackageEntity> fromPackages;
    @OneToMany(mappedBy = "toWhere")
    List<PackageEntity> toPackages;
    @OneToMany(mappedBy = "fromWhere")
    List<TripEntity> fromTrips;
    @OneToMany(mappedBy = "toWhere")
    List<TripEntity> toTrips;
}

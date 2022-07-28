package com.gowita.entity;

import java.util.List;
import javax.persistence.Entity;
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
@Table(name = "user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends BaseEntity {

    String name;
    String surname;
    String password;
    String phoneNumber;
    String additionalNumber;
    boolean status;
    @ManyToOne
    CountryEntity country;

    @OneToMany(mappedBy = "user")
    List<UserTokenEntity> tokenUsers;

    @OneToMany(mappedBy = "user")
    List<PackageEntity> packages;

    @OneToMany(mappedBy = "user")
    List<TripEntity> trips;

}


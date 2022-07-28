package com.gowita.entity;

import java.util.List;
import javax.persistence.Entity;
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
@Table(name = "admin")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminEntity extends BaseEntity {
    String name;
    String surname;
    String phoneNumber;
    String password;
    @OneToMany(mappedBy = "admin")
    List<AdminTokenEntity> tokenAdmins;


}

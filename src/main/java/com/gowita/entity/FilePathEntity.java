package com.gowita.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "file_path")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilePathEntity extends BaseEntity {
    String path;

    @ManyToOne
    PackageEntity pack;

    @Override
    public String toString() {
        return path;
    }
}

package com.service.addressAPI.Domain.Model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
public class DefaultEntity {

    @Column(name = "inserted_at")
    private LocalDateTime insertedAt;



    @PrePersist
    public void prePersist() {
        this.insertedAt = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));
    }

}

package com.accenture.franchise.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("franchises")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Franchise {

    @Id
    private Long id;
    private String name;
}
package com.accenture.franchise.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("branches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch {

    @Id
    private Long id;
    private Long franchiseId;
    private String name;
}
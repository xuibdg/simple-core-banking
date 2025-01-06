package com.b2camp.simple_core_banking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "m_saving", schema = "public")
public class MSaving {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "saving_id", nullable = false)
    private String savingId;

    @Column(name = "saving_code")
    private String savingCode;

    @Column(name = "saving_name")
    private String savingName;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "denom")
    private Integer denom;

    @Column(name = "minimal_balance")
    private Integer minimalBalance;

    @Column(name = "tax")
    private Integer tax;
}

package com.b2camp.simple_core_banking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "m_cif", schema = "public")
public class MCif extends BaseReference {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "cif_id", nullable = false)
    private String cifId;

    @Column(name = "customer_name", nullable = false, length = 100)
    private String customerName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "address",nullable = false, length = 255)
    private String address;

    @Column(name = "phone_number",nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "email",nullable = false, length = 100)
    private String email;

    @Column(name = "id_number",nullable = false, length = 50)
    private String idNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_number_type", referencedColumnName = "type_id")
    private RNumberType rNumberType;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    private RStatus rStatus;

    @Column(name = "authorization_at")
    private Timestamp authorizationAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorization_by", referencedColumnName = "user_id")
    private MUser mUserAuthorizationBy;


    @ManyToOne(fetch = FetchType.LAZY) // Relasi ke MUser
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private MUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "user_id")
    private MUser updatedBy;
}
